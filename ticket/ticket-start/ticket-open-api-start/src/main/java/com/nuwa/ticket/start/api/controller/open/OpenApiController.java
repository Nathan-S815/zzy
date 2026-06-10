package com.nuwa.ticket.start.api.controller.open;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.api.sms.SmsClientI;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.OpenApiOneConfigService;
import com.nuwa.infrastructure.ticket.database.one.entity.*;
import com.nuwa.infrastructure.ticket.database.one.service.*;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.UserVoucherOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.service.OrderVoucherService;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.controller.open.param.*;
import com.nuwa.ticket.start.api.controller.open.service.OpenApiService;
import com.nuwa.ticket.start.api.controller.open.service.OpenRsaApiService;
import com.nuwa.ticket.start.api.controller.open.vo.EelectronicIdentityQrCodeVO;
import com.nuwa.ticket.start.api.controller.open.vo.EelectronicIdentityQrCodeVOV2;
import com.nuwa.ticket.start.api.controller.open.vo.IdentityRightsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("api")
@Api(tags = {"开放接口"})
public class OpenApiController {

    @Autowired
    private OneClientConfigService oneClientConfigService;

    @Autowired
    private OneAdConfigService oneAdConfigService;

    @Autowired
    private OneUserCenterService oneUserCenterService;

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
    private OpenApiOneConfigService openApiOneConfigService;

    @Autowired
    private OpenApiService openApiService;

    @Autowired
    private OneRightsConfService oneRightsConfService;

    @Autowired
    private ServiceOpenApiConfigService serviceOpenApiConfigService;

    @Autowired
    private OneMemberService oneMemberService;

    @Autowired
    private OneMerchantScenicspotRightsService oneMerchantScenicspotRightsService;

    @Autowired
    private OpenRsaApiService openRsaApiService;

    @ApiOperation(value = "解析电子身份码信息(加密版本)")
    @RequestMapping(value = "one/parseEelectronic", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<EelectronicIdentityQrCodeVOV2> parseEelectronic(@RequestBody ParseEelectronicApiRequest param) {
        ApiHead head = param.getHead();
        ParseEelectronicApiBody body = param.getBody();
        SingleResponse<EelectronicIdentityQrCodeVOV2> checkResponse = openRsaApiService.check(head, body);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }
        String qrCodeJson = stringRedisTemplate.opsForValue().get(body.getQrCode());
        if (StrUtil.isBlank(qrCodeJson) || "invalid_data".equalsIgnoreCase(qrCodeJson)) {
            return SingleResponse.buildFailure("9874", "电子凭证码已失效");
        }

        String[] splitData = body.getQrCode().split(":");
        String outAppId = splitData[0];
        OneClientConfig oneClientConfig = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getOutAppId, outAppId).one();
        Long mchId = oneClientConfig.getMchId();

        ServiceOpenApiConfig serviceOpenApiConfig = serviceOpenApiConfigService.lambdaQuery().eq(ServiceOpenApiConfig::getAppId, head.getAppId()).one();
        String privateKey = serviceOpenApiConfig.getServicePrivateKey();
        String publicKey = serviceOpenApiConfig.getServicePublicKey();

        EelectronicIdentityQrCodeVOV2 vo = JSONUtil.toBean(qrCodeJson, EelectronicIdentityQrCodeVOV2.class);
        vo.setClientName(oneClientConfig.getName());
        Integer memberId = vo.getMemberId();
        OneMember oneMember = oneMemberService.getById(memberId);
        String identityCode = oneMember.getIdentityCode();
        if (StrUtil.isNotBlank(identityCode)) {
            List<String> identityCodeList = Arrays.stream(identityCode.split(",")).collect(Collectors.toList());
            List<IdentityRightsVO> identityRightsVOList = oneMerchantScenicspotRightsService.lambdaQuery()
                    .eq(OneMerchantScenicspotRights::getScenicspotId, serviceOpenApiConfig.getScenicspotList())
                    .eq(OneMerchantScenicspotRights::getMerchantId, mchId)
                    .list().stream().map(x -> {
                        return oneRightsConfService.getById(x.getRightsId());
                    }).filter(x -> {
                                if (x.getIdentityCodeList().equalsIgnoreCase("-1")) {
                                    return true;
                                }
                                List<String> rightsIdentityCode = Arrays.stream(x.getIdentityCodeList().split(",")).collect(Collectors.toList());
                                Set<String> ts = CollectionUtil.intersectionDistinct(rightsIdentityCode, identityCodeList);
                                return ts.size() > 0;
                            }
                    ).map(x -> {
                        IdentityRightsVO rightsVO = new IdentityRightsVO();
                        BeanUtils.copyProperties(x, rightsVO);
                        rightsVO.setId(x.getId());
                        return rightsVO;
                    }).collect(Collectors.toList());
            vo.setIdentityRightsList(identityRightsVOList);
        }

        OneOpenApiRecord openApiRecord = new OneOpenApiRecord();
        openApiRecord.setCreateTime(new Date());
        openApiRecord.setIdName(vo.getIdName());
        openApiRecord.setIdNo(vo.getIdCardNo());
        openApiRecord.setMobile(vo.getMobile());
        openApiRecord.setOneCleintAppId(outAppId);
        openApiRecord.setMchName(null);
        openApiRecord.setOneCleintName(oneClientConfig.getName());
        openApiRecord.setOneMchId(oneClientConfig.getMchId());
        openApiRecord.setOneCleintId(oneClientConfig.getId());
        openApiRecord.setScanClientAppId(serviceOpenApiConfig.getAppId());
        openApiRecord.setScanClientAppName(serviceOpenApiConfig.getName());
        openApiRecord.setScanMchId(null);
        openApiRecord.setScanClientType("service_open_client");
        openApiRecord.setScenicspotId(Long.parseLong(body.getScenicspotId()));
        openApiRecord.setIdentityCode(oneMember.getIdentityCode());
        if (Objects.nonNull(vo.getIdentityRightsList())) {
            openApiRecord.setRightsInfo(JSONUtil.toJsonStr(vo.getIdentityRightsList()));
        }
        Scenicspot scenicspot = scenicspotService.getById(body.getScenicspotId());
        if (Objects.nonNull(scenicspot)) {
            openApiRecord.setScenicspotName(scenicspot.getName());
        }
        openApiRecord.insert();

        RSA rsa = new RSA(privateKey, publicKey);

        String idCardNo = vo.getIdCardNo();
        String idCardNoEncrypt = rsa.encryptBase64(idCardNo, KeyType.PublicKey);
        vo.setIdCardNo(idCardNoEncrypt);

        String mobile = vo.getMobile();
        String mobileEncrypt = rsa.encryptBase64(mobile, KeyType.PublicKey);
        vo.setMobile(mobileEncrypt);

        //stringRedisTemplate.delete(body.getQrCode());
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "解析电子身份码信息")
    @RequestMapping(value = "parseEelectronicQrCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<EelectronicIdentityQrCodeVO> parseEelectronicQrCodeAPI(@RequestBody ParseEelectronicApiRequest param) {
        ApiHead head = param.getHead();
        ParseEelectronicApiBody body = param.getBody();
        SingleResponse<EelectronicIdentityQrCodeVO> checkResponse = openApiService.check(head, body);
        if (!checkResponse.isSuccess()) {
            return checkResponse;
        }
        String qrCodeJson = stringRedisTemplate.opsForValue().get(body.getQrCode());
        if (StrUtil.isBlank(qrCodeJson) || "invalid_data".equalsIgnoreCase(qrCodeJson)) {
            return SingleResponse.buildFailure("9874", "电子凭证码已失效");
        }

        stringRedisTemplate.delete(body.getQrCode());
        EelectronicIdentityQrCodeVO vo = JSONUtil.toBean(qrCodeJson, EelectronicIdentityQrCodeVO.class);
        return SingleResponse.of(vo);
    }

    public static void main(String[] args) {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMoCk4AUlvFleLAaBrZWQ9a15YWh8xlRcZGNif/5n4GcRO+vXU7mnmHb5JIJRmPI5TL8oqXBRQTeU8zA6nTkF1iUFVZLmo4GKNPtDdLzHJpYQG7oWu5M4/i6K2xqEMv5JD1zLHvZUfVbXxyzmxzYWKlcUA5r59OllTnkIXaNd5/dAgMBAAECgYEAunskVMEtEky827w65AnAeC3UYsc81et0Lox9jzqNv6VVkfnN5i9ImEfYueMsAqOKycWkE+XVZAglTcfs59lDnlKY84Ucg9J58P68/p/3AjhPCX33YU/XIuRlt49BDkaGMRjNXZFU8eTGSdlMjFHGhg5Av3TGyR/BKGTt9xIEsqECQQDptmyHCFXc/E7RH7YMwdYKU4oIwDo4KkJPpbvcQRYKnfU7NyqVWFQCZtsrbXeb0uitGd7Z5skgp1tjYFos3us5AkEA3UY0f+pT0nApjdVAXkNvBZu+YB+KfBCLJrf+Xd28W11Tx2yqJQuLnampg4N3MlRa6xvo2x5jBK0cKvjsi3iFxQJBANI9i7WRVwRbaF+RYkhpmq1hZxvmKLlbsplJowxI9JYKcI+bWdBNTA15D5IqgF2JxkvpqOJmTOn2Ay0LgTM6OWkCQB0cVV/WxjxTQURFn/hGyt5kBQHEZIW573SilDZpK5ShjJoZ87B3+mA9p+2DaDUY3/U6cMITF1fQJnqI0SXwLzkCQDcMRuyG3BxFwxbtBRdQj0yMqkr12rN5eBCpVo0QTnHqBdu2djwkUp7a91ZydhFtnCKvIT25iC86V9/QS4gHRFQ=";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKApOAFJbxZXiwGga2VkPWteWFofMZUXGRjYn/+Z+BnETvr11O5p5h2+SSCUZjyOUy/KKlwUUE3lPMwOp05BdYlBVWS5qOBijT7Q3S8xyaWEBu6FruTOP4uitsahDL+SQ9cyx72VH1W18cs5sc2FipXFAOa+fTpZU55CF2jXef3QIDAQAB";

        String content = "陈爽";

        RSA rsa = new RSA(privateKey, publicKey);
        String encrypt = rsa.encryptBase64(content, KeyType.PublicKey);
        System.out.println("encryptStr:" + encrypt);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("decryptStr:" + StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
    }

}
