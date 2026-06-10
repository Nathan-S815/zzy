package com.nuwa.discovery.start.api.controller.common;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.discovery.material.MaterialUploadVideoCmdExe;
import com.nuwa.client.zeus.dto.clientobject.material.MaterialUploadCmd;
import com.nuwa.client.zeus.dto.clientobject.material.co.MaterialUploadCO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPlatform;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPlatformService;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private MaterialUploadVideoCmdExe materialUploadVideoCmdExe;

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

    @ApiOperation(value = "上传视频")
    @PostMapping(value = "/upload/video")
    public SingleResponse<?> uploadVideo(@Valid @ApiParam(value = "file", type = "MultipartFile") MultipartFile file, Long typeId, UserAware userAware) throws Exception {
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCO = new MaterialUploadCO();
        materialUploadCO.setFile(file);
        materialUploadCO.setTypeId(typeId);
        cmd.setMaterialUploadCO(materialUploadCO);
        cmd.setUserAware(userAware);
        return materialUploadVideoCmdExe.execute(cmd);
    }
}
