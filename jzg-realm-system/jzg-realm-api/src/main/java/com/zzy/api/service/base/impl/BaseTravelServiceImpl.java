package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseTravelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseTravelMapper;
import com.zzy.db.entity.base.BaseTravel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 旅行社 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class BaseTravelServiceImpl extends ServiceImpl<BaseTravelMapper, BaseTravel> implements IBaseTravelService {
    @Autowired
    private BaseTravelMapper baseTravelMapper;
    @Override
    public PageInfo<BaseTravel> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseTravelMapper.selectPageListBaseTravel(para));
    }
}
