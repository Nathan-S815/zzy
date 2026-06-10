package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseRecreation;
import com.zzy.db.entity.base.BaseRestaurant;

import java.util.Map;

/**
 * <p>
 * 餐厅基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseRestaurantService extends IService<BaseRestaurant> {
    PageInfo<BaseRestaurant> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
