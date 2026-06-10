package com.zzy.core.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortUtil {
    public static final String DESC = "desc";
    public static final String ASC = "asc";

    public static List<Map<String, Object>> comparator(List<Map<String, Object>> list,String name, String sort) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name1 = Integer.valueOf(o1.get(name).toString());
                Integer name2 = Integer.valueOf(o2.get(name).toString());
                if (sort.equals(SortUtil.DESC)) {
                    return name2.compareTo(name1);
                } else
                    return name1.compareTo(name2);
            }
        });
        return list;
    }
}
