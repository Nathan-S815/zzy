package com.nuwa.infrastructure.ticket.delayqueue.utils;

import com.nuwa.infrastructure.ticket.util.SpringContextKit;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By IntelliJ IDEA
 * redisson 工具类
 *
 * @author Yang WenJie
 * @date 2017/12/3 12:54
 */
public class RedissonUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedissonUtils.class);

    private RedissonUtils() {

    }

    /**
     * 获取 redissonClient
     *
     * @return redissonclient
     */
    public static RedissonClient getRedissonClient() {
        return SpringContextKit.getBean(RedissonClient.class);
    }


    /**
     * 关闭 redissonClient
     *
     * @return
     */
    public static void close() {
        getRedissonClient().shutdown();
    }

    /**
     * 通用对象桶
     * Redisson的分布式RBucketJava对象是一种通用对象桶可以用来存放任类型的对象*
     *
     * @param <V>        泛型
     * @param objectName 对象名
     * @return RBucket
     */
    public static <V> RBucket<V> getRBucket(String objectName) {
        return getRedissonClient().getBucket(objectName);
    }

    /**
     * 获取map对象
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r map
     */
    public static <K, V> RMap<K, V> getMap(String objectName) {
        return getRedissonClient().getMap(objectName);
    }

    /**
     * 获取支持单个元素过期的map对象
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r map cache
     */
    public static <K, V> RMapCache<K, V> getMapCache(String objectName) {
        return getRedissonClient().getMapCache(objectName);
    }

    /**
     * 获取set对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r set
     */
    public static <V> RSet<V> getSet(String objectName) {
        return getRedissonClient().getSet(objectName);
    }

    /**
     * 获取SortedSet对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r sorted set
     */
    public static <V> RSortedSet<V> getSorteSet(String objectName) {
        return getRedissonClient().getSortedSet(objectName);
    }

    /**
     * 获取ScoredSortedSett对象
     *
     * @param objectName
     * @param <V>
     * @return
     */
    public static <V> RScoredSortedSet<V> getScoredSorteSet(String objectName) {
        return getRedissonClient().getScoredSortedSet(objectName);
    }

    /**
     * 获取list对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r list
     */
    public static <V> RList<V> getList(String objectName) {
        return getRedissonClient().getList(objectName);
    }

    /**
     * 获取queue对象
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r queue
     */
    public static <V> RQueue<V> getQueue(String objectName) {
        return getRedissonClient().getQueue(objectName);
    }


    /**
     * Get blocking queue r blocking queue.
     *
     * @param <V>        the type parameter
     * @param objectName the object name
     * @return the r blocking queue
     */
    public static <V> RBlockingQueue<V> getBlockingQueue(String objectName) {
        return getRedissonClient().getBlockingQueue(objectName);
    }

    /**
     * Get atomic long r atomic long.
     *
     * @param objectName the object name
     * @return the r atomic long
     */
    public static RAtomicLong getAtomicLong(String objectName) {
        return getRedissonClient().getAtomicLong(objectName);
    }

    /**
     * Get lock r lock.
     *
     * @param objectName the object name
     * @return the r lock
     */
    public static RLock getLock(String objectName) {
        return getRedissonClient().getLock(objectName);
    }
}
