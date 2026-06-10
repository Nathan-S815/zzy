package com.zzy.api.service.base;

import com.zzy.db.entity.base.BaseTicket;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-06-22
 */
public interface IBaseTicketService extends IService<BaseTicket> {

    public List<Map<String,Object>> getTouristDistribution(String startTime, String endTime);

    public int getAllTouristNumber();

    public int getAllTouristNumberByTime(String startTime,String endTime);

    public List<Map<String,Object>> getScenicInformation();

    public Map<String,Object> getAllTouristNumberForecast();

    public Map<String,Object> getTouristNumberContrastByHistory(String startTimeHistory, String endTimeHistory,String startTime, String endTime);

    public Map<String,Object> getTouristNumberContrastByHistoryAndScenic(String startTimeHistory, String endTimeHistory,String startTime, String endTime);

}
