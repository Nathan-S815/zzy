package com.zzy.core.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsoupUtil {

    private static final Logger log= LoggerFactory.getLogger(JsoupUtil.class);


    public enum MethodOption{
        GET("GET"),POST("POST");
        private String val;
        MethodOption(String val){
            this.val = val;
        }
        public String getVal(){
            return val;
        }
    }



    public static Document doReq(String url, Map<String,String> headers, Map<String, String> params, MethodOption method){
        Connection conn = getConnect(url);
        if(headers!=null){
            conn.headers(headers);
        }
        if(method.getVal().equalsIgnoreCase("post")){
            conn.header("Content-Type","application/x-www-form-urlencoded");
        }
        conn.header("Accept-Encoding", "deflate, sdch");
        conn.header("Accept-Language", "zh-CN,zh;q=0.8");
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        if(params!=null){
            conn.data(params);
        }
        try {
            if(method.getVal().equalsIgnoreCase("get")){
                return conn.get();
            }else if(method.getVal().equalsIgnoreCase("post")){
                return conn.post();
            }
        } catch (Exception e) {
            log.error("异常:{},url:{}",e.getMessage(),url);
        }
        return null;
    }

    public static Document doPostXml(String url, Map<String,String> headers, String xmlData){
        Connection conn = getConnect(url);
        conn.headers(headers);
        try {
            conn.requestBody(xmlData);
            return conn.post();
        } catch (IOException e) {
            log.error("异常:{}",e);
        }
        return null;
    }



    public static Connection getConnect(String url){
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                .timeout(65000);
    }
}
