package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IInfoEvacuationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.InfoEvacuationMapper;
import com.zzy.db.entity.base.InfoEvacuation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 疏导分流 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class InfoEvacuationServiceImpl extends ServiceImpl<InfoEvacuationMapper, InfoEvacuation> implements IInfoEvacuationService {
    @Autowired
    private InfoEvacuationMapper infoEvacuationMapper;
    @Override
    public PageInfo<InfoEvacuation> selectPageListInfoEvacuation(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(infoEvacuationMapper.selectPageListInfoEvacuation(para));
    }
}
