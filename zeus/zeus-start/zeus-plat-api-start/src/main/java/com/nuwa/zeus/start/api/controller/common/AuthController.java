package com.nuwa.zeus.start.api.controller.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nuwa.app.zeus.command.auth.LoginCmdExe;
import com.nuwa.app.zeus.command.auth.LoginMobileCmdExe;
import com.nuwa.app.zeus.command.auth.qry.GetCurrentUserInfoQryExe;
import com.nuwa.client.zeus.dto.clientobject.auth.LoginCmd;
import com.nuwa.client.zeus.dto.clientobject.auth.LoginMobileCmd;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetCurrentUserInfoQry;
import com.nuwa.client.zeus.dto.domainevent.sms.LoginSucceededEvent;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.shiro.starter.JWTToken;
import com.nuwa.infrastructure.zeus.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.zeus.constant.SmsBizConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.enums.SmsStatusEnum;
import com.nuwa.zeus.start.api.util.IpAddressUtil;
import com.nuwa.zeus.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.zeus.start.api.config.shiro.util.JWTUtil;
import com.nuwa.zeus.start.api.constants.NoticeMessageTypeEnum;
import com.nuwa.zeus.start.api.constants.RedisKeyConstant;
import com.nuwa.zeus.start.api.controller.common.param.MerchantRegisterSendCodeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * AuthController
 *
 * @author hy
 * @date 2021/5/25 14:20
 * @since 1.0.0
 */
@Api(tags = {"登录"})
@RestController
public class AuthController {

    @Autowired
    private LoginCmdExe loginCmdExe;

    @Autowired
    private LoginMobileCmdExe loginMobileCmdExe;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private GetCurrentUserInfoQryExe getCurrentUserInfoQryExe;

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "用户名登录")
    @IgnoreAuth
    @PostMapping(value = "login/byUserName")
    public SingleResponse login(@Validated @RequestBody LoginCmd cmd, HttpServletRequest request) {
        if (StrUtil.isNotBlank(cmd.getUuid())) {
            String code = stringRedisTemplate.opsForValue().get(cmd.getUuid());
            if (code == null) {
                return ErrorEnum.LOGIN_VALID_CODE_INVALID.buildFailure();
            }
            // 判断验证码
            if (!code.equalsIgnoreCase(cmd.getCode())) {
                return ErrorEnum.LOGIN_VALID_CODE_INVALID.buildFailure();
            }
        }

        SingleResponse<BaseUser> adminUserResp = loginCmdExe.execute(cmd);
        if (!adminUserResp.isSuccess()) {
            return adminUserResp;
        }

        String mchName = "";
        BaseUser user = adminUserResp.getData();
        Merchant merchant = merchantService.getById(user.getTenantId());
        if (Objects.nonNull(merchant)) {
            mchName = merchant.getMchName();
        }
        Subject subject = SecurityUtils.getSubject();
        String tokenStr = JWTUtil.sign(user.getUsername(), user.getId().longValue(), user.getTenantId(), mchName);
        JWTToken token = new JWTToken(tokenStr);
        subject.login(token);

        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + JWTUtil.getTokenId(tokenStr), JSONUtil.toJsonStr(user), JWTUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);

        String ip = IpAddressUtil.getIpAddress(request);
        String browser = request.getHeader("User-Agent");

        if (StrUtil.isNotBlank(tokenStr)) {
            domainEventPublisher.publishEvent(new LoginSucceededEvent(user.getId().longValue(), user.getUsername(), ip, browser));
        }

        return SingleResponse.of(tokenStr);
    }

    @ApiOperation(value = "手机号登录")
    @IgnoreAuth
    @PostMapping(value = "login/byMobile")
    public SingleResponse login(@Validated @RequestBody LoginMobileCmd cmd, HttpServletRequest request) {
        SingleResponse<BaseUser> adminUserResp = loginMobileCmdExe.execute(cmd);
        if (!adminUserResp.isSuccess()) {
            return adminUserResp;
        }

        BaseUser user = adminUserResp.getData();
        Subject subject = SecurityUtils.getSubject();
        String mchName = "";
        Merchant merchant = merchantService.getById(user.getTenantId());
        if (Objects.nonNull(merchant)) {
            mchName = merchant.getMchName();
        }
        String tokenStr = JWTUtil.sign(user.getUsername(), user.getId().longValue(), user.getTenantId(), mchName);
        JWTToken token = new JWTToken(tokenStr);
        subject.login(token);

        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + JWTUtil.getTokenId(tokenStr), JSONUtil.toJsonStr(user), JWTUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);

        String ip = IpAddressUtil.getIpAddress(request);
        String browser = request.getHeader("User-Agent");

        if (StrUtil.isNotBlank(tokenStr)) {
            domainEventPublisher.publishEvent(new LoginSucceededEvent(user.getId().longValue(), user.getUsername(), ip, browser));
        }

        return SingleResponse.of(tokenStr);
    }

    @ApiOperation(value = "发送验证码(手机号登陆)")
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse sendCode(@RequestBody @Validated MerchantRegisterSendCodeParam param) {
        Integer count = baseUserService.lambdaQuery().eq(BaseUser::getMobilePhone, param.getMobile()).count();
        if (count == 0) {
            return ErrorEnum.ADMIN_DOES_NOT_EXIST.buildFailure();
        }
        boolean checkFlag = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_LOGIN)
                .eq(SmsCode::getMobile, param.getMobile())
                .gt(SmsCode::getSendTime, DateUtil.offsetMinute(new Date(), -1))
                .count() > 0;
        if (checkFlag) {
            return ErrorEnum.VALID_CODE_TO_BUSY.buildFailure();
        }
        String code = RandomUtil.randomNumbers(4);
        String strTemplate = NoticeMessageTypeEnum.VALID_CODE.getMessage();
        String content = String.format(strTemplate, code);
        Date timestamp = new Date();
        SmsCode sms = new SmsCode();
        sms.setTitle("手机号登陆");
        sms.setCode(code);
        sms.setContent(content);
        sms.setCreateDate(timestamp);
        sms.setMobile(param.getMobile());
        sms.setSendTime(timestamp);
        sms.setDeadTime(DateUtil.offsetSecond(timestamp, 300));
        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
        sms.setSmsId(StrUtil.EMPTY);
        sms.setBizCode(SmsBizConstant.MCH_LOGIN);
        sms.setSendType(10);
        sms.setCheckStatus(10);
        boolean insert = sms.insert();
        if (insert) {
            domainEventPublisher.publishEvent(new PersistentSmsCodeSucceededEvent(sms.getId()));
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.VALID_CODE_SEND_FAILED.buildFailure();
    }

    @ApiOperation(value = "注销")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public SingleResponse loginOut(String token) {
        stringRedisTemplate.delete(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + JWTUtil.getTokenId(token));
        return SingleResponse.of("注销成功");
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/user/getCurrentUserByToken", method = RequestMethod.GET)
    public SingleResponse<GetCurrentUserInfoQryExe.AdminUserVO> getCurrentUserByToken(GetCurrentUserInfoQry cmd, UserAware userAware) {
        return getCurrentUserInfoQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取登录验证码")
    @IgnoreAuth
    @GetMapping("/captcha")
    public void getImageCode(HttpServletResponse response, @RequestParam("uuid") String uuid) throws Exception {
        //禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //设置响应格式为png图片
        response.setContentType("image/png");

        //为验证码创建一个文本
        // String codeText = defaultKaptcha.createText();
        String codeText = RandomUtil.randomNumbers(4);
        //将验证码存到redis
        stringRedisTemplate.opsForValue().set(uuid, codeText, 60 * 1000, TimeUnit.SECONDS);
        // 用创建的验证码文本生成图片
        BufferedImage bi = defaultKaptcha.createImage(codeText);
        ServletOutputStream out = response.getOutputStream();
        //写出图片
        ImageIO.write(bi, "png", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
