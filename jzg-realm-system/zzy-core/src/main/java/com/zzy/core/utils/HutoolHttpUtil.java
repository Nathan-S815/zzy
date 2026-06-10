package com.zzy.core.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

public class HutoolHttpUtil {



    public static String httpReq(String baseUrl, String path, Map<String,String> header, Map<String,Object> param, Method type){
        HttpRequest req = HttpUtil.createRequest(type,baseUrl+path);
        if(header!=null){
            req.addHeaders(header);
        }
       if(param!=null){
           req.form(param);
        }
        req.setReadTimeout(85000);
        req.setConnectionTimeout(65000);
        return req.execute().body();
    }
}
