package com.nuwa.discovery.start.api.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.discovery.start.api.constants.NoticeMessageTypeEnum;
import com.nuwa.discovery.start.api.constants.RedisKeyConstant;
import com.nuwa.discovery.start.api.constants.SmsBizConstant;
import com.nuwa.discovery.start.api.constants.SmsStatusEnum;
import com.nuwa.discovery.start.api.controller.user.param.*;
import com.nuwa.discovery.start.api.event.BindMobileSendCodeEvent;
import com.nuwa.discovery.start.api.util.MemberJwtUtil;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.appconfig.entity.AppConfig;
import com.nuwa.infrastructure.discovery.database.appconfig.mapper.AppConfigMapper;
import com.nuwa.infrastructure.discovery.database.cooperation.entity.BusinessCooperation;
import com.nuwa.infrastructure.discovery.database.cooperation.mapper.BusinessCooperationMapper;
import com.nuwa.infrastructure.discovery.database.cooperation.service.BusinessCooperationService;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralRecord;
import com.nuwa.infrastructure.discovery.database.system.entity.SystemConfig;
import com.nuwa.infrastructure.discovery.database.system.mapper.SystemConfigMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.SmsCode;
import com.nuwa.infrastructure.discovery.database.user.entity.ThirdUser;
import com.nuwa.infrastructure.discovery.database.user.service.MemberIntegralRecordService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.database.user.service.SmsCodeService;
import com.nuwa.infrastructure.discovery.database.user.service.ThirdUserService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberIntegralVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberRankVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberVO;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Api(tags = {"C端登录相关"})
@Slf4j
@RestController
@RequestMapping("/member")
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ThirdUserService thirdUserService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private MemberIntegralRecordService memberIntegralRecordService;

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private BusinessCooperationService businessCooperationService;

    @Autowired
    private BusinessCooperationMapper businessCooperationMapper;

    @Value("${wechat.mpAppId}")
    private String mpAppId;

    @Value("${wechat.appSecret}")
    private String appSecret;


    private static final Snowflake SNOW_FLAKE = IdUtil.getSnowflake(1, 1);


    @ApiOperation(value = "微信公众号登录Test(code换取用户信息)")
    @PostMapping(value = "/login/wechat/auth2/test")
    public SingleResponse<?> loginByWxMpAuthTest(@Validated @RequestBody LoginByWechatOauthParam param) {
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(mpAppId);
        config.setSecret(appSecret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        try {
            WxMpUser wxMpUser = new WxMpUser();
            wxMpUser.setCity("杭州");
            wxMpUser.setOpenId(param.getCode().hashCode() + "");
            wxMpUser.setCountry("中国");
            wxMpUser.setProvince("浙江");
            wxMpUser.setHeadImgUrl("https://file.zhongzhiyou.cn/-1/IMAGE/20211112/d0de84694f5dfad893e2926e158c6a22.png");
            wxMpUser.setSex(1);
            wxMpUser.setNickname("飞鸟");

            String unionId = wxMpUser.getUnionId();
            String openId = wxMpUser.getOpenId();
            String headImgUrl = wxMpUser.getHeadImgUrl();
            Integer sex = wxMpUser.getSex();
            String province = wxMpUser.getProvince();
            String city = wxMpUser.getCity();
            String country = wxMpUser.getCountry();
            String nickname = wxMpUser.getNickname();
            ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).one();
            if (Objects.nonNull(thirdUserOne)) {
                Long userId = thirdUserOne.getUserId();
                Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
                String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), "1");
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                return SingleResponse.of(tokenStr);
            }
            Member member = new Member();
            member.setBalance(new BigDecimal("0"));
            member.setCreateTime(new Date());
            member.setIntegral(Double.valueOf("0"));
            member.setIp("127.0.0.1");
            member.setRegion(country + "|" + province + "|" + city);
            member.setUserImg(headImgUrl);
            member.setUserLevelId(1);
            member.setUserNike(nickname);
            member.setUserSex(0);

            boolean insert = member.insert();
            if (insert) {
                ThirdUser thirdUser = new ThirdUser();
                thirdUser.setUserId(member.getUserId().longValue());
                thirdUser.setCreateTime(new Date());
                thirdUser.setChannelCode("weixin");
                thirdUser.setOpenId(openId);
                boolean thirdUserFlag = thirdUser.insert();
                if (thirdUserFlag) {
                    String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(), "1");
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);

                    return SingleResponse.of(tokenStr);
                }
            }
        } catch (Exception ex) {
            log.error("获取微信oauth2getAccessToken失败", ex);
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }


    @ApiOperation(value = "微信公众号登录(code换取用户信息)")
    @PostMapping(value = "/login/wechat/auth2")
    public SingleResponse<?> loginByWxMpAuth2(@Validated @RequestBody LoginByWechatOauthParam param) {

        QueryWrapper<AppConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", param.getAppId());
        AppConfig appConfig = appConfigMapper.selectOne(queryWrapper);
        if(appConfig == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "对应appId未配置");
        }
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appConfig.getAppId());
        config.setSecret(appConfig.getAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        try {
            WxMpOAuth2AccessToken wxMpAuth2AccessToken = wxMpService.oauth2getAccessToken(param.getCode());
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpAuth2AccessToken, null);
       /*     WxMpUser wxMpUser = new WxMpUser();
            wxMpUser.setCity("杭州");
            wxMpUser.setOpenId(param.getCode().hashCode() + "");
            wxMpUser.setCountry("中国");
            wxMpUser.setProvince("浙江");
            wxMpUser.setHeadImgUrl("https://file.zhongzhiyou.cn/-1/IMAGE/20211112/d0de84694f5dfad893e2926e158c6a22.png");
            wxMpUser.setSex(1);
            wxMpUser.setNickname("飞鸟");*/

            String unionId = wxMpUser.getUnionId();
            String openId = wxMpUser.getOpenId();
            String headImgUrl = wxMpUser.getHeadImgUrl();
            Integer sex = wxMpUser.getSex();
            String province = wxMpUser.getProvince();
            String city = wxMpUser.getCity();
            String country = wxMpUser.getCountry();
            String nickname = wxMpUser.getNickname();
            ThirdUser thirdUserOne = thirdUserService.lambdaQuery().eq(ThirdUser::getOpenId, openId).one();
            if (Objects.nonNull(thirdUserOne)) {
                Long userId = thirdUserOne.getUserId();
                Member memberOne = memberService.lambdaQuery().eq(Member::getUserId, userId).one();
                String tokenStr = MemberJwtUtil.sign(memberOne.getUserId(), memberOne.getUserNike(), appConfig.getMchId());
                stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
                return SingleResponse.of(tokenStr);
            }
            Member member = new Member();
            member.setBalance(new BigDecimal("0"));
            member.setCreateTime(new Date());
            member.setIntegral(Double.valueOf("0"));
            member.setIp("127.0.0.1");
            member.setRegion(country + "|" + province + "|" + city);
            member.setUserImg(headImgUrl);
            member.setUserLevelId(1);
            member.setUserNike(nickname);
            member.setUserSex(0);
            member.setMchId(appConfig.getMchId());
            boolean insert = member.insert();
            if (insert) {
                memberIntegralRecordService.incrCount(1L);
                ThirdUser thirdUser = new ThirdUser();
                thirdUser.setUserId(member.getUserId().longValue());
                thirdUser.setCreateTime(new Date());
                thirdUser.setChannelCode("weixin");
                thirdUser.setOpenId(openId);
                boolean thirdUserFlag = thirdUser.insert();
                if (thirdUserFlag) {
                    String tokenStr = MemberJwtUtil.sign(member.getUserId(), member.getUserNike(),  appConfig.getMchId());
                    stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);

                    return SingleResponse.of(tokenStr);
                }
            }
        } catch (Exception ex) {
            log.error("获取微信oauth2getAccessToken失败", ex);
        }
        return SingleResponse.buildFailure("956", "授权登录失败");
    }

    @ApiOperation(value = "获取当前用户")
    @GetMapping(value = "/current")
    public SingleResponse<Member> getCurrentUser(UserAware userAware) {
        Member member = memberService.getById(userAware.getUserId());
        return SingleResponse.of(member);
    }

    @ApiOperation(value = "获取当前用户-新")
    @GetMapping(value = "/get/current")
    public SingleResponse<?> getCurrentMember(UserAware userAware) {
        MemberVO currentMember = memberService.getCurrentMember(userAware.getUserId());
        return SingleResponse.of(currentMember);
    }

    @ApiOperation(value = "获取当前用户达人等级信息")
    @GetMapping(value = "/get/current/integral")
    public SingleResponse<?> getCurrentIntegral(UserAware userAware) {
        MemberIntegralVO currentIntegral = memberService.getCurrentIntegral(userAware.getUserId());
        return SingleResponse.of(currentIntegral);
    }


    @ApiOperation(value = "获取当前达人成长值记录")
    @GetMapping(value = "/page/by_user_id")
    public SingleResponse<?> getMemberIntegralRecordPage(UserAware userAware, Long pageSize, Long pageNum) {
        IPage<MemberIntegralRecord> memberIntegralRecordPage = memberIntegralRecordService.getMemberIntegralRecordPage(pageSize, pageNum, userAware.getUserId());
        return SingleResponse.of(memberIntegralRecordPage);
    }

    @ApiOperation(value = "获取达人等级分页数据")
    @GetMapping(value = "/integral/page")
    public SingleResponse<?> getMemberIntegralLevelPage(Long pageSize, Long pageNum) {
        IPage<MemberIntegralLevel> memberIntegralLevelPage = memberIntegralRecordService.getMemberIntegralLevelPage(pageSize, pageNum);
        return SingleResponse.of(memberIntegralLevelPage);
    }

    @ApiOperation(value = "获取用户榜单")
    @GetMapping(value = "/get/ranking")
    public SingleResponse<?> getMemberRanking() {
        List<MemberRankVO> memberRankVO = memberService.getMemberRankVO();
        return SingleResponse.of(memberRankVO);
    }

    @ApiOperation(value = "获取用户数量")
    @GetMapping(value = "/get/count")
    public SingleResponse<?> getMemberCount() {
        int count = memberService.count();
        //加上5000用户基数
        count += 5000;
        return SingleResponse.of(count);
    }

    @ApiOperation(value = "获取banner用户")
    @GetMapping(value = "/get/banner")
    public SingleResponse<?> getBannerMember() {
        List<BusinessCooperation> bannerMember = businessCooperationService.list();
       //  List<String> bann = bannerMember.stream().map(BusinessCooperation::getName).collect(Collectors.toList());
        List<String> bann = new ArrayList<>();
        bann.add("汪新鹏");
        return SingleResponse.of(bann);
    }

    @ApiOperation(value = "发送验证码(手机号绑定)")
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> sendCode(@RequestBody @Validated BindMobileSendCodeParam param) {
        boolean checkFlag = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.BIND_MOBILE)
                .eq(SmsCode::getMobile, param.getMobile())
                .gt(SmsCode::getSendTime, DateUtil.offsetMinute(new Date(), -1))
                .count() > 0;
        if (checkFlag) {
            return SingleResponse.buildFailure("9556", "短信验证码发送太频繁，请稍后重试");
        }
        String code = RandomUtil.randomNumbers(4);
        String strTemplate = NoticeMessageTypeEnum.VALID_CODE.getMessage();
        String content = String.format(strTemplate, code);
        Date timestamp = new Date();
        SmsCode sms = new SmsCode();
        sms.setTitle("手机号绑定");
        sms.setCode(code);
        sms.setContent(content);
        sms.setCreateDate(timestamp);
        sms.setMobile(param.getMobile());
        sms.setSendTime(timestamp);
        sms.setDeadTime(DateUtil.offsetSecond(timestamp, 300));
        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
        sms.setSmsId(StrUtil.EMPTY);
        sms.setBizCode(SmsBizConstant.BIND_MOBILE);
        sms.setSendType(10);
        sms.setCheckStatus(10);
        boolean insert = sms.insert();
        if (insert) {
            domainEventPublisher.publishEvent(new BindMobileSendCodeEvent(sms.getId()));
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.VALID_CODE_SEND_FAILED.buildFailure();
    }

    @ApiOperation(value = "手机号绑定")
    @RequestMapping(value = "bindMobile", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> bindMobile(@RequestBody @Validated BindMobileParam param, UserAware userAware) {
        SmsCode smsCode = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.BIND_MOBILE)
                .eq(SmsCode::getMobile, param.getMobile())
                .eq(SmsCode::getCheckStatus, 10)
                .gt(SmsCode::getDeadTime, new Date())
                .orderByDesc(SmsCode::getSendTime)
                .last("limit 1")
                .one();
        if (BeanUtil.isEmpty(smsCode) || !smsCode.getCode().equals(param.getCode())) {
            return ErrorEnum.CODE_CHECK_FAILED.buildFailure();
        }

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", param.getMobile());
        queryWrapper.eq("mch_id", param.getMchId());
        Member member = memberService.getOne(queryWrapper);
        if(member != null ){
            return ErrorEnum.MOBILE_IS_EXIST.buildFailure();
        }
        smsCodeService.lambdaUpdate()
                .set(SmsCode::getCheckStatus, 20)
                .eq(SmsCode::getCode, param.getCode())
                .eq(SmsCode::getMobile, param.getMobile())
                .update();


        boolean update = memberService.lambdaUpdate()
                .set(Member::getUserPhone, param.getMobile())
                .set(Member::getUpdateTime, new Date())
                .eq(Member::getUserId, userAware.getUserId())
                .update();

        if (!update) {
            return SingleResponse.buildFailure("9654", "绑定手机号失败");
        }else{
            Member byId = memberService.getById(userAware.getUserId());
            if (byId.getUserNumber() == null) {
                //雪花算法生成用户编号
                long userNumber = SNOW_FLAKE.nextId();
                memberService.lambdaUpdate()
                        .set(Member::getUserNumber, userNumber)
                        .eq(Member::getUserId, userAware.getUserId())
                        .update();
                MemberIntegralRecord memberIntegralRecord = new MemberIntegralRecord();
                memberIntegralRecord.setUserId(Convert.toInt(userAware.getUserId()));
                memberIntegralRecord.setIntegralCount(100);
                memberIntegralRecord.setEventContent("新用户注册");
                memberIntegralRecord.setEventId(1);
                memberIntegralRecord.setEventType(1);
                memberIntegralRecordService.addMemberIntegral(memberIntegralRecord);
            }

        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "微信授权签名")
    @GetMapping(value = "/sign")
    public SingleResponse<WxJsapiSignature> sign(SignParam cmd) throws WxErrorException, UnsupportedEncodingException {
        QueryWrapper<AppConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", cmd.getAppId());
        AppConfig appConfig = appConfigMapper.selectOne(queryWrapper);
        if(appConfig == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "对应appId未配置");
        }
        String url = cmd.getUrl();
        log.info("url:{}", url);
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appConfig.getAppId());
        config.setSecret(appConfig.getAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        String jsapiTicket = wxMpService.getJsapiTicket();
        log.info("jsapiTicket:{}", jsapiTicket);
        try {
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
            return SingleResponse.of(wxJsapiSignature);
        } catch (WxErrorException ex) {
            log.error("获取签名失败", ex);
        }
        return SingleResponse.buildFailure("9632", "获取签名失败");
    }


    @ApiOperation(value = "商家累计入驻")
    @GetMapping(value = "/business/count")
    public SingleResponse<?> businessCount(){
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conf_key","business_count");
        SystemConfig systemConfig = systemConfigMapper.selectOne(queryWrapper);
        return SingleResponse.of(systemConfig.getConfValue());
    }

    @ApiOperation(value = "更新用户数据")
    @PostMapping(value = "/update/member")
    public SingleResponse<?> updateMember(@RequestBody MemberUpdateParam memberUpdateParam, UserAware userAware) {
        Member member = new Member();
        member.setUserId(Convert.toInt(userAware.getUserId()));
        if(StrUtil.isNotBlank(memberUpdateParam.getUserImg())){
            member.setUserImg(memberUpdateParam.getUserImg());
        }
        if(StrUtil.isNotBlank(memberUpdateParam.getUserNike())){
            member.setUserNike(memberUpdateParam.getUserNike());
        }
        memberService.updateById(member);
        return SingleResponse.buildSuccess();
    }
}
