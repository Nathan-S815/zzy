package com.nuwa.infrastructure.ticket.delay.redis;

import cn.hutool.json.JSONUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisDelayListWrapper {
    private static final Long DELETE_SUCCESS = 1L;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void publish(String key, Object val, long delayTime) {
        String strVal = val instanceof String ? (String) val : JSONUtil.toJsonStr(val);
        redisTemplate.opsForZSet().add(key, strVal, System.currentTimeMillis() + delayTime);
    }

    public String fetchOne(String key) {
        Set<String> sets = redisTemplate.opsForZSet().rangeByScore(key, 0, System.currentTimeMillis(), 0, 1);
        if (CollectionUtils.isEmpty(sets)) {
            return null;
        }

        for (String val : sets) {
            if (DELETE_SUCCESS.equals(redisTemplate.opsForZSet().remove(key, val))) {
                // 删除成功，表示抢占到
                return val;
            }
        }
        return null;
    }
}
