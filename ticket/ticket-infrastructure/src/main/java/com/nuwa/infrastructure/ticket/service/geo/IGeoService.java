package com.nuwa.infrastructure.ticket.service.geo;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.Collection;
import java.util.List;

/**
 * Geo 服务接口定义
 *
 * @author hy
 */
public interface IGeoService {

    /**
     * <h2>把城市信息保存到 Redis 中</h2>
     *
     * @param scenicItems {@link ScenicGpsInfo}
     */
    void saveScenicToRedis(String geoKey,Collection<ScenicGpsInfo> scenicItems);

    void removeScenicToRedis(String geoKey,String scenicItems);

    /**
     * <h2>获取给定城市的坐标</h2>
     *
     * @param scenicItems 给定城市 key
     * @return {@link Point}s
     */
    List<Point> getScenicPos(String geoKey,String[] scenicItems);

    /**
     * <h2>获取两个城市之间的距离</h2>
     *
     * @param scenic1 第一个城市
     * @param scenic2 第二个城市
     * @param metric  {@link Metric} 单位信息, 可以是 null
     * @return {@link Distance}
     */
    Distance getTwoScenicDistance(String geoKey,String scenic1, String scenic2, Metric metric);

    /**
     * <h2>根据给定地理位置坐标获取指定范围内的地理位置集合</h2>
     *
     * @param within {@link Circle} 中心点和距离
     * @param args   {@link RedisGeoCommands.GeoRadiusCommandArgs} 限制返回的个数和排序方式, 可以是 null
     * @return {@link RedisGeoCommands.GeoLocation}
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getPointRadius(
            String geoKey,Circle within, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * <h2>根据给定地理位置获取指定范围内的地理位置集合</h2>
     */
    GeoResults<RedisGeoCommands.GeoLocation<String>> getMemberRadius(
            String geoKey,String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * <h2>获取某个地理位置的 geohash 值</h2>
     *
     * @param scenicItems 给定城市 key
     * @return scenic geohashs
     */
    List<String> getScenicGeoHash(String geoKey,String[] scenicItems);
}
