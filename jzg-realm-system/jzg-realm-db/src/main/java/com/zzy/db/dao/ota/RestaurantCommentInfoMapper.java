package com.zzy.db.dao.ota;

import com.zzy.db.entity.ota.RestaurantCommentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店OTA评论数据 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-22
 */
public interface RestaurantCommentInfoMapper extends BaseMapper<RestaurantCommentInfo> {

    List<RestaurantCommentInfo> selectNowRestaurantScore(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Double selectBeforeRestaurantScore(@Param("commentPlaceName") String  commentPlaceName,@Param("beforeTime") String beforeTime,@Param("startTime") String startTime);
}
