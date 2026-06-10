package com.zzy.api.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.base.BaseTravelLineReport;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 路线客流表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
public interface IBaseTravelLineReportService extends IService<BaseTravelLineReport> {

    List<Map<String,Object>> getTravelLineRate(String startTime,String endTime);

    List<Map<String,Object>> getTravelProduct(String startTime,String endTime);

    Map<String,Object> getBaseTravelReportByTravelId(Integer pagNumber,Integer pagSize,Integer travelId,String startTime,String endTime);

    Integer getTravelForecastWeek(String startTime,String endTime);

    List<Map<String,Object>> getScenicTravelReceptionCount(String Time);

}
