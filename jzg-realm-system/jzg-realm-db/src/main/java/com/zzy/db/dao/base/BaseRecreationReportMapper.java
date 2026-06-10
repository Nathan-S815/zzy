package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseRecreationReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.base.BaseRestaurantReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 娱乐客流收入表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface BaseRecreationReportMapper extends BaseMapper<BaseRecreationReport> {

    List<Map<String,Object>> selectPageListBaseRecreationReport(@Param("keyword") String keyword);

    List<Map<String,Object>> getByRecreationId(@Param("recreationId") Integer recreationId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<BaseRecreationReport> getReportByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateIncome(@Param("income") Integer income);

    List<Map<String,Object>> getTouristQualityByScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
