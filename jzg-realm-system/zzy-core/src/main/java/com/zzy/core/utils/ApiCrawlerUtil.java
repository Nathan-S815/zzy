package com.zzy.core.utils;

import java.util.Map;
import java.util.TreeMap;

public final class ApiCrawlerUtil {

    private ApiCrawlerUtil() {}


    /**
     * 传入原url和get请求参数返回符合get请求拼接的url(参数无排序)
     * @param url
     * @param params
     * @return
     */
    public static String toGetRequestUrl(String url, final Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if(params!=null && !params.isEmpty()){
            params.forEach((k,v)->sb.append(String.join("=","&"+k,v.toString())));
            return url+sb.toString().replaceFirst("&","?");
        }else {
            return url;
        }
    }



    /**
     * 按键值首字母升序
     * @param param
     * @return
     */
    public static Map<String, String> getAscParamsMap(final Map<String, String> param){
        Map<String, String> para = new TreeMap<>();
        param.keySet().forEach(k->para.put(k, param.get(k)));
        return para;
    }

    public static Map<String, Object> getAscParamsMap(final Map<String, Object> param, boolean flag){
        Map<String, Object> para = new TreeMap<>();
        param.keySet().forEach(k->para.put(k, param.get(k)));
        return para;
    }





}
