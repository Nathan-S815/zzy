package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseScenicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseScenicMapper;
import com.zzy.db.entity.base.BaseScenic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区基础信息 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
@Service
public class BaseScenicServiceImpl extends ServiceImpl<BaseScenicMapper, BaseScenic> implements IBaseScenicService {

    @Autowired
    private BaseScenicMapper baseScenicMapper;
    @Override
    public Page<BaseScenic> getValueList(Page<BaseScenic> page, String typeCode) {
       return null;
    }

    @Override
    public PageInfo<BaseScenic> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseScenicMapper.selectPageList(para));
    }
}
