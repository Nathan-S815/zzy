package com.zzy.api.service.reportbase.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseEntertainmentService;
import com.zzy.db.dao.reportbase.ReportBaseEntertainmentMapper;
import com.zzy.db.dao.reportbase.ReportBaseHotelMapper;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.zzy.db.entity.reportbase.ReportBaseHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 娱乐上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseEntertainmentServiceImpl extends ServiceImpl<ReportBaseEntertainmentMapper, ReportBaseEntertainment> implements IReportBaseEntertainmentService {

    @Autowired
    private ReportBaseEntertainmentMapper reportBaseEntertainmentMapper;
    @Override
    public PageInfo<ReportBaseEntertainment> getReportBaseEntertainmentList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseEntertainment>(reportBaseEntertainmentMapper.selectPageListBaseEntertainment(keyword));
    }
}
