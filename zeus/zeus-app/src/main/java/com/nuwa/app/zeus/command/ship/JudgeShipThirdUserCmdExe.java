package com.nuwa.app.zeus.command.ship;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.ship.JudgeShipThirdUserCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.infrastructure.zeus.database.ship.entity.ShipThirdUser;
import com.nuwa.infrastructure.zeus.database.ship.service.ShipThirdUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 判断是否已已登录过
 */
@Slf4j
@Component
public class JudgeShipThirdUserCmdExe extends AbstractCmdExe<JudgeShipThirdUserCmd, SingleResponse> {
    @Autowired
    private ShipThirdUserService shipThirdUserService;
    
    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Override
    protected SingleResponse handle(JudgeShipThirdUserCmd cmd) {
        MerchantAppUrl merchantAppUrl = merchantAppUrlService.lambdaQuery().eq(MerchantAppUrl::getMchId, cmd.getMerchantId())
                .eq(MerchantAppUrl::getAppId, cmd.getMerchantAppId()).one();
        if (StrUtil.hasBlank(merchantAppUrl.getLoginSubmitUrl())) {
            return ErrorEnum.LOGIN_URL_FAILED.buildSuccess();
        }
        ShipThirdUser shipThirdUser = shipThirdUserService.lambdaQuery()
                .eq(ShipThirdUser::getMerchantId, cmd.getMerchantId())
                .eq(ShipThirdUser::getMerchantAppId, cmd.getMerchantAppId())
                .eq(ShipThirdUser::getUserId, cmd.getUserAware().getMchUserId())
                .one();
        if (shipThirdUser != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", shipThirdUser.getUsername());
            map.put("password", shipThirdUser.getPassword());
            map.put("companycode", merchantAppUrl.getScenicCode());
            map.put("domain", merchantAppUrl.getLoginSubmitUrl());
            String result = HttpRequest.post(StrUtil.removeSuffix(merchantAppUrl.getLoginSubmitUrl(), "/") + "/partner/login/verify").body(JSONUtil.toJsonStr(map)).execute().body();
            log.info("result:{}", result);
            if (StrUtil.isBlank(result)) {
                return ErrorEnum.DOMAIN_ERROR.buildFailure();
            }
            LoginResult loginResult = com.alibaba.fastjson.JSONObject.parseObject(result, LoginResult.class);
            if (loginResult.getCode() == 1000) {
                return SingleResponse.of(loginResult.getLoginUrl());
            }
        }
        return SingleResponse.buildSuccess();
    }

    @Data
    public static class LoginResult {
        private Integer code;
        private String loginUrl;
        private String msg;
    }
}
