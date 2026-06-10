package com.nuwa.zeus.start.api.controller.merchant;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.mch.CheckDomainCmdExe;
import com.nuwa.app.zeus.command.mch.CreateMerchantSiteCmdExe;
import com.nuwa.app.zeus.command.mch.qry.*;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.CheckDomainCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantSiteCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.*;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import com.nuwa.zeus.start.api.controller.merchant.param.ModifyDomainAndApproveNoParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * MerchantAppController
 *
 * @author hy
 * @date 2021/6/9 14:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/site")
@Api(tags = {"登录页配置模块"})
public class MerchantSiteConfigController {

    @Autowired
    private CreateMerchantSiteCmdExe createMerchantSiteCmdExe;

    @Autowired
    private MerchantSiteQryExe merchantSiteQryExe;

    @Autowired
    private MerchantSiteDomainQryExe merchantSiteDomainQryExe;
    @Autowired
    private CheckDomainCmdExe checkDomainCmdExe;

    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;

    @ApiOperation(value = "保存配置")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse save(@Validated @RequestBody CreateMerchantSiteCmd cmd, UserAware userAware) {
        return createMerchantSiteCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改域名和备案号")
    @RequestMapping(value = "modifyDomainAndApproveNo", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modifyDomainAndApproveNo(@Validated @RequestBody ModifyDomainAndApproveNoParam cmd, UserAware userAware) {
        boolean update = merchantSiteConfigService.lambdaUpdate()
                .set(MerchantSiteConfig::getDomain, cmd.getDomain())
                .set(MerchantSiteConfig::getWebsiteApproveNo, cmd.getWebsiteApproveNo())
                .eq(MerchantSiteConfig::getId, cmd.getId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "查询配置")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantSiteConfig> get(@Validated MerchantSiteQry cmd, UserAware userAware) {
        return merchantSiteQryExe.execute(cmd);
    }

    @ApiOperation(value = "根据域名查询")
    @RequestMapping(value = "domain", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantSiteConfig> domain(@Validated MerchantSiteDomainQry cmd) {
        return merchantSiteDomainQryExe.execute(cmd);
    }

    @ApiOperation(value = "校验域名是否可用")
    @RequestMapping(value = "checkDomain", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantSiteConfig> checkDomain(@Validated CheckDomainCmd cmd, UserAware userAware) {
        return checkDomainCmdExe.execute(cmd);
    }


}
