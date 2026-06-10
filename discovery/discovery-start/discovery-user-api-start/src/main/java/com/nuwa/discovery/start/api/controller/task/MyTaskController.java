package com.nuwa.discovery.start.api.controller.task;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.discovery.command.query.MyTaskPageJoinQry;
import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import com.nuwa.discovery.start.api.controller.task.param.SaveTaskParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.appconfig.entity.AppConfig;
import com.nuwa.infrastructure.discovery.database.appconfig.mapper.AppConfigMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskPrizeMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.MyTaskJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.UserMyTaskPageJoinQuery;
import com.nuwa.infrastructure.discovery.database.user.service.IndustryService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberIntegralRecordService;
import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskPageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskVO;
import com.nuwa.infrastructure.discovery.enums.AuditStatusEnum;
import com.nuwa.infrastructure.discovery.enums.ScenicTaskStatusEnum;
import com.nuwa.infrastructure.discovery.enums.SwitchEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/my/task")
@Api(tags = {"我的任务管理相关"})
public class MyTaskController {

    private final Pattern compile = Pattern.compile("\\[(.*?)]");

    @Autowired
    private MyTaskJoinMapper myTaskJoinMapper;

    @Autowired
    private TaskPrizeTypeService taskPrizeTypeService;

    @Autowired
    private MemberTaskPrizeMapper memberTaskPrizeMapper;

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private ScenicTaskService scenicTaskService;

    @ApiOperation(value = "我的任务")
    @GetMapping("/page/{appId}")
    @ResponseBody
    public SingleResponse<IPage<MyTaskPageVO>> getByPage(@PathVariable("appId") String appId, MyTaskPageJoinQry qry, UserAware userAware) {
        QueryWrapper<AppConfig> queryWrapperAppid = new QueryWrapper<>();
        queryWrapperAppid.eq("app_id", appId);
        AppConfig appConfig = appConfigMapper.selectOne(queryWrapperAppid);
        if (appConfig == null) {
            return SingleResponse.buildFailure("999", "appId对应记录不存在");
        }
        UserMyTaskPageJoinQuery queryPage = new UserMyTaskPageJoinQuery();
        queryPage.setUserId(userAware.getUserId());
        queryPage.setMchId(appConfig.getMchId());
        BeanUtils.copyProperties(qry, queryPage);
        Page<MyTaskPageVO> pageData = myTaskJoinMapper.paginateByQuery(queryPage);
        Map<Long, String> mapTypeData = taskPrizeTypeService.lambdaQuery()
                .list()
                .stream()
                .collect(Collectors.toMap(TaskPrizeType::getId, TaskPrizeType::getPrizeTypeName));
        if(CollectionUtils.isEmpty(pageData.getRecords())){
            return SingleResponse.of(pageData);
        }
        //根据任务id集合获取所有权益
        List<Long> taskIds = new ArrayList<>();
        for (MyTaskPageVO record : pageData.getRecords()) {
            taskIds.add(record.getId());
        }
        QueryWrapper<MemberTaskPrize> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_id", taskIds);
        queryWrapper.eq("user_id",userAware.getUserId());
        List<MemberTaskPrize> memberTaskPrizes = memberTaskPrizeMapper.selectList(queryWrapper);
        Map<Long, List<MemberTaskPrize>> map = new HashMap<>();
        for (MemberTaskPrize memberTaskPrize : memberTaskPrizes) {
            List<MemberTaskPrize> orDefault = map.getOrDefault(memberTaskPrize.getTaskId(),new ArrayList<>());
            orDefault.add(memberTaskPrize);
            map.put(memberTaskPrize.getTaskId(), orDefault);
        }

        pageData.getRecords().stream().filter(x -> StrUtil.isNotBlank(x.getPrizeTypeTag())).forEach(x -> {
            List<String> newTypeNames = ReUtil.findAllGroup1(compile, x.getPrizeTypeTag())
                    .stream().map(Long::valueOf)
                    .map(mapTypeData::get)
                    .collect(Collectors.toList());
            x.setPrizeTypeList(newTypeNames);
            List<MemberTaskPrize> memberTaskPrizes1 = map.get(x.getId());
            //初始化未领取的权益集合
            List<String> unclaimedPrizeTypeList = new ArrayList<>();
            //初始化已领取权益集合
            List<String> receivedPrizeTypeList = new ArrayList<>();
            for (MemberTaskPrize memberTaskPrize : memberTaskPrizes1) {
                String taskPrize = mapTypeData.get(Convert.toLong(memberTaskPrize.getPrizeTypeId()));
                if(memberTaskPrize.getStatus() == 1 || memberTaskPrize.getStatus() == 4 || memberTaskPrize.getStatus() == 2){
                    unclaimedPrizeTypeList.add(taskPrize);
                }else if(memberTaskPrize.getStatus() == 3){
                    receivedPrizeTypeList.add(taskPrize);
                }
            }
            x.setUnclaimedPrizeTypeList(unclaimedPrizeTypeList);
            x.setReceivedPrizeTypeList(receivedPrizeTypeList);
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "我进行中任务数量")
    @GetMapping("/doing/count")
    @ResponseBody
    public SingleResponse<Long> getDoingTaskCount(UserAware userAware) {
        UserMyTaskPageJoinQuery queryPage = new UserMyTaskPageJoinQuery();
        queryPage.setUserId(userAware.getUserId());
        Page<MyTaskPageVO> pageData = myTaskJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData.getTotal());
    }


    /**
     * 我发布的任务列表
     * @param pageSize
     * @param pageNum
     * @param userAware
     * @return
     */
    @ApiOperation(value = "我发布的任务列表(C端)")
    @GetMapping("/page")
    @ResponseBody
    public SingleResponse<IPage<MyTaskVO>> getMyPublishTaskList(@RequestParam(defaultValue = "10") Long pageSize,
                                                                @RequestParam(defaultValue = "1") Long pageNum, UserAware userAware) {
        IPage<MyTaskVO> page = scenicTaskService.getMyPublishTaskList(pageSize,pageNum,userAware);
        return SingleResponse.of(page);
    }
}


