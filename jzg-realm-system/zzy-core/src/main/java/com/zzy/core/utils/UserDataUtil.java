package com.zzy.core.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.Date;

public class UserDataUtil {

    public static String toAnonymousStr(String res){
        if(StrUtil.isBlank(res)){
            return "***********";
        }
        char st = res.charAt(0);
        char ed = res.charAt(res.length()-1);
        return new StringBuilder().append(st).append(getAnonyChar(res.length()-2)).append(ed).toString();
    }


    private static String getAnonyChar(int length){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length+3; i++){
            sb.append("*");
        }
        return sb.toString();
    }


}
