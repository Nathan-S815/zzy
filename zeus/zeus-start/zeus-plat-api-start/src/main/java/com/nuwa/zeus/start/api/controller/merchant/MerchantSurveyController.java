package com.nuwa.zeus.start.api.controller.merchant;


import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.base.qry.*;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseAppListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserLoginListQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradeListQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("merchant/survey")
@Api(tags = {"平台概况"})
public class MerchantSurveyController {

    @Autowired
    private BaseUserLoginListQryExe baseUserLoginListQryExe;

    @Autowired
    private BaseUserIPListQryExe baseUserIPListQryExe;

    @Autowired
    private BaseAppNumListQryExe baseAppNumListQryExe;

    @Autowired
    private BaseAppManageListQryExe baseAppManageListQryExe;

    @Autowired
    private BaseMerchantQryExe baseMerchantQryExe;

    @ApiOperation(value = "登陆账号统计")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseUserLoginListQryExe.BaseUserLoginVO>> page(BaseUserLoginListQry cmd, UserAware userAware) {
        return baseUserLoginListQryExe.execute(cmd);
    }

    @ApiOperation(value = "登陆IP统计")
    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseUserIPListQryExe.BaseUserIPVO>> ip(BaseUserLoginListQry cmd, UserAware userAware) {
        return baseUserIPListQryExe.execute(cmd);
    }

    @ApiOperation(value = "应用数量")
    @RequestMapping(value = "/app", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseAppNumListQryExe.BaseAppNumVO>> num(BaseAppListQry cmd, UserAware userAware) {
        return baseAppNumListQryExe.execute(cmd);
    }

    @ApiOperation(value = "应用管理")
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BaseAppManageListQryExe.BaseAppManageVO>> manage(BaseAppListQry cmd, UserAware userAware) {
        return baseAppManageListQryExe.execute(cmd);
    }

    @ApiOperation(value = "商户信息")
    @RequestMapping(value = "/merchant", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<BaseMerchantQryExe.BaseMerchantVO> merchant(BaseUserLoginListQry cmd, UserAware userAware) {
        return baseMerchantQryExe.execute(cmd);
    }
}
