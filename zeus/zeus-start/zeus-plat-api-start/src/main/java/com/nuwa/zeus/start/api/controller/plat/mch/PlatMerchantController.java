package com.nuwa.zeus.start.api.controller.plat.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.command.mch.*;
import com.nuwa.app.zeus.command.mch.qry.MerchantPageQryExe;
import com.nuwa.app.zeus.command.mch.qry.MerchantQryExe;
import com.nuwa.client.zeus.dto.clientobject.mch.*;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * MerchantController
 *
 * @author hy
 * @date 2021/5/31 14:17
 * @since 1.0.0
 */
@Controller
@RequestMapping("plat/merchant")
@Api(tags = {"商户管理模块"})
public class PlatMerchantController {

    @Autowired
    private CreateMerchantCmdExe createMerchantCmdExe;

    @Autowired
    private ModifyMerchantCmdExe modifyMerchantCmdExe;

    @Autowired
    private MerchantPageQryExe merchantPageQryExe;

    @Autowired
    private MerchantQryExe merchantQryExe;

    @Autowired
    private ResetMerchantPasswordCmdExe resetMerchantPasswordCmdExe;

    @Autowired
    private EnableMerchantCmdExe enableMerchantCmdExe;

    @Autowired
    private DisableMerchantCmdExe disableMerchantCmdExe;

    @Autowired
    private PassMerchantCmdExe passMerchantCmdExe;

    @Autowired
    private ForbidMerchantCmdExe forbidMerchantCmdExe;

    @Autowired
    private EditPasswordCmdExe editPasswordCmdExe;

    @ApiOperation(value = "创建商户")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse create(@Validated @RequestBody CreateMerchantCmd cmd, UserAware userAware) {
        return createMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "编辑商户")
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@Validated @RequestBody ModifyMerchantCmd cmd, UserAware userAware) {
        return modifyMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantPageQryExe.MerchantPageVO>> page(MerchantPageQry cmd, UserAware userAware) {
        return merchantPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "商户详情")
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Merchant> getById(MerchantQry cmd, UserAware userAware) {
        return merchantQryExe.execute(cmd);
    }

    @LogRecordAnnotation(
            fail = "重置密码失败，原因：「{{#_errorMsg}}」",
            category = LogCategoryType.PLAT,
            success = "重置密码成功",
            prefix = LogRecordType.MERCHANT_APP,
            bizNo = "{{#cmd.merchantId}}",
            detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "reset", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse reset(ResetMerchantPasswordCmd cmd, UserAware userAware) {
        return resetMerchantPasswordCmdExe.execute(cmd);
    }

    @ApiOperation(value = "启用商户")
    @RequestMapping(value = "enable", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse enable(EnableMerchantCmd cmd, UserAware userAware) {
        return enableMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "停用商户")
    @RequestMapping(value = "disable", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse disable(DisableMerchantCmd cmd, UserAware userAware) {
        return disableMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "审核通过")
    @RequestMapping(value = "pass", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse pass(PassMerchantCmd cmd, UserAware userAware) {
        return passMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "审核失败")
    @RequestMapping(value = "forbid", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse forbid(ForbidMerchantCmd cmd, UserAware userAware) {
        return forbidMerchantCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "password", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse password(EditPasswordCmd cmd, UserAware userAware) {
        return editPasswordCmdExe.execute(cmd);
    }

}
