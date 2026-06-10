package com.nuwa.infrastructure.ticket.util;

import java.util.Arrays;
import java.util.List;

public class SerializUtil {

    public static List<String> strToList(String str){
        return Arrays.asList(str.split(","));
    }

    public static String listToStr(List<String> list){
        StringBuilder sb = new StringBuilder();
        list.forEach(x ->{
            sb.append(x).append(",");
        });
        if (sb.length()>0){
            sb.deleteCharAt(sb.charAt(sb.length()-1));
            return sb.toString();
        }else {
            return "";
        }

    }
}
