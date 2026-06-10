package com.zzy.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 短信发送工具
 */
@Slf4j
public class PhoneMsgUtil {
  

    /**
     * 助通接口
     */
    private static final String zhuTongUrl="http://api.mix2.zthysms.com/v2/sendSms";


    /**
     * 账号
     */
    private static final String username="jyzyhy";


    /**
     * 密码
     */
    private static final String password=<REDACTED>


    public static void main(String[] args) {
    }


    public static String sendMobileMsg(String content, String mobile) {
        if(StrUtil.isBlank(content) || StrUtil.isBlank(mobile)){
            throw new RuntimeException("NullParamter");
        }
        if(!Validator.isMobile(mobile)){
            throw new RuntimeException("Illegal Phone Number");
        }
        Map m = getAuthParam();
        m.put("content", content);
        m.put("mobile", mobile);
        return HttpUtil.post(zhuTongUrl, JSON.toJSONString(m));
    }


    /**
     * 群发短信
     * @param content
     * @param mobiles
     * @return
     */
    public static String sendGroupMsg(String content, String... mobiles) {
        if(StrUtil.isBlank(content) || mobiles==null || mobiles.length < 1){
            throw new RuntimeException("NullParamter");
        }
        Map m = getAuthParam();
        m.put("content", content);
        m.put("mobile", ArrayUtil.toString(mobiles).replace("[", "").replace("]", ""));
        return HttpUtil.post(zhuTongUrl, JSON.toJSONString(m));
    }

    /**
     * 群发短信
     * @param content
     * @param mobiles
     * @return
     */
    public static String sendGroupMsg(String content, List<String> mobiles) {
        return sendGroupMsg(content, mobiles.toArray(new String[]{}));
    }


    private static Map<String,Object> getAuthParam(){
        Map<String,Object> para = new HashMap<>();
        Long tk = DateUtil.currentSeconds();
        para.put("username",username);
        para.put("password", DigestUtil.md5Hex(DigestUtil.md5Hex(password)+tk));
        para.put("tKey", tk);
        return para;
    }



}
