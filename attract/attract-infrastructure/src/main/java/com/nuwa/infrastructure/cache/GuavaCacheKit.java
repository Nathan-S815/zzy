package com.nuwa.infrastructure.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

/**
 * GuavaCacheKit 缓存工具类
 *
 * @author hy
 * @date 2020/11/12 15:59
 * @since 1.0.0
 */
@Slf4j
public class GuavaCacheKit {

    /**
     * 使用google guava缓存处理
     */
    private final static Cache<String, Object> cache;

    static {
        cache = CacheBuilder.newBuilder().maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .initialCapacity(2000)
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> rn) {
                        if (log.isInfoEnabled()) {
                            log.info("被移除缓存 key:{}", rn.getKey());
                        }
                    }
                }).build();
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return Object
     */
    public static Object get(String key) {
        if (log.isDebugEnabled()) {
            log.info("get cache key:{}", key);
        }
        return StrUtil.isNotEmpty(key) ? cache.getIfPresent(key) : null;
    }

    /**
     * 放入缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public static void put(String key, Object value) {
        if (log.isDebugEnabled()) {
            log.info("put cache key:{}", key);
        }
        if (StrUtil.isNotEmpty(key) && value != null) {
            cache.put(key, value);
        }
    }

    /**
     * 移除缓存
     *
     * @param key 缓存key
     */
    public static void remove(String key) {
        if (StrUtil.isNotEmpty(key)) {
            cache.invalidate(key);
        }
    }

    /**
     * 批量删除缓存
     *
     * @param keys 缓存列表
     */
    public static void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            cache.invalidateAll(keys);
        }
    }
}
