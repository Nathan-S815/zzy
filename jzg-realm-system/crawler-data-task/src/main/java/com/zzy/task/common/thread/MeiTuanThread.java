package com.zzy.task.common.thread;

import cn.hutool.core.util.RandomUtil;
import com.zzy.task.client.domain.MeiTuanPlaceCommentTag;
import com.zzy.task.common.constant.MeiTuanConstant;
import com.zzy.task.common.constant.PlaceType;
import com.zzy.task.common.db.dao.*;
import com.zzy.task.common.db.entity.*;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.MeiTuanComment;
import com.zzy.task.client.domain.MeiTuanPlace;
import com.zzy.task.common.util.DomainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class MeiTuanThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MeiTuanThread.class);

    private int no;
    private PlaceInfoMapper placeInfoMapper;
    private MeiTuanConstant placeWord;
    private int size;
    private ScenicCommentInfoMapper scenicCommentInfoMapper;
    private HotelCommentInfoMapper hotelCommentInfoMapper;
    private RestaurantCommentInfoMapper restaurantCommentInfoMapper;
    private PlaceCommentContentKeyWordMapper commentContentKeyWordMapper;
    private PlaceType type;
    public static volatile Set<String> setStr = new CopyOnWriteArraySet<>();

    byte[] lock = new byte[0];

    private boolean isFirst = false;
    public MeiTuanThread(int index, int size, PlaceCommentContentKeyWordMapper commentContentKeyWordMapper, PlaceType meituanCate,MeiTuanConstant kw) {
        this.no = index;
        this.placeWord = kw;
        this.size = size;
        this.type = meituanCate;
        this.commentContentKeyWordMapper = commentContentKeyWordMapper;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public MeiTuanThread setFirst(boolean first) {
        isFirst = first;
        return this;
    }



    public MeiTuanThread(int i, int size, PlaceInfoMapper placeInfoMapper, ScenicCommentInfoMapper scenicCommentInfoMapper, PlaceType meituanCate,MeiTuanConstant kw) {
        this.placeInfoMapper = placeInfoMapper;
        this.no = i;
        this.placeWord = kw;
        this.size = size;
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
        this.type = meituanCate;
    }

    public MeiTuanThread(int index, int size, PlaceInfoMapper placeInfoMapper, HotelCommentInfoMapper hotelCommentInfoMapper, PlaceType meituanCate, MeiTuanConstant kw) {
        this.placeInfoMapper = placeInfoMapper;
        this.no = index;
        this.placeWord = kw;
        this.size = size;
        this.hotelCommentInfoMapper = hotelCommentInfoMapper;
        this.type = meituanCate;
    }

    public MeiTuanThread(int index, int size, PlaceInfoMapper placeInfoMapper, RestaurantCommentInfoMapper restaurantCommentInfoMapper, PlaceType meituanCate, MeiTuanConstant kw) {
        this.placeInfoMapper = placeInfoMapper;
        this.no = index;
        this.placeWord = kw;
        this.size = size;
        this.restaurantCommentInfoMapper = restaurantCommentInfoMapper;
        this.type = meituanCate;
    }



    @Override
    public void run() {
        List<PlaceInfo> l = null;
        log.info("collectMeiTuanData execute running with page:p{}-{}", no, size);
        try {
            List<MeiTuanPlace> list = QueryCrawlerClient.getPlaceListByMeiTuan(placeWord, type, no, size);
            if (list == null || list.size() < 1) {
                return;
            }
            Set<String> set = null;
            int pages = 0;
            List<MeiTuanComment> li = null;
            if(placeInfoMapper!=null){
                List<PlaceInfo> places = list.stream().map(MeiTuanPlace::toDataBasePlace).collect(Collectors.toList());
                placeInfoMapper.batchInsertInfo(places);
            }else if (scenicCommentInfoMapper != null && hotelCommentInfoMapper == null && restaurantCommentInfoMapper == null) {
                List<ScenicCommentInfo> li2 = null;
                set = baseNames(scenicCommentInfoMapper);
                for (MeiTuanPlace place : list) {
                    if (!set.contains(place.getTitle())) continue;
                    if(!setStr.add(place.getTitle())) continue;
                    if(place.getComments() < 1) continue;
                    pages = DomainUtil.getPages(place.getComments(), 50);
                    for (int i = 1; i <=pages; i++) {
//                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50,isFirst);
                        Thread.sleep(6000L);
                        if (li == null || li.size() < 1) continue;
                        li2 = li.stream().map(MeiTuanComment::toScenicComment).collect(Collectors.toList());
                        log.info("景区场所评论更新:{}", scenicCommentInfoMapper.batchInsertInfo(li2));

                    }
                }
            } else if (hotelCommentInfoMapper != null && scenicCommentInfoMapper == null && restaurantCommentInfoMapper == null) {
                List<HotelCommentInfo> li2 = null;
                set = baseNames(hotelCommentInfoMapper);
                for (MeiTuanPlace place : list) {
                    if (!set.contains(place.getTitle())) continue;
                    if(!setStr.add(place.getTitle())) continue;
                    pages = DomainUtil.getPages(place.getComments(), 50);
                    for (int i = 1; i <=pages; i++) {
//                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50,isFirst);
                        Thread.sleep(6000L);
                        if (li == null || li.size() < 1) continue;
                        li2 = li.stream().map(MeiTuanComment::toHotelComment).collect(Collectors.toList());
                            log.info("酒店场所评论更新:{}", hotelCommentInfoMapper.batchInsertInfo(li2));
                    }
                }
            } else if (restaurantCommentInfoMapper != null && hotelCommentInfoMapper == null && scenicCommentInfoMapper == null && commentContentKeyWordMapper==null) {
                List<RestaurantCommentInfo> li2 = null;
                set = baseNames(restaurantCommentInfoMapper);
                for (MeiTuanPlace place : list) {
                    if (place.getComments() < 1) continue;
                    if (!set.contains(place.getTitle())) continue;
                    if(!setStr.add(place.getTitle())) continue;
                    pages = DomainUtil.getPages(place.getComments(), 50);
                    for (int i = 1; i <=pages; i++) {
//                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50,isFirst);
                        Thread.sleep(3000L);
                        if (li == null || li.size() < 1) continue;
                        li2 = li.stream().map(MeiTuanComment::toRestautantComment).collect(Collectors.toList());
                        log.info("餐馆场所评论更新:{}", restaurantCommentInfoMapper.batchInsertInfo(li2));
                    }
                }
            }else if(commentContentKeyWordMapper!=null){
                List<PlaceCommentContentKeyWord> li2 = null;
                List<MeiTuanPlaceCommentTag> tmp = null;
                set = baseNames(commentContentKeyWordMapper,type);
                for (MeiTuanPlace place : list) {
                    if (!set.contains(place.getTitle())) continue;
                        tmp = QueryCrawlerClient.getPlaceCommentTagByMeiTuan(place);
                        Thread.sleep(3000L);
                        if (tmp == null || tmp.size() < 1) continue;
                        li2 = tmp.stream().map(MeiTuanPlaceCommentTag::toPlaceCommentContentKeyWord).collect(Collectors.toList());
                        log.info("场所评论标签内容更新:{}", commentContentKeyWordMapper.batchInsertInfo(li2));
                }
            }
            Thread.sleep(5000L);
        } catch (Exception e) {
           log.error("collectMeiTuanData-error",e);
        }
    }

    private Set<String> baseNames(PlaceCommentContentKeyWordMapper commentContentKeyWordMapper,PlaceType type) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", "MEITUAN");
        m.put("placeType", type.getPlaceTypeDbStr());
        m.put("keyWord", placeWord.toDbCode());
        Set<String> list = null;
        list = commentContentKeyWordMapper.selectBaseNamesByMap(m);
        return list;
    }

    private Set<String> baseNames(HotelCommentInfoMapper hotelCommentInfoMapper) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", "MEITUAN");
        m.put("placeType", "HOTEL");
        m.put("keyWord", placeWord.toDbCode());
        Set<String> list = null;
        list = hotelCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }

    private Set<String> baseNames(ScenicCommentInfoMapper scenicCommentInfoMapper) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", "MEITUAN");
        m.put("placeType", "SCENIC");
        m.put("keyWord", placeWord.toDbCode());
        Set<String> list = null;
        list = scenicCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }


    private Set<String> baseNames(RestaurantCommentInfoMapper restaurantCommentInfoMapper) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", "MEITUAN");
        m.put("placeType", "RESTAURANT");
        m.put("keyWord", placeWord.toDbCode());
        Set<String> list = null;
        list = restaurantCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }
}
