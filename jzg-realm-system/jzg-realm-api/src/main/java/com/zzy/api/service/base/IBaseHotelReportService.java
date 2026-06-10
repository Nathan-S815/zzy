package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseRecreation;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 酒店客流收入表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IBaseHotelReportService extends IService<BaseHotelReport> {

    PageInfo<Map<String,Object>> selectPageListBaseHotelReport(Integer pagNumber, Integer pagSize, String keyword);

    Map<String,Object> getBaseHotelReportByHotelId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime);

    Map<String,Object> getInPeopleAndIncome(String userId,String nowDate);

    Integer getTotalNumber();

    List<Map<String,Object>> getHotelOccupancy();

    String getHotelOccupancyByWeek();

    Map<String,Object> getForecastByWeek();

    List<Map<String,Object>> getOccupancyByScenic();

    List<Map<String,Object>> getEmptyRoomByScenic();

    List<Map<String,Object>> getTotelPeopleByScenic();

    Map<String,Object> getTouristStay(String startTime,String endTime);

    List<Map<String,Object>> getTouristStayByScenic(String startTime,String endTime);

    Map<String, List<Map<String, String>>> getTouristQuality(String startTime,String endTime);
}
