package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 娱乐上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseEntertainmentMapper extends BaseMapper<ReportBaseEntertainment> {
    List<ReportBaseEntertainment> selectPageListBaseEntertainment(@Param("keyword")String keyword);
}
