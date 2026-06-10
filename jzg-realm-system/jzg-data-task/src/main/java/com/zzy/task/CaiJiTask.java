package com.zzy.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.client.service.JzgApiClientService;
import com.zzy.db.dao.carpark.GetEnterCarMapper;
import com.zzy.db.dao.carpark.GetOutCarMapper;
import com.zzy.db.dao.carpark.GetParkInfoMapper;
import com.zzy.db.entity.carpark.GetEnterCar;
import com.zzy.db.entity.carpark.GetOutCar;
import com.zzy.db.entity.carpark.GetParkInfo;
import com.zzy.db.entity.carpark.GetRemainingSpaceH;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class CaiJiTask {


    @Autowired
    GetEnterCarMapper getEnterCarMapper;

    @Autowired
    GetOutCarMapper getOutCarMapper;

    @Autowired
    GetParkInfoMapper getParkInfoMapper;


    int size = 1200;


    /**
     * 接口返回表无数据
     */
    @Scheduled(cron = "${caiji.task.car.outenter.cron}")
    public void getEnterCarTask(){
        log.info("getEnterCarTask定时器开始调用");
        int no = 1;
//        int size = 500;
        try {
            Map<String,List<GetEnterCar>> m = JzgApiClientService.getEnterCarData(no,size);
            if(m==null || m.isEmpty()){
                log.info("getEnterCarTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getEnterCarData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
                log.info("GetEnterCar定时器更新数量:{}",getEnterCarMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("GetEnterCar定时器异常",e);
        }
    }



    @Scheduled(cron = "${caiji.task.car.outenter.cron}")
    public void getOutCarTask(){
        log.info("getOutCarTask定时器开始调用");
        int no = 1;
//        int size = 500;
        try {
            Map<String,List<GetOutCar>> m = JzgApiClientService.getOutCarData(no,size);
            if(m==null || m.isEmpty()){
                log.info("getOutCarTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            GetOutCar gr = null;
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getOutCarData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
                gr = m.get(keys).get(m.get(keys).size()-1);
                gr=getOutCarMapper.selectOneByInfo(gr);
                if(gr !=null){
                    continue;
                }
                log.info("GetOutCar定时器更新数量:{}",getOutCarMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("GetOutCar定时器异常",e);
        }
    }


//    @Scheduled(cron = "${caiji.task.car.cron}")
    public void getParkInfoDataTask(){
        log.info("getParkInfoDataTask定时器开始调用");
        int no = 1;
        int size = 500;
        try {
            Map<String,List<GetParkInfo>> m = JzgApiClientService.getParkInfoData(no,size);
            if(m==null || m.isEmpty()){
                log.info("getParkInfoDataTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getParkInfoData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
                log.info("getParkInfoDataTask定时器更新数量:{}",getParkInfoMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getParkInfoDataTask定时器异常",e);
        }
    }


















}
