package com.zzy.api.service.ota;

import com.zzy.db.entity.ota.RestaurantCommentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店OTA评论数据 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
public interface IRestaurantCommentInfoService extends IService<RestaurantCommentInfo> {
    public List<Map<String,Object>> getRestaurant(String startTime,String endTime);
}
