package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseTravel;
import com.zzy.db.entity.reportbase.ReportBaseTravel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 旅行社上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseTravelMapper extends BaseMapper<ReportBaseTravel> {
    List<ReportBaseTravel> selectPageListBaseTravel(@Param("keyword")String keyword);
}
