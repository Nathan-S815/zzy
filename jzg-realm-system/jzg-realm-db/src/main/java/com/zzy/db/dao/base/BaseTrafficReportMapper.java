package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseRestaurantReport;
import com.zzy.db.entity.base.BaseTrafficReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交通客流收入表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface BaseTrafficReportMapper extends BaseMapper<BaseTrafficReport> {
    List<Map<String,Object>> selectPageListBaseTrafficReport(@Param("keyword") String keyword);

    List<Map<String,Object>> getByTrafficId(@Param("trafficId") Integer trafficId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<BaseTrafficReport> getReportByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateIncome(@Param("income") Integer income);

    List<Map<String,Object>> getTouristQualityByScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
