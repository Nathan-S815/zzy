package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseRecreation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 休闲娱乐 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseRecreationMapper extends BaseMapper<BaseRecreation> {
    List<BaseRecreation> selectPageListBaseRecreation(Map<String, Object> para);
}
