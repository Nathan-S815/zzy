package com.nuwa.zeus.start.api.controller.plat.base;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.base.*;
import com.nuwa.app.zeus.command.base.qry.VersionPageQryExe;
import com.nuwa.app.zeus.command.base.qry.VersionQryExe;
import com.nuwa.client.zeus.dto.clientobject.base.*;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("version")
@Api(tags = {"版本管理"})
public class VersionController {

    @Autowired
    private CreateVersionCmdExe createVersionCmdExe;

    @Autowired
    private DeleteVersionCmdExe deleteVersionCmdExe;

    @Autowired
    private ModifyVersionCmdExe modifyVersionCmdExe;

    @Autowired
    private VersionQryExe versionQryExe;

    @Autowired
    private VersionPageQryExe versionPageQryExe;

    @ApiOperation(value = "新增版本")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse add(@RequestBody CreateVersionCmd cmd, UserAware userAware) {
        return createVersionCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除版本")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse delete(@RequestBody DeleteVersionCmd cmd, UserAware userAware) {
        return deleteVersionCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改版本")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@RequestBody ModifyVersionCmd cmd, UserAware userAware) {
        return modifyVersionCmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<VersionQryExe.PlatUpgradeVO> getById(PlatUpgradeQry cmd, UserAware userAware) {
        return versionQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<VersionQryExe.PlatUpgradeVO> page(PlatUpgradePageQry cmd, UserAware userAware) {
        return versionPageQryExe.execute(cmd);
    }
}
