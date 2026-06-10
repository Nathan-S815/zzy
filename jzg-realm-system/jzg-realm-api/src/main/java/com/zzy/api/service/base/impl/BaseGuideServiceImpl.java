package com.zzy.api.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseGuideService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseGuideMapper;
import com.zzy.db.entity.base.BaseGuide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 导游基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class BaseGuideServiceImpl extends ServiceImpl<BaseGuideMapper, BaseGuide> implements IBaseGuideService {
    @Autowired
    private BaseGuideMapper baseGuideMapper;
    @Override
    public PageInfo<BaseGuide> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(baseGuideMapper.selectPageListBaseGuide(para));
    }
}
