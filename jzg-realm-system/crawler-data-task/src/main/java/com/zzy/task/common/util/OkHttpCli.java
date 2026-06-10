package com.zzy.task.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zzy.core.utils.ApiCrawlerUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class OkHttpCli {

    private static final Logger log = LoggerFactory.getLogger(OkHttpCli.class);

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML_TYPE = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;


    public String doGet(String url){
        return doGet(url,null,null);
    }

    public String doGet(String url, Map<String,Object> params){
        return doGet(url,params,null);
    }

    public String doGet(String url, HashMap<String,String> headers){
        return doGet(url,null,headers);
    }

    public String doGet(String url, Map<String,Object> params, HashMap<String,String> headers){
        return  doHttpReq(url,params,headers,"get","json",true,null);
    }

    public String doPostForm(String url,  Map<String,Object> params, HashMap<String,String> headers){
        if(params==null) params = new HashMap<>();
        return doHttpReq(url,params,headers,"post","json",true, null);
    }


    public String doPostJson(String url, Map<String,Object> params,Map<String,String> headers) {
        if(params==null) params = new HashMap<>();
        return doHttpReq(url,params,headers,"post","json",false, null);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url       请求url地址
     * @param xmlContent       请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, Map<String,String> headers, String xmlContent) {
        log.info("do post request and url[{}]", url);
        return doHttpReq(url,null,headers,"post","json",false, xmlContent);
    }

    public String doHttpReq(String url, Map<String,Object> params, Map<String,String> headers,String method, String contentType,boolean isPostForm,String xmlData){
        Request.Builder builder = new Request.Builder();
        if(headers!=null && !headers.isEmpty()) {
            headers.forEach((k, v) -> builder.addHeader(k, v));
        }
        Request req = null;
        if(method.equalsIgnoreCase("get")){
            builder.url(ApiCrawlerUtil.toGetRequestUrl(url,params));
            req= builder.build();
        }else if(method.equalsIgnoreCase("post")){
            MediaType mt = null;
            if(contentType.contains("json")){
                mt = JSON_TYPE;
            }else if(contentType.contains("xml")){
                mt = XML_TYPE;
            }else {
                mt = MediaType.parse(contentType);
            }
            if(isPostForm) {
                FormBody.Builder form = new FormBody.Builder();
                params.forEach((k, v) -> form.add(k, v.toString()));
                req = builder.url(url).post(form.build()).build();
            }else {
                RequestBody requestBody = null;
                if(StrUtil.isBlank(xmlData)){
                    requestBody = RequestBody.create(mt,JSON.toJSONString(params));
                }else {
                    requestBody = RequestBody.create(mt,xmlData);
                }
                req = builder.url(url).post(requestBody).build();
            }
        }
        return execute(req);

    }


    private String execute(Request req) {
        Response resp = null;
        try {
            resp = okHttpClient.newCall(req).execute();
            return resp.body().string();
        }catch (Exception e){
            log.error("httpReq-ERROR>>>>>",e);
            return "";
        }finally {
            if(resp!=null){
                resp.close();
            }
        }
    }


}
