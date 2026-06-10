package com.nuwa.ticket.start.api.controller.open.util;

import cn.hutool.crypto.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Reason: TODO ADD REASON.
 *
 * @author Chen Lingang
 * @version $Id: SignUtils, v 0.1 2017/9/5 下午5:02
 */
public class SignUtils {

    public static final String UTF8 = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 使用<code>secret</code>对paramValues按以下算法进行签名： <br/>
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, String secret) throws Exception {
        return sign(paramValues, null, secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     *
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, List<String> ignoreParamNames, String secret) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<String>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
            for (String ignoreParamName : ignoreParamNames) {
                paramNames.remove(ignoreParamName);
            }
        }
        Collections.sort(paramNames);
        sb.append(secret);
        if (logger.isInfoEnabled()) {
            logger.info("待签名的参数列表 size=" + paramValues.size());
        }
        for (String paramName : paramNames) {
            sb.append(paramName).append("=").append(paramValues.get(paramName));
            if (logger.isInfoEnabled()) {
                logger.info(paramName + "=>" + paramValues.get(paramName));
            }
        }
        sb.append(secret);
        if (logger.isInfoEnabled()) {
            logger.info(">>>>签名字符串: {}", sb);
        }
        return SecureUtil.md5().digestHex(sb.toString(), UTF8).toUpperCase();
    }

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes(UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }
}
