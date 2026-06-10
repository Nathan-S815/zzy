package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseTravelService;
import com.zzy.db.dao.reportbase.ReportBaseTravelMapper;
import com.zzy.db.dao.reportbase.ReportBaseTravelMapper;
import com.zzy.db.entity.reportbase.ReportBaseTravel;
import com.zzy.db.entity.reportbase.ReportBaseTravel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 旅行社上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseTravelServiceImpl extends ServiceImpl<ReportBaseTravelMapper, ReportBaseTravel> implements IReportBaseTravelService {

    @Autowired
    private ReportBaseTravelMapper reportBaseTravelMapper;
    @Override
    public PageInfo<ReportBaseTravel> getReportBaseTravelList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseTravel>(reportBaseTravelMapper.selectPageListBaseTravel(keyword));
    }
}
