package com.zzy.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public final class JsonUtil {


    public static String toJsonStr(Object obj){
        return JSON.toJSONString(obj);
    }

    public static JSONObject strToJSONObject(String str) throws JSONException {
        return JSON.parseObject(str);
    }

    public static JSONArray strToJSONArray(String str) throws JSONException {
        return JSON.parseArray(str);
    }

    public static JSONObject objToJSONObject(Object obj){
//        return (JSONObject) JSONObject.toJSON(obj);
        return strToJSONObject(toJsonStr(obj));
    }

    public static JSONArray objToJSONArray(Object obj){
//        return (JSONObject) JSONObject.toJSON(obj);
        return JSON.parseArray(String.valueOf(obj));
    }

}
