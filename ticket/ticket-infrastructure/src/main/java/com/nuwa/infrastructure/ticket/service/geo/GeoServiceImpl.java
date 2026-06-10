package com.nuwa.infrastructure.ticket.service.geo;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author hy
 */
@Slf4j
@Service
public class GeoServiceImpl implements IGeoService {

    private final String GEO_KEY_PREFIX = "geo-scenic-";

    /**
     * redis 客户端
     */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public GeoServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveScenicToRedis(String geoKey, Collection<ScenicGpsInfo> scenicItems) {
        log.info("start to save scenic info: {}.", JSONUtil.toJsonPrettyStr(scenicItems));
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        Set<RedisGeoCommands.GeoLocation<String>> locations = new HashSet<>();
        scenicItems.forEach(ci -> locations.add(new RedisGeoCommands.GeoLocation<String>(
                ci.getId(), new Point(ci.getLongitude(), ci.getLatitude())
        )));
        log.info("done to save scenicGpsInfo");
        ops.add(GEO_KEY_PREFIX + geoKey, locations);
    }

    @Override
    public void removeScenicToRedis(String geoKey, String scenicItems) {
        log.info("start to remove scenic info: {}.", scenicItems);
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        log.info("done to remove scenicGpsInfo");
        ops.remove(GEO_KEY_PREFIX + geoKey, scenicItems);
    }

    @Override
    public List<Point> getScenicPos(String geoKey, String[] scenicItems) {
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        return ops.position(GEO_KEY_PREFIX + geoKey, scenicItems);
    }

    @Override
    public Distance getTwoScenicDistance(String geoKey, String scenic1, String scenic2, Metric metric) {
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        return metric == null ?
                ops.distance(GEO_KEY_PREFIX + geoKey, scenic1, scenic2) : ops.distance(GEO_KEY_PREFIX + geoKey, scenic1, scenic2, metric);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getPointRadius(String geoKey, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        return args == null ?
                ops.radius(GEO_KEY_PREFIX + geoKey, within) : ops.radius(GEO_KEY_PREFIX + geoKey, within, args);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getMemberRadius(
            String geoKey, String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        return args == null ?
                ops.radius(GEO_KEY_PREFIX + geoKey, member, distance) : ops.radius(GEO_KEY_PREFIX + geoKey, member, distance, args);
    }

    @Override
    public List<String> getScenicGeoHash(String geoKey, String[] scenicList) {
        GeoOperations<String, String> ops = redisTemplate.opsForGeo();
        return ops.hash(GEO_KEY_PREFIX + geoKey, scenicList);
    }
}
