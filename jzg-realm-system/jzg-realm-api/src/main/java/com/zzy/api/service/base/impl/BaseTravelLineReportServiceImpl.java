package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseTravelLineReportService;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.SortUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.BaseTravelLineReportMapper;
import com.zzy.db.dao.base.BaseTravelReportMapper;
import com.zzy.db.dao.reportbase.ReportBaseTravelMapper;
import com.zzy.db.entity.base.BaseTravelLineReport;
import com.zzy.db.entity.reportbase.ReportBaseTravel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 路线客流表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
@Service
public class BaseTravelLineReportServiceImpl extends ServiceImpl<BaseTravelLineReportMapper, BaseTravelLineReport> implements IBaseTravelLineReportService {

    @Autowired
    private BaseTravelLineReportMapper baseTravelLineReportMapper;
    @Autowired
    private ReportBaseTravelMapper reportBaseTravelMapper;

    @Override
    public List<Map<String, Object>> getTravelLineRate(String startTime, String endTime) {
        List<Map<String, Object>> result = new ArrayList();
        //        List<BaseTravelLineReport> baseTravelLineReports = baseTravelLineReportMapper.getBaseTravelLineReportByTime(startTime, endTime);
        List<BaseTravelLineReport> baseTravelLineReports = baseTravelLineReportMapper.selectList(new QueryWrapper<BaseTravelLineReport>().select("sum(people_num) as people_num,travel_line").between("report_time", startTime, endTime).groupBy("travel_line").orderByDesc("sum(people_num)"));
        int totelNum = 0;
        for (BaseTravelLineReport baseTravelLineReport : baseTravelLineReports) {
            Map map = new HashMap();
            map.put("line", baseTravelLineReport.getTravelLine());
            map.put("num", baseTravelLineReport.getPeopleNum());
            result.add(map);
            totelNum += baseTravelLineReport.getPeopleNum();
        }
        for (Map<String, Object> map : result) {
            map.put("rate", NumberMathUtil.getRate(map.get("num"), totelNum, 0));
        }
        return SortUtil.comparator(result, "rate", SortUtil.DESC);
    }

    @Override
    public Map<String, Object> getBaseTravelReportByTravelId(Integer pagNumber, Integer pagSize, Integer travelId, String startTime, String endTime) {

        PageHelper.startPage(pagNumber, pagSize);
        PageInfo page = new PageInfo<BaseTravelLineReport>(baseTravelLineReportMapper.selectPageListBaseTravelLineReport(travelId, startTime, endTime));
        List<BaseTravelLineReport> list = page.getList();
        List<Map<String, Object>> result = new ArrayList<>();
        for (BaseTravelLineReport baseTravelLineReport : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("reportTime", baseTravelLineReport.getReportTime());
            map.put("id", baseTravelLineReport.getId());
            map.put("teamNum", 0);
            map.put("peopleNum", baseTravelLineReport.getPeopleNum());
            map.put("line", baseTravelLineReport.getTravelLine());
            result.add(map);
        }
        Map r = new HashMap();
        r.put("list", result);
        r.put("total", page.getTotal());
        r.put("pages", page.getPages());
        return r;

    }

    @Override
    public Integer getTravelForecastWeek(String startTime, String endTime) {
        return baseTravelLineReportMapper.getTravelForecastWeek(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getScenicTravelReceptionCount(String Time) {
        String startTime = Time;
        String endTime = TimeDateUtil.getDateBefore(-1);
        List<Map<String, Object>> baseTravelLineReportByTime = baseTravelLineReportMapper.getBaseTravelByTime(startTime, endTime);
        return baseTravelLineReportByTime;
    }

    @Override
    public List<Map<String, Object>> getTravelProduct(String startTime, String endTime) {
        List<Map<String, Object>> result = new ArrayList();
        List<BaseTravelLineReport> baseTravelLineReports = baseTravelLineReportMapper.getBaseTravelLineReportByTime(startTime, endTime);
        Set set = new HashSet();
        int totelNum = 0;
        for (BaseTravelLineReport baseTravelLineReport : baseTravelLineReports) {
            Map<String, Object> map = new HashMap<>();
            map.put("lineName", baseTravelLineReport.getTravelLine());

            ReportBaseTravel reportBaseTravel = reportBaseTravelMapper.selectOne(new QueryWrapper<ReportBaseTravel>().eq("id", baseTravelLineReport.getSubTravelId()));

            map.put("travelName", baseTravelLineReport.getSubTravelName());
            map.put("travelNameShort", reportBaseTravel.getAbbreviation());
            map.put("num", baseTravelLineReport.getPeopleNum());
            map.put("introduction", baseTravelLineReport.getLineIntroduction());
            result.add(map);
        }
        return SortUtil.comparator(result, "num", SortUtil.DESC);
    }
}
