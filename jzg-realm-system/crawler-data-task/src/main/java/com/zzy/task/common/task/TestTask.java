package com.zzy.task.common.task;


import cn.hutool.core.date.DateUtil;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.MeiTuanComment;
import com.zzy.task.common.db.dao.*;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class TestTask {

    @Autowired
    private PlaceInfoMapper placeInfoMapper;

    @Autowired
    private HotelCommentInfoMapper hotelCommentInfoMapper;

    @Autowired
    private ScenicCommentInfoMapper scenicCommentInfoMapper;

    @Autowired
    private RestaurantCommentInfoMapper restaurantCommentInfoMapper;

    @Autowired
    private PlaceCommentContentKeyWordMapper commentContentKeyWordMapper;



    public void test() {

    }


//    @Scheduled(fixedDelay = 1000 * 3600 * 8)
    public void testMeituanCrawler() throws InterruptedException {
//        String placeTitle = "猪猪星球";
//        String placeId = "162134432";

//        String placeId = "6347974";
//        String placeTitle = "阿勇海鲜排档（莲花路1号店）";

        String placeTitle = "鑫城宾馆";
        String placeId = "65116340";
        Date dt = DateUtil.parse("2020-01-01", "yyyy-MM-dd");
        int count = 0;
        List<MeiTuanComment> listAll = new ArrayList<>();
        for (int i = 0; i <= 300; i++) {
//            List<MeiTuanComment> list =  QueryCrawlerClient.testMeituanDaZhongDianping(i,"沈家门渔港");//.testMeituanCommentList(placeTitle,placeId,i*10);
//            List<MeiTuanComment> list =  QueryCrawlerClient.testRestaurantMeituanCommentList(placeTitle,placeId,i*50);
            List<MeiTuanComment> list =  QueryCrawlerClient.testHotelMeituanCommentList(placeTitle,placeId,i*50);
//            List<MeiTuanComment> list = QueryCrawlerClient.testScenicMeituanCommentList(placeTitle, placeId, i * 50);
            if (list == null || list.size() < 1) {
                count++;
            } else {
                listAll.addAll(list);
                listAll = listAll.stream().distinct().collect(Collectors.toList());
                if (listAll.size() >= 1000) {
//                    List<ScenicCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toScenicComment).collect(Collectors.toList());
//                    scenicCommentInfoMapper.batchInsertInfo(pscnic);
//                    List<RestaurantCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toRestautantComment).collect(Collectors.toList());
//                    restaurantCommentInfoMapper.batchInsertInfo(pscnic);
                    List<HotelCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toHotelComment).collect(Collectors.toList());
                    hotelCommentInfoMapper.batchInsertInfo(pscnic);
                    listAll.clear();
                }
            }
            Thread.sleep(3500L);
            if (count == 5) {
                if (listAll.size() > 0) {
//                    List<ScenicCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toScenicComment).collect(Collectors.toList());
//                    scenicCommentInfoMapper.batchInsertInfo(pscnic);
//                List<RestaurantCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toRestautantComment).collect(Collectors.toList());
//                restaurantCommentInfoMapper.batchInsertInfo(pscnic);
                    List<HotelCommentInfo> pscnic = listAll.stream().map(MeiTuanComment::toHotelComment).collect(Collectors.toList());
                    hotelCommentInfoMapper.batchInsertInfo(pscnic);
                    listAll.clear();
                }
                break;
            }
        }
        log.info("testMeituan定时器结束");


    }


}
