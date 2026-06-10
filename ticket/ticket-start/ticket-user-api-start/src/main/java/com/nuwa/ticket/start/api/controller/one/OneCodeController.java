package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneAdConfigPageQry;
import com.nuwa.client.zeus.api.sms.SmsClientI;
import com.nuwa.client.zeus.api.sms.param.SendSmsCodeParam;
import com.nuwa.client.zeus.api.sms.param.VerifySmsCodeParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.*;
import com.nuwa.infrastructure.ticket.database.one.param.OneAdConfigPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.*;
import com.nuwa.infrastructure.ticket.database.order.entity.OrderVoucher;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.UserVoucherOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserVoucherOrderListJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.service.OrderVoucherService;
import com.nuwa.infrastructure.ticket.database.order.vo.UserVoucherOrderListVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.constants.NoticeMessageTypeEnum;
import com.nuwa.ticket.start.api.constants.RedisKeyConstant;
import com.nuwa.ticket.start.api.controller.one.param.*;
import com.nuwa.ticket.start.api.controller.one.vo.*;
import com.nuwa.ticket.start.api.util.DesensitizedUtils;
import com.nuwa.ticket.start.api.util.MemberJwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("onecode")
@Api(tags = {"一码通相关接口"})
public class OneCodeController {

    @Autowired
    private OneClientConfigService oneClientConfigService;

    @Autowired
    private OneAdConfigService oneAdConfigService;

    @Autowired
    private OneUserCenterService oneUserCenterService;

    @Autowired
    private OneMemberService oneMemberService;

    @Autowired
    private SmsClientI smsClientI;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserVoucherOrderJoinMapper userVoucherOrderJoinMapper;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private OrderVoucherService orderVoucherService;

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @ApiOperation(value = "根据应用appId获取一码通端口")
    @RequestMapping(value = "/{appId}/getOneClientConf", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneClientConfig> getOneClientConf(@PathVariable("appId") String oenAppId) {
        OneClientConfig clientConfig = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getOutAppId, oenAppId).one();
        if (clientConfig.getStatus().equalsIgnoreCase("off")) {
            return SingleResponse.buildFailure("9871", "应用已停用");
        }
        return SingleResponse.of(clientConfig);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/getCurrentLoginUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserCenterVO> getCurrentLoginUserInfo(UserAware userAware) {
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        userCenter.setUserRealName(DesensitizedUtils.chineseName(userCenter.getUserRealName()));
        userCenter.setUserIdCard(DesensitizedUtils.idCardNum(userCenter.getUserIdCard()));
        userCenter.setUserPhone(DesensitizedUtils.mobilePhone(userCenter.getUserPhone()));
        UserCenterVO userCenterVO = toUserCenterVO(userCenter);
        String identityCode = oneMember.getIdentityCode();
        if (StrUtil.isNotBlank(identityCode)) {
            List<String> identityNameList = oneUsableIdentityService.lambdaQuery()
                    .in(OneUsableIdentity::getIdentityCode, ListUtil.toList(identityCode.split(",")))
                    .list()
                    .stream()
                    .map(OneUsableIdentity::getName)
                    .collect(Collectors.toList());
            userCenterVO.setIdentityNameList(identityNameList);
        }
        return SingleResponse.of(userCenterVO);
    }

    @ApiOperation(value = "获取广告列表")
    @RequestMapping(value = "/adList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<OneAdConfig>> clientAdPage(Long oneClientId) {
        OneAdConfigPageQry pageQry = new OneAdConfigPageQry();
        pageQry.setLimit(1000);
        pageQry.setOneClientId(oneClientId);
        OneAdConfigPageParam pageParam = new OneAdConfigPageParam(pageQry);
        IPage<OneAdConfig> pageData = oneAdConfigService.paginateByParam(pageParam);
        return SingleResponse.of(pageData.getRecords());
    }

    @ApiOperation(value = "生成身份码信息")
    @RequestMapping(value = "generatEelectronicIdentityQrCode", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<EelectronicIdentityQrCodeVO> generatEelectronicIdentityQrCode(UserAware userAware) {
        OneClientConfig oneClientConfig = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getMchId, userAware.getMchId()).one();
        Assert.notNull(oneClientConfig);
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        if (StrUtil.isBlank(userCenter.getUserIdCard())) {
            return SingleResponse.buildFailure("9874", "未实名认证的用户不允许使用身份码");
        }
        EelectronicIdentityQrCodeVO vo = new EelectronicIdentityQrCodeVO();
        String qrCode = oneClientConfig.getOutAppId() + ":" + RandomUtil.randomString(32);
        vo.setAppId(oneClientConfig.getOutAppId());
        vo.setMemberId(oneMember.getUserId());
        vo.setQrCode(qrCode);
        vo.setIdName(userCenter.getUserRealName());
        vo.setIdCardNo(userCenter.getUserIdCard());
        vo.setLastUpdatetime(new Date());
        vo.setMobile(userCenter.getUserPhone());
        stringRedisTemplate.opsForValue().set(qrCode, JSONUtil.toJsonStr(vo), 60, TimeUnit.SECONDS);
        vo.setIdName(DesensitizedUtils.chineseName(vo.getIdName()));
        vo.setIdCardNo(DesensitizedUtils.idCardNum(vo.getIdCardNo()));
        vo.setRealIdCardNo(userCenter.getUserIdCard());
        int age = IdcardUtil.getAgeByIdCard(userCenter.getUserIdCard());
        vo.setOldPeople(age >= 65);
        return SingleResponse.of(vo);
    }

    public static void main(String[] args) {
        String idCard = "220122199511016522";
        int age = IdcardUtil.getAgeByIdCard(idCard);
        System.out.println("age:" + age);
    }

    @ApiOperation(value = "获取核销订单列表")
    @RequestMapping(value = "listVoucherOrder", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<UserVoucherOrderPoiListVO>> listVoucherOrder(ListVoucherOrderParam param, UserAware userAware) {
        Date now = new Date();
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        UserVoucherOrderListJoinQuery query = new UserVoucherOrderListJoinQuery();
        query.setLinkMobile(userCenter.getUserPhone());
        List<Integer> statusIn = new ArrayList<>();
        statusIn.add(3);
        query.setStatusIn(statusIn);
        query.setMchId(param.getMchId());
        query.setEntranceCertificate("qrCode");
        query.setVisitDateStart(DateUtil.beginOfDay(now));
        query.setVisitDateEnd(DateUtil.endOfDay(DateUtil.offsetDay(now, 7)));
        List<UserVoucherOrderListVO> userVoucherOrderListVOS = userVoucherOrderJoinMapper.listAllByQuery(query);
        List<UserVoucherOrderPoiListVO> poiListVOList = userVoucherOrderListVOS.stream().map(x -> {
            UserVoucherOrderPoiListVO vo = new UserVoucherOrderPoiListVO();
            BeanUtils.copyProperties(x, vo);
            Scenicspot scenicspot = scenicspotService.getById(x.getScenicspotId());
            if (Objects.nonNull(scenicspot)) {
                vo.setPoiName(scenicspot.getName());
                vo.setPoiImage(scenicspot.getMainPicture());
            }
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(poiListVOList);
    }

    @ApiOperation(value = "获取核销码订单详情")
    @RequestMapping(value = "voucherDetail/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserVoucherOrderDetailVO> voucherDetail(@PathVariable("orderId") Long orderId, UserAware userAware) {
        UserVoucherOrderDetailVO detailVO = new UserVoucherOrderDetailVO();
        Date now = new Date();
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        UserVoucherOrderListJoinQuery query = new UserVoucherOrderListJoinQuery();
        query.setId(orderId);
        List<UserVoucherOrderListVO> userVoucherOrderListVOS = userVoucherOrderJoinMapper.listAllByQuery(query);
        List<UserVoucherOrderPoiListVO> poiListVOList = userVoucherOrderListVOS.stream().map(x -> {
            UserVoucherOrderPoiListVO vo = new UserVoucherOrderPoiListVO();
            BeanUtils.copyProperties(x, vo);
            Scenicspot scenicspot = scenicspotService.getById(x.getScenicspotId());
            if (Objects.nonNull(scenicspot)) {
                vo.setPoiName(scenicspot.getName());
                vo.setPoiImage(scenicspot.getMainPicture());
            }
            return vo;
        }).collect(Collectors.toList());
        if (poiListVOList.size() == 1) {
            detailVO.setVoucherOrderVO(poiListVOList.get(0));
        }
        List<OrderVoucher> orderVoucherList = orderVoucherService.lambdaQuery().eq(OrderVoucher::getOrderId, orderId).list();
        detailVO.setOrderVoucherList(orderVoucherList);
        return SingleResponse.of(detailVO);
    }

    @ApiOperation(value = "解析电子身份码信息")
    @RequestMapping(value = "parseEelectronicIdentityQrCode", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<EelectronicIdentityQrCodeVO> parseEelectronicIdentityQrCode(ParseEelectronicIdentityQrCodeParam param) {
        String qrCodeJson = stringRedisTemplate.opsForValue().get(param.getQrCode());
        if (StrUtil.isBlank(qrCodeJson) || "invalid_data".equalsIgnoreCase(qrCodeJson)) {
            return SingleResponse.buildFailure("9874", "电子凭证码已失效");
        }
        stringRedisTemplate.opsForValue().set(param.getQrCode(), "invalid_data", 2, TimeUnit.SECONDS);
        EelectronicIdentityQrCodeVO vo = JSONUtil.toBean(qrCodeJson, EelectronicIdentityQrCodeVO.class);
        return SingleResponse.of(vo);
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

    @ApiOperation(value = "注册或登录")
    @RequestMapping(value = "registerOrLogin", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<UserCenterLoginVO> registerOrLogin(@RequestBody @Valid RegisterOrLoginParam param, HttpServletRequest request) {
        VerifySmsCodeParam req = new VerifySmsCodeParam();
        req.setCode(param.getCode());
        req.setMobile(param.getMobile());
        req.setBizCode("one-registerOrlogin-sendcode");
        SingleResponse<?> sendResult = smsClientI.verify(req);
        String defaultHeadId;
        if (springProfilesActive.equalsIgnoreCase("test")) {
            defaultHeadId = "14395";
        } else {
            defaultHeadId = "15207";
        }

        if (sendResult.isSuccess()) {
            OneMember oneMember = null;
            Integer countMember = oneMemberService.lambdaQuery()
                    .eq(OneMember::getUserPhone, param.getMobile())
                    .eq(OneMember::getMchId, param.getMchId())
                    .count();
            if (countMember > 0) {
                oneMember = oneMemberService.lambdaQuery()
                        .eq(OneMember::getUserPhone, param.getMobile())
                        .eq(OneMember::getMchId, param.getMchId())
                        .one();
            } else {
                oneMember = new OneMember();
                oneMember.setUserPhone(param.getMobile());
                oneMember.setMchId(param.getMchId());
                oneMember.setIp(getIP(request));
                oneMember.setUserImg(defaultHeadId);
                oneMember.setUserNike(RandomUtil.randomString(10));
                oneMember.insert();
            }

            OneUserCenter oneUserCenter = null;
            Integer count = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, param.getMobile()).count();
            if (count > 0) {
                oneUserCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, param.getMobile()).one();
            } else {
                oneUserCenter = new OneUserCenter();
                oneUserCenter.setUserPhone(param.getMobile());
                oneUserCenter.setMchId(param.getMchId());
                oneUserCenter.setIp(getIP(request));
                oneUserCenter.setUserImg(defaultHeadId);
                oneUserCenter.setUserNike(RandomUtil.randomString(10));
                oneUserCenter.insert();
            }
            String tokenStr = MemberJwtUtil.sign(oneMember.getUserId(), oneMember.getUserNike(), oneMember.getMchId());
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + MemberJwtUtil.getTokenId(tokenStr), tokenStr, MemberJwtUtil.EXPIRE_HOUR_TIME, TimeUnit.HOURS);
            UserCenterLoginVO loginVO = new UserCenterLoginVO();
            loginVO.setToken(tokenStr);
            loginVO.setIdentityAuth(StrUtil.isNotBlank(oneUserCenter.getUserIdCard()) ? "yes" : "no");
            return SingleResponse.of(loginVO);

        } else {
            return SingleResponse.buildFailure("9871", "验证码错误");
        }
    }

    @ApiOperation(value = "身份认证")
    @RequestMapping(value = "identityAuth", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> identityAuth(@RequestBody @Valid IdentityAuthParam param, UserAware userAware) {
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        if (Objects.nonNull(userCenter) && StrUtil.isNotBlank(userCenter.getUserIdCard())) {
            return SingleResponse.buildFailure("9874", "不能重复认证");
        }
        boolean update = oneUserCenterService.lambdaUpdate()
                .set(OneUserCenter::getUserRealName, param.getUserRealName())
                .set(OneUserCenter::getUserIdCard, param.getUserIdCard())
                .set(StrUtil.isNotBlank(param.getUserNike()), OneUserCenter::getUserNike, param.getUserNike())
                .set(StrUtil.isNotBlank(param.getUserImg()), OneUserCenter::getUserImg, param.getUserImg())
                .eq(OneUserCenter::getUserId, userCenter.getUserId())
                .update();
        if (StrUtil.isNotBlank(param.getUserIdCard())) {
            int age = IdcardUtil.getAgeByIdCard(param.getUserIdCard());
            if (age >= 65) {
                String identityCode = oneMember.getIdentityCode();
                if (StrUtil.isNotBlank(identityCode)) {
                    identityCode += "," + "[OLD-PEOPLE]";
                } else {
                    identityCode = "[OLD-PEOPLE]";
                }
                oneMemberService.lambdaUpdate()
                        .set(OneMember::getIdentityCode, identityCode)
                        .eq(OneMember::getUserId, oneMember.getUserId())
                        .update();
            }
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "老年身份认证申请提交")
    @RequestMapping(value = "oldPeopleAuth/submit", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> oldPeopleAuthSubmit(UserAware userAware) {
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        String identityCodeList = oneMember.getIdentityCode();
        if (StrUtil.isNotBlank(identityCodeList) && identityCodeList.indexOf("[OLD-PEOPLE]") > 0) {
            return SingleResponse.buildFailure("9874", "不能重复认证");
        }
        if (StrUtil.isNotBlank(userCenter.getUserIdCard())) {
            int age = IdcardUtil.getAgeByIdCard(userCenter.getUserIdCard());
            if (age >= 65) {
                String identityCode = oneMember.getIdentityCode();
                if (StrUtil.isNotBlank(identityCode)) {
                    identityCode += "," + "[OLD-PEOPLE]";
                } else {
                    identityCode = "[OLD-PEOPLE]";
                }
                oneMemberService.lambdaUpdate()
                        .set(OneMember::getIdentityCode, identityCode)
                        .eq(OneMember::getUserId, oneMember.getUserId())
                        .update();
            } else {
                return SingleResponse.buildFailure("9875", "您的年龄不符合要求");
            }
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改身份认证信息")
    @RequestMapping(value = "modifyUserIdentity", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyUserIdentity(@RequestBody @Valid ModifyIdentityAuthParam param, UserAware userAware) {
        /*Integer count = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserNike, param.getUserNike()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9871", "昵称不能重复");
        }*/
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();

        boolean update = oneUserCenterService.lambdaUpdate()
                .set(StrUtil.isNotBlank(param.getUserNike()), OneUserCenter::getUserNike, param.getUserNike())
                .set(StrUtil.isNotBlank(param.getUserImg()), OneUserCenter::getUserImg, param.getUserImg())
                .eq(OneUserCenter::getUserId, userCenter.getUserId())
                .update();
        return SingleResponse.buildSuccess();
    }

    private UserCenterVO toUserCenterVO(OneUserCenter userCenter) {
        UserCenterVO vo = new UserCenterVO();
        BeanUtils.copyProperties(userCenter, vo);
        vo.setIdentityAuth(StrUtil.isNotBlank(userCenter.getUserIdCard()) ? "yes" : "no");
        return vo;
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
