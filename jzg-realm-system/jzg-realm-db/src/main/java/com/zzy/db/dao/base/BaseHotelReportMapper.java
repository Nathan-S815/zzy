package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseHotelReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店客流收入表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-04
 */
public interface BaseHotelReportMapper extends BaseMapper<BaseHotelReport> {

    List<Map<String,Object>> selectPageListBaseHotelReport(@Param("keyword") String keyword);

    List<Map<String,Object>> getBaseHotelReportByHotelId(@Param("beginNumber") Integer beginNumber ,@Param("pagSize") Integer pagSize ,@Param("id") Integer id ,@Param("startTime") String startTime ,@Param("endTime") String endTime);

    Map<String,Object> getInPeopleAndIncome(@Param("userId") String userId, @Param("nowDate") String nowDate);

    List<BaseHotelReport> getReportByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<BaseHotelReport> getReportByTimeWithScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateSurplusRoom(@Param("id") Integer id, @Param("num") Integer num, @Param("inPeople") Integer inPeople, @Param("time") String time);

    int updateIncome(@Param("income") Integer income);

    List<Map<String,Object>> getTouristStayByScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String,Object>> getTouristQualityByScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
