package com.zzy.db.dao.reportbase;

import com.zzy.db.entity.reportbase.ReportBaseScenic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区上报基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
public interface ReportBaseScenicMapper extends BaseMapper<ReportBaseScenic> {
    List<ReportBaseScenic> selectPageListBaseScenic(@Param("keyword")String keyword);

    List<Map<String, Object>> selectBaseSpotListByPara(Map<String, Object> para);

    ReportBaseScenic getIdByName(@Param("name")String name);
}
