package com.nuwa.infrastructure.discovery.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {

    public static String processTemplate(String template, Map<String, Object> params) {
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", "张三");
        map.put("money", String.format("%.2f", 10.155));
        map.put("point", 10);
        String message = processTemplate("您好${name}，晚上好！您目前余额：${money}元，积分：${point}", map);
        System.out.println(message);
        //您好张三，晚上好！您目前余额：10.16元，积分：10
    }
}
