package com.nuwa.discovery.start.api.controller.task;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.discovery.dto.clientobject.task.qry.ScenicTaskPageQry;
import com.nuwa.discovery.start.api.controller.dto.ScenicTaskDetailVO;
import com.nuwa.discovery.start.api.controller.dto.ScenicTaskPageVO;
import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import com.nuwa.discovery.start.api.controller.task.param.*;
import com.nuwa.discovery.start.api.controller.task.vo.TaskMessageSubscribeVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberIntegralLevelMapper;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsTemplateService;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskMessageSubscribe;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.param.ScenicTaskPageParam;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskMessageSubscribeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.mapper.IndustryMapper;
import com.nuwa.infrastructure.discovery.database.user.service.IndustryService;
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
@RequestMapping("task")
@Api(tags = {"任务管理相关"})
public class ScenicspotTaskController {

    @Autowired
    private TaskPrizeService taskPrizeService;

    @Autowired
    private ScenicTaskService scenicTaskService;

    @Autowired
    private TaskPrizeTypeService taskPrizeTypeService;

    @Autowired
    private TaskMessageSubscribeService messageSubscribeService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private MemberIntegralLevelMapper memberIntegralLevelMapper;

    @Autowired
    private IndustryMapper industryMapper;

    @Autowired
    private IndustryService industryService;

    private final Pattern compile = Pattern.compile("\\[(.*?)]");

    @ApiOperation(value = "新增")
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
        task.setCreateById(userAware.getMchUserId() + "");
        task.setIndustryCode((StringUtils.join(form.getIndustryCodeList(), ",")));
        task.setCreateTime(new Date());
        task.setCreateByName(userAware.getUserName());
        task.setMchId(userAware.getMchId() + "");
        task.setAuditStatus(AuditStatusEnum.AUDIT_PASS.getCode());
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

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid SaveTaskParam form, UserAware userAware) {
        if (Objects.isNull(form.getId())) {
            return SingleResponse.buildFailure("901", "任务id不能为空！");
        }

        if (Objects.nonNull(form.getTaskPrizes())) {
            long count = form.getTaskPrizes().stream().filter(x -> x.getPrizeType().equals(11)).count();
            if (count > 1) {
                return SingleResponse.buildFailure("901", "探店免票权益只能1个！");
            }
        }

        ScenicTask task = scenicTaskService.getById(form.getId());
        if (!task.getStatus().equals(ScenicTaskStatusEnum.WAIT.getCode())) {
            log.warn("ScenicTask[id:{}] 当前状态不允许修改", task.getId());
            return SingleResponse.buildFailure("901", "当前任务不允许修改！");
        }

        List<TaskPrizeDTO> taskPrizes = form.getTaskPrizes();
        Map<Integer, List<TaskPrizeDTO>> prizeTypeMap = taskPrizes.stream().collect(Collectors.groupingBy(TaskPrizeDTO::getPrizeType));
        String typeIdTags = prizeTypeMap.keySet().stream().map(x -> "[" + x + "]").collect(Collectors.joining(""));

        BeanUtils.copyProperties(form, task);
        task.setLastUpdateById(userAware.getMchUserId() + "");
        task.setIndustryCode((StringUtils.join(form.getIndustryCodeList(), ",")));
        task.setLastUpdateTime(new Date());
        task.setLastUpdateByName(userAware.getUserName());
        task.setAuditStatus(AuditStatusEnum.AUDIT_PASS.getCode());
        task.setStatus(ScenicTaskStatusEnum.WAIT.getCode());
        task.setPrizeTypeTag(typeIdTags);
        if (Objects.nonNull(form.getOtherRemarkList()) && form.getOtherRemarkList().size() > 0) {
            task.setOtherRemarkList(JSONUtil.toJsonPrettyStr(form.getOtherRemarkList()));
        }
        if (Objects.nonNull(form.getSpecialTipList()) && form.getSpecialTipList().size() > 0) {
            task.setSpecialTipList(JSONUtil.toJsonPrettyStr(form.getSpecialTipList()));
        }
        boolean insert = task.updateById();
        if (!insert) {
            log.error("update ScenicTask failed.");
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
                return SingleResponse.buildFailure("902", "修改任务失败！");
            }
        }
        log.info("update TaskPrize[id:{}] success.", task.getId());
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "开始任务")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> start(@RequestBody @Valid StartTaskParam form, UserAware userAware) {
        boolean update = scenicTaskService.lambdaUpdate()
                .set(ScenicTask::getStatus, ScenicTaskStatusEnum.DOING.getCode())
                .set(ScenicTask::getLastUpdateTime, new Date())
                .set(ScenicTask::getLastUpdateById, userAware.getMchUserId())
                .set(ScenicTask::getLastUpdateByName, userAware.getUserName())
                .eq(ScenicTask::getId, form.getId())
                .update();
        if (!update) {
            log.error("start task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "开始任务失败");
        }
        log.info("start task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "暂停任务")
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> stop(@RequestBody @Valid StartTaskParam form, UserAware userAware) {
        boolean update = scenicTaskService.lambdaUpdate()
                .set(ScenicTask::getStatus, ScenicTaskStatusEnum.STOP.getCode())
                .set(ScenicTask::getLastUpdateTime, new Date())
                .set(ScenicTask::getLastUpdateById, userAware.getMchUserId())
                .set(ScenicTask::getLastUpdateByName, userAware.getUserName())
                .eq(ScenicTask::getId, form.getId())
                .update();
        if (!update) {
            log.error("stop task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "暂停任务失败");
        }
        log.info("stop task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "首页推荐")
    @RequestMapping(value = "/recommend/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> indexRecommendOn(@RequestBody @Valid TopTaskParam form, UserAware userAware) {
        boolean update = scenicTaskService.lambdaUpdate()
                .set(ScenicTask::getIndexRecommend, 1)
                .set(ScenicTask::getLastUpdateTime, new Date())
                .set(ScenicTask::getLastUpdateById, userAware.getMchUserId())
                .set(ScenicTask::getLastUpdateByName, userAware.getUserName())
                .eq(ScenicTask::getId, form.getId())
                .update();
        if (!update) {
            log.error("IndexRecommend on task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "首页推荐任务失败");
        }
        log.info("IndexRecommend on task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "首页取消推荐")
    @RequestMapping(value = "/recommend/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> indexRecommendOff(@RequestBody @Valid TopTaskParam form, UserAware userAware) {
        boolean update = scenicTaskService.lambdaUpdate()
                .set(ScenicTask::getIndexRecommend, 0)
                .set(ScenicTask::getLastUpdateTime, new Date())
                .set(ScenicTask::getLastUpdateById, userAware.getMchUserId())
                .set(ScenicTask::getLastUpdateByName, userAware.getUserName())
                .eq(ScenicTask::getId, form.getId())
                .update();
        if (!update) {
            log.error("IndexRecommend off task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "首页取消推荐任务失败");
        }
        log.info("IndexRecommend off task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "任务权重修改")
    @RequestMapping(value = "/weight/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> weightModify(@RequestBody @Valid ModifyTaskWeightParam form, UserAware userAware) {
        boolean update = scenicTaskService.lambdaUpdate()
                .set(ScenicTask::getWeight, form.getWeight())
                .set(ScenicTask::getLastUpdateTime, new Date())
                .set(ScenicTask::getLastUpdateById, userAware.getMchUserId())
                .set(ScenicTask::getLastUpdateByName, userAware.getUserName())
                .eq(ScenicTask::getId, form.getId())
                .update();
        if (!update) {
            log.error("weightModify  task[id:{}] failed.", form.getId());
            return SingleResponse.buildFailure("904", "任务权重修改失败");
        }
        log.info("weightModify task[id:{}] success.", form.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取任务详情")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicTaskDetailVO> getDetailById(@Valid GetTaskDetailById form, UserAware userAware) {
        ScenicTaskDetailVO vo = new ScenicTaskDetailVO();
        Map<Long, String> mapTypeData = taskPrizeTypeService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(TaskPrizeType::getId, TaskPrizeType::getPrizeTypeName));

        ScenicTask scenicTask = scenicTaskService.getById(form.getId());
        BeanUtils.copyProperties(scenicTask, vo);
        List<TaskPrizeDTO> taskPrizeList = taskPrizeService.lambdaQuery()
                .eq(TaskPrize::getTaskId, form.getId()).list().stream()
                .map(x -> new TaskPrizeDTO(x.getPlatformCode(), x.getPrizeType(), mapTypeData.get(x.getPrizeType().longValue()), x.getPrizeTitle(), x.getPrizeContent()))
                .collect(Collectors.toList());
        vo.setTaskPrizeItems(taskPrizeList);
        if (StrUtil.isNotBlank(scenicTask.getOtherRemarkList())) {
            JSONArray otherRemarkList = JSONUtil.parseArray(scenicTask.getOtherRemarkList());
            vo.setOtherRemarkList(otherRemarkList.toList(String.class));
        }

        if (StringUtils.isNotBlank(scenicTask.getIndustryCode())) {
           vo.setIndustryCodeList(Arrays.asList(scenicTask.getIndustryCode().split(",")));
        }

        if (StrUtil.isNotBlank(scenicTask.getSpecialTipList())) {
            JSONArray specialTipList = JSONUtil.parseArray(scenicTask.getSpecialTipList());
            vo.setSpecialTipList(specialTipList.toList(String.class));
        }
        QueryWrapper<MemberIntegralLevel> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("level", vo.getLimitLevel());
        MemberIntegralLevel memberIntegralLevel = memberIntegralLevelMapper.selectOne(queryWrapper2);
        if(memberIntegralLevel != null){
            vo.setLevelName(memberIntegralLevel.getLevelName());
        }
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    @ResponseBody
    public SingleResponse<IPage<ScenicTaskPageVO>> getByPage(ScenicTaskPageQry qry, UserAware userAware) {
        Long mchUserId = userAware.getMchUserId();
        qry.setCreateById(mchUserId);
        IPage<ScenicTaskPageVO> scenicTaskPage = scenicTaskService.paginateAndConvert(new ScenicTaskPageParam(qry), this::toVO);
        Map<Long, String> mapTypeData = taskPrizeTypeService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(TaskPrizeType::getId, TaskPrizeType::getPrizeTypeName));

        scenicTaskPage.getRecords().stream().filter(x -> StrUtil.isNotBlank(x.getPrizeTypeTag())).forEach(x -> {
            List<String> newTypeNames = ReUtil.findAllGroup1(compile, x.getPrizeTypeTag())
                    .stream().map(Long::valueOf)
                    .map(mapTypeData::get)
                    .collect(Collectors.toList());
            x.setPrizeTypeList(newTypeNames);
        });
        return SingleResponse.of(scenicTaskPage);
    }

    @ApiOperation(value = "新增消息订阅")
    @RequestMapping(value = "/message/subscribe/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> addMessageSubscribe(@RequestBody @Valid AddMessageSubscribeParam form, UserAware userAware) {
        Integer count = messageSubscribeService.lambdaQuery()
                .eq(TaskMessageSubscribe::getTaskId, form.getTaskId())
                .eq(TaskMessageSubscribe::getBizCode, form.getBizCode())
                .count();
        if (count > 0) {
            return SingleResponse.buildFailure("9851", "操作失败,已有重复的通知设置");
        }
        TaskMessageSubscribe messageSubscribe = new TaskMessageSubscribe();
        messageSubscribe.setTaskId(form.getTaskId());
        messageSubscribe.setTemplateId(form.getTemplateId());
        messageSubscribe.setStatus(form.getStatus());
        messageSubscribe.setAccountList(form.getAccountList());
        messageSubscribe.setBizCode(form.getBizCode());
        messageSubscribe.setAlertType(form.getAlertType());
        messageSubscribe.setCreateTime(new Date());
        boolean insert = messageSubscribe.insert();
        if (!insert) {
            return SingleResponse.buildFailure("9851", "操作失败");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改消息订阅")
    @RequestMapping(value = "/message/subscribe/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyMessageSubscribe(@RequestBody @Valid ModifyMessageSubscribeParam form, UserAware userAware) {
        TaskMessageSubscribe taskMessageSubscribe = messageSubscribeService.lambdaQuery()
                .eq(TaskMessageSubscribe::getTaskId, form.getTaskId())
                .eq(TaskMessageSubscribe::getBizCode, form.getBizCode())
                .one();
        if (Objects.isNull(taskMessageSubscribe)) {
            return SingleResponse.buildFailure("9851", "操作失败");
        }
        boolean update = messageSubscribeService.lambdaUpdate()
                .set(TaskMessageSubscribe::getStatus, form.getStatus())
                .set(TaskMessageSubscribe::getTemplateId, form.getTemplateId())
                .set(TaskMessageSubscribe::getAccountList, form.getAccountList())
                .set(TaskMessageSubscribe::getAlertType, form.getAlertType())
                .eq(TaskMessageSubscribe::getId, taskMessageSubscribe.getId())
                .update();

        if (!update) {
            return SingleResponse.buildFailure("9851", "操作失败");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取消息配置列表")
    @GetMapping("/{taskId}/listMessageSubscribe")
    @ResponseBody
    public SingleResponse<List<TaskMessageSubscribeVO>> listMessageSubscribe(@PathVariable("taskId") Long taskId, String bizCode) {
        List<TaskMessageSubscribe> list = messageSubscribeService.lambdaQuery()
                .eq(StrUtil.isNotBlank(bizCode), TaskMessageSubscribe::getBizCode, bizCode)
                .eq(TaskMessageSubscribe::getTaskId, taskId)
                .list();
        List<TaskMessageSubscribeVO> voList = list.stream().map(x -> {
            TaskMessageSubscribeVO vo = new TaskMessageSubscribeVO();
            BeanUtils.copyProperties(x, vo);
            SmsTemplate smsTemplate = smsTemplateService.getById(x.getTemplateId());
            vo.setTemplateContent(smsTemplate.getContent());
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(voList);
    }

    private ScenicTaskPageVO toVO(ScenicTask task) {
        ScenicTaskPageVO vo = new ScenicTaskPageVO();
        BeanUtils.copyProperties(task, vo);
        return vo;
    }
}
