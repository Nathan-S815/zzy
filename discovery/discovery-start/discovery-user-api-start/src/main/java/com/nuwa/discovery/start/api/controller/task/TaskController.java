package com.nuwa.discovery.start.api.controller.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.discovery.command.query.MchTaskApplyPageJoinQry;
import com.nuwa.client.discovery.dto.clientobject.task.qry.UserScenicTaskPageQry;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeSubmitEvent;
import com.nuwa.client.discovery.dto.domainevent.UserTaskApplyEvent;
import com.nuwa.discovery.start.api.controller.dto.ScenicTaskDetailVO;
import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import com.nuwa.discovery.start.api.controller.dto.UserTaskPageVO;
import com.nuwa.discovery.start.api.controller.task.param.*;
import com.nuwa.discovery.start.api.controller.vo.CheckAuthResultVO;
import com.nuwa.discovery.start.api.controller.vo.CheckTicketResultVO;
import com.nuwa.discovery.start.api.util.DesensitizedUtils;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.discovery.database.appconfig.entity.AppConfig;
import com.nuwa.infrastructure.discovery.database.appconfig.mapper.AppConfigMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberIntegralLevelMapper;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.param.UserScenicTaskPageParam;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import com.nuwa.infrastructure.discovery.database.user.entity.*;
import com.nuwa.infrastructure.discovery.database.user.mapper.IndustryMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskPrizeRecordMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskApplyJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskPrizeJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.UserTaskJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.service.*;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskApplyPageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizeDetailVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizePageVO;
import com.nuwa.infrastructure.discovery.enums.AuditStatusEnum;
import com.nuwa.infrastructure.discovery.enums.ScenicTaskStatusEnum;
import com.nuwa.infrastructure.discovery.enums.SwitchEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/user/task")
@Api(tags = {"任务中心相关"})
public class TaskController {

    @Autowired
    private TaskPrizeService taskPrizeService;

    @Autowired
    private ScenicTaskService scenicTaskService;

    @Autowired
    private TaskPrizeTypeService taskPrizeTypeService;

    @Autowired
    private MemberTaskApplyService memberTaskApplyService;

    @Autowired
    private MemberTaskPrizeService memberTaskPrizeService;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @Autowired
    private UserTaskApplyJoinMapper userTaskApplyJoinMapper;

    @Autowired
    private UserTaskPrizeJoinMapper userTaskPrizeJoinMapper;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private MemberTagBindService memberTagBindService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberTaskPrizeRecordMapper memberTaskPrizeRecordMapper;

    @Autowired
    private MemberIntegralLevelMapper memberIntegralLevelMapper;

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private IndustryService industryService;

    @Autowired
    private MemberIntegralRecordService memberIntegralRecordService;

    @Autowired
    private IndustryMapper industryMapper;

    private final Pattern compile = Pattern.compile("\\[(.*?)]");

    @ApiOperation(value = "绑定账号并报名(taskId不传代表只做绑定账号)")
    @RequestMapping(value = "/bindAccountAndApply", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> bindAccountAndApply(@RequestBody @Valid BindAccountAndApplyTaskParam form, UserAware userAware) {
        MemberAccountBind memberAccountBindOne = memberAccountBindService.lambdaQuery()
                .eq(MemberAccountBind::getUserId, userAware.getUserId())
                .eq(MemberAccountBind::getChannelCode, form.getPlatCode())
                .one();
        MemberAccountBind memberAccountBind = new MemberAccountBind();
        if (Objects.nonNull(memberAccountBindOne)) {
            memberAccountBind.setId(memberAccountBindOne.getId());
            memberAccountBind.setStatus(memberAccountBindOne.getStatus());
        }else{
            memberAccountBind.setStatus(0);
        }
        memberAccountBind.setPictures(form.getPictures());
        memberAccountBind.setChannelCode(form.getPlatCode());
//        memberAccountBind.setInviteCode(form.getInviteCode());
        memberAccountBind.setCreateTime(new Date());
        memberAccountBind.setUserId(userAware.getUserId().intValue());
//        memberAccountBind.setExpiresTime(DateUtil.offsetDay(new Date(), 356));
//        memberAccountBind.setAccountId(form.getAccountId());
        memberAccountBind.setWeixinId(form.getWeixinId());
//        memberAccountBind.setFansCount(form.getFansCount());
//        memberAccountBind.setNick(form.getNick());
//        memberAccountBind.setLevel(form.getLevel());
//        memberAccountBind.setRegion(form.getRegion());
//        memberAccountBind.setSex(form.getSex());
        memberAccountBind.setUId(form.getUid());
//        memberAccountBind.setContent(form.getContent());
//        memberAccountBind.setBirthday(form.getBirthday());
//        memberAccountBind.setThirdPartyTag(form.getThirdPartyTag());



        if (Objects.nonNull(form.getTaskId())) {
            boolean insert = memberAccountBind.insertOrUpdate();
            return applyTask(form.getTaskId(), userAware);
        }

        //将绑定记录主体状态设置为需要重新认证
        memberAccountBind.setRecertificationStatus(2);
        boolean insert = memberAccountBind.insertOrUpdate();
        MemberAccountBindRecord memberAccountBindRecord = new MemberAccountBindRecord();
        memberAccountBindRecord.setMemberAccountBindId(memberAccountBind.getId());
        memberAccountBindRecord.setUserId(userAware.getUserId().intValue());
        memberAccountBindRecord.setAccountId(form.getAccountId());
        memberAccountBindRecord.setChannelCode(form.getPlatCode());
        memberAccountBindRecord.setNick(form.getNick());
        memberAccountBindRecord.setFansCount(form.getFansCount());
        memberAccountBindRecord.setSex(form.getSex());
        memberAccountBindRecord.setBirthday(form.getBirthday());
        memberAccountBindRecord.setRegion(form.getRegion());
        memberAccountBindRecord.setLevel(form.getLevel());
        memberAccountBindRecord.setInviteCode(form.getInviteCode());
        memberAccountBindRecord.setStatus(0);
        memberAccountBindRecord.setExpiresTime(DateUtil.offsetDay(new Date(), 356));
        memberAccountBindRecord.setContent(form.getContent());
        memberAccountBindRecord.setPictures(form.getPictures());
        memberAccountBindRecord.setThirdPartyTag(form.getThirdPartyTag());
        memberAccountBindRecord.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "单独报名")
    @RequestMapping(value = "/{taskId}/apply", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> doApplyTask(@PathVariable("taskId") Long taskId, UserAware userAware) {
        return applyTask(taskId, userAware);
    }

    private SingleResponse<?> applyTask(Long taskId, UserAware userAware) {
        ScenicTask task = scenicTaskService.getById(taskId);
        Long limitApplyMax = task.getLimitApplyMax();
        if (task.getApplyTotal() + 1 > limitApplyMax) {
            return SingleResponse.buildFailure("1010", "报名人数已经超限");
        }
        Member member = memberService.getById(userAware.getUserId());
        if (member.getUserLevelId() < task.getLimitLevel()) {
            return SingleResponse.buildFailure("1011", "达人等级不符");
        }
        QueryWrapper<MemberAccountBind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",member.getUserId());
        MemberAccountBind memberAccountBind = memberAccountBindService.getOne(queryWrapper);
        if(memberAccountBind == null || memberAccountBind.getStatus() != 1){
            return SingleResponse.buildFailure("1012", "未通过达人认证");
        }
        if (task.getStatus().equals(2)) {
            MemberTaskApply oldTaskApply = memberTaskApplyService.lambdaQuery()
                    .eq(MemberTaskApply::getTaskId, taskId)
                    .eq(MemberTaskApply::getUserId, userAware.getUserId())
                    .one();
            if (Objects.isNull(oldTaskApply)) {
                MemberTaskApply taskApply = new MemberTaskApply();
                taskApply.setTaskId(taskId);
                taskApply.setCreateTime(new Date());
                taskApply.setCreateById(userAware.getUserId() + "");
                taskApply.setCreateByName(userAware.getUserName());
                taskApply.setUserId(userAware.getUserId());
                //获取用户标签
                List<MemberTag> memberTagByMemberId = memberTagBindService.getMemberTagByMemberId(userAware.getUserId());
                StringBuilder tagName = new StringBuilder();
                StringBuilder tagId = new StringBuilder();
                for (int i = 0; i < memberTagByMemberId.size(); i++) {
                    if(i != 0){
                        tagName.append(",");
                        tagId.append(",");
                    }
                    tagName.append(memberTagByMemberId.get(i).getName());
                    tagId.append(memberTagByMemberId.get(i).getId());
                }
                taskApply.setMemberTag(tagName.toString());
                taskApply.setMemberTagId(tagId.toString());
                //获取用户数据
                MemberVO memberVOByUserId = memberService.getMemberVOByUserId(userAware.getUserId());
                taskApply.setSex(memberVOByUserId.getSex());
                taskApply.setRegion(memberVOByUserId.getRegion());
                taskApply.setUserLevelId(memberVOByUserId.getUserLevelId());

                boolean taskApplyFlag = taskApply.insert();
                if (taskApplyFlag) {
                    scenicTaskService.incrementUpdate(ScenicTask::getApplyTotal, 1).eq(ScenicTask::getId, task.getId()).update();

                    //重新计算报名比例
                    ScenicTask scenicTask = new ScenicTask();
                    scenicTask.setId(task.getId());
                    Long applyTotal = task.getApplyTotal() + 1L;
                    scenicTask.setApplyProportion(Convert.toDouble(NumberUtil.div(applyTotal, task.getLimitApplyMax(), 4)) * 100);
                    scenicTaskService.updateById(scenicTask);

                    List<TaskPrize> taskPrizeList = taskPrizeService.lambdaQuery().eq(TaskPrize::getTaskId, task.getId()).list();
                    List<TaskPrize> ticketPrize = taskPrizeList.stream().filter(x -> x.getPrizeType().equals(11)).collect(Collectors.toList());
                    if (!ticketPrize.isEmpty()) {
                        TaskPrize taskPrize = ticketPrize.get(0);
                        MemberTaskPrize memberTicketTaskPrize = new MemberTaskPrize();
                        memberTicketTaskPrize.setTaskId(task.getId());
                        memberTicketTaskPrize.setUserId(userAware.getUserId());
                        memberTicketTaskPrize.setCreateTime(new Date());
                        memberTicketTaskPrize.setCreateByName(userAware.getUserName());
                        memberTicketTaskPrize.setPrizeTypeId(taskPrize.getPrizeType() + "");
                        memberTicketTaskPrize.setName(taskPrize.getPrizeTitle());
                        memberTicketTaskPrize.setStatus(1);
                        memberTicketTaskPrize.setPlatformCode(taskPrize.getPlatformCode());
                        memberTicketTaskPrize.setTaskPrizeId(taskPrize.getId());
                        memberTicketTaskPrize.insert();
                    }
                    List<TaskPrize> otherPrize = taskPrizeList.stream().filter(x -> !x.getPrizeType().equals(11)).collect(Collectors.toList());
                    List<MemberTaskPrize> memberTaskPrizeList = otherPrize.stream().map(x -> {
                        MemberTaskPrize memberTaskPrize = new MemberTaskPrize();
                        memberTaskPrize.setTaskId(task.getId());
                        memberTaskPrize.setUserId(userAware.getUserId());
                        memberTaskPrize.setCreateTime(new Date());
                        memberTaskPrize.setCreateByName(userAware.getUserName());
                        memberTaskPrize.setPrizeTypeId(x.getPrizeType() + "");
                        memberTaskPrize.setName(x.getPrizeTitle());
                        memberTaskPrize.setStatus(1);
                        memberTaskPrize.setPlatformCode(x.getPlatformCode());
                        memberTaskPrize.setTaskPrizeId(x.getId());
                        return memberTaskPrize;
                    }).collect(Collectors.toList());
                    memberTaskPrizeService.saveBatch(memberTaskPrizeList);

                    UserTaskApplyEvent event = new UserTaskApplyEvent(taskId,taskApply.getId());
                    try {
                        domainEventPublisher.publishEvent(event);
                    } catch (Exception ex) {
                        log.error("publishEvent [{}] error", event);
                    }
                }
            }
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "门票权益认领")
    @RequestMapping(value = "/{taskPrizeId}/submitTicketTask", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> submitTicketTask(@PathVariable("taskPrizeId") Long taskPrizeId, @RequestBody @Valid TicketPrizeTaskSubmitParam form, UserAware userAware) {
        log.info("param:{}", JSONUtil.toJsonPrettyStr(form));
//        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(taskPrizeId);
        QueryWrapper<MemberTaskPrize> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_prize_id",taskPrizeId)
                .eq("user_id",userAware.getUserId());
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getOne(queryWrapper);
        String prizeTypeId = memberTaskPrize.getPrizeTypeId();
        if (!"11".equalsIgnoreCase(prizeTypeId)) {
            return SingleResponse.buildFailure("9036", "权益类型错误");
        }

        if (memberTaskPrize.getStatus().equals(2) || memberTaskPrize.getStatus().equals(3)) {
            return SingleResponse.buildFailure("9036", "认领失败，权益已被认领过.");
        }

        TaskPrize taskPrize = taskPrizeService.getById(memberTaskPrize.getTaskPrizeId());

        ScenicTask scenicTask = scenicTaskService.getById(taskPrize.getTaskId());

        memberTaskPrize.setExtData(JSONUtil.toJsonStr(form));
        memberTaskPrize.setIntroduceText(form.getIntroduceText());
        memberTaskPrize.setLastUpdateById(userAware.getUserId() + "");
        memberTaskPrize.setLastUpdateByName(userAware.getUserName());
        memberTaskPrize.setLastUpdateTime(new Date());
        memberTaskPrize.setStatus(2);
        memberTaskPrize.setSubmitTime(new Date());
        boolean ret = memberTaskPrize.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "权益认领失败");
        }
        MemberTaskPrizeRecord prizeRecord = new MemberTaskPrizeRecord();
        prizeRecord.setExtData(JSONUtil.toJsonStr(form));
        prizeRecord.setIntroduceText(form.getIntroduceText());
        prizeRecord.setStatus(2);
        prizeRecord.setSubmitTime(new Date());
        prizeRecord.setTaskId(scenicTask.getId());
        prizeRecord.setTaskPrizeId(taskPrize.getId());
        prizeRecord.setCreateTime(new Date());
        prizeRecord.setMemberTaskPrizeId(memberTaskPrize.getId());
        prizeRecord.setName(scenicTask.getName());
        prizeRecord.setUserId(userAware.getUserId());
        prizeRecord.setPrizeTitle(taskPrize.getPrizeTitle());
        prizeRecord.setPrizeTypeId(prizeTypeId);
        prizeRecord.setPlatformCode(memberTaskPrize.getPlatformCode());
        QueryWrapper<MemberTaskPrizeRecord> memberTaskPrizeRecordQueryWrapper = new QueryWrapper<>();
        memberTaskPrizeRecordQueryWrapper.eq("member_task_prize_id",prizeRecord.getMemberTaskPrizeId());
        MemberTaskPrizeRecord memberTaskPrizeRecord = memberTaskPrizeRecordMapper.selectOne(memberTaskPrizeRecordQueryWrapper);
        if(memberTaskPrizeRecord != null){
            prizeRecord.setId(memberTaskPrizeRecord.getId());
            prizeRecord.updateById();
        }else{
            prizeRecord.insert();
        }

        UserPrizeSubmitEvent event = new UserPrizeSubmitEvent(memberTaskPrize.getId());
        try {
            domainEventPublisher.publishEvent(event);
        } catch (Exception ex) {
            log.error("publishEvent [{}] error", event);
        }

        return SingleResponse.of("权益认领成功");
    }

    @ApiOperation(value = "权益认领")
    @RequestMapping(value = "/{taskPrizeId}/submitTask", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> submitTask(@PathVariable("taskPrizeId") Long taskPrizeId, @RequestBody @Valid OtherPrizeTaskSubmitParam form, UserAware userAware) {
//        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(taskPrizeId);
        QueryWrapper<MemberTaskPrize> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_prize_id",taskPrizeId)
                .eq("user_id",userAware.getUserId());
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getOne(queryWrapper);
        String prizeTypeId = memberTaskPrize.getPrizeTypeId();

        if (memberTaskPrize.getStatus().equals(2) || memberTaskPrize.getStatus().equals(3)) {
            return SingleResponse.buildFailure("9036", "认领失败，权益已被认领过.");
        }

        TaskPrize taskPrize = taskPrizeService.getById(memberTaskPrize.getTaskPrizeId());
        ScenicTask scenicTask = scenicTaskService.getById(taskPrize.getTaskId());

        memberTaskPrize.setPictures(form.getPictures());
        memberTaskPrize.setIntroduceText(form.getContent());
        memberTaskPrize.setLastUpdateById(userAware.getUserId() + "");
        memberTaskPrize.setLastUpdateByName(userAware.getUserName());
        memberTaskPrize.setLastUpdateTime(new Date());
        memberTaskPrize.setSubmitTime(new Date());
        memberTaskPrize.setStatus(2);
        boolean ret = memberTaskPrize.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "权益认领失败");
        }
        MemberTaskPrizeRecord prizeRecord = new MemberTaskPrizeRecord();
        prizeRecord.setPictures(form.getPictures());
        prizeRecord.setExtData(JSONUtil.toJsonStr(form));
        prizeRecord.setStatus(2);
        prizeRecord.setSubmitTime(new Date());
        prizeRecord.setTaskId(scenicTask.getId());
        prizeRecord.setTaskPrizeId(taskPrize.getId());
        prizeRecord.setCreateTime(new Date());
        prizeRecord.setMemberTaskPrizeId(memberTaskPrize.getId());
        prizeRecord.setName(scenicTask.getName());
        prizeRecord.setUserId(userAware.getUserId());
        prizeRecord.setPrizeTitle(taskPrize.getPrizeTitle());
        prizeRecord.setPrizeTypeId(prizeTypeId);
        prizeRecord.setPlatformCode(memberTaskPrize.getPlatformCode());
        QueryWrapper<MemberTaskPrizeRecord> memberTaskPrizeRecordQueryWrapper = new QueryWrapper<>();
        memberTaskPrizeRecordQueryWrapper.eq("member_task_prize_id",prizeRecord.getMemberTaskPrizeId());
        MemberTaskPrizeRecord memberTaskPrizeRecord = memberTaskPrizeRecordMapper.selectOne(memberTaskPrizeRecordQueryWrapper);
        if(memberTaskPrizeRecord != null){
            prizeRecord.setId(memberTaskPrizeRecord.getId());
            prizeRecord.updateById();
        }else{
            prizeRecord.insert();
        }

        UserPrizeSubmitEvent event = new UserPrizeSubmitEvent(memberTaskPrize.getId());
        try {
            domainEventPublisher.publishEvent(event);
        } catch (Exception ex) {
            log.error("publishEvent [{}] error", event);
        }
        return SingleResponse.of("权益认领成功");
    }

    @ApiOperation(value = "检测用户平台用户认证状态")
    @RequestMapping(value = "/checkUserPlatAuthStatus", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<CheckAuthResultVO> checkUserPlatAuthStatus(@Valid CheckUserAccountAuthStatusParam form, UserAware userAware) {
        MemberAccountBind memberAccountBindOne = memberAccountBindService.lambdaQuery()
                .eq(MemberAccountBind::getUserId, userAware.getUserId())
                .eq(MemberAccountBind::getChannelCode, form.getPlatCode())
                .one();
        CheckAuthResultVO checkAuthResultVO = new CheckAuthResultVO();
        checkAuthResultVO.setAuthFlag(Objects.nonNull(memberAccountBindOne));
        checkAuthResultVO.setAuthMsg("");
        return SingleResponse.of(checkAuthResultVO);
    }


    @ApiOperation(value = "获取绑定账号列表")
    @RequestMapping(value = "/getAccountList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MemberAccountBind>> getAccountList(@Valid CheckUserAccountAuthStatusParam form, UserAware userAware) {
        List<MemberAccountBind> list = memberAccountBindService.lambdaQuery().eq(MemberAccountBind::getUserId, userAware.getUserId()).list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "检测用户购票任务状态")
    @RequestMapping(value = "/checkUserTicketStatus", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<CheckTicketResultVO> checkUserTicketStatus(@Valid CheckUserTicketPrizeStatusParam form, UserAware userAware) {
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.lambdaQuery()
                .eq(MemberTaskPrize::getUserId, userAware.getUserId())
                .eq(MemberTaskPrize::getPrizeTypeId, 11)
                .eq(MemberTaskPrize::getStatus, 1)
                .eq(MemberTaskPrize::getTaskId, form.getTaskId())
                .one();

        CheckTicketResultVO checkAuthResultVO = new CheckTicketResultVO();
        checkAuthResultVO.setStatus(Objects.isNull(memberTaskPrize));
        checkAuthResultVO.setMsg(Objects.isNull(memberTaskPrize) ? "已认领" : "未认领");
        if (Objects.nonNull(memberTaskPrize)) {
            checkAuthResultVO.setPrizeId(memberTaskPrize.getId());
        }
        return SingleResponse.of(checkAuthResultVO);
    }

    @ApiOperation(value = "获取任务详情")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicTaskDetailVO> getDetailById(@Valid GetTaskDetailById form, UserAware userAware) {
        ScenicTaskDetailVO vo = new ScenicTaskDetailVO();
        ScenicTask scenicTask = scenicTaskService.getById(form.getId());
        BeanUtils.copyProperties(scenicTask, vo);
        List<TaskPrizeDTO> taskPrizeList = taskPrizeService.lambdaQuery()
                .eq(TaskPrize::getTaskId, form.getId())
                .list().stream().map(x -> new TaskPrizeDTO(x.getPlatformCode(), x.getPrizeType(), x.getPrizeTitle() , x.getPrizeContent(), x.getId(), null))
                .collect(Collectors.toList());
        QueryWrapper<MemberTaskPrize> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",userAware.getUserId())
                .eq("task_id",form.getId());
        List<MemberTaskPrize> memberTaskPrizeList = memberTaskPrizeService.list(queryWrapper1);
        if (scenicTask.getIndustryCode() !=null){
            QueryWrapper<Industry> industryQueryWrapper = new QueryWrapper<>();
            industryQueryWrapper.in("industry_code", Arrays.asList(scenicTask.getIndustryCode().split(",")));
            List<Industry> industryList = industryMapper.selectList(industryQueryWrapper);
            vo.setIndustryName(industryList.stream().map(Industry::getIndustryName).collect(Collectors.toList()));
        }
        //判断用户是否报名 根据是否报名来判断是否需要显示权益状态
        if (CollUtil.isNotEmpty(memberTaskPrizeList)) {
            Map<Long, Integer> map = memberTaskPrizeList.stream().collect(Collectors.toMap(MemberTaskPrize::getTaskPrizeId, MemberTaskPrize::getStatus, (key1, key2) -> key2));
            for (TaskPrizeDTO taskPrizeDTO : taskPrizeList) {
                taskPrizeDTO.setStatus(map.get(taskPrizeDTO.getPrizeId()));
            }
        }
        vo.setTaskPrizeItems(taskPrizeList);
        if (StrUtil.isNotBlank(scenicTask.getOtherRemarkList())) {
            JSONArray otherRemarkList = JSONUtil.parseArray(scenicTask.getOtherRemarkList());
            vo.setOtherRemarkList(otherRemarkList.toList(String.class));
        }
        if (StrUtil.isNotBlank(scenicTask.getSpecialTipList())) {
            JSONArray specialTipList = JSONUtil.parseArray(scenicTask.getSpecialTipList());
            vo.setSpecialTipList(specialTipList.toList(String.class));
        }
        Integer count = memberTaskApplyService.lambdaQuery()
                .eq(MemberTaskApply::getUserId, userAware.getUserId())
                .eq(MemberTaskApply::getTaskId, form.getId())
                .count();
        vo.setEnabledApply(count > 0);

        QueryWrapper<ScenicTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lon", vo.getLon()).eq("lat", vo.getLat()).eq("status", 2);
        int taskCount = scenicTaskService.count(queryWrapper);
        //除去该任务
        vo.setTaskCount(taskCount - 1);

        QueryWrapper<MemberIntegralLevel> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("level", vo.getLimitLevel());
        MemberIntegralLevel memberIntegralLevel = memberIntegralLevelMapper.selectOne(queryWrapper2);
        if(memberIntegralLevel != null){
            vo.setLevelName(memberIntegralLevel.getLevelName());
        }


        MemberTaskApply memberTaskApply = memberTaskApplyService.lambdaQuery()
                .eq(MemberTaskApply::getUserId, userAware.getUserId())
                .eq(MemberTaskApply::getTaskId, form.getId()).one();
        if(memberTaskApply != null){
            vo.setVideo(memberTaskApply.getVideo());
        }
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "获取我的任务权益列表")
    @RequestMapping(value = "/list/taskPrize/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> listTaskPrize(@PathVariable("taskId") Long taskId, UserAware userAware) {
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setLimit(100);
        queryPage.setTaskId(taskId);
        queryPage.setUserId(userAware.getUserId());
        queryPage.setShowWaitStatus(true);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData.getRecords());
    }

    @ApiOperation(value = "获取我的任务权益记录")
    @RequestMapping(value = "/list/taskPrizeRecord/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> listTaskPrizeRecord(@PathVariable("taskId") Long taskId, UserAware userAware) {
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setLimit(100);
        queryPage.setTaskId(taskId);
        queryPage.setUserId(userAware.getUserId());
        queryPage.setShowWaitStatus(false);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData.getRecords());
    }

    @ApiOperation(value = "获取任务权益详情")
    @RequestMapping(value = "/get/taskPrize/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserTaskPrizeDetailVO> taskPrizeDetail(@PathVariable("id") Long id, UserAware userAware) {
        UserTaskPrizeDetailVO vo = new UserTaskPrizeDetailVO();
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setLimit(1);
        queryPage.setUserPrizeId(id);
        queryPage.setShowWaitStatus(true);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);

        UserTaskPrizePageVO userTaskPrizePageVO = pageData.getRecords().get(0);
        BeanUtils.copyProperties(userTaskPrizePageVO, vo);
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(id);
        vo.setPictures(memberTaskPrize.getPictures());
        vo.setExtData(memberTaskPrize.getExtData());
        vo.setRemarkText(memberTaskPrize.getRemarkText());
        vo.setIntroduceText(memberTaskPrize.getIntroduceText());
        vo.setRemarkPictures(memberTaskPrize.getRemarkPictures());
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "任务中心分页")
    @GetMapping("/page/{appId}")
    @ResponseBody
    public SingleResponse<IPage<UserTaskPageVO>> getByPage(@PathVariable("appId") String appId, UserScenicTaskPageQry qry) {
        QueryWrapper<AppConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id",appId);
        AppConfig appConfig = appConfigMapper.selectOne(queryWrapper);
        if(appConfig == null){
            return SingleResponse.buildFailure("999","appId对应记录不存在");

        }
        qry.setMchId(appConfig.getMchId());
        qry.setStatus(2);
        IPage<UserTaskPageVO> scenicTaskPage = scenicTaskService.paginateAndConvert(new UserScenicTaskPageParam(qry), this::toVO);
        Map<Long, String> mapTypeData = taskPrizeTypeService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(TaskPrizeType::getId, TaskPrizeType::getPrizeTypeName));

        Map<Integer, String> levelMap = getLevelMap();
        scenicTaskPage.getRecords().stream().filter(x -> StrUtil.isNotBlank(x.getPrizeTypeTag())).forEach(x -> {
            List<String> newTypeNames = ReUtil.findAllGroup1(compile, x.getPrizeTypeTag())
                    .stream().map(Long::valueOf)
                    .map(mapTypeData::get)
                    .collect(Collectors.toList());
            x.setPrizeTypeList(newTypeNames);
            x.setLevelName(levelMap.get(x.getLimitLevel()));
        });
        return SingleResponse.of(scenicTaskPage);
    }

    @ApiOperation(value = "任务广场列表")
    @GetMapping("/index/list")
    @ResponseBody
    public SingleResponse<List<UserTaskPageVO>> taskIndexList() {
        UserScenicTaskPageQry qry = new UserScenicTaskPageQry();
        qry.setIndexRecommend(1);
        qry.setStatus(2);
        IPage<UserTaskPageVO> scenicTaskPage = scenicTaskService.paginateAndConvert(new UserScenicTaskPageParam(qry), this::toVO);
        Map<Long, String> mapTypeData = taskPrizeTypeService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(TaskPrizeType::getId, TaskPrizeType::getPrizeTypeName));
        Map<Integer, String> levelMap = getLevelMap();
        scenicTaskPage.getRecords().stream().filter(x -> StrUtil.isNotBlank(x.getPrizeTypeTag())).forEach(x -> {
            List<String> newTypeNames = ReUtil.findAllGroup1(compile, x.getPrizeTypeTag())
                    .stream().map(Long::valueOf)
                    .map(mapTypeData::get)
                    .collect(Collectors.toList());
            x.setPrizeTypeList(newTypeNames);
            x.setLevelName(levelMap.get(x.getLimitLevel()));
        });
        return SingleResponse.of(scenicTaskPage.getRecords());
    }

    @ApiOperation(value = "任务报名记录分页")
    @RequestMapping(value = "/{taskId}/applyPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserTaskApplyPageVO>> taskApplyPage(@PathVariable("taskId") Long taskId, MchTaskApplyPageJoinQry query) {
        UserTaskJoinPageQuery queryPage = new UserTaskJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setTaskId(taskId);
        Page<UserTaskApplyPageVO> pageData = userTaskApplyJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            x.setNick(DesensitizedUtils.desValue(x.getNick(), 1, 1, "*"));
            x.setUserNick(DesensitizedUtils.desValue(x.getUserNick(), 1, 1, "*"));
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "任务前10条报名记录")
    @RequestMapping(value = "/{taskId}/topApplyList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<UserTaskApplyPageVO>> top10(@PathVariable("taskId") Long taskId, MchTaskApplyPageJoinQry query) {
        UserTaskJoinPageQuery queryPage = new UserTaskJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setTaskId(taskId);
        Page<UserTaskApplyPageVO> pageData = userTaskApplyJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            x.setNick(DesensitizedUtils.desValue(x.getNick(), 1, 1, "*"));
            x.setUserNick(DesensitizedUtils.desValue(x.getUserNick(), 1, 1, "*"));
        });
        return SingleResponse.of(pageData.getRecords());
    }

    @ApiOperation(value = "更新任务记录视频")
    @RequestMapping(value = "/apply/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> applyUpdate(@RequestBody MemberTaskApply memberTaskApply, UserAware userAware) {
        MemberTaskApply memberTaskApply1 = new MemberTaskApply();
        QueryWrapper<MemberTaskApply> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("user_id",userAware.getUserId()).eq("task_id",memberTaskApply.getTaskId());
        memberTaskApply1.setVideo(memberTaskApply.getVideo());
        memberTaskApplyService.update(memberTaskApply1,updateWrapper);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "根据任务id获取达人报名情况")
    @RequestMapping(value = "/member/count/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getMemberCount(@PathVariable("taskId") Integer taskId) {
        QueryWrapper<MemberTaskApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id",taskId);
        List<MemberTaskApply> list = memberTaskApplyService.list(queryWrapper);
        //暂时写死
        Map<Integer, Integer>memberCountMap = new HashMap<>();
        memberCountMap.put(1,0);
        memberCountMap.put(2,0);
        memberCountMap.put(3,0);
        for (MemberTaskApply memberTaskApply : list) {
            memberCountMap.put(memberTaskApply.getUserLevelId(),memberCountMap.get(memberTaskApply.getUserLevelId()) + 1);
        }
        return SingleResponse.of(memberCountMap);
    }


    @ApiOperation(value = "获取平台任务总数")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getTaskCount() {
        int count = scenicTaskService.count();
        return SingleResponse.of(count);
    }

    private UserTaskPageVO toVO(ScenicTask task) {
        UserTaskPageVO vo = new UserTaskPageVO();
        BeanUtils.copyProperties(task, vo);
        return vo;
    }

    private Map<Integer, String> getLevelMap(){
        Map<Integer, String> levelMap = new HashMap<>();
        List<MemberIntegralLevel> memberIntegralLevels = memberIntegralLevelMapper.selectList(new QueryWrapper<>());
        levelMap = memberIntegralLevels.stream().collect(Collectors.toMap(MemberIntegralLevel :: getLevel, MemberIntegralLevel::getLevelName,(key1, key2) -> key2));
        return levelMap;
    }


    @ApiOperation(value = "新增任务")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody @Valid SaveTaskParam form, UserAware userAware) {
        ScenicTask task = new ScenicTask();
        List<TaskPrizeDTO> taskPrizes = form.getTaskPrizes();
        Map<Integer, List<TaskPrizeDTO>> prizeTypeMap = taskPrizes.stream().collect(Collectors.groupingBy(TaskPrizeDTO::getPrizeType));
        String typeIdTags = prizeTypeMap.keySet().stream().map(x -> "[" + x + "]").collect(Collectors.joining(""));

        if (Objects.nonNull(form.getTaskPrizes())) {
            long count = form.getTaskPrizes().stream().filter(x -> x.getPrizeType().equals(11)).count();
            if (count > 1) {
                return SingleResponse.buildFailure("901", "探店免票权益只能1个！");
            }
        }
        BeanUtils.copyProperties(form, task);
        task.setCreateById(String.valueOf(userAware.getUserId()));
        task.setIndustryCode((StringUtils.join(form.getIndustryCodeList(), ",")));
        task.setCreateTime(new Date());
        task.setCreateByName(userAware.getUserName());
        task.setMchId(userAware.getMchId() + "");
        task.setAuditStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
        task.setStatus(ScenicTaskStatusEnum.WAIT.getCode());
        task.setPrizeTypeTag(typeIdTags);
        if (Objects.nonNull(form.getOtherRemarkList()) && form.getOtherRemarkList().size() > 0) {
            task.setOtherRemarkList(JSONUtil.toJsonPrettyStr(form.getOtherRemarkList()));
        }
        if (Objects.nonNull(form.getSpecialTipList()) && form.getSpecialTipList().size() > 0) {
            task.setSpecialTipList(JSONUtil.toJsonPrettyStr(form.getSpecialTipList()));
        }
        boolean insert = task.insert();
        if (!insert) {
            log.error("save ScenicTask failed.");
            return SingleResponse.buildFailure("901", "新增任务失败！");
        }
        log.info("save ScenicTask[id:{}] success.", task.getId());

        taskPrizeService.remove(Wrappers.<TaskPrize>lambdaQuery().eq(TaskPrize::getTaskId, task.getId()));
        if (taskPrizes.size() > 0) {
            List<TaskPrize> batchTaskPrizeItems = taskPrizes.stream().map(x -> {
                TaskPrize taskPrize = new TaskPrize();
                BeanUtils.copyProperties(x, taskPrize);
                taskPrize.setTaskId(task.getId());
                taskPrize.setCreateTime(new Date());
                taskPrize.setStatus(SwitchEnum.ON.getCode());
                return taskPrize;
            }).collect(Collectors.toList());
            boolean b = taskPrizeService.saveBatch(batchTaskPrizeItems);
            if (!b) {
                log.error("save TaskPrize failed.");
                return SingleResponse.buildFailure("902", "新增任务失败！");
            }
        }
        log.info("save TaskPrize[id:{}] success.", task.getId());
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "获取任务类型列表")
    @RequestMapping(value = "/listIndustry", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<Industry>> listTaskPrizeList() {
        List<Industry> list = industryService.lambdaQuery().list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "获取达人等级分页数据")
    @GetMapping(value = "/page")
    @ResponseBody
    public SingleResponse<?> getMemberIntegralLevelPage(Long pageSize, Long pageNum) {
        IPage<MemberIntegralLevel> memberIntegralLevelPage = memberIntegralRecordService.getMemberIntegralLevelPage(pageSize, pageNum);
        return SingleResponse.of(memberIntegralLevelPage);
    }
}
