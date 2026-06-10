package com.zzy.api.task;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.db.dao.base.*;
import com.zzy.db.entity.base.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SystemTask {


    @Autowired
    private BaseTravelLineReportMapper baseTravelLineReportMapper;

    @Autowired
    private BaseHotelReportMapper baseHotelReportMapper;

    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;

    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;

    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;

    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;


    /**
     * 更新数据日期（以下数据未发现更新逻辑）
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDate(){
        BaseTravelLineReport baseTravelLineReport = new BaseTravelLineReport();
        baseTravelLineReport.setReportTime(DateUtil.yesterday());
        baseTravelLineReportMapper.update(baseTravelLineReport, new QueryWrapper<>());

        baseTravelLineReport.setId(294L);
        baseTravelLineReport.setReportTime(new Date());
        baseTravelLineReportMapper.updateById(baseTravelLineReport);
        baseTravelLineReport.setId(295L);
        baseTravelLineReportMapper.updateById(baseTravelLineReport);

        BaseHotelReport baseHotelReport = new BaseHotelReport();
        baseHotelReport.setReportTime(DateUtil.yesterday());
        baseHotelReportMapper.update(baseHotelReport, new QueryWrapper<>());

        BaseShoppingReport baseShoppingReport = new BaseShoppingReport();
        baseShoppingReport.setReportTime(DateUtil.yesterday());
        baseShoppingReportMapper.update(baseShoppingReport, new QueryWrapper<>());

        BaseRecreationReport baseRecreationReport = new BaseRecreationReport();
        baseRecreationReport.setReportTime(DateUtil.yesterday());
        baseRecreationReportMapper.update(baseRecreationReport, new QueryWrapper<>());

        BaseRestaurantReport baseRestaurantReport = new BaseRestaurantReport();
        baseRestaurantReport.setReportTime(DateUtil.yesterday());
        baseRestaurantReportMapper.update(baseRestaurantReport, new QueryWrapper<>());

        BaseTrafficReport baseTrafficReport = new BaseTrafficReport();
        baseTrafficReport.setReportTime(DateUtil.yesterday());
        baseTrafficReportMapper.update(baseTrafficReport, new QueryWrapper<>());
    }

}
