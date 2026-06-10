package com.zzy.api.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseGuideReport;
import com.zzy.db.dao.base.BaseGuideReportMapper;
import com.zzy.api.service.base.IBaseGuideReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-16
 */
@Service
public class BaseGuideReportServiceImpl extends ServiceImpl<BaseGuideReportMapper, BaseGuideReport> implements IBaseGuideReportService {

    @Autowired
    BaseGuideReportMapper baseGuideReportMapper;
    @Override
    public PageInfo<Map<String,Object>> selectPageListBaseGuideReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<Map<String,Object>>(baseGuideReportMapper.selectPageListBaseGuideReport(keyword));
    }

    @Override
    public PageInfo<BaseGuideReport> getBaseGuideReportByGuideId(Integer pagNumber, Integer pagSize, int guideId) {
        PageHelper.startPage(pagNumber,pagSize);
        List<BaseGuideReport> baseGuideReports = baseGuideReportMapper.selectList(new QueryWrapper<BaseGuideReport>().eq("guide_id", guideId).orderByDesc("report_time"));
        return new PageInfo<BaseGuideReport>(baseGuideReports);
    }
}
