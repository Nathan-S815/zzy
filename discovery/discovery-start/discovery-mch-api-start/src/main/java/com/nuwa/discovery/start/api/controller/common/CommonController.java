package com.nuwa.discovery.start.api.controller.common;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.discovery.dto.clientobject.task.qry.ScenicTaskPageQry;
import com.nuwa.discovery.start.api.controller.dto.ScenicTaskDetailVO;
import com.nuwa.discovery.start.api.controller.dto.ScenicTaskPageVO;
import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import com.nuwa.discovery.start.api.controller.task.param.GetTaskDetailById;
import com.nuwa.discovery.start.api.controller.task.param.SaveTaskParam;
import com.nuwa.discovery.start.api.controller.task.param.StartTaskParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPlatform;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.param.ScenicTaskPageParam;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPlatformService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("common")
@Api(tags = {"公共接口"})
public class CommonController {

    @Autowired
    private TaskPlatformService taskPlatformService;

    @Autowired
    private TaskPrizeTypeService taskPrizeTypeService;

    @ApiOperation(value = "获取任务权益类型列表")
    @RequestMapping(value = "/listTaskPrizeTypeList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<TaskPrizeType>> listTaskPrizeTypeList() {
        List<TaskPrizeType> list = taskPrizeTypeService.lambdaQuery().list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "获取任务平台列表")
    @RequestMapping(value = "/listPlatformList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<TaskPlatform>> listTaskPrizeList() {
        List<TaskPlatform> list = taskPlatformService.lambdaQuery().list();
        return SingleResponse.of(list);
    }
}
