package com.zzy.api.service.consumption;

import java.util.List;
import java.util.Map;

public interface ConsumptionService {

    List<Map<String,Object>> getConsumption(String startTime,String endTime);

    List<Map<String,Object>> getConsumptionPerPerson(String startTime,String endTime);

    List<Map<String,Object>> getConsumptionForecast();
}
