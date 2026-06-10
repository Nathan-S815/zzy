package com.zzy.task;

import com.zzy.client.service.JzgApiClientService;
import com.zzy.db.dao.carpark.CarAlarmMapper;
import com.zzy.db.dao.carpark.CarDriverMapper;
import com.zzy.db.dao.carpark.CarGpsMapper;
import com.zzy.db.dao.carpark.CarInfoMapper;
import com.zzy.db.entity.carpark.CarAlarm;
import com.zzy.db.entity.carpark.CarDriver;
import com.zzy.db.entity.carpark.CarGps;
import com.zzy.db.entity.carpark.CarInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TrafficTask {

    @Autowired
    CarGpsMapper carGpsMapper;

    @Autowired
    CarInfoMapper carInfoMapper;

    @Autowired
    CarDriverMapper carDriverMapper;

    @Autowired
    CarAlarmMapper carAlarmMapper;

    @Scheduled(fixedDelay = 1000*60*5)
    public void getCarGpsDataTask() {
        log.info("getCarGpsDataTask定时器开始调用");
        int no = 1;
        int size = 500;
        try {
            Map<String, List<CarGps>> m = JzgApiClientService.getCarGpsData(no, size);
            if (m == null || m.isEmpty()) {
                log.info("getCarGpsDataTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if (m.get(keys).size() < 1) {
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getCarGpsData(i, size);
                if (m.get(keys) == null || m.get(keys).size() < 1) {
                    return;
                }
                log.info("getCarGpsDataTask定时器更新数量:{}", carGpsMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getCarGpsDataTask定时器异常", e);
        }
    }

    @Scheduled(cron = "${caiji.task.car.cron}")
    public void getCarInfoDataTask() {
        log.info("getCarInfoDataTask定时器开始调用");
        int no = 1;
        int size = 500;
        try {
            Map<String, List<CarInfo>> m = JzgApiClientService.getCarInfoData(no, size);
            if (m == null || m.isEmpty()) {
                log.info("getCarInfoDataTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if (m.get(keys).size() < 1) {
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getCarInfoData(i, size);
                if (m.get(keys) == null || m.get(keys).size() < 1) {
                    return;
                }
                log.info("getCarInfoDataTask定时器更新数量:{}", carInfoMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getCarInfoDataTask定时器异常", e);
        }
    }

    @Scheduled(cron = "${caiji.task.car.cron}")
    public void getCarDriverDataTask() {
        log.info("getCarDriverDataTask定时器开始调用");
        int no = 1;
        int size = 500;
        try {
            Map<String, List<CarDriver>> m = JzgApiClientService.getCarDriverData(no, size);
            if (m == null || m.isEmpty()) {
                log.info("getCarDriverDataTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if (m.get(keys).size() < 1) {
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getCarDriverData(i, size);
                if (m.get(keys) == null || m.get(keys).size() < 1) {
                    return;
                }
                log.info("getCarDriverDataTask定时器更新数量:{}", carDriverMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getCarDriverDataTask定时器异常", e);
        }
    }


    @Scheduled(fixedDelay = 1000*60*5)
    public void getCarAlarmDataTask() {
        log.info("getCarAlarmDataTask定时器开始调用");
        int no = 1;
        int size = 500;
        try {
            Map<String, List<CarAlarm>> m = JzgApiClientService.getCarAlarmData(no, size);
            if (m == null || m.isEmpty()) {
                log.info("getCarAlarmDataTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if (m.get(keys).size() < 1) {
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getCarAlarmData(i, size);
                if (m.get(keys) == null || m.get(keys).size() < 1) {
                    return;
                }
                log.info("getCarAlarmDataTask定时器更新数量:{}", carAlarmMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getCarAlarmDataTask定时器异常", e);
        }
    }

}
