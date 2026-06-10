package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseTravelLineReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 路线客流表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-28
 */
public interface BaseTravelLineReportMapper extends BaseMapper<BaseTravelLineReport> {

    List<BaseTravelLineReport> getBaseTravelLineReportByTime(@Param("startTime") String startTime, @Param("endTime")String endTime);

    List<Map<String,Object>> getBaseTravelByTime(@Param("startTime") String startTime, @Param("endTime")String endTime);

    List<BaseTravelLineReport> selectPageListBaseTravelLineReport(@Param("travelId") Integer travelId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    Integer getTravelForecastWeek(@Param("startTime") String startTime,@Param("endTime")String endTime);
}
