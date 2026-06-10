package com.zzy.task;


import com.zzy.client.service.JzgApiClientService;
import com.zzy.db.dao.ticket.BookingTicketInformationMapper;
import com.zzy.db.dao.ticket.FutureTicketInformationMapper;
import com.zzy.db.dao.ticket.ScenicEnterPeopleMapper;
import com.zzy.db.entity.ticket.BookingTicketInformation;
import com.zzy.db.entity.ticket.FutureTicketInformation;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class TicketTask {


    @Autowired
    FutureTicketInformationMapper futureTicketInformationMapper;

    @Autowired
    BookingTicketInformationMapper bookingTicketInformationMapper;

    @Autowired
    ScenicEnterPeopleMapper scenicEnterPeopleMapper;


    int size = 1200;



    @Scheduled(fixedDelay = 1000*180)
    public void getScenicEnterPeopleTask(){
        String tk = "ScenicEnterPeople";
        log.info("{}定时器开始调用",tk);
        int no = 1;
//        int size = 500;
        try {
            Map<String, List<ScenicEnterPeople>> m = JzgApiClientService.getScenicEnterPeopleData(no,size);
            if(m==null || m.isEmpty()){
                log.info("{}定时器api数据为空",tk);
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
//            ScenicEnterPeople gr = null;
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getScenicEnterPeopleData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
//                gr = m.get(keys).get(m.get(keys).size()-1);
//                gr=scenicEnterPeopleMapper.selectOneByInfo(gr);
//                if(gr !=null){
//                    continue;
//                }
                log.info("{}定时器更新数量:{}",tk,scenicEnterPeopleMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
            log.error("{}定时器异常",tk,e);
        }
        log.info("{}定时器结束",tk);
    }


    @Scheduled(fixedDelay = 1000*600)
//    @Scheduled(cron = "${ticket.task.bookinfo}")
    public void getBookingTicketInformationTask(){
        String tk = "BookingTicketInformation";
        log.info("{}定时器开始调用",tk);
        int no = 1;
//        int size = 500;
        try {
            Map<String, List<BookingTicketInformation>> m = JzgApiClientService.getBookingTicketInformationData(no,size);
            if(m==null || m.isEmpty()){
                log.info("{}定时器api数据为空",tk);
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            BookingTicketInformation gr = null;
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getBookingTicketInformationData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
//                gr = m.get(keys).get(m.get(keys).size()-1);
//                gr=bookingTicketInformationMapper.selectOneByInfo(gr);
//                if(gr !=null){
//                    continue;
//                }
                log.info("{}定时器更新数量:{}",tk,bookingTicketInformationMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
            log.error("{}定时器异常",tk,e);
        }
        log.info("{}定时器结束",tk);
    }


    @Scheduled(fixedDelay = 1000*600)
//    @Scheduled(cron = "${ticket.task.bookinfo}")
    public void getFutureTicketInformationTask(){
        String tk = "FutureTicketInformation";
        log.info("{}定时器开始调用",tk);
        int no = 1;
//        int size = 500;
        try {
            Map<String, List<FutureTicketInformation>> m = JzgApiClientService.getFutureTicketInformationData(no,size);
            if(m==null || m.isEmpty()){
                log.info("{}定时器api数据为空",tk);
                return;
            }
            String keys = m.keySet().iterator().next();
            Integer pages = Integer.parseInt(keys);
            if(m.get(keys).size()<1){
                return;
            }
            FutureTicketInformation gr = null;
            for (int i = 1; i <= pages; i++) {
                m = JzgApiClientService.getFutureTicketInformationData(i,size);
                if(m.get(keys)==null|| m.get(keys).size()<1){
                    return;
                }
//                gr = m.get(keys).get(m.get(keys).size()-1);
//                gr=futureTicketInformationMapper.selectOneByInfo(gr);
//                if(gr !=null){
//                    continue;
//                }
                log.info("{}定时器更新数量:{}",tk,futureTicketInformationMapper.batchInsert(m.get(keys)));
                Thread.sleep(1500L);
            }
        } catch (Exception e) {
            log.error("{}定时器异常",tk,e);
        }
        log.info("{}定时器结束",tk);
    }














}
