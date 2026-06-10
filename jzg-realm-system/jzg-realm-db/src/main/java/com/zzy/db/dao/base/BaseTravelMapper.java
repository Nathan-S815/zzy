package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseShopping;
import com.zzy.db.entity.base.BaseTravel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 旅行社 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseTravelMapper extends BaseMapper<BaseTravel> {
    List<BaseTravel> selectPageListBaseTravel(Map<String, Object> para);
}
