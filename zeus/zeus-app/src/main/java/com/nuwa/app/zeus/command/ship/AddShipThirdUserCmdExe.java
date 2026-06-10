package com.nuwa.app.zeus.command.ship;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.client.zeus.dto.clientobject.ship.AddShipThirdUserCmd;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class AddShipThirdUserCmdExe extends AbstractCmdExe<AddShipThirdUserCmd, SingleResponse> {

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private MerchantAppUrlService merchantAppUrlService;

    @Autowired
    private ShipThirdUserService shipThirdUserService;

    @Override
    protected SingleResponse handle(AddShipThirdUserCmd cmd) {
        MerchantApp merchantApp = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getAppId, cmd.getMerchantAppId())
                .eq(MerchantApp::getMerchantId, cmd.getMerchantId())
                .one();
        if (BeanUtil.isEmpty(merchantApp)) {
            return ErrorEnum.INFO_CHANGED.buildFailure();
        }
        merchantAppUrlService.lambdaUpdate()
                .eq(MerchantAppUrl::getAppId, merchantApp.getAppId())
                .eq(MerchantAppUrl::getMchId, merchantApp.getMerchantId())
                .set(MerchantAppUrl::getLoginSubmitUrl, cmd.getLoginSubmitUrl())
                .set(MerchantAppUrl::getScenicCode, cmd.getScenicCode())
                .update();

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", cmd.getUsername());
        map.put("password", cmd.getPassword());
        map.put("companycode", cmd.getScenicCode());
        map.put("domain", cmd.getLoginSubmitUrl());
        String result = HttpRequest.post(StrUtil.removeSuffix(cmd.getLoginSubmitUrl(), "/") + "/partner/login/verify").body(JSONUtil.toJsonStr(map)).execute().body();
        log.info("result:{}", result);
        if (StrUtil.isBlank(result)) {
            return ErrorEnum.DOMAIN_ERROR.buildFailure();
        }
        LoginResult loginResult = JSONObject.parseObject(result, LoginResult.class);
        if (loginResult.getCode() == 1001) {
            return ErrorEnum.ADMIN_DOES_NOT_EXIST.buildFailure();
        }
        if (loginResult.getCode() == 1002) {
            return ErrorEnum.PASSWORD_WRONG.buildFailure();
        }
        if (loginResult.getCode() == 1003) {
            return ErrorEnum.ADMIN_FORBID.buildFailure();
        }
        if (loginResult.getCode() == 1004) {
            return ErrorEnum.ADMIN_FORBID.buildFailure();
        }
        if (loginResult.getCode() == 1000) {
            ShipThirdUser shipThirdUser = new ShipThirdUser();
            BeanUtils.copyProperties(cmd, shipThirdUser);
            shipThirdUser.setUserId(cmd.getUserAware().getMchUserId());
            shipThirdUserService.remove(new LambdaQueryWrapper<ShipThirdUser>()
                    .eq(ShipThirdUser::getMerchantId, cmd.getMerchantId())
                    .eq(ShipThirdUser::getUserId, cmd.getUserAware().getMchUserId())
                    .eq(ShipThirdUser::getMerchantAppId, cmd.getMerchantAppId()));

            boolean save = shipThirdUserService.save(shipThirdUser);
            if (save) {
                return SingleResponse.of(loginResult.getLoginUrl());
            }
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }

    @Data
    public static class LoginResult {
        private Integer code;
        private String loginUrl;
        private String msg;
    }

}
