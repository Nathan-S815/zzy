package com.zzy.task;

import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.*;
import com.zzy.db.dao.carpark.GetRemainingSpaceMapper;
import com.zzy.db.dao.ticket.ScenicEnterPeopleMapper;
import com.zzy.db.entity.carpark.GetRemainingSpace;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class ModifyDataTask {

    private static final Logger log = LoggerFactory.getLogger(ModifyDataTask.class);

    @Autowired
    private BaseHotelReportMapper baseHotelReportMapper;

    @Autowired
    private BaseTravelReportMapper baseTravelReportMapper;

    @Autowired
    private BaseScenicReportMapper baseScenicReportMapper;

    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;

    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;

    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;

    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;

    @Autowired
    private ScenicEnterPeopleMapper scenicEnterPeopleMapper;

    @Autowired
    private GetRemainingSpaceMapper getRemainingSpaceMapper;

//    @Scheduled(fixedDelay = 1000*60)
//    public void HotelReportTask() {
//        log.info("jzg-酒店数据修改定时器执行开始");
//        for (int i = 1; i <= 5; i++) {
//            int num = i * 100 - new Random().nextInt(50);
//            int inPeople = 10000 + new Random().nextInt(100);
//            String time = TimeDateUtil.getYesterdayDate();
//            baseHotelReportMapper.updateSurplusRoom(i, num, inPeople,time);
//        }
//        log.info("jzg-酒店数据修改定时器执行结束");
//    }

//    @Scheduled(fixedDelay = 1000*60)
//    public void TravelReportTask() {
//        log.info("jzg-旅行社数据修改定时器执行开始");
//        SimpleDateFormat hourF = new SimpleDateFormat("HH");
//        SimpleDateFormat minuteF = new SimpleDateFormat("mm");
//        int hour = Integer.parseInt(hourF.format(new Date()));
//        int minute = Integer.parseInt(minuteF.format(new Date()));
//        int inPeople = 100000 + 11 * (60 * hour + minute);
//        baseTravelReportMapper.updateInPeople(inPeople);
//        log.info("jzg-旅行社数据修改定时器执行结束");
//    }

//    @Scheduled(fixedDelay = 1000*60)
//    public void IncomeTask() {
//        log.info("jzg-收入数据修改定时器执行开始");
//        SimpleDateFormat hourF = new SimpleDateFormat("HH");
//        SimpleDateFormat minuteF = new SimpleDateFormat("mm");
//        int hour = Integer.parseInt(hourF.format(new Date()));
//        int minute = Integer.parseInt(minuteF.format(new Date()));
//        int num = 51 * (60 * hour + minute);
//        baseHotelReportMapper.updateIncome(568452 + num);
//        baseRecreationReportMapper.updateIncome(795876 + num);
//        baseRestaurantReportMapper.updateIncome(613524 + num);
//        baseScenicReportMapper.updateIncome(265472 + num);
//        baseShoppingReportMapper.updateIncome(668752 + num);
//        baseTrafficReportMapper.updateIncome(232468 + num);
//        baseTravelReportMapper.updateIncome(556471 + num);
//        log.info("jzg-收入数据修改定时器执行结束");
//    }

//    @Scheduled(fixedDelay = 1000*60)
//    public void ScenicEnterTask() {
//        log.info("jzg-入园人数数据修改定时器执行开始");
//        List<ScenicEnterPeople> list = new ArrayList<>();
//        ScenicEnterPeople jiuzhaigou = new ScenicEnterPeople();
//        jiuzhaigou.setName("九寨沟");
//        jiuzhaigou.setRecordDate(LocalDateTime.now());
//        long num = 13000 + new Random().nextInt(100);
//        jiuzhaigou.setEnterNumber(num);
//        jiuzhaigou.setRestNumber(20000-num);
//        list.add(jiuzhaigou);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(jiuzhaigou);
//        ScenicEnterPeople jiawuhai = new ScenicEnterPeople();
//        jiawuhai.setName("甲勿海");
//        jiawuhai.setRecordDate(LocalDateTime.now());
//        num = 8000 + new Random().nextInt(100);
//        jiawuhai.setEnterNumber(num);
//        jiawuhai.setRestNumber(15000-num);
//        list.add(jiawuhai);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(jiawuhai);
//        ScenicEnterPeople shenxianchi = new ScenicEnterPeople();
//        shenxianchi.setName("嫩恩桑措(神仙池)");
//        shenxianchi.setRecordDate(LocalDateTime.now());
//        num = 9000 + new Random().nextInt(100);
//        shenxianchi.setEnterNumber(num);
//        shenxianchi.setRestNumber(15000-num);
//        list.add(shenxianchi);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(shenxianchi);
//        ScenicEnterPeople ganhaizi = new ScenicEnterPeople();
//        ganhaizi.setName("爱情海(甘海子)");
//        ganhaizi.setRecordDate(LocalDateTime.now());
//        num = 7000 + new Random().nextInt(100);
//        ganhaizi.setEnterNumber(num);
//        ganhaizi.setRestNumber(12000-num);
//        list.add(ganhaizi);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(ganhaizi);
//        ScenicEnterPeople daxiongmao = new ScenicEnterPeople();
//        daxiongmao.setName("甲勿海大熊猫保护研究园");
//        daxiongmao.setRecordDate(LocalDateTime.now());
//        num = 8000 + new Random().nextInt(100);
//        daxiongmao.setEnterNumber(num);
//        daxiongmao.setRestNumber(12000-num);
//        list.add(daxiongmao);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(daxiongmao);
//        ScenicEnterPeople guzangzhai = new ScenicEnterPeople();
//        guzangzhai.setName("古藏寨");
//        guzangzhai.setRecordDate(LocalDateTime.now());
//        num = 4000 + new Random().nextInt(100);
//        guzangzhai.setEnterNumber(num);
//        guzangzhai.setRestNumber(10000-num);
//        list.add(guzangzhai);
////        scenicEnterPeopleMapper.insertScenicEnterPeople(guzangzhai);
//        scenicEnterPeopleMapper.batchInsert(list);
//        log.info("jzg-入园人数数据修改定时器执行结束");
//    }

//    @Scheduled(fixedDelay = 1000*60)
//    public void CarParkTask() {
//        log.info("jzg-停车场数据修改定时器执行开始");
//        int num = 540+new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(1,num);
//        num = 600 +new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(6,num);
//        num = 580 +new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(7,num);
//        num = 520 +new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(8,num);
//        num = 500 +new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(9,num);
//        num = 400 +new Random().nextInt(10);
//        getRemainingSpaceMapper.updateCarPark(10,num);
//        log.info("jzg-停车场数据修改定时器执行结束");
//    }

}
