package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseRestaurantService;
import com.zzy.db.dao.reportbase.ReportBaseEntertainmentMapper;
import com.zzy.db.dao.reportbase.ReportBaseRestaurantMapper;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.zzy.db.entity.reportbase.ReportBaseRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 餐饮基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseRestaurantServiceImpl extends ServiceImpl<ReportBaseRestaurantMapper, ReportBaseRestaurant> implements IReportBaseRestaurantService {

    @Autowired
    private ReportBaseRestaurantMapper reportBaseRestaurantMapper;
    @Override
    public PageInfo<ReportBaseRestaurant> getReportBaseRestaurantList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseRestaurant>(reportBaseRestaurantMapper.selectPageListBaseRestaurant(keyword));
    }
}
