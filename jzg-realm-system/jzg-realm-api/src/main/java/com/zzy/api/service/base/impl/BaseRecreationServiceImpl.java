package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseRecreationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseRecreationMapper;
import com.zzy.db.entity.base.BaseRecreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 休闲娱乐 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class BaseRecreationServiceImpl extends ServiceImpl<BaseRecreationMapper, BaseRecreation> implements IBaseRecreationService {
    @Autowired
    private BaseRecreationMapper baseRecreationMapper;
    @Override
    public PageInfo<BaseRecreation> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseRecreationMapper.selectPageListBaseRecreation(para));
    }
}
