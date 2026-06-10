package com.zzy.db.dao.ota;

import com.zzy.db.entity.ota.ScenicCommentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
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
public interface ScenicCommentInfoMapper extends BaseMapper<ScenicCommentInfo> {

//    List<ScenicCommentInfo> selectNowScenicScore(Map<String,Object> map);

    List<ScenicCommentInfo> selectNowScenicScore(@Param("startTime") String startTime, @Param("endTime")String endTime, @Param("item")Integer item);

    Double selectBeforeScenicScore(@Param("commentPlaceName") String  commentPlaceName,@Param("beforeTime") String beforeTime,@Param("startTime") String startTime);

    Double selectNowEntiretyScenicScore(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Long selectNowGoodNumber(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Long selectNowBadNumber(@Param("startTime") String startTime, @Param("endTime")String endTime);

    Double selectNowEntiretyScenicScoreByName(@Param("startTime") String startTime, @Param("endTime")String endTime, @Param("commentPlaceName")String commentPlaceName);

    Long selectNowGoodNumberByName(@Param("startTime") String startTime, @Param("endTime")String endTime, @Param("commentPlaceName")String commentPlaceName);

    Long selectNowBadNumberByName(@Param("startTime") String startTime, @Param("endTime")String endTime, @Param("commentPlaceName")String commentPlaceName);

    List<Map<String, Object>> selectCommentKeyWordTag();
}
