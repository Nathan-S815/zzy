package com.zzy.db.dao.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.db.entity.base.BaseScenic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区基础信息 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface BaseScenicMapper extends BaseMapper<BaseScenic> {

    List<BaseScenic> query(Page<BaseScenic> page, @Param("map") Map<String, Object> paramMap);

    List<BaseScenic> selectPageList(Map<String, Object> para);

    BaseScenic selectByScenicName(@Param("scenicName") String scenicName);

}
