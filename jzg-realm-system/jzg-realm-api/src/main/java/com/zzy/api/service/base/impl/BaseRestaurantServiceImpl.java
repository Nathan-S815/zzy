package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseRestaurantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseRestaurantMapper;
import com.zzy.db.entity.base.BaseRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 餐厅基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class BaseRestaurantServiceImpl extends ServiceImpl<BaseRestaurantMapper, BaseRestaurant> implements IBaseRestaurantService {
    @Autowired
    private BaseRestaurantMapper baseRestaurantMapper;
    @Override
    public PageInfo<BaseRestaurant> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseRestaurantMapper.selectPageListBaseRestaurant(para));
    }
}
