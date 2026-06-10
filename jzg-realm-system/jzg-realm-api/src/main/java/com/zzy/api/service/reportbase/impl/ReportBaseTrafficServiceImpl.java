package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseTrafficService;
import com.zzy.db.dao.reportbase.ReportBaseTrafficMapper;
import com.zzy.db.dao.reportbase.ReportBaseTrafficMapper;
import com.zzy.db.entity.reportbase.ReportBaseTraffic;
import com.zzy.db.entity.reportbase.ReportBaseTraffic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交通上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseTrafficServiceImpl extends ServiceImpl<ReportBaseTrafficMapper, ReportBaseTraffic> implements IReportBaseTrafficService {

    @Autowired
    private ReportBaseTrafficMapper reportBaseTrafficMapper;
    @Override
    public PageInfo<ReportBaseTraffic> getReportBaseTrafficList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseTraffic>(reportBaseTrafficMapper.selectPageListBaseTraffic(keyword));
    }
}
