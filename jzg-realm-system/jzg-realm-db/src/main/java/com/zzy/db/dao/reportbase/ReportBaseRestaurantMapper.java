package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseRestaurant;
import com.zzy.db.entity.reportbase.ReportBaseRestaurant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 餐饮基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseRestaurantMapper extends BaseMapper<ReportBaseRestaurant> {
    List<ReportBaseRestaurant> selectPageListBaseRestaurant(@Param("keyword")String keyword);
}
