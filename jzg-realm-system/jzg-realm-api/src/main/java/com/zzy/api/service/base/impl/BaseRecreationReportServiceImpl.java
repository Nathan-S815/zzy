package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseRecreationReportService;
import com.zzy.db.dao.base.BaseRecreationReportMapper;
import com.zzy.db.dao.base.BaseRecreationReportMapper;
import com.zzy.db.entity.base.BaseRecreationReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 娱乐客流收入表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
@Service
public class BaseRecreationReportServiceImpl extends ServiceImpl<BaseRecreationReportMapper, BaseRecreationReport> implements IBaseRecreationReportService {

    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;

    @Override
    public PageInfo<Map<String, Object>> selectPageListBaseRecreationReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<Map<String, Object>>(baseRecreationReportMapper.selectPageListBaseRecreationReport(keyword));
    }

    @Override
    public PageInfo<Map<String, Object>> getBaseRecreationReportByRecreationId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<Map<String, Object>>(baseRecreationReportMapper.getByRecreationId(id,startTime,endTime));
    }
}
