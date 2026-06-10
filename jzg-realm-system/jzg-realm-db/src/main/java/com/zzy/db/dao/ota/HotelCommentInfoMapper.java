package com.zzy.db.dao.ota;

import com.zzy.db.entity.ota.HotelCommentInfo;
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
public interface HotelCommentInfoMapper extends BaseMapper<HotelCommentInfo> {

    List<HotelCommentInfo> selectNowHotelScore(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Double selectBeforeHotelScore(@Param("commentPlaceName") String  commentPlaceName,@Param("beforeTime") String beforeTime,@Param("startTime") String startTime);
}
