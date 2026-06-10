package com.nuwa.infrastructure.ticket.service.trade;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.HashMap;

/**
 * @author hy
 */
public class SupplierRetryTimesUtil {

    public static final Integer MAX_RETRY_COUNT = 6;

    public static HashMap<Integer, Integer> getWxRetryCountTimeMap() {
        HashMap<Integer, Integer> m = new HashMap<>(10);
        m.put(1, 1);
        m.put(2, 10);
        m.put(3, 20);
        m.put(4, 20);
        m.put(5, 30);
        m.put(6, 30);
        return m;
    }

    public static Date getRetryCountTime(Integer count) {
        if (count >= MAX_RETRY_COUNT) {
            count = MAX_RETRY_COUNT;
        }
        int second = getWxRetryCountTimeMap().get(count);
        return DateUtil.offsetSecond(new Date(), second);
    }
}
