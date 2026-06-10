package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseShoppingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseShoppingMapper;
import com.zzy.db.entity.base.BaseShopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 购物场所 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class BaseShoppingServiceImpl extends ServiceImpl<BaseShoppingMapper, BaseShopping> implements IBaseShoppingService {
    @Autowired
    private BaseShoppingMapper baseShoppingMapper;
    @Override
    public PageInfo<BaseShopping> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseShoppingMapper.selectPageListBaseShopping(para));
    }
}
