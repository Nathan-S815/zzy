package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseTraffic;
import com.zzy.db.entity.reportbase.ReportBaseTraffic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 交通上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseTrafficMapper extends BaseMapper<ReportBaseTraffic> {
    List<ReportBaseTraffic> selectPageListBaseTraffic(@Param("keyword")String keyword);
}
