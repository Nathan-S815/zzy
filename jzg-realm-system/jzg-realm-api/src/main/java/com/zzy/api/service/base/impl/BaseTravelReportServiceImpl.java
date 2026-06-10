package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseTravelReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.BaseTravelReportMapper;
import com.zzy.db.entity.base.BaseTravelReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 旅行社客流收入 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class BaseTravelReportServiceImpl extends ServiceImpl<BaseTravelReportMapper, BaseTravelReport> implements IBaseTravelReportService {
    @Autowired
    private BaseTravelReportMapper baseTravelReportMapper;
    @Override
    public PageInfo<Map<String,Object>> selectPageListBaseTravelReport(Integer pagNumber, Integer pagSize,String keyword) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<Map<String,Object>>(baseTravelReportMapper.selectPageListBaseTravelReport(keyword));
    }

    @Override
    public Map<String, Object> getInPeopleAndIncome(String userId, String nowDate) {
        return baseTravelReportMapper.getInPeopleAndIncome(userId,nowDate);
    }

    /**
     * 旅行社接待总量
     * */
    @Override
    public Integer getTravelTotalReception(String year) {
        Integer travelTotalReception = baseTravelReportMapper.getTravelTotalReception(year);
        return travelTotalReception;
    }

    @Override
    public List<Map<String, Object>> getScenicTravelReceptionCount(String startTime,String endTime) {
        return baseTravelReportMapper.getScenicTravelReceptionCount(startTime,endTime);
    }

    @Override
    public Integer getTravelForecastWeek(String startTime, String endTime) {
        return baseTravelReportMapper.getTravelForecastWeek(startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> getScenicTravelReceptionCountNO() {
        List<Map<String, Object>> scenicTravelReceptionCount = baseTravelReportMapper.getScenicTravelReceptionCount(null, null);
        for (Map<String, Object> stringObjectMap : scenicTravelReceptionCount) {
            stringObjectMap.put("inPeople",0);
        }
        return scenicTravelReceptionCount;
    }
}
