package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseTrafficReportService;
import com.zzy.db.dao.base.BaseTrafficReportMapper;
import com.zzy.db.dao.base.BaseTrafficReportMapper;
import com.zzy.db.entity.base.BaseTrafficReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 交通客流收入表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
@Service
public class BaseTrafficReportServiceImpl extends ServiceImpl<BaseTrafficReportMapper, BaseTrafficReport> implements IBaseTrafficReportService {

    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;

    @Override
    public PageInfo<Map<String, Object>> selectPageListBaseTrafficReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        try{
            return new PageInfo<Map<String, Object>>(baseTrafficReportMapper.selectPageListBaseTrafficReport(keyword));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public PageInfo<Map<String, Object>> getBaseTrafficReportByTrafficId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime) {
        PageHelper.startPage(pagNumber, pagSize);
        try {
            return new PageInfo<Map<String, Object>>(baseTrafficReportMapper.getByTrafficId(id,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
