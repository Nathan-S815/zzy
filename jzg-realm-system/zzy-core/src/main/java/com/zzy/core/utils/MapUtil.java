package com.zzy.core.utils;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtil {

    public static Map<String, Object> filterBlankMap(Map<String, Object> m) {
        Map<String, Object> m2 = new HashMap<>();
        m.entrySet().stream().filter(o -> isNotEmpty(String.valueOf(o.getValue()))).collect(Collectors.toSet()).forEach(o -> m2.put(o.getKey(), o.getValue()));
        return m2;
    }

    public static boolean isNotEmpty(String str) {
        return (StrUtil.isNotBlank(str) && StrUtil.isNotEmpty(str) && str != null && str != "null");
    }
}
