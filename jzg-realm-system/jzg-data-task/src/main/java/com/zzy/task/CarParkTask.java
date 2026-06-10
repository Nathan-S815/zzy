package com.zzy.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.client.service.JzgApiClientService;
import com.zzy.db.dao.carpark.GetRemainingSpaceHMapper;
import com.zzy.db.dao.carpark.GetRemainingSpaceMapper;
import com.zzy.db.entity.carpark.GetParkInfo;
import com.zzy.db.entity.carpark.GetRemainingSpace;
import com.zzy.db.entity.carpark.GetRemainingSpaceH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CarParkTask {

    private static final Logger log = LoggerFactory.getLogger(CarParkTask.class);

    @Autowired
    private GetRemainingSpaceHMapper getRemainingSpaceHMapper;

    @Autowired
    private GetRemainingSpaceMapper getRemainingSpaceMapper;

    int size = 1200;


    @Scheduled(cron = "${caiji.task.car.cron}")
    public void getRemainingSpaceHTask(){
        log.info("getRemainingSpaceHTask数据开始调用");
        int no = 1;
//         int size = 500;
        try {
            Map<String,List<GetRemainingSpaceH>> m = JzgApiClientService.getRemainingSpaceHData(no,size);
            if(m==null || m.isEmpty()){
                log.info("getRemainingSpaceHTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            GetRemainingSpaceH gr = null;
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getRemainingSpaceHData(i, size);
                if(m.get(keys)==null || m.get(keys).size()<1){
                    continue;
                }
                gr = m.get(keys).get(m.get(keys).size()-1);
                if(getRemainingSpaceHMapper.selectOneByInfo(gr)!=null){
                    continue;
                }
                log.info("停车场历史定时器更新数量:{}", getRemainingSpaceHMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
            log.error("停车场历史数据异常",e);
        }
        log.info("getRemainingSpaceHTask数据定时器结束");
    }


    @Scheduled(fixedDelay = 1000*180)
    public void getRemainingSpaceTask(){
        log.info("getRemainingSpace数据开始调用");
        int no = 1;
        // int size = 500;
        try {
            Map<String,List<GetRemainingSpace>> m = JzgApiClientService.getRemainingSpaceData(no,size);
            if(m==null || m.isEmpty()){
                log.info("getRemainingSpaceTask定时器api数据为空");
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getRemainingSpaceData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
                log.info("getRemainingSpaceTask定时器更新数量:{}",getRemainingSpaceMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("getRemainingSpaceTask定时器异常",e);
        }
    }

}
