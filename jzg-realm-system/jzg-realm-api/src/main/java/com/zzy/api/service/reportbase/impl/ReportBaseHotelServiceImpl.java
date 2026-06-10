package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseHotelService;
import com.zzy.db.dao.reportbase.ReportBaseHotelMapper;
import com.zzy.db.dao.reportbase.ReportBaseScenicMapper;
import com.zzy.db.entity.reportbase.ReportBaseHotel;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 酒店上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseHotelServiceImpl extends ServiceImpl<ReportBaseHotelMapper, ReportBaseHotel> implements IReportBaseHotelService {
    @Autowired
    private ReportBaseHotelMapper reportBaseHotelMapper;
    @Override
    public PageInfo<ReportBaseHotel> getReportBaseHotelList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseHotel>(reportBaseHotelMapper.selectPageListBaseHotel(keyword));
    }
}
