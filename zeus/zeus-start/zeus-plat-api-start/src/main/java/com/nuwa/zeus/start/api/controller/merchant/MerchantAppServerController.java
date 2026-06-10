package com.nuwa.zeus.start.api.controller.merchant;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.zeus.start.api.controller.merchant.param.AuthServerTimeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;

/**
 * MerchantAppServerController
 *
 * @author hy
 * @date 2021/6/9 14:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/app/server")
@Api(tags = {"商户应用服务管理相关"})
public class MerchantAppServerController {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @ApiOperation(value = "授权服务周期")
    @RequestMapping(value = "auth_time", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> authTime(@RequestBody @Validated AuthServerTimeParam param, UserAware userAware) {
        AppInfo appInfo = appInfoService.getById(param.getAppId());
        MerchantAppServer appServer = merchantAppServerService.lambdaQuery()
                .eq(MerchantAppServer::getAppId, param.getAppId())
                .eq(MerchantAppServer::getParentAppId, param.getParentAppId())
                .eq(MerchantAppServer::getMerchantId, param.getMchId())
                .one();
        if (Objects.isNull(appServer)) {
            appServer = new MerchantAppServer();
        }
        BeanUtil.copyProperties(param, appServer);
        appServer.setMerchantId(param.getMchId());
        appServer.setCreateTime(new Date());
        appServer.setAppName(appInfo.getAppName());
        appServer.setCreateUserId(userAware.getUserId() + "");
        appServer.setStatus(1);
        boolean insert = appServer.insertOrUpdate();
        if (insert) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "授权服务周期操作失败");
    }
}
