package com.nuwa.ticket.start.api.pubsystem.util;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import java.util.Map;

public class GatewayDevelopKit {
    public static String doCheck(Map<String, String> params) throws AlipayApiException {
        verifySign(params);
        String bizContent = setResponse();
        return encryptAndSign(bizContent,
                AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
                AlipayServiceEnvConstants.PRIVATE_KEY, AlipayServiceEnvConstants.CHARSET,
                false, true, AlipayServiceEnvConstants.SIGN_TYPE);
    }

    private static String setResponse() {
        //固定响应格式，必须按此格式返回
        StringBuilder builder = new StringBuilder();
        builder.append("<success>").append(Boolean.TRUE.toString()).append("</success>");
        builder.append("<biz_content>").append(AlipayServiceEnvConstants.PUBLIC_KEY)
                .append("</biz_content>");
        return builder.toString();
    }


    private static void verifySign(Map<String, String> params) throws AlipayApiException {
        if (!AlipaySignature.rsaCheckV2(params, AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
                AlipayServiceEnvConstants.SIGN_CHARSET, AlipayServiceEnvConstants.SIGN_TYPE)) {
            throw new AlipayApiException("verify sign fail.");
        }
    }

    private static String encryptAndSign(String bizContent, String alipayPublicKey, String cusPrivateKey, String charset,
                                  boolean isEncrypt, boolean isSign, String signType) throws AlipayApiException {
        StringBuilder sb = new StringBuilder();
        if (StrUtil.isEmpty(charset)) {
            charset = "gbk";
        }
        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        if (isEncrypt) {// 加密
            sb.append("<alipay>");
            String encrypted = AlipaySignature.rsaEncrypt(bizContent, alipayPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>AES</encryption_type>");
            if (isSign) {
                String sign = AlipaySignature.rsaSign(encrypted, cusPrivateKey, charset, signType);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>");
                sb.append(signType);
                sb.append("</sign_type>");
            }
            sb.append("</alipay>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<alipay>");
            sb.append("<response>" + bizContent + "</response>");
            String sign = AlipaySignature.rsaSign(bizContent, cusPrivateKey, charset, signType);
            sb.append("<sign>" + sign + "</sign>");
            sb.append("<sign_type>");
            sb.append(signType);
            sb.append("</sign_type>");
            sb.append("</alipay>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }
}
