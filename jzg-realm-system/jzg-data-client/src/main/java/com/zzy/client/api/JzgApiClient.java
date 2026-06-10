package com.zzy.client.api;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzy.client.common.JzgApiConstant;
import com.zzy.client.common.PushDataDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;

/**
 * 九寨沟联通数据接口
 */
@Slf4j
public class JzgApiClient {


    /**
     * 最后的后缀不能有"/"
     */
    private static final String baseUrl = "http://112.44.67.32:82";

    private static final String USERNAME = "user20";
    private static final String PASSWORD = "123456";
    private static final String GRANT_TYPE = "client_credentials"; //client_credentials|password
    private static final String client_id = "UZxIozUtsSGO1NuvOh";
    private static final String client_auth_sceret = "LWa5cuyVlQWexKF37GvOQ7wNOB5IokxI3LZ2t1HjXbQnyNMS";
    private static final String api_sign_sceret = "abLBV3wubvwcq5yJ";

    private static final String CLIENT_HEADER_PREFIX = "Basic ";
    private static final String API_HEADER_PREFIX = "Bearer ";

    private static final String mediaTypeTxt = "text/plain";
    private static final String mediaTypeJson = "application/json";

    public static void main(String[] args) {
        Object o = null;
//        o = pushData();
        o = fetchData(1,10,JzgApiConstant.ServiceCodeEnum.car_gps);
//        o = checkToken();
//        o = userAuth();
//        System.out.println(o);
//        String accessToken = JSON.parseObject(String.valueOf(o)).getJSONObject("data").getString("refreshToken");
//         o = checkToken(accessToken);
//        o = okHttp(mediaTypeTxt,null);
        System.out.println(o);
    }


    public static String getSign(String secret, Map<String, Object> params) {
        return getSign(secret, "sign", params);
    }

    public static String getSign(String secret, String signName, Map<String, Object> paramMap) {
        paramMap = treeMapKeyLowerCase(paramMap);
        Map<String, Object> sortMap = sortMapByKey(paramMap);
        StringBuilder reqStr = new StringBuilder();
        Iterator var5 = sortMap.entrySet().iterator();
        while(true) {
            Map.Entry entry;
            String key;
            do {
                if (!var5.hasNext()) {
                    reqStr.deleteCharAt(reqStr.length() - 1);
                    String signData = secret + reqStr.toString();
                    signData=signData.replace("pageno", "pageNo").replace("pagesize", "pageSize");
                    log.info("info={}", signData);
                    String mySign = md5(signData);
                    mySign = md5(mySign + secret);
//                    log.info("mySign={}", mySign + secret);
                    return mySign;
                }
                entry = (Map.Entry)var5.next();
                key = (String)entry.getKey();
            } while(signName.equals(key));

            Object value = entry.getValue();
            if (value instanceof Map || value instanceof List || value instanceof Set) {
                value = JSON.toJSONString(value);
            }
            reqStr.append(key).append("=").append(value).append("&");
        }
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

//    private static String md5(String str) {
//        return MD5.create().digestHex(str);
//    }

    private static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            Map<String, Object> sortMap = new
                    TreeMap<String, Object>((o1, o2) -> {
                        Collator collator = Collator.getInstance();
                CollationKey key1 =
                        collator.getCollationKey(o1);
                CollationKey key2 =
                        collator.getCollationKey(o2);
                return key1.compareTo(key2);
            });
            sortMap.putAll(map);
            return sortMap;
        } else {
            return null;
        }
    }

    private static Map<String, Object> treeMapKeyLowerCase(Map<String, Object> map) {
        String key = "";
        Object value = null;
        Map<String, Object> outMap = new TreeMap();
        List<Map<String, Object>> list = null;
        new ArrayList();
        for (Iterator var6 = map.entrySet().iterator(); var6.hasNext(); outMap.put(key.toLowerCase(), value)) {
            Map.Entry<String, Object> entry = (Map.Entry) var6.next();
            list = new ArrayList();
            key = (String) entry.getKey();
            value = entry.getValue();
            if (value!=null && value instanceof List) {
                List<Map<String, Object>> listV = (List) value;
                if (listV.get(0) instanceof Map) {
                    Iterator var8 = listV.iterator();
                    while (var8.hasNext()) {
                        Map<String, Object> vMap = (Map) var8.next();
                        list.add(treeMapKeyLowerCase(vMap));
                    }
                    value = list;
                }
            }
            if (value!=null && value instanceof Map) {
                value = treeMapKeyLowerCase((Map) value);
            }
        }
        return outMap;
    }


    public static String getAccessToken(){
        String res = userAuth();
        try {
            return JSON.parseObject(res).getJSONObject("data").getString("accessToken");
        }catch (Exception e){
            return "系统异常";
        }

    }


    /**
     * 检验Token
     * @param
     * @return
     *
     * 基本信息
     * 接口名称：校验token创 建 人：徐涛
     * 状  态：未完成更新时间：2020-02-11 15:34:37
     * 接口路径：GET/oauth-service/oauth/check_token
     * Mock地址：http://yapi.seanai.cn/mock/43/oauth-service/oauth/check_token
     * 请求参数
     * Query：
     * 参数名称	是否必须	示例	备注
     * Authorization	是  Beaer asdasd
     *
     */
    public static String checkToken(String accessToken){
        String path = "/oauth-service/oauth/check_token";
        Map<String,Object> m = new HashMap<>();
        m.put("Authorization", API_HEADER_PREFIX+accessToken);
        return HttpUtil.get(baseUrl+path,m);
    }


    /**
     * 用户授权
     * @return
     *
     * 接口名称：用户授权创 建 人：徐涛
     * 状  态：未完成更新时间：2020-01-13 11:33:25
     * 接口路径：POST/oauth-service/oauth/token
     * Mock地址：http://yapi.seanai.cn/mock/43/oauth-service/oauth/token
     * 请求参数
     * Headers：
     * 参数名称	参数值	是否必须	示例	备注
     * Content-Type	application/x-www-form-urlencoded	是
     * Authorization		是
     * Basic asd1239asd
     *
     * Basic Base64(client_id+:+clientSecret)
     *
     *
     */
    public static String userAuth(){
        String path = "/oauth-service/oauth/token";
        Map<String,Object> m = new HashMap<>();
        m.put("grant_type", GRANT_TYPE);
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", CLIENT_HEADER_PREFIX+Base64.encode(client_id+":"+client_auth_sceret, "UTF-8"));
        return post(baseUrl+path, m, headers);
    }


    /**
     * 数据获取
     *
     */
    public static String fetchData(Integer pageNo, Integer pageSize, JzgApiConstant.ServiceCodeEnum serviceCode){
        String path = "/data-service/v1/data/fetch";
        Assert.notBlank(serviceCode.getServiceCode(), "服务编码不能为空");
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        String at = getAccessToken();
//        System.out.println("at:"+at);
        if (at.equals("系统异常")){
            return "系统异常";
        }
        headers.put("Authorization", API_HEADER_PREFIX+at);
        Map<String,Object> jo0 = new HashMap<>();
        jo0.put("pageNo",pageNo);
        jo0.put("pageSize",pageSize);
        Map<String,Object> jo = new HashMap<>();
        jo.put("page",jo0);
        Map<String,Object> m = new HashMap<>();
        m.put("params",jo);
        m.put("serviceCode", serviceCode.getServiceCode());
        m.put("timestamp", System.currentTimeMillis());
//        System.out.println(JSON.toJSONString(m));
        m.put("sign", getSign(api_sign_sceret,m));
//        System.out.println(JSON.toJSONString(m));
        return post(baseUrl+path, JSON.toJSONString(m), headers);
    }


    /**
     * 数据推送
     * @param dto
     * @return
     * 接口名称：数据推送创 建 人：徐涛
     * 状  态：未完成更新时间：2019-12-30 16:18:26
     * 接口路径：POST/v1/data/push
     * Mock地址：http://yapi.seanai.cn/mock/43/v1/data/push
     * 请求参数
     * Headers：
     * 参数名称	参数值	是否必须	示例	备注
     * Content-Type	application/json	是
     * Body:
     * 名称	类型	是否必须	默认值	备注	其他信息
     * serviceCode	string 非必须 服务代码
     * data	string 非必须 推送的数据 json格式
     * action	string 非必须 0 增加 1更新 2删除
     * sign	string 非必须 签名
     * timestamp	string 非必须 时间戳
     * callback	string 非必须 回调函数
     * keys	string 必须 主键字段（去重、删除需要判断）
     */
    public static String pushData(PushDataDTO dto){
        String path = "/v1/data/push";
        Assert.notBlank(dto.getKeys(), "服务编码不能为空");
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        return post(baseUrl+path, JSON.toJSONString(dto), headers);
    }


    /**
     * 终端登录
     * @return
     * 接口名称：终端登录创 建 人：徐涛
     * 状  态：未完成更新时间：2020-01-13 11:30:05
     * 接口路径：POST/oauth/token
     * Mock地址：http://yapi.seanai.cn/mock/43/oauth/token
     * 请求参数
     * Headers：
     * 参数名称	参数值	是否必须	示例	备注
     * Content-Type	application/x-www-form-urlencoded	是
     * Body:
     * 参数名称	参数类型	是否必须	示例	备注
     * client_id	T文本	是
     * client_2
     *
     * client_secret	T文本	是
     * 123456
     *
     * grant_type	T文本	是
     * client_credentials
     *
     */
    public static String endPointLogin(String sceret){
        String path = "/oauth/token";
        Map<String,Object> m = new HashMap<>();
        m.put("client_id", client_id);
        m.put("client_secret", sceret);
        m.put("grant_type", "client_credentials");
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        return post(baseUrl+path, JSON.toJSONString(m), headers);
    }








    private static String post(String url,Map<String,Object> param,Map<String,String> headers){
        return HttpUtil.createPost(url).timeout(1000*60)
                .headerMap(headers, true).form(param).execute().body();
    }

    private static String post(String url,String jsonBody,Map<String,String> headers){
        return HttpUtil.createPost(url).timeout(1000*60)
                .headerMap(headers, true).body(jsonBody).execute().body();
    }


    private static String okHttp(String mediaType, RequestBody bodys){
        OkHttpClient oc = new OkHttpClient().newBuilder().build();
        MediaType type = MediaType.parse(mediaType);
        RequestBody body = RequestBody.create(type, "");
        Request request = new Request.Builder()
                .url("http://112.44.67.32:82/oauth-service/oauth/token?grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Authorization", "Basic VVp4SW96VXRzU0dPMU51dk9oOkxXYTVjdXlWbFFXZXhLRjM3R3ZPUTd3Tk9CNUlva3hJM0xaMnQxSGpYYlFueU5NUw==")
                                .build();
        Response response = null;
        try {
            response = oc.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(response);
    }






}
