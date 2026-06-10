package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseRestaurantReport;
import com.zzy.db.entity.base.BaseShoppingReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物场所收入表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
public interface BaseShoppingReportMapper extends BaseMapper<BaseShoppingReport> {
    List<Map<String,Object>> selectPageListBaseShoppingReport(@Param("keyword") String keyword);

    List<Map<String,Object>> getByShoppingId(@Param("shoppingId") Integer shoppingId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<BaseShoppingReport> getReportByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateIncome(@Param("income") Integer income);

    List<Map<String,Object>> getTouristQualityByScenic(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
