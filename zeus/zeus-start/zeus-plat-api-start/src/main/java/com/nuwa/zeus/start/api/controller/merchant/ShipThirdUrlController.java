package com.nuwa.zeus.start.api.controller.merchant;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.app.zeus.command.ship.AddShipThirdUserCmdExe;
import com.nuwa.app.zeus.command.ship.JudgeShipThirdUserCmdExe;
import com.nuwa.client.zeus.dto.clientobject.ship.AddShipThirdUserCmd;
import com.nuwa.client.zeus.dto.clientobject.ship.JudgeShipThirdUserCmd;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.ship.entity.ShipThirdUser;
import com.nuwa.infrastructure.zeus.database.ship.service.ShipThirdUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.zeus.start.api.config.shiro.entity.User;
import com.nuwa.zeus.start.api.controller.merchant.param.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("judge")
@Api(tags = {"Erp和B2B单点登录接口"})
public class ShipThirdUrlController {

    @Autowired
    private JudgeShipThirdUserCmdExe judgeShipThirdUserCmdExe;

    @Autowired
    private AddShipThirdUserCmdExe addShipThirdUserCmdExe;

    @Autowired
    private ShipThirdUserService shipThirdUserService;

    @GetMapping("/listThirdUser")
    @ApiOperation(value = "获取当前用户已绑定用户列表")
    public SingleResponse listThirdUser(GetCurrentThirdUserListParam param, UserAware userAware) {
        List<ShipThirdUser> userList = shipThirdUserService.lambdaQuery()
                .eq(ShipThirdUser::getMerchantAppId, param.getAppId())
                .eq(ShipThirdUser::getUserId, userAware.getMchUserId())
                .list();
        return SingleResponse.of(userList);
    }

    @GetMapping("/listMerchantAppThirdUser")
    @ApiOperation(value = "获取当前商户,当前应用下已绑定用户列表")
    public SingleResponse listMerchantAppThirdUser(GetCurrentThirdUserListParam param, UserAware userAware) {
        List<ShipThirdUser> userList = shipThirdUserService.lambdaQuery()
                .eq(ShipThirdUser::getMerchantAppId, param.getAppId())
                .eq(ShipThirdUser::getMerchantId, userAware.getMchId())
                .list();
        return SingleResponse.of(userList);
    }

    @GetMapping("/checkAndGetAdminUrl")
    @ApiOperation(value = "检测用户状态，并返回登录地址")
    public SingleResponse checkAndGetAdminUrl(CheckUserStatusParam param, UserAware userAware) {
        ShipThirdUser shipUser = shipThirdUserService.lambdaQuery()
                .eq(ShipThirdUser::getId, param.getId())
                .one();
        if (Objects.isNull(shipUser)) {
            return SingleResponse.buildFailure("9865", "用户不存在");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", shipUser.getUsername());
        map.put("password", shipUser.getPassword());
        map.put("companycode", shipUser.getScenicCode());
        map.put("domain", shipUser.getAdminUrl());
        String result = null;
        try {
            result = HttpRequest.post(StrUtil.removeSuffix(shipUser.getAdminUrl(), "/") + "/partner/login/verify").body(JSONUtil.toJsonStr(map)).execute().body();
            log.info("result:{}", result);
        } catch (Exception ex) {
            log.error("HttpRequest.post url:{} error", StrUtil.removeSuffix(shipUser.getAdminUrl(), "/") + "/partner/login/verify", ex);
            return SingleResponse.buildFailure("9875", "绑定失败,登录地址不合法");
        }
        log.info("result:{}", result);
        if (StrUtil.isBlank(result)) {
            return ErrorEnum.DOMAIN_ERROR.buildFailure();
        }
        if (!JSONUtil.isJson(result)) {
            return SingleResponse.buildFailure("9875", "绑定失败,系统不支持免密登录");
        }
        JudgeShipThirdUserCmdExe.LoginResult loginResult = com.alibaba.fastjson.JSONObject.parseObject(result, JudgeShipThirdUserCmdExe.LoginResult.class);
        if (loginResult.getCode() == 1000) {
            return SingleResponse.of(loginResult.getLoginUrl());
        } else {
            return SingleResponse.buildFailure("9875", "账号状态异常");
        }
    }

    @GetMapping("/bindUserAccount")
    @ApiOperation(value = "绑定新用户")
    public SingleResponse bindUserAccount(BindShipThirdUserParam param, UserAware userAware) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", param.getUsername());
        map.put("password", param.getPassword());
        map.put("companycode", param.getScenicCode());
        map.put("domain", param.getLoginSubmitUrl());
        String result = null;
        try {
            result = HttpRequest.post(StrUtil.removeSuffix(param.getLoginSubmitUrl(), "/") + "/partner/login/verify").body(JSONUtil.toJsonStr(map)).execute().body();
            log.info("result:{}", result);
        } catch (Exception ex) {
            log.error("HttpRequest.post url:{} error", StrUtil.removeSuffix(param.getLoginSubmitUrl(), "/") + "/partner/login/verify", ex);
            return SingleResponse.buildFailure("9875", "绑定失败,登录地址不合法");
        }
        if (StrUtil.isBlank(result)) {
            return ErrorEnum.DOMAIN_ERROR.buildFailure();
        }
        if (!JSONUtil.isJson(result)) {
            return SingleResponse.buildFailure("9875", "绑定失败,系统不支持免密登录");
        }
        AddShipThirdUserCmdExe.LoginResult loginResult = JSONObject.parseObject(result, AddShipThirdUserCmdExe.LoginResult.class);
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
            BeanUtils.copyProperties(param, shipThirdUser);
            shipThirdUser.setRoleName(param.getUsername());
            shipThirdUser.setUserId(userAware.getMchUserId());
            shipThirdUser.setAdminUrl(param.getLoginSubmitUrl());
            shipThirdUser.setMerchantId(userAware.getMchId());
            shipThirdUser.setMerchantAppId(param.getMerchantAppId());
            shipThirdUser.setStatus(1);
            shipThirdUser.setLastLoginIp(userAware.getHostIp());
            shipThirdUser.setCreateTime(new Date());
            boolean save = shipThirdUserService.save(shipThirdUser);
            if (save) {
                return SingleResponse.of(loginResult.getLoginUrl());
            }
        }
        return SingleResponse.buildFailure("9875", "绑定账户失败");
    }

    @GetMapping("/bindUserAppJumpUrl")
    @ApiOperation(value = "绑定当前app,当前用户的跳转地址")
    public SingleResponse bindAppJumpUrl(BindAppJumpUrlParam param, UserAware userAware) {
        ShipThirdUser shipThirdUser = new ShipThirdUser();
        BeanUtils.copyProperties(param, shipThirdUser);
        shipThirdUser.setRoleName(param.getUsername());
        shipThirdUser.setUsername(param.getUsername());
        shipThirdUser.setUserId(userAware.getMchUserId());
        shipThirdUser.setAdminUrl(param.getJumpUrl());
        shipThirdUser.setMerchantId(userAware.getMchId());
        shipThirdUser.setMerchantAppId(param.getMerchantAppId());
        shipThirdUser.setStatus(1);
        shipThirdUser.setLastLoginIp(userAware.getHostIp());
        shipThirdUser.setCreateTime(new Date());
        boolean save = shipThirdUserService.save(shipThirdUser);
        if (save) {
            return SingleResponse.of(param.getJumpUrl());
        }
        return SingleResponse.buildFailure("9874", "绑定跳转地址失败");
    }

    @GetMapping("/getUserAppJumpUrl")
    @ApiOperation(value = "获取当前用户指定app跳转地址")
    public SingleResponse getUserAppJumpUrl(CheckUserStatusParam param, UserAware userAware) {
        ShipThirdUser shipThirdUser = shipThirdUserService.getById(param.getId());
        return SingleResponse.of(shipThirdUser.getAdminUrl());
    }

    @GetMapping("/unBindUserAccount")
    @ApiOperation(value = "用户解绑")
    public SingleResponse unBindUserAccount(UnBindShipThirdUserParam param, UserAware userAware) {
        boolean remove = shipThirdUserService.lambdaUpdate()
                .eq(ShipThirdUser::getId, param.getId())
                .eq(ShipThirdUser::getMerchantId, userAware.getMchId())
                .remove();
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "用户解绑操作失败");
    }

    @GetMapping("/judgeShipThirdUser")
    @ApiOperation(value = "判断是否已已登录过")
    public SingleResponse judgeShipThirdUser(JudgeShipThirdUserCmd cmd, UserAware userAware) {
        return judgeShipThirdUserCmdExe.execute(cmd);
    }

    @GetMapping("/addShipThirdUser")
    @ApiOperation(value = "首次登录绑定用户名密码")
    public SingleResponse addShipThirdUser(AddShipThirdUserCmd cmd, UserAware userAware) {
        return addShipThirdUserCmdExe.execute(cmd);
    }
}
