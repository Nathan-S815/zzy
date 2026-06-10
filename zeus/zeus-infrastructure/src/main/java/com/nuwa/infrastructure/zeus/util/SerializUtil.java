package com.nuwa.infrastructure.zeus.util;

import java.util.Arrays;
import java.util.List;

public class SerializUtil {

    public static List<String> strToList(String str){
        return Arrays.asList(str.split(","));
    }

    public static String listToStr(List list){
        StringBuilder sb = new StringBuilder();
        list.forEach(x ->{
            sb.append(x.toString()).append(",");
        });
        if (sb.length()>0){
            sb.deleteCharAt(sb.lastIndexOf(","));
            return sb.toString();
        }else {
            return "";
        }

    }
}
