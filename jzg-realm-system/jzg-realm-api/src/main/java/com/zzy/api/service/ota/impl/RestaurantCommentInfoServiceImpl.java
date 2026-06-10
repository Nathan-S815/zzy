package com.zzy.api.service.ota.impl;


import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.ota.RestaurantCommentInfo;
import com.zzy.db.dao.ota.RestaurantCommentInfoMapper;
import com.zzy.api.service.ota.IRestaurantCommentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 酒店OTA评论数据 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
@Service
public class RestaurantCommentInfoServiceImpl extends ServiceImpl<RestaurantCommentInfoMapper, RestaurantCommentInfo> implements IRestaurantCommentInfoService {

    @Autowired
    private RestaurantCommentInfoMapper restaurantCommentInfoMapper;

    @Override
    public List<Map<String,Object>> getRestaurant(String startTime,String endTime) {
        List<RestaurantCommentInfo> restaurantCommentInfos = restaurantCommentInfoMapper.selectNowRestaurantScore(startTime,endTime);
        if (restaurantCommentInfos.size() == 0) {
            return null;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        int i = 1;
        DecimalFormat df = new DecimalFormat("######0.00");
        for (RestaurantCommentInfo restaurantCommentInfo : restaurantCommentInfos) {
            String commentPlaceName = restaurantCommentInfo.getCommentPlaceName();
            Double commentScore = restaurantCommentInfo.getCommentScore();
            String beforeTime = TimeDateUtil.getBeforeDate(startTime, endTime);
            Double before = restaurantCommentInfoMapper.selectBeforeRestaurantScore(commentPlaceName,beforeTime,startTime);
            if (before == null) {
                before = 0.0;
            }
            Map<String, Object> value = new HashMap<>();
            value.put("commentPlaceName", commentPlaceName);
            value.put("score", df.format(commentScore * 20));
            value.put("rank", i);
            if (before > commentScore) {
                value.put("float", "down");
            } else {
                value.put("float", "up");
            }
            i++;
            list.add(value);
        }
        return list;
    }
}
