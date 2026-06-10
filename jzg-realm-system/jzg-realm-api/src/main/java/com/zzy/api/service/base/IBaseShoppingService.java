package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseRestaurant;
import com.zzy.db.entity.base.BaseShopping;

import java.util.Map;

/**
 * <p>
 * 购物场所 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseShoppingService extends IService<BaseShopping> {
    PageInfo<BaseShopping> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
