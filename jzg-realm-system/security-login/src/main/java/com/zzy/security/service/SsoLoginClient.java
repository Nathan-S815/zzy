package com.zzy.security.service;

import com.zzy.core.utils.ApiCrawlerUtil;
import com.zzy.core.utils.EncodeUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SsoLoginClient {

    public static final String APP_KEY = "theyjfghher6t5yey36u4jfgyjrhjfje7y35r";
    public static final String APP_SECRET = "<REDACTED>";
    public static final String APP_UserId = "330903";
    public static final String APP_UserName = "330903";

    public static final String baseUrl = "http://i.tourzj.com/allsyslogin/allsyslogin.ashx";


    public static void main(String[] args) {
//        String rs = getSystemWebUrl(3);
//        System.out.println(rs);
    }



    public static String getSSOSign(){
        return EncodeUtil.get32Md5Str
                (new StringBuilder(APP_KEY).append(APP_SECRET).append(System.currentTimeMillis()).toString());
    }

    public static String getSSOSign(String appkey,String appSecret, String timestamp){
        return EncodeUtil.get32Md5Str
                (new StringBuilder(appkey).append(appSecret).append(timestamp).toString());
    }


    public static String getSystsemWebUrl(int syscode){
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("appKey",APP_KEY);
        m.put("username",APP_UserName);
        m.put("timestamp",System.currentTimeMillis());
        m.put("sign",getSSOSign());
        m.put("service",EncodeUtil.getEncodeUrlStr("http://dt.tourzj.gov.cn/portal/home/system.do?systemcode="+syscode));
        return ApiCrawlerUtil.toGetRequestUrl(baseUrl,m).toLowerCase();
    }

    public static String getSystemWebUrl(String serviceUrl){
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("loginname",APP_UserName);
        m.put("userType",1);
        m.put("systemType",serviceUrl.toLowerCase().replace("systemtype=",""));
        return ApiCrawlerUtil.toGetRequestUrl(baseUrl,m).toLowerCase();
    }


}
