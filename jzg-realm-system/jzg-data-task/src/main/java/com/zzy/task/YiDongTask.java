package com.zzy.task;


import cn.hutool.core.date.DateUtil;
import com.zzy.client.service.JzgYidongClientService;
import com.zzy.db.dao.hotmap.GetAbjzgHourLocalDataMapper;
import com.zzy.db.dao.hotmap.GetAbjzgMinuteLocalDataMapper;
import com.zzy.db.dao.hotmap.GetAbjzgMinutePeopleHotDataMapper;
import com.zzy.db.dao.yidong.*;
import com.zzy.db.entity.hotmap.GetAbjzgHourLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinuteLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.zzy.db.entity.yidong.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class YiDongTask {

    @Autowired
    JzgydCityPassengerSourceMapper jzgydCityPassengerSourceMapper;

    @Autowired
    JzgydCountryPassengerSourceMapper jzgydCountryPassengerSourceMapper;

    @Autowired
    JzgydCountyPassengerSourceMapper jzgydCountyPassengerSourceMapper;

    @Autowired
    JzgydCountyScenicPassengerMapper jzgydCountyScenicPassengerMapper;

    @Autowired
    JzgydScenicConsumptionMapper jzgydScenicConsumptionMapper;

    @Autowired
    JzgydProvincePassengerSourceMapper jzgydProvincePassengerSourceMapper;

    @Autowired
    JzgydScenicPassengerAgeMapper jzgydScenicPassengerAgeMapper;

    @Autowired
    JzgydScenicPassengerGenderMapper jzgydScenicPassengerGenderMapper;

    @Autowired
    JzgydScenicPassengerStayTimeMapper jzgydScenicPassengerStayTimeMapper;

    @Autowired
    GetAbjzgHourLocalDataMapper getAbjzgHourLocalDataMapper;

    @Autowired
    GetAbjzgMinuteLocalDataMapper getAbjzgMinuteLocalDataMapper;

    @Autowired
    GetAbjzgMinutePeopleHotDataMapper getAbjzgMinutePeopleHotDataMapper;


    /**
     * 区县客流量定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void countyPassenger(){
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydCountyPassengerSource> list =  JzgYidongClientService.getJzgydCountyPassengerSource(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydCountyPassengerSourceMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydCountyPassengerSource(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydCountyPassengerSourceMapper.batchInsert(list));
        }
    }


    /**
     * 城市客流量定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void cityPassenger(){
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydCityPassengerSource> list =  JzgYidongClientService.getJzgydCityPassengerSource(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydCityPassengerSourceMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydCityPassengerSource(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydCityPassengerSourceMapper.batchInsert(list));
        }
    }


    /**
     * 省客流量定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void provincePassenger(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydProvincePassengerSource> list =  JzgYidongClientService.getJzgydProvincePassengerSource(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydProvincePassengerSourceMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydProvincePassengerSource(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydProvincePassengerSourceMapper.batchInsert(list));
        }
    }


    /**
     * 国家客流量定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void countryPassenger(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydCountryPassengerSource> list =  JzgYidongClientService.getJzgydCountryPassengerSource(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydCountryPassengerSourceMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydCountryPassengerSource(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydCountryPassengerSourceMapper.batchInsert(list));
        }
    }


    /**
     * 客流年龄定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void passengerAge(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydScenicPassengerAge> list =  JzgYidongClientService.getJzgydScenicPassengerAge(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydScenicPassengerAgeMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydScenicPassengerAge(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydScenicPassengerAgeMapper.batchInsert(list));
        }
    }


    /**
     * 客流性别定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void passengerGender(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydScenicPassengerGender> list =  JzgYidongClientService.getJzgydScenicPassengerGender(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydScenicPassengerGenderMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydScenicPassengerGender(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydScenicPassengerGenderMapper.batchInsert(list));
        }
    }


    /**
     * 客流消费定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void scenicConsumption(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydScenicConsumption> list =  JzgYidongClientService.getJzgydScenicConsumption(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydScenicConsumptionMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydScenicConsumption(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydScenicConsumptionMapper.batchInsert(list));
        }
    }



    /**
     * 客流停留时长定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void passengerStayTime(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);
        List<JzgydScenicPassengerStayTime> list =  JzgYidongClientService.getJzgydScenicPassengerStayTime(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydScenicPassengerStayTimeMapper.batchInsert(list));
        }
        list =  JzgYidongClientService.getJzgydScenicPassengerStayTime(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydScenicPassengerStayTimeMapper.batchInsert(list));
        }
    }

    /**
     * 客流停留时长定时器
     */
    @Scheduled(cron = "${yidong.task.cron}")
    public void passengerNumber(){
        ;
        Date st = DateUtil.offsetDay(new Date(), -1);

        List<JzgydCountyScenicPassenger> list =  JzgYidongClientService.getJzgydCountyScenicPassenger(st,1);
        if(list!=null && list.size()>0){
            log.info("日客流量更新数量:{}",jzgydCountyScenicPassengerMapper.batchInsert(list));
        }

        list =  JzgYidongClientService.getJzgydCountyScenicPassenger(st,2);
        if(list!=null && list.size()>0){
            log.info("月客流量更新数量:{}",jzgydCountyScenicPassengerMapper.batchInsert(list));
        }
    }

    /**
     * 小时客流数据
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void passengerNumberByHour(){
        ;
        Date st = DateUtil.offsetHour(new Date(), -1);
        List<GetAbjzgHourLocalData> list =  JzgYidongClientService.getGetAbjzgHourLocalData(st);
        if(list!=null && list.size()>0){
            log.info("小时流量更新数量:{}",getAbjzgHourLocalDataMapper.batchInsert(list));
        }
    }

    /**
     * 小时客流数据
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void passengerNumberNow(){
        ;
        List<GetAbjzgMinuteLocalData> list =  JzgYidongClientService.getGetAbjzgMinuteLocalData();
        if(list!=null && list.size()>0){
            log.info("实时流量更新数量:{}",getAbjzgMinuteLocalDataMapper.batchInsert(list));
        }
    }

    /**
     * 热力图
     */
    @Scheduled(fixedDelay = 1000*60*60)
    public void hotMap(){
        ;
        List<GetAbjzgMinutePeopleHotData> list =  JzgYidongClientService.getGetAbjzgMinutePeopleHotData();
        if(list!=null && list.size()>0){
            log.info("热力图更新数量:{}",getAbjzgMinutePeopleHotDataMapper.batchInsert(list));
        }
    }














}
