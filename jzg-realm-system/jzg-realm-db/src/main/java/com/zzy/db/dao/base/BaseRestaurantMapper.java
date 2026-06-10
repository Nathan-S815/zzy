package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseRecreation;
import com.zzy.db.entity.base.BaseRestaurant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐厅基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface BaseRestaurantMapper extends BaseMapper<BaseRestaurant> {
    List<BaseRestaurant> selectPageListBaseRestaurant(Map<String, Object> para);
}
