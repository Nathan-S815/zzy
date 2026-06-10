package com.nuwa.zeus.start.api.controller.common;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.material.*;
import com.nuwa.app.zeus.command.material.query.MaterialPageQryExe;
import com.nuwa.app.zeus.command.material.query.MaterialTypeListQryExe;
import com.nuwa.client.zeus.dto.clientobject.app.qry.PageInfoListQry;
import com.nuwa.client.zeus.dto.clientobject.material.*;
import com.nuwa.client.zeus.dto.clientobject.material.co.MaterialUploadCO;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialPageQry;
import com.nuwa.client.zeus.dto.clientobject.material.qry.MaterialTypeListQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.material.param.MaterialPageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = {"素材管理线管接口"})
@Slf4j
@RestController
@RequestMapping("/material")
public class MerchantMaterialController {

    @Autowired
    private MaterialUploadImageCmdExe materialUploadImageCmdExe;

    @Autowired
    private MaterialUploadVideoCmdExe materialUploadVideoCmdExe;

    @Autowired
    private MaterialUploadKeyCmdExe materialUploadKeyCmdExe;

    @Autowired
    private MaterialTypeListQryExe materialTypeListQryExe;

    @Autowired
    private CreateMaterialTypeCmdExe createMaterialTypeCmdExe;

    @Autowired
    private ModifyMaterialTypeCmdExe modifyMaterialTypeCmdExe;

    @Autowired
    private DeleteMaterialTypeCmdExe deleteMaterialTypeCmdExe;

    @Autowired
    private MaterialPageQryExe materialPageQryExe;

    @Autowired
    private ModifyMaterialCmdExe modifyMaterialCmdExe;

    @Autowired
    private DelMaterialCmdExe delMaterialCmdExe;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/upload/image")
    public SingleResponse<?> uploadImage(@Valid @ApiParam(value = "file", type = "MultipartFile") MultipartFile file, Long typeId, UserAware userAware) throws Exception {
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCO = new MaterialUploadCO();
        materialUploadCO.setFile(file);
        materialUploadCO.setTypeId(typeId);
        cmd.setMaterialUploadCO(materialUploadCO);
        cmd.setUserAware(userAware);
        return materialUploadImageCmdExe.execute(cmd);
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

    @ApiOperation(value = "上传证书")
    @PostMapping(value = "/upload/key")
    public SingleResponse<?> uploadKey(@Valid @ApiParam(value = "file", type = "MultipartFile") MultipartFile file, Long typeId, UserAware userAware) throws Exception {
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCO = new MaterialUploadCO();
        materialUploadCO.setFile(file);
        materialUploadCO.setTypeId(typeId);
        cmd.setMaterialUploadCO(materialUploadCO);
        cmd.setUserAware(userAware);
        return materialUploadKeyCmdExe.execute(cmd);
    }

    @ApiOperation(value = "批量删除图片资源")
    @GetMapping(value = "/delMaterial")
    public SingleResponse delMaterial(DelMaterialCmd cmd, UserAware userAware) {
        return delMaterialCmdExe.execute(cmd);
    }

    @ApiOperation(value = "获取资源分类")
    @GetMapping(value = "/type")
    public SingleResponse type(MaterialTypeListQry cmd, UserAware userAware) {
        return materialTypeListQryExe.execute(cmd);
    }

    @ApiOperation(value = "新建资源分类")
    @GetMapping(value = "/type/create")
    public SingleResponse create(CreateMaterialTypeCmd cmd, UserAware userAware) {
        return createMaterialTypeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改资源分类")
    @GetMapping(value = "/type/modify")
    public SingleResponse modify(ModifyMaterialTypeCmd cmd, UserAware userAware) {
        return modifyMaterialTypeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除资源分类")
    @GetMapping(value = "/type/delete")
    public SingleResponse delete(DeleteMaterialTypeCmd cmd, UserAware userAware) {
        return deleteMaterialTypeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "分页获取资源")
    @GetMapping(value = "/page")
    public SingleResponse icon(MaterialPageQry cmd, UserAware userAware) {
        return materialPageQryExe.execute(cmd);
    }


    @ApiOperation(value = "分页获取[公共资源]")
    @GetMapping(value = "/public/page")
    public SingleResponse getPublicMaterialPage(MaterialPageQry cmd, UserAware userAware) {
        cmd.getUserAware().setMchId(-2L);
        return materialPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "移动资源")
    @GetMapping(value = "/move")
    public SingleResponse move(ModifyMaterialCmd cmd, UserAware userAware) {
        return modifyMaterialCmdExe.execute(cmd);
    }
}
