package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseAdmin;
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
public interface BaseAdminMapper extends BaseMapper<BaseAdmin> {

    List<Map<String,Object>> selectBaseSpotListByPara(@Param("keyWord") String keyWord);
}
