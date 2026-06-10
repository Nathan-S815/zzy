package com.zzy.db.dao.base;

import com.zzy.db.entity.base.InfoActivityPropagate;
import com.zzy.db.entity.base.InfoEvacuation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 疏导分流 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface InfoEvacuationMapper extends BaseMapper<InfoEvacuation> {
    List<InfoEvacuation> selectPageListInfoEvacuation(Map<String, Object> para);
}
