package com.zzy.api.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseRestaurantReportService;
import com.zzy.db.dao.base.BaseHotelReportMapper;
import com.zzy.db.dao.base.BaseRestaurantReportMapper;
import com.zzy.db.entity.base.BaseRestaurantReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐饮场所收入表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
@Service
public class BaseRestaurantReportServiceImpl extends ServiceImpl<BaseRestaurantReportMapper, BaseRestaurantReport> implements IBaseRestaurantReportService {

    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;

    @Override
    public PageInfo<Map<String, Object>> selectPageListBaseRestaurantReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<Map<String, Object>>(baseRestaurantReportMapper.selectPageListBaseRestaurantReport(keyword));
    }

    @Override
    public PageInfo<Map<String, Object>> getBaseRestaurantReportByRestaurantId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<Map<String, Object>>(baseRestaurantReportMapper.getByRestaurantId(id,startTime,endTime));
    }
}
