package com.nuwa.ticket.start.api.controller.user;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.nacos.common.util.Md5Utils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.zeus.api.sms.SmsClientI;
import com.nuwa.client.zeus.api.sms.param.SendSmsCodeParam;
import com.nuwa.client.zeus.api.sms.param.VerifySmsCodeParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.member.service.ThirdUserService;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUserCenter;
import com.nuwa.infrastructure.ticket.database.one.service.OneUserCenterService;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinSender;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.Code2SessionReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.Code2SessionResp;
import com.nuwa.ticket.start.api.constants.NoticeMessageTypeEnum;
import com.nuwa.ticket.start.api.constants.RedisKeyConstant;
import com.nuwa.ticket.start.api.controller.one.param.ModifyIdentityAuthParam;
import com.nuwa.ticket.start.api.controller.one.param.RegisterOrLoginParam;
import com.nuwa.ticket.start.api.controller.one.param.SendRegisterSmsCodeParam;
import com.nuwa.ticket.start.api.controller.one.vo.UserCenterLoginVO;
import com.nuwa.ticket.start.api.controller.user.param.*;
import com.nuwa.ticket.start.api.controller.user.vo.MemberVO;
import com.nuwa.ticket.start.api.util.DecryptUtil;
import com.nuwa.ticket.start.api.util.MemberJwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author hy
 */
@Api(tags = {"C端登录相关"})
@Slf4j
@RestController
@RequestMapping("/member")
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThirdUserService thirdUserService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @Autowired
    private SmsClientI smsClientI;

    @Autowired
    private OneUserCenterService oneUserCenterService;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @ApiOperation(value = "抖音小程序登录(code换取用户信息)")
    @PostMapping(value = "/login/douyin/auth2")
    public SingleResponse<?> loginByDouYinAuth(@Validated @RequestBody LoginByDouyinOauthParam param, HttpServletRequest request) {
        log.info("loginByDouYinAuth param:{}", param);
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getAppId()).last("limit 1").one();
        String openId = null;
        String sessionKey = null;
        Code2SessionReq code2SessionReq = new Code2SessionReq();
        code2SessionReq.setCode(param.getCode());
        code2SessionReq.setAppId(param.getAppId());
        code2SessionReq.setSecret(appConf.getSecret());
        ChannelResult<Code2SessionResp> code2SessionRespChannelResult = DouYinSender.code2Session(code2SessionReq);
        if (code2SessionRespChannelResult.isSuccessful()) {
            log.info(JSONUtil.toJsonStr(code2SessionRespChannelResult.getData()));
            Code2SessionResp data = code2SessionRespChannelResult.getData();
            if (Objects.nonNull(data)) {
                openId = data.getData().getOpenid();
                sessionKey = data.getData().getSession_key();
            }
        } else {
            return SingleResponse.buildFailure("9865", "授权登录失败");
        }

        String headImgUrl = "https://data.ctpaas.com/oss/thor/1/IMAGE/20210513/564383d95efc9fa9cc66f69ec79b981b.png";
        Integer sex = 0;
        String nickname = "";
        ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).eq(ThirdUser::getOutAppId, param.getAppId()).one();
        if (Objects.nonNull(thirdUserOne)) {
            Long userId = thirdUserOne.getUserId();
            Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
            String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), memberOne.getMchId());
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            if (StrUtil.isNotEmpty(sessionKey)) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + memberOne.getUserId(), sessionKey, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            }
            return SingleResponse.of(tokenStr);
        }
        Member member = new Member();
        member.setBalance(new BigDecimal("0"));
        member.setCreateTime(new Date());
        member.setIp(getIP(request));
        member.setRegion(null);
        member.setUserImg(headImgUrl);
        member.setUserLevelId(0);
        member.setUserNike(nickname);
        member.setMchId(param.getMchId());
        member.setUserSex(0);
        member.setSrcAppId(appConf.getId());
        member.setSrcAppName(appConf.getAppName());

        boolean insert = member.insert();
        if (insert) {
            ThirdUser thirdUser = new ThirdUser();
            thirdUser.setUserId(member.getUserId().longValue());
            thirdUser.setCreateTime(new Date());
            thirdUser.setChannelCode("douyin");
            thirdUser.setOpenId(openId);
            thirdUser.setOutAppId(param.getAppId());
            boolean thirdUserFlag = thirdUser.insert();
            if (thirdUserFlag) {
                String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), param.getMchId());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                if (sessionKey != null && StrUtil.isNotEmpty(sessionKey)) {
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + member.getUserId(), sessionKey, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                }
                return SingleResponse.of(tokenStr);
            }
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }

    @ApiOperation(value = "微信小程序登录")
    @PostMapping(value = "/login/wechat")
    public SingleResponse<?> loginByWx(@RequestBody LoginByWechatParam param, HttpServletRequest request) {
        log.info("LoginByWechatCmd param:{}", param);
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getAppId()).last("limit 1").one();
        String openId = null;
        // String unionId = null;
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(appConf.getOutAppId());
        config.setSecret(appConf.getSecret());
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);
        try {
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(param.getCode());
            openId = result.getOpenid();
            //  unionId = result.getUnionid();

        } catch (Exception ex) {
            log.error("loginByWx", ex);
            return SingleResponse.buildFailure("9865", "授权登录失败");
        }
        int sex = 0;
        String nickname = "";
        String headImgUrl = "";
        LoginByWechatParam.WeChatUserCo userCo = param.getUserCo();
        if (Objects.nonNull(userCo)) {
            headImgUrl = userCo.getAvatarUrl();
            if (Objects.nonNull(userCo.getGender())) {
                sex = Integer.parseInt(userCo.getGender());
            }
            nickname = userCo.getNickName();
        }

        ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).eq(ThirdUser::getOutAppId, param.getAppId()).one();
        if (Objects.nonNull(thirdUserOne)) {
            Long userId = thirdUserOne.getUserId();
            Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
            String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), memberOne.getMchId());
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            if (StrUtil.isNotEmpty(openId)) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + memberOne.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            }

            memberService.lambdaUpdate().set(Member::getUserNike, nickname).set(Member::getUserImg, headImgUrl).set(Member::getRegion, userCo.getCountry() + "|" + userCo.getProvince() + "|" + userCo.getCity()).eq(Member::getUserId, userId).update();

            return SingleResponse.of(tokenStr);
        }
        Member member = new Member();
        member.setBalance(new BigDecimal("0"));
        member.setCreateTime(new Date());
        member.setIp(getIP(request));
        if (Objects.nonNull(userCo)) {
            member.setRegion(userCo.getCountry() + "|" + userCo.getProvince() + "|" + userCo.getCity());
        }
        member.setUserImg(headImgUrl);
        member.setUserLevelId(0);
        member.setUserNike(nickname);
        member.setMchId(appConf.getMchId());
        member.setUserSex(sex);
        member.setSrcAppId(appConf.getId());
        member.setSrcAppName(appConf.getAppName());

        boolean insert = member.insert();
        if (insert) {
            ThirdUser thirdUser = new ThirdUser();
            thirdUser.setUserId(member.getUserId().longValue());
            thirdUser.setCreateTime(new Date());
            thirdUser.setChannelCode(appConf.getAppType());
            thirdUser.setOpenId(openId);
            thirdUser.setOutAppId(param.getAppId());
            boolean thirdUserFlag = thirdUser.insert();
            if (thirdUserFlag) {
                String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), appConf.getMchId());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                if (openId != null && StrUtil.isNotEmpty(openId)) {
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + member.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                }
                return SingleResponse.of(tokenStr);
            }
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }

    @ApiOperation(value = "微信公众号登录")
    @PostMapping(value = "/login/wechatMp")
    public SingleResponse<?> loginByWechatMp(@RequestBody LoginByWechatMpParam param, HttpServletRequest request) {
        log.info("LoginByWechatMpParam param:{}", param);
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getAppId()).last("limit 1").one();
        String openId = null;
        // String unionId = null;
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appConf.getOutAppId());
        config.setSecret(appConf.getSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        WxMpUser wxMpUser = null;
        try {
            WxMpOAuth2AccessToken wxMpAuth2AccessToken = wxMpService.oauth2getAccessToken(param.getCode());
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpAuth2AccessToken, null);
            openId = wxMpUser.getOpenId();
        } catch (Exception ex) {
            log.error("授权异常", ex);
            return SingleResponse.buildFailure("9865", "授权登录失败");
        }
        int sex = 0;
        ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).eq(ThirdUser::getOutAppId, param.getAppId()).one();
        if (Objects.nonNull(thirdUserOne)) {
            Long userId = thirdUserOne.getUserId();
            Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
            String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), memberOne.getMchId());
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            if (StrUtil.isNotEmpty(openId)) {
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + memberOne.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            }

            memberService.lambdaUpdate().set(Member::getUserNike, wxMpUser.getNickname()).set(Member::getUserImg, wxMpUser.getHeadImgUrl()).set(Member::getRegion, wxMpUser.getCountry() + "|" + wxMpUser.getProvince() + "|" + wxMpUser.getCity()).eq(Member::getUserId, userId).update();

            return SingleResponse.of(tokenStr);
        }
        Member member = new Member();
        member.setBalance(new BigDecimal("0"));
        member.setCreateTime(new Date());
        member.setIp(getIP(request));
        member.setRegion(wxMpUser.getCountry() + "|" + wxMpUser.getProvince() + "|" + wxMpUser.getCity());
        member.setUserImg(wxMpUser.getHeadImgUrl());
        member.setUserLevelId(0);
        member.setUserNike(wxMpUser.getNickname());
        member.setMchId(appConf.getMchId());
        member.setUserSex(sex);
        member.setSrcAppId(appConf.getId());
        member.setSrcAppName(appConf.getAppName());

        boolean insert = member.insert();
        if (insert) {
            ThirdUser thirdUser = new ThirdUser();
            thirdUser.setUserId(member.getUserId().longValue());
            thirdUser.setCreateTime(new Date());
            thirdUser.setChannelCode(appConf.getAppType());
            thirdUser.setOpenId(openId);
            thirdUser.setOutAppId(param.getAppId());
            boolean thirdUserFlag = thirdUser.insert();
            if (thirdUserFlag) {
                String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), appConf.getMchId());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                if (openId != null && StrUtil.isNotEmpty(openId)) {
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + member.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                }
                return SingleResponse.of(tokenStr);
            }
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }

    @ApiOperation(value = "支付宝小程序登录")
    @PostMapping(value = "/login/loginAliPay")
    public SingleResponse<String> loginAliPay(@RequestBody LoginByAlipayParam param, HttpServletRequest request) {
        log.info("LoginByAlipayParam param:{}", param);
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getAppId()).last("limit 1").one();
        String appId = null;
        boolean isTemplate = StrUtil.isNotEmpty(appConf.getAppAuthCode());
        if (isTemplate) {
            String appTemplateId = appConf.getAppTemplateId();
            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appTemplateId).one();
            Assert.notNull(psTemplateInfo, "模板配置错误");
            appId = psTemplateInfo.getAppId();
        } else {
            appId = appConf.getOutAppId();
        }
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, appConf.getMchPrivateKey(), "json", "GBK", appConf.getChannelPublicKey(), "RSA2");
        AlipaySystemOauthTokenRequest authRequest = new AlipaySystemOauthTokenRequest();
        if (isTemplate) {
            //第三方应用代小程序模板调用接口必须传入
            authRequest.putOtherTextParam("app_auth_token", appConf.getAppAuthCode());
        }
        authRequest.setGrantType("authorization_code");
        authRequest.setCode(param.getCode());
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = alipayClient.execute(authRequest);
            log.info("authRequest -> res:{}", JSONUtil.toJsonStr(response));
        } catch (AlipayApiException e) {
            log.error("(mchAppId:{}) 获取支付宝授权AccessToken失败", appConf.getId(), e);
            e.printStackTrace();
        }
        String openId = null;

        assert response != null;
        if (response.isSuccess()) {
            AlipayUserInfoShareRequest req = new AlipayUserInfoShareRequest();
            if (isTemplate) {
                //第三方应用代小程序模板调用接口必须传入
                req.putOtherTextParam("app_auth_token", appConf.getAppAuthCode());
            }
            AlipayUserInfoShareResponse res = null;
            try {
                res = alipayClient.execute(req, response.getAccessToken());
                log.info("AlipayUserInfoShare -> res:{}", JSONUtil.toJsonPrettyStr(res));
                openId = res.getUserId();
            } catch (AlipayApiException e) {
                log.error("(mchAppId:{}) 获取支付宝用户信息失败", appConf.getId(), e);
                e.printStackTrace();
            }
            assert res != null;
            if (response.isSuccess()) {
                openId = response.getUserId();
                ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).eq(ThirdUser::getOutAppId, param.getAppId()).one();
                if (Objects.nonNull(thirdUserOne)) {
                    Long userId = thirdUserOne.getUserId();
                    Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
                    String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), memberOne.getMchId());
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                    if (StrUtil.isNotEmpty(openId)) {
                        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + memberOne.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                    }

                    memberService.lambdaUpdate().set(Member::getUserNike, res.getNickName()).set(Member::getUserImg, res.getAvatar()).set(Member::getRegion, res.getAddress()).eq(Member::getUserId, userId).update();

                    return SingleResponse.of(tokenStr);
                }
                Member member = new Member();
                member.setBalance(new BigDecimal("0"));
                member.setCreateTime(new Date());
                member.setIp(getIP(request));
                member.setRegion(res.getCountryCode() + "|" + res.getProvince() + "|" + res.getCity());
                member.setUserImg(res.getAvatar());
                member.setUserLevelId(0);
                member.setUserNike(res.getNickName());
                member.setMchId(appConf.getMchId());
                if ("F".equals(res.getGender())) {
                    member.setUserSex(2);
                }
                member.setUserSex(1);
                member.setSrcAppId(appConf.getId());
                member.setSrcAppName(appConf.getAppName());

                boolean insert = member.insert();
                if (insert) {
                    ThirdUser thirdUser = new ThirdUser();
                    thirdUser.setUserId(member.getUserId().longValue());
                    thirdUser.setCreateTime(new Date());
                    thirdUser.setChannelCode("alipay_mini");
                    thirdUser.setOpenId(openId);
                    thirdUser.setOutAppId(param.getAppId());
                    boolean thirdUserFlag = thirdUser.insert();
                    if (thirdUserFlag) {
                        String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), appConf.getMchId());
                        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                        if (openId != null && StrUtil.isNotEmpty(openId)) {
                            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + member.getUserId(), openId, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                        }
                        return SingleResponse.of(tokenStr);
                    }
                }
                return SingleResponse.buildFailure("956", "授权登录失败");
            } else {
                return SingleResponse.buildFailure(res.getCode(), res.getSubMsg());
            }
        } else {
            return SingleResponse.buildFailure(response.getCode(), response.getSubMsg());
        }
    }

    @ApiOperation(value = "test")
    @PostMapping(value = "/login/douyin/auth2/test")
    public SingleResponse<?> loginByDouYinAuthTest(@Validated @RequestBody LoginByDouyinOauthParam param, HttpServletRequest request) {
        log.info("loginByDouYinAuth param:{}", param);
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getAppId()).last("limit 1").one();
        String openId = Md5Utils.getMD5(param.getCode(), "utf-8");

        String headImgUrl = "https://data.ctpaas.com/oss/thor/1/IMAGE/20210513/564383d95efc9fa9cc66f69ec79b981b.png";
        Integer sex = 0;
        String province = "";
        String city = "";
        String country = "";
        String nickname = "money";
        ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).eq(ThirdUser::getOutAppId, param.getAppId()).one();
        if (Objects.nonNull(thirdUserOne)) {
            Long userId = thirdUserOne.getUserId();
            Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
            String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), 979L);
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            return SingleResponse.of(tokenStr);
        }
        Member member = new Member();
        member.setBalance(new BigDecimal("0"));
        member.setCreateTime(new Date());
        member.setIp(getIP(request));
        member.setRegion(country + "|" + province + "|" + city);
        member.setUserImg(headImgUrl);
        member.setUserLevelId(0);
        member.setUserNike(nickname);
        member.setMchId(param.getMchId());
        member.setUserSex(0);

        boolean insert = member.insert();
        if (insert) {
            ThirdUser thirdUser = new ThirdUser();
            thirdUser.setUserId(member.getUserId().longValue());
            thirdUser.setCreateTime(new Date());
            thirdUser.setChannelCode("douyin");
            thirdUser.setOpenId(openId);
            thirdUser.setOutAppId(param.getAppId());
            boolean thirdUserFlag = thirdUser.insert();
            if (thirdUserFlag) {
                String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), param.getMchId());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                return SingleResponse.of(tokenStr);
            }
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }

    @ApiOperation(value = "抖音小程序更新用户信息")
    @PostMapping(value = "/login/douyin/updateUserInfo")
    public SingleResponse<?> updateUserInfo(@Validated @RequestBody UpdateUserInfoByDouyinParam param, UserAware userAware) {
        log.info("UpdateUserInfoByDouyinParam param:{}", param);
       /* MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery()
                .eq(MerchantAppBaseConf::getMchId, userAware.getMchId())
                .last("limit 1")
                .one();*/
        try {
            String sessionKey = stringRedisTemplate.opsForValue().get(RedisKeyConstant.REDIS_KEY_DOUYIN + ":" + userAware.getUserId());
            String s = DecryptUtil.decryptStr(param.getEncryptedData(), sessionKey, param.getIv());
            log.info("decryptStr:{}", s);
            //{"nickName":"君临甬城","avatarUrl":"https://p3.douyinpic.com/aweme/100x100/aweme-avatar/tos-cn-avt-0015_898b64ea76e619b0ff9fc8d2932cb4ce.jpeg?from=4010531038","gender":1,"city":"宁波","province":"","country":"中国","language":"","openId":"69e9f97b-6fbc-44ae-a2d5-c919adb11ea2","unionId":"32312860-710a-4717-a535-c32bad64d8a5","watermark":{"appid":"tta4f5d2483bcc5c3a01","timestamp":1642730458}}
            JSONObject jsonObject = JSONUtil.parseObj(s);
            String nickName = jsonObject.getStr("nickName");
            String avatarUrl = jsonObject.getStr("avatarUrl");
            String gender = jsonObject.getStr("gender");
            String province = jsonObject.getStr("province");
            String country = jsonObject.getStr("country");
            String city = jsonObject.getStr("city");

            StringBuilder regionSb = new StringBuilder();
            if (StrUtil.isNotEmpty(country)) {
                regionSb.append(country);
            }
            if (StrUtil.isNotEmpty(province)) {
                if (regionSb.length() > 0) {
                    regionSb.append("|");
                }
                regionSb.append(province);
            }
            if (StrUtil.isNotEmpty(city)) {
                if (regionSb.length() > 0) {
                    regionSb.append("|");
                }
                regionSb.append(city);
            }
            memberService.lambdaUpdate().set(Member::getUserNike, nickName).set(Member::getUserImg, avatarUrl).set(Member::getUserSex, gender).set(Member::getRegion, regionSb.toString()).eq(Member::getUserId, userAware.getUserId()).update();
        } catch (Exception ex) {
            log.error("解密错误", ex);
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取当前用户")
    @GetMapping(value = "/current")
    public SingleResponse<MemberVO> getCurrentUser(UserAware userAware) {
        Member member = memberService.getById(userAware.getUserId());
        MemberVO vo = new MemberVO();
        BeanUtils.copyProperties(member, vo);
        if (StrUtil.isNotBlank(member.getUserPhone())) {
            OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, member.getUserPhone()).one();
            vo.setUserCenter(userCenter);
        }
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "修改用户中心昵称或头像")
    @RequestMapping(value = "modifyUserCenterInfo", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyUserCenterInfo(@RequestBody @Valid ModifyIdentityAuthParam param, UserAware userAware) {
        Member member = memberService.getById(userAware.getUserId());
        if (StrUtil.isNotEmpty(member.getUserPhone())) {
            Integer count = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserNike, param.getUserNike()).count();
            if (count > 0) {
                return SingleResponse.buildFailure("9871", "昵称不能重复");
            }
            boolean update = oneUserCenterService.lambdaUpdate()
                    .set(StrUtil.isNotBlank(param.getUserNike()), OneUserCenter::getUserNike, param.getUserNike())
                    .set(StrUtil.isNotBlank(param.getUserImg()), OneUserCenter::getUserImg, param.getUserImg())
                    .eq(OneUserCenter::getUserPhone, member.getUserPhone())
                    .update();
            return SingleResponse.buildSuccess();
        } else {
            return SingleResponse.buildFailure("9874", "未绑定手机号不允许修改");
        }
    }

    @ApiOperation(value = "发送验证码")
    @RequestMapping(value = "sendSmsCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> sendSmsCode(@RequestBody @Valid SendRegisterSmsCodeParam param) {
        String code = RandomUtil.randomNumbers(4);
        String strTemplate = NoticeMessageTypeEnum.VALID_CODE.getMessage();
        String content = String.format(strTemplate, code);
        SendSmsCodeParam req = new SendSmsCodeParam();
        req.setBizCode("one-registerOrlogin-sendcode");
        req.setContent(content);
        req.setCode(code);
        req.setMobile(param.getMobile());
        req.setTitle("一码通注册或登录验证码");
        SingleResponse<?> sendResult = smsClientI.send(req);
        if (sendResult.isSuccess()) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9871", "验证码发送失败");
    }

    @ApiOperation(value = "绑定手机号")
    @PostMapping(value = "/login/bindMobile")
    public SingleResponse<?> bindMobile(@Validated @RequestBody BindMobileParam param, HttpServletRequest request, UserAware userAware) {
        VerifySmsCodeParam req = new VerifySmsCodeParam();
        req.setCode(param.getCode());
        req.setMobile(param.getMobile());
        req.setBizCode("one-registerOrlogin-sendcode");
        SingleResponse<?> sendResult = smsClientI.verify(req);
        if (sendResult.isSuccess()) {

            memberService.lambdaUpdate().set(Member::getUserPhone, param.getMobile()).set(Member::getUpdateTime, new Date()).eq(Member::getUserId, userAware.getUserId()).update();

            OneUserCenter oneUserCenter;
            Integer count = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, param.getMobile()).count();
            if (count > 0) {
                oneUserCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, param.getMobile()).one();
            } else {
                String defaultHeadId;
                if (springProfilesActive.equalsIgnoreCase("test")) {
                    defaultHeadId = "14395";
                }else{
                    defaultHeadId = "15207";
                }
                oneUserCenter = new OneUserCenter();
                oneUserCenter.setUserPhone(param.getMobile());
                oneUserCenter.setMchId(userAware.getMchId());
                oneUserCenter.setIp(getIP(request));
                oneUserCenter.setUserImg(defaultHeadId);
                oneUserCenter.setUserNike(RandomUtil.randomString(10));
                oneUserCenter.insert();
            }
            return SingleResponse.of(oneUserCenter);
        } else {
            return SingleResponse.buildFailure("9871", "验证码错误");
        }
    }

    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
