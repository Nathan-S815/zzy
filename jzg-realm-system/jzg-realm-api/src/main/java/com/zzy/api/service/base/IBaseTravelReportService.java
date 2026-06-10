package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseTravelReport;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 旅行社客流收入 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IBaseTravelReportService extends IService<BaseTravelReport> {

    PageInfo<Map<String,Object>> selectPageListBaseTravelReport(Integer pagNumber, Integer pagSize, String keyword);

    Map<String,Object> getInPeopleAndIncome(String userId,String nowDate);

    Integer getTravelTotalReception(String year);

    List<Map<String,Object>> getScenicTravelReceptionCount( String startTime,String endTime);

    Integer getTravelForecastWeek(String startTime,String endTime);

    List<Map<String,Object>> getScenicTravelReceptionCountNO();
}
