package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IInfoActivityPropagateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.InfoActivityPropagateMapper;
import com.zzy.db.entity.base.InfoActivityPropagate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 活动宣传 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class InfoActivityPropagateServiceImpl extends ServiceImpl<InfoActivityPropagateMapper, InfoActivityPropagate> implements IInfoActivityPropagateService {
    @Autowired
    private InfoActivityPropagateMapper infoActivityPropagateMapper;
    @Override
    public PageInfo<InfoActivityPropagate> selectPageListInfoActivityPropagate(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(infoActivityPropagateMapper.selectPageListInfoActivityPropagate(para));
    }
}
