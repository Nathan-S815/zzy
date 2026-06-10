package com.zzy.task.common.thread;

import com.zzy.task.common.constant.TuNiuConstant;
import com.zzy.task.common.db.dao.HotelCommentInfoMapper;
import com.zzy.task.common.db.dao.PlaceInfoMapper;
import com.zzy.task.common.db.dao.ScenicCommentInfoMapper;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.PlaceInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.TuNiuComment;
import com.zzy.task.client.domain.TuNiuHotel;
import com.zzy.task.client.domain.TuNiuScenic;
import com.zzy.task.client.domain.TuNiuScenicComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class TuNiuThread implements Runnable {


    private static final Logger log = LoggerFactory.getLogger(TuNiuThread.class);


    PlaceInfoMapper placeInfoMapper;
    HotelCommentInfoMapper hotelCommentInfoMapper;
    ScenicCommentInfoMapper scenicCommentInfoMapper;
    CountDownLatch latch;
    List<TuNiuScenic> scenics;
    List<TuNiuHotel> hotels;
    byte[] lock = new byte[0];
    TuNiuConstant constant;
    public static Set<String> setStr = new CopyOnWriteArraySet<>();

    private boolean isFirst = false;

    public boolean isFirst() {
        return isFirst;
    }

    public TuNiuThread setFirst(boolean first) {
        isFirst = first;
        return this;
    }



    public TuNiuThread(PlaceInfoMapper placeInfoMapper, HotelCommentInfoMapper hotelCommentInfoMapper, List<TuNiuHotel> hotels,TuNiuConstant constant) {
        this.hotels = hotels;
        this.placeInfoMapper = placeInfoMapper;
        this.hotelCommentInfoMapper = hotelCommentInfoMapper;
        this.constant = constant;
    }

    public TuNiuThread(PlaceInfoMapper placeInfoMapper, ScenicCommentInfoMapper scenicCommentInfoMapper, List<TuNiuScenic> sces, TuNiuConstant constant) {
        this.scenics = sces;
        this.placeInfoMapper = placeInfoMapper;
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
        this.constant = constant;
    }



    List<TuNiuScenicComment> tuNiuSceList = new CopyOnWriteArrayList<>();
    @Override
    public void run() {
        int res = 0;
        try {
            if(placeInfoMapper!=null){
                if(scenics!=null && hotels==null){
                    List<PlaceInfo> li = scenics.stream().map(TuNiuScenic::toDataBasePlace).collect(Collectors.toList());
                    res = placeInfoMapper.batchInsertInfo(li);
                    log.info("途牛景区更新数量:{}",res);
                }else if(hotels!=null && scenics==null){
                    List<PlaceInfo> li = hotels.stream().map(TuNiuHotel::toDataBasePlace).collect(Collectors.toList());
                    res = placeInfoMapper.batchInsertInfo(li);
                    log.info("途牛酒店更新数量:{}",res);
                }
            }else if(scenicCommentInfoMapper!=null && hotelCommentInfoMapper==null){
                if(scenics==null){
                    throw new RuntimeException("途牛景区评论数据插入异常，列表数据为空");
                }
                List<TuNiuScenicComment> li = null;
                List<ScenicCommentInfo> tmp = null;
                int pages = 0;
                Set<String> set = getUncommentPlaces(scenicCommentInfoMapper);
                for (TuNiuScenic scenic : scenics) {
                    if(!set.contains(scenic.getTitle())) continue;
                    if(!setStr.add(scenic.getTitle())) continue;
                    pages = DomainUtil.getPages(scenic.getComments(),10);
                    for (int i = 1; i <= pages; i++) {
                        li = QueryCrawlerClient.getTuNiuScenicCommentsList(i,scenic,isFirst);
                        if(li==null || li.size() <1) continue;
                        tuNiuSceList.addAll(li);
                        if(tuNiuSceList.size()>=300){
                            tmp = tuNiuSceList.stream().distinct().map(TuNiuScenicComment::toScenicComment).collect(Collectors.toList());
                            if(scenic.getKw().equals("putuo")){
                                log.info("途牛普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(tmp));
                            }else{
                                log.info("途牛景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(tmp));
                            }
                            tuNiuSceList.clear();
                        }
                        if(i==pages && tuNiuSceList.size()<300){
                            tmp = tuNiuSceList.stream().distinct().map(TuNiuScenicComment::toScenicComment).collect(Collectors.toList());
                            if(scenic.getKw().equals("putuo")){
                                log.info("途牛普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(tmp));
                            }else{
                                log.info("途牛景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(tmp));
                            }
                            tuNiuSceList.clear();
                        }
//                        tmp = li.stream().map(TuNiuScenicComment::toScenicComment).collect(Collectors.toList());
//                        if(scenic.getKw().equals("putuo")){
//                            log.info("途牛普陀景区评论数据更新数量:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(tmp));
//                        }else {
//                            log.info("途牛景区评论数据更新数量:{}",scenicCommentInfoMapper.batchInsertInfo(tmp));
//                        }
                        Thread.sleep(3000L);
                    }

                }
            }else if(hotelCommentInfoMapper!=null && scenicCommentInfoMapper==null){
                if(hotels==null){
                    throw new RuntimeException("途牛酒店评论数据插入异常，数据列表为空");
                }
                List<TuNiuComment> li = null;
                List<HotelCommentInfo> tmp = null;
                int pages = 0;
                Set<String> set = getUncommentPlaces(hotelCommentInfoMapper);
                for (TuNiuHotel hot : hotels) {
                    if(hot==null || hot.getComment().getCount()==null) continue;
                    if(!set.contains(hot.getHotel().getChineseName())) continue;
                    if(!setStr.add(hot.getHotel().getChineseName())) continue;
                    li = QueryCrawlerClient.getTuNiuHotelCommentList(hot,1,50);
                    if(li==null || li.size()<1) continue;
                    tmp = li.stream().map(TuNiuComment::toHotelComment).collect(Collectors.toList());
                    if(hot.getKw().equals("putuo")){
                        log.info("途牛普陀酒店评论数据更新数量:{}",hotelCommentInfoMapper.batchInsertPutuoInfo(tmp));
                    }else {
                        log.info("途牛酒店评论数据更新数量:{}",hotelCommentInfoMapper.batchInsertInfo(tmp));
                    }
                    pages = DomainUtil.getPages(li.get(0).getContentsCommentNum(),50);
                    if(pages==0) continue;
                    for (int i = 1; i <= pages; i++) {
                        li = QueryCrawlerClient.getTuNiuHotelCommentList(hot,i,50);
                        if(li==null || li.size()<1) continue;
                        tmp = li.stream().map(TuNiuComment::toHotelComment).collect(Collectors.toList());
                        if(hot.getKw().equals("putuo")){
                            log.info("途牛普陀酒店评论数据更新数量:{}",hotelCommentInfoMapper.batchInsertPutuoInfo(tmp));
                        }else {
                            log.info("途牛酒店评论数据更新数量:{}",hotelCommentInfoMapper.batchInsertInfo(tmp));
                        }
                        Thread.sleep(3500L);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Set<String> getUncommentPlaces(HotelCommentInfoMapper hotelCommentInfoMapper){
        Map<String,Object> m = new HashMap<>();
        m.put("placeSource","TUNIU");
        m.put("placeType","HOTEL");
        m.put("keyWord",constant.getDbCode());
        Set<String> list = null;
        list = hotelCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }

    private Set<String> getUncommentPlaces(ScenicCommentInfoMapper scenicCommentInfoMapper){
        Map<String,Object> m = new HashMap<>();
        m.put("placeSource","TUNIU");
        m.put("placeType","SCENIC");
        m.put("keyWord",constant.getDbCode());
        Set<String> list = null;
        list = scenicCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }
}
