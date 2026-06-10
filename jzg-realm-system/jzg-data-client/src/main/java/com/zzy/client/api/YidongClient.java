package com.zzy.client.api;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.crypt.CryptUtil;
import com.zzy.client.common.ANameEnum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 移动客流数据
 */
public class YidongClient {


    private static final String uid = "AbJzg_lv";

    public static void main(String[] args) {
        Object o = null;
        o = getDataFromExternalInterfacePlatform(ANameEnum.monthProvincePassengerSource,DateUtil.parse("2020-05-11","yyyy-MM-dd"));
        System.out.println(o);
    }


    public static String getDataFromExternalInterfacePlatform(ANameEnum aName, Date date){
        String url = "http://218.205.252.11:8183/ExternalInterfacePlatform/getSafetySupervision.do";
        String timestamp = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.S");
        String queryTime = null;
        if(aName.name().startsWith("day")){
            queryTime = DateUtil.format(date,"yyyyMMdd");
        }else if (aName.name().startsWith("Hour")){
            queryTime = DateUtil.format(date,"yyyyMMddHH");
        }else {
            queryTime = DateUtil.format(date,"yyyyMM");
        }
        JSONObject jo = new JSONObject();
        jo.put("queryTime", queryTime);
        CryptUtil crypt = new CryptUtil();
        String r = CryptUtil.CIPHER_STRING + aName.getCode() + "AbJzglv@ABG83" + uid + timestamp;
        String signature = crypt.stringMD5(r);
        Map<String,Object> m = new HashMap<>();
        m.put("aName", crypt.encrypt(aName.getCode()));
        m.put("uid", crypt.encrypt(uid));
        m.put("timestamp", crypt.encrypt(timestamp));
        m.put("signature", signature);
        m.put("param", crypt.encrypt(jo.toJSONString()));
        return HttpUtil.get(url,m);
    }

    public static String getDataFromExternalInterfacePlatform(ANameEnum aName){
        String url = "http://218.205.252.11:8183/ExternalInterfacePlatform/getSafetySupervision.do";
        String timestamp = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.S");
        JSONObject jo = new JSONObject();
        CryptUtil crypt = new CryptUtil();
        String r = CryptUtil.CIPHER_STRING + aName.getCode() + "AbJzglv@ABG83" + uid + timestamp;
        String signature = crypt.stringMD5(r);
        Map<String,Object> m = new HashMap<>();
        m.put("aName", crypt.encrypt(aName.getCode()));
        m.put("uid", crypt.encrypt(uid));
        m.put("timestamp", crypt.encrypt(timestamp));
        m.put("signature", signature);
        m.put("param", crypt.encrypt(jo.toJSONString()));
        return HttpUtil.get(url,m);
    }


}
