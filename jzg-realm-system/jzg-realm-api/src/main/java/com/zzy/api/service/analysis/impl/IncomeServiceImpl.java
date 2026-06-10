package com.zzy.api.service.analysis.impl;

import com.zzy.api.service.analysis.IncomeService;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.*;
import com.zzy.db.entity.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private BaseScenicReportMapper baseScenicReportMapper;
    @Autowired
    private BaseHotelReportMapper baseHotelReportMapper;
    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;
    @Autowired
    private BaseTravelReportMapper baseTravelReportMapper;
    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;
    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;
    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;
    @Autowired
    private BaseTicketMapper baseTicketMapper;

    @Override
    public long getCurrentIncome(String startTime, String endTime) {
        Long scenicIncome = baseTicketMapper.getIncomeByTime(startTime, endTime);
        Long hotelIncome = baseHotelReportMapper.getIncomeByTime(startTime, endTime);
        Long restaurantIncome = baseRestaurantReportMapper.getIncomeByTime(startTime, endTime);
        Long travelIncome = baseTravelReportMapper.getIncomeByTime(startTime, endTime);
        Long trafficIncome = baseTrafficReportMapper.getIncomeByTime(startTime, endTime);
        Long shoppingIncome = baseShoppingReportMapper.getIncomeByTime(startTime, endTime);
        Long recreationIncome = baseRecreationReportMapper.getIncomeByTime(startTime, endTime);
        return (scenicIncome != null ? scenicIncome : 0 )
                + (hotelIncome != null ? hotelIncome : 0 )
                + (restaurantIncome != null ? restaurantIncome : 0 )
                + (travelIncome != null ? travelIncome : 0 )
                + (trafficIncome != null ? trafficIncome : 0 )
                + (shoppingIncome != null ? shoppingIncome : 0 )
                + (recreationIncome != null ? recreationIncome : 0 );
    }
}
