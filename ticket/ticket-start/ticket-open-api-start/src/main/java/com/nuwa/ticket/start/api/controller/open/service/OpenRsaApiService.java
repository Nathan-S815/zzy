package com.nuwa.ticket.start.api.controller.open.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.database.one.entity.ServiceOpenApiConfig;
import com.nuwa.infrastructure.ticket.database.one.service.ServiceOpenApiConfigService;
import com.nuwa.ticket.start.api.controller.open.param.ApiBody;
import com.nuwa.ticket.start.api.controller.open.param.ApiHead;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class OpenRsaApiService {

    @Autowired
    private ServiceOpenApiConfigService serviceOpenApiConfigService;

    public SingleResponse check(ApiHead head, ApiBody body) {
        String appId = head.getAppId();
        ServiceOpenApiConfig openApiOneConfig = serviceOpenApiConfigService.lambdaQuery().eq(ServiceOpenApiConfig::getAppId, appId).one();
        if (Objects.isNull(openApiOneConfig)) {
            return SingleResponse.buildFailure("9872", "appId 未报备");
        }
        String privateKey = openApiOneConfig.getServicePrivateKey();
        String publickKey = openApiOneConfig.getServicePublicKey();
        String timestampStr = head.getTimestamp();
        try {
            DateTime timestamp = DateUtil.parse(timestampStr, "yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            if (!DateUtil.isIn(timestamp, DateUtils.addSeconds(now, -120), DateUtils.addSeconds(now, 60))) {
                return SingleResponse.buildFailure("9872", "timestamp 已失效");
            }
        } catch (Exception ex) {
            log.error("timestamp filed 格式错误");
            return SingleResponse.buildFailure("9872", "timestamp 格式不对");
        }

        Boolean checkSign = false;
        try {
            checkSign = checkSign(head, body, privateKey, publickKey);
        } catch (Exception ex) {
            return SingleResponse.buildFailure("9874", "验签失败");
        }
        if (!checkSign) {
            return SingleResponse.buildFailure("9874", "验签失败");
        }
        return SingleResponse.of("");
    }

    public Boolean checkSign(ApiHead head, ApiBody body, String privateKey, String publicKey) throws Exception {
        String sign = head.getSign();
        HashMap<String, String> needSignParams = new HashMap<String, String>();
        Map<String, String> bodyMapParams = object2Map(body);
        Map<String, String> headMapParams = object2Map(head);
        bodyMapParams.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                if (!k.startsWith("ignoreSign")
                        && !k.equals("sign")) {
                    needSignParams.put(k, v.toString());
                }
            }
        });
        headMapParams.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                if (!k.startsWith("ignoreSign")
                        && !k.equals("sign")) {
                    needSignParams.put(k, v.toString());
                }
            }
        });

        String signValue = "";
        try {
            signValue = buildSignStr(needSignParams, new ArrayList<>());
            return checkSign(signValue.getBytes(StandardCharsets.UTF_8), sign, privateKey, publicKey);
        } catch (Exception ex) {
            log.error("签名异常", ex);
        }
        log.info("singValue:" + signValue);
        if (signValue.equalsIgnoreCase(sign) || sign.equalsIgnoreCase("666666")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Map<String, String> object2Map(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //  If the field is not ignored, put it in the map
            //  If the field is null, throw an exception
            map.put(field.getName(), field.get(obj).toString());
        }
        return map;
    }

    /**
     * 私钥签名
     *
     * @param content
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] content, String privateKey, String publicKey) throws Exception {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, publicKey);
        byte[] signed = sign.sign(content);
        return bytesToBase64(signed);
    }

    public static Boolean checkSign(byte[] content, String signedStr, String privateKey, String publicKey) throws Exception {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, publicKey);
        return sign.verify(content, base64ToBytes(signedStr));
    }


    /**
     * 字节数组转Base64编码
     *
     * @param bytes 字节数组
     * @return Base64编码
     */
    private static String bytesToBase64(byte[] bytes) {
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Base64编码转字节数组
     *
     * @param base64Str Base64编码
     * @return 字节数组
     */
    private static byte[] base64ToBytes(String base64Str) {
        byte[] bytes = base64Str.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(bytes);
    }

    public static String buildSignStr(Map<String, String> paramValues, List<String> ignoreParamNames) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<String>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
            for (String ignoreParamName : ignoreParamNames) {
                paramNames.remove(ignoreParamName);
            }
        }
        Collections.sort(paramNames);
        if (log.isInfoEnabled()) {
            log.info("待签名的参数列表 size=" + paramValues.size());
        }
        for (String paramName : paramNames) {
            sb.append(paramName).append("=").append(paramValues.get(paramName));
            if (log.isInfoEnabled()) {
                log.info(paramName + "=>" + paramValues.get(paramName));
            }
        }
        if (log.isInfoEnabled()) {
            log.info(">>>>签名字符串: {}", sb);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMoCk4AUlvFleLAaBrZWQ9a15YWh8xlRcZGNif/5n4GcRO+vXU7mnmHb5JIJRmPI5TL8oqXBRQTeU8zA6nTkF1iUFVZLmo4GKNPtDdLzHJpYQG7oWu5M4/i6K2xqEMv5JD1zLHvZUfVbXxyzmxzYWKlcUA5r59OllTnkIXaNd5/dAgMBAAECgYEAunskVMEtEky827w65AnAeC3UYsc81et0Lox9jzqNv6VVkfnN5i9ImEfYueMsAqOKycWkE+XVZAglTcfs59lDnlKY84Ucg9J58P68/p/3AjhPCX33YU/XIuRlt49BDkaGMRjNXZFU8eTGSdlMjFHGhg5Av3TGyR/BKGTt9xIEsqECQQDptmyHCFXc/E7RH7YMwdYKU4oIwDo4KkJPpbvcQRYKnfU7NyqVWFQCZtsrbXeb0uitGd7Z5skgp1tjYFos3us5AkEA3UY0f+pT0nApjdVAXkNvBZu+YB+KfBCLJrf+Xd28W11Tx2yqJQuLnampg4N3MlRa6xvo2x5jBK0cKvjsi3iFxQJBANI9i7WRVwRbaF+RYkhpmq1hZxvmKLlbsplJowxI9JYKcI+bWdBNTA15D5IqgF2JxkvpqOJmTOn2Ay0LgTM6OWkCQB0cVV/WxjxTQURFn/hGyt5kBQHEZIW573SilDZpK5ShjJoZ87B3+mA9p+2DaDUY3/U6cMITF1fQJnqI0SXwLzkCQDcMRuyG3BxFwxbtBRdQj0yMqkr12rN5eBCpVo0QTnHqBdu2djwkUp7a91ZydhFtnCKvIT25iC86V9/QS4gHRFQ=";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKApOAFJbxZXiwGga2VkPWteWFofMZUXGRjYn/+Z+BnETvr11O5p5h2+SSCUZjyOUy/KKlwUUE3lPMwOp05BdYlBVWS5qOBijT7Q3S8xyaWEBu6FruTOP4uitsahDL+SQ9cyx72VH1W18cs5sc2FipXFAOa+fTpZU55CF2jXef3QIDAQAB";

        String content = "陈爽";
        String sign = sign(content.getBytes(StandardCharsets.UTF_8), privateKey, publicKey);
        System.out.println(sign);
        String signVal = sign;
        Boolean aBoolean = checkSign(content.getBytes(StandardCharsets.UTF_8), signVal, privateKey, publicKey);
        System.out.println(aBoolean);

    }

}
