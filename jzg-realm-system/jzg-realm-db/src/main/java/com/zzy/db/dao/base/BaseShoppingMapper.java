package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseScenic;
import com.zzy.db.entity.base.BaseShopping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物场所 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseShoppingMapper extends BaseMapper<BaseShopping> {
    List<BaseShopping> selectPageListBaseShopping(Map<String, Object> para);
}
