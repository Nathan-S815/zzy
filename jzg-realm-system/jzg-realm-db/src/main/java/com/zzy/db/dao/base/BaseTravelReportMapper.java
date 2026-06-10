package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseTravelReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 旅行社客流收入 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseTravelReportMapper extends BaseMapper<BaseTravelReport> {

    List<Map<String,Object>> selectPageListBaseTravelReport(@Param("keyword") String keyword);

    Map<String,Object> getInPeopleAndIncome(@Param("userId") String userId, @Param("nowDate") String nowDate);

   /**
    * 旅行社接待总量
    * */
    Integer getTravelTotalReception(@Param("year") String year);

    /**
     * 各景区旅行社接待人数
     * */

    List<Map<String,Object>> getScenicTravelReceptionCount(@Param("startTime") String startTime,@Param("endTime")String endTime);

    /**
     *
     *预计一周接待人数
     * */
    Integer getTravelForecastWeek(@Param("startTime") String startTime,@Param("endTime")String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateInPeople(@Param("inPeople") Integer inPeople);

    int updateIncome(@Param("income") Integer income);
}
