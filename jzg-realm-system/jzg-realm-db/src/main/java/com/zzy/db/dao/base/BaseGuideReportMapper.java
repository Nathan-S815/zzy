package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseGuideReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
public interface BaseGuideReportMapper extends BaseMapper<BaseGuideReport> {

    List<Map<String,Object>> selectPageListBaseGuideReport(@Param("keyword") String keyword);

    List<Map<String,Object>> getBaseGuideReportByGuideId(@Param("guideId") Integer guideId);

}
