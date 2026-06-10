package com.zzy.core.utils;

import cn.hutool.crypto.digest.MD5;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 常用加密
 */
public class EncodeUtil {


    /**
     * 获取对token进行base64加密后的字符串(utf-8格式)
     * @param token
     * @return
     */
    public static String getEncodeBase64Str(String token){
        try {
            return new String(Base64.encodeBase64(token.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String getEncodeUrlStr(String speChar){
        try {
            return URLEncoder.encode(speChar, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public static String get32Md5Str(String token){
        return MD5.create().digestHex(token);
    }
}
