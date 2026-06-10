package com.nuwa.attract.start.api.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.attract.command.MaterialUploadImageCmdExe;
import com.nuwa.attract.start.api.controller.common.param.RegionParam;
import com.nuwa.client.attract.co.MaterialUploadCO;
import com.nuwa.client.attract.dto.clientobject.MaterialUploadCmd;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.common.entity.CnRegionInfo;
import com.nuwa.infrastructure.attract.database.common.service.CnRegionInfoService;
import com.nuwa.infrastructure.enums.MaterialFileTypeEnum;
import com.nuwa.infrastructure.enums.MaterialTargetEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.common:CommonController.java,v1.0.0 2022-09-08 10:22:16 nanHuang Exp $
 */
@Api(tags = {"基类接口"})
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private MaterialUploadImageCmdExe materialUploadImageCmdExe;
    @Resource
    private CnRegionInfoService       cnRegionInfoService;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/upload/image")
    public SingleResponse<?> uploadImage(@Valid @ApiParam(value = "file", type = "MultipartFile") MultipartFile file,
                                         Long typeId) throws Exception {
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCo = new MaterialUploadCO();
        materialUploadCo.setFile(file);
        materialUploadCo.setTypeId(typeId);
        materialUploadCo.setMchId(0L);
        materialUploadCo.setFileType(MaterialFileTypeEnum.IMAGE.getCode());
        materialUploadCo.setTargeType(MaterialTargetEnum.OTHER.getCode());
        cmd.setMaterialUploadCO(materialUploadCo);
        return materialUploadImageCmdExe.execute(cmd);
    }

    @ApiOperation(value = "地区查询接口")
    @GetMapping("/region")
    public SingleResponse<?> getRegionList(RegionParam param) {
        if (!"1".equals(param.getLevel())) {
            if (StrUtil.isBlankOrUndefined(param.getAreaCode())) {
                return SingleResponse.buildFailure("9632", "缺少参数");
            }
        }
        List<CnRegionInfo> list = cnRegionInfoService.lambdaQuery()
            .eq(CnRegionInfo::getCriLevel, param.getLevel())
            .eq(!StrUtil.isBlankOrUndefined(param.getAreaCode()), CnRegionInfo::getCriSuperiorCode, param.getAreaCode())
            .list();
        return SingleResponse.of(list);
    }
}
