package com.zzy.client.api;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.sun.jmx.snmp.Timestamp;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.*;

@Slf4j
public class NewsClient {

    private static final String baseUrl = "112.44.67.32:9095/data/news/yesterday";

    public static void main(String[] args) {
        NewsClient.getNews();
    }

    public static String getNews() {
        long timestamp = new Date().getTime()/1000;
        String mySign = md5(timestamp+"news");
        String s = baseUrl + "?timestamp=" + timestamp + "&sign=" + mySign;
        String result = HttpUtil.get(baseUrl+"?timestamp="+timestamp+"&sign="+mySign);
        return result;
    }

    private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String md5(String str) {
        try {
            byte[] btInput = str.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] ch = new char[j * 2];
            int k = 0;
            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                ch[k++] = hexDigits[byte0 >>> 4 & 15];
                ch[k++] = hexDigits[byte0 & 15];
            }
            return new String(ch);
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }
}
