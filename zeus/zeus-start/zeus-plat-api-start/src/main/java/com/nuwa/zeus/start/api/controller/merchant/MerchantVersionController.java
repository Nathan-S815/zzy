package com.nuwa.zeus.start.api.controller.merchant;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.base.CreateVersionCmdExe;
import com.nuwa.app.zeus.command.base.DeleteVersionCmdExe;
import com.nuwa.app.zeus.command.base.ModifyVersionCmdExe;
import com.nuwa.app.zeus.command.base.qry.VersionListQryExe;
import com.nuwa.app.zeus.command.base.qry.VersionPageQryExe;
import com.nuwa.app.zeus.command.base.qry.VersionQryExe;
import com.nuwa.client.zeus.dto.clientobject.base.CreateVersionCmd;
import com.nuwa.client.zeus.dto.clientobject.base.DeleteVersionCmd;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyVersionCmd;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("merchant/version")
@Api(tags = {"版本管理"})
public class MerchantVersionController {

    @Autowired
    private VersionListQryExe versionListQryExe;

    @ApiOperation(value = "升级日志查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<VersionListQryExe.PlatUpgradeVO>> page(PlatUpgradeListQry cmd, UserAware userAware) {
        return versionListQryExe.execute(cmd);
    }
}
