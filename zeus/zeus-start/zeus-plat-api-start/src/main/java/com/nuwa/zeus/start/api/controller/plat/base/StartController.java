package com.nuwa.zeus.start.api.controller.plat.base;


import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.command.base.*;
import com.nuwa.app.zeus.command.base.qry.StartContentQryExe;
import com.nuwa.app.zeus.command.base.qry.StartLabelTreeQryExe;
import com.nuwa.app.zeus.command.base.qry.VersionPageQryExe;
import com.nuwa.app.zeus.command.base.qry.VersionQryExe;
import com.nuwa.client.zeus.dto.clientobject.base.*;
import com.nuwa.client.zeus.dto.clientobject.base.qry.*;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.param.PlatUpgradePageParam;
import com.nuwa.infrastructure.zeus.database.base.param.StartPageParam;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("start")
@Api(tags = {"新手入门"})
public class StartController {

    @Autowired
    private CreateStartLabelCmdExe createStartLabelCmdExe;

    @Autowired
    private CreateStartContentCmdExe createStartContentCmdExe;

    @Autowired
    private DeleteStartLabelCmdExe deleteStartLabelCmdExe;

    @Autowired
    private ModifyStartContentCmdExe modifyStartContentCmdExe;

    @Autowired
    private ModifyStartLabelCmdExe modifyStartLabelCmdExe;

    @Autowired
    private StartLabelTreeQryExe startLabelTreeQryExe;

    @Autowired
    private StartContentQryExe startContentQryExe;

    @Autowired
    private GettingStartedService gettingStartedService;

    @ApiOperation(value = "新增标签")
    @RequestMapping(value = "/label/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse add(@RequestBody CreateStartLabelCmd cmd, UserAware userAware) {
        return createStartLabelCmdExe.execute(cmd);
    }

    @ApiOperation(value = "新增文章")
    @RequestMapping(value = "/content/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse add(@RequestBody CreateStartContentCmd cmd, UserAware userAware) {
        return createStartContentCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除标签")
    @RequestMapping(value = "/label/delete", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse delete(@RequestBody DeleteStartLabelCmd cmd, UserAware userAware) {
        return deleteStartLabelCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改文章")
    @RequestMapping(value = "/content/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyContent(@RequestBody ModifyStartContentCmd cmd, UserAware userAware) {
        return modifyStartContentCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改标签")
    @RequestMapping(value = "/label/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyLabel(@RequestBody ModifyStartLabelCmd cmd, UserAware userAware) {
        return modifyStartLabelCmdExe.execute(cmd);
    }

    @ApiOperation(value = "查询标签")
    @RequestMapping(value = "/label/tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse getLabel(GettingStartedTreeQry cmd, UserAware userAware) {
        return startLabelTreeQryExe.execute(cmd);
    }

    @ApiOperation(value = "查询正文")
    @RequestMapping(value = "/content/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse getContent(GettingStartedQry cmd, UserAware userAware) {
        return startContentQryExe.execute(cmd);
    }

    @ApiOperation(value = "正文分页查询")
    @RequestMapping(value = "/content/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<GettingStarted>> contentPage(GetStartPageQry pageQry, UserAware userAware) {
        IPage<GettingStarted> gettingStartedIPage = gettingStartedService.paginateByParam(new StartPageParam(pageQry));
        return SingleResponse.of(gettingStartedIPage);
    }

}
