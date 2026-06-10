package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseHotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 酒店上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseHotelMapper extends BaseMapper<ReportBaseHotel> {
    List<ReportBaseHotel> selectPageListBaseHotel(@Param("keyword")String keyword);
}
