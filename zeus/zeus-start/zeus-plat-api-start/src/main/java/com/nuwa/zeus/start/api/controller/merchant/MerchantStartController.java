package com.nuwa.zeus.start.api.controller.merchant;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.base.CreateStartContentCmdExe;
import com.nuwa.app.zeus.command.base.CreateStartLabelCmdExe;
import com.nuwa.app.zeus.command.base.DeleteStartLabelCmdExe;
import com.nuwa.app.zeus.command.base.ModifyStartContentCmdExe;
import com.nuwa.app.zeus.command.base.qry.StartContentQryExe;
import com.nuwa.app.zeus.command.base.qry.StartLabelTreeQryExe;
import com.nuwa.client.zeus.dto.clientobject.base.CreateStartContentCmd;
import com.nuwa.client.zeus.dto.clientobject.base.CreateStartLabelCmd;
import com.nuwa.client.zeus.dto.clientobject.base.DeleteStartLabelCmd;
import com.nuwa.client.zeus.dto.clientobject.base.ModifyStartContentCmd;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GettingStartedTreeQry;
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


@Controller
@RequestMapping("merchant/start")
@Api(tags = {"新手入门"})
public class MerchantStartController {

    @Autowired
    private StartLabelTreeQryExe startLabelTreeQryExe;

    @Autowired
    private StartContentQryExe startContentQryExe;

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

}
