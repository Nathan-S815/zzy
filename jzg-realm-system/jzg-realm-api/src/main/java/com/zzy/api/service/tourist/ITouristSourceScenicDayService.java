package com.zzy.api.service.tourist;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.tourist.TouristSourceScenicDay;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区游客来源地市分析(日) 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
public interface ITouristSourceScenicDayService extends IService<TouristSourceScenicDay> {
    public List<Map<String,Object>> getTouristDistribution(String startTime, String endTime);

    public List<Map<String,Object>> getTouristFromByScenic(String startTime, String endTime, String scenicName);

    public List<Map<String,Object>> getTouristFromByScenicCity(String startTime, String endTime, String scenicName);

    public List<Map<String,Object>> getTouristNumberContrast(String startTime, String endTime);

    public Long getAllTouristNumber(String startTime,String endTime);

    public Map<String,Object> getTouristNumberContrastByHistory(String startTimeHistory, String endTimeHistory,String startTime, String endTime);

    public Map<String,Object> getTouristNumberContrastByHistoryAndScenic(String startTimeHistory, String endTimeHistory,String startTime, String endTime);
}
