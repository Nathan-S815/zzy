package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseScenicReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseScenicReportMapper;
import com.zzy.db.entity.base.BaseScenicReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 景区人数收入上报表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class BaseScenicReportServiceImpl extends ServiceImpl<BaseScenicReportMapper, BaseScenicReport> implements IBaseScenicReportService {
    @Autowired
    private BaseScenicReportMapper baseScenicReportMapper;
    @Override
    public PageInfo<Map<String,Object>> selectPageListBaseScenicReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<Map<String,Object>>(baseScenicReportMapper.selectPageListBaseScenicReport(keyword));
    }

    @Override
    public Map<String, Object> getInPeopleAndIncome(String userId,String nowDate) {
        return baseScenicReportMapper.getInPeopleAndIncome(userId ,nowDate);
    }

    @Override
    public PageInfo<Map<String, Object>> getBaseScenicReportByScenicId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime) {
        PageHelper.startPage(pagNumber, pagSize);
        try {
            return new PageInfo<Map<String, Object>>(baseScenicReportMapper.getByScenicId(id,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
