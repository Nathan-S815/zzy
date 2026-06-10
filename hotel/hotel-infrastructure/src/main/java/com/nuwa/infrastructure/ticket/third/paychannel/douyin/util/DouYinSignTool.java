package com.nuwa.infrastructure.ticket.third.paychannel.douyin.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * @author hy
 */
public class DouYinSignTool {

    public static String getSign(Map<String, Object> paramsMap, String salt) {
        List<String> paramsArr = new ArrayList<>();
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals("other_settle_params")) {
                continue;
            }
            if (Objects.isNull(entry.getValue())) {
                continue;
            }
            String value = entry.getValue().toString();

            value = value.trim();
            if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 1) {
                value = value.substring(1, value.length() - 1);
            }
            value = value.trim();
            if (value.equals("") || value.equals("null")) {
                continue;
            }
            switch (key) {
                case "app_id":
                case "thirdparty_id":
                case "sign":
                    break;
                default:
                    paramsArr.add(value);
                    break;
            }
        }
        paramsArr.add(salt);
        Collections.sort(paramsArr);
        StringBuilder signStr = new StringBuilder();
        String sep = "";
        for (String s : paramsArr) {
            signStr.append(sep).append(s);
            sep = "&";
        }
        return md5FromStr(signStr.toString());
    }

    public static String md5FromStr(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
