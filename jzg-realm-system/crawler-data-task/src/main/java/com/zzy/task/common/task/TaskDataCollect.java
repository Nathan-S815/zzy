package com.zzy.task.common.task;


import cn.hutool.core.date.DateUtil;
import com.zzy.task.common.constant.*;
import com.zzy.task.common.db.dao.*;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.PlaceInfo;
import com.zzy.task.common.db.entity.RestaurantCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.thread.*;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.*;
import com.zzy.task.common.util.DomainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Component
public class TaskDataCollect {

    private static final Logger log = LoggerFactory.getLogger(TaskDataCollect.class);


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


    /**
     * ok-done
     */
    public void collectMeiTuanScenicData(MeiTuanConstant constant,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
//            for (int i = 0; i <= 10; i++) {
//                pool.submit(new MeiTuanThread(i*32, 32, null, scenicCommentInfoMapper,PlaceType.MEITUAN_CATE_SCENIC, constant).setFirst(isFirst));
//                final int t = i;
                mtCrawler(PlaceType.MEITUAN_CATE_SCENIC,constant,isFirst);
                Thread.sleep(35000L);
//            }
        } catch (Exception e) {
            log.error("collectMeiTuanScenicData异常>>>>>",e);
        }
        MeiTuanThread.setStr.clear();
    }


    /**
     * ok-done
     */
    public void collectMeiTuanHotelData(MeiTuanConstant constant,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
//            for (int i = 0; i < 20; i++) {
//                pool.submit(new MeiTuanThread(i*35, 35, null, hotelCommentInfoMapper, PlaceType.MEITUAN_CATE_HOTEL, constant).setFirst(isFirst));
                mtCrawler(PlaceType.MEITUAN_CATE_HOTEL,constant,isFirst);
                Thread.sleep(350000L);
//                break;
//            }
        } catch (Exception e) {
            log.error("collectMeiTuanHotelData异常>>>>>",e);
        }
        MeiTuanThread.setStr.clear();

    }

    private void mtCrawler(PlaceType meituanCate, MeiTuanConstant placeWord, boolean isFirst) {
//        log.info("collectMeiTuanData execute running with page:p{}-{}", offset, size);
        try {
            Map<String,Object> p = new HashMap<>();
            p.put("placeSource", "MEITUAN");
            p.put("keyWord", "jiuzhaigou");
            p.put("placeType", meituanCate.name().replace("MEITUAN_CATE_", ""));
            List<MeiTuanPlace> list = placeInfoMapper.selectHrefPlaceByMap(p).stream().map(PlaceInfo::toMeiTuanPlace).collect(Collectors.toList());
            if (list == null || list.size() < 1) {
                return;
            }
//            Set<String> set = null;
            List<MeiTuanComment> li = null;
            List<MeiTuanComment> liAll = new CopyOnWriteArrayList<>();
            p.put("tableName", meituanCate.name().replace("MEITUAN_CATE_", "")+"_comment_info");
            List<HotelCommentInfo> li1 = null;
            List<RestaurantCommentInfo> li2 = null;
            List<ScenicCommentInfo> li3 = null;
            Date compareDate = DateUtil.offsetDay(new Date(),-1);
            if(meituanCate.equals(PlaceType.MEITUAN_CATE_HOTEL)){
                HotelCommentInfo ci = hotelCommentInfoMapper.selectNewestCommentInfo();
                if(ci!=null){
                    compareDate = DateUtil.offsetDay(ci.getCommentTime(),-1);
                }
                for (MeiTuanPlace place : list) {
//                    if (!set.contains(place.getTitle())) continue;
                    int count = 0;
                    for (int i = 0; i <= 300; i++) {
                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50, isFirst,compareDate);
                        if (li == null || li.size() < 1) {
                            count++;
                        }
                        if(!li.isEmpty()){
                            liAll.addAll(li);
                        }
                        liAll = liAll.stream().distinct().collect(Collectors.toList());
                        if(liAll.size()>=300){
                            li1 = liAll.stream().map(MeiTuanComment::toHotelComment).collect(Collectors.toList());
                            log.info("酒店场所评论更新:{}", hotelCommentInfoMapper.batchInsertInfo(li1));
                            liAll.clear();
                        }
                        if(count==300 || i*50>=place.getComments()*3){
                            if(!liAll.isEmpty()){
                                li1 = liAll.stream().map(MeiTuanComment::toHotelComment).collect(Collectors.toList());
                                log.info("酒店场所评论更新:{}", hotelCommentInfoMapper.batchInsertInfo(li1));
                                liAll.clear();
                                break;
                            }

                        }
                        if(count==300 || i*50>=place.getComments()*3){
                            if(liAll.isEmpty()){
                                break;
                            }
                        }
                        Thread.sleep(3000L);
                    }
                }
            } else if(meituanCate.equals(PlaceType.MEITUAN_CATE_RESTAURANT)){
                RestaurantCommentInfo ci = restaurantCommentInfoMapper.selectNewestCommentInfo();
                if(ci!=null){
                    compareDate = DateUtil.offsetDay(ci.getCommentTime(),-1);
                }
                for (MeiTuanPlace place : list) {
                    int count = 0;
                    for (int i = 0; i <= 300; i++) {
                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50, isFirst,compareDate);
                        if (li == null || li.size() < 1) {
                            count++;
                            continue;
                        }
                        if(!li.isEmpty()){
                            liAll.addAll(li);
                        }
                        liAll = liAll.stream().distinct().collect(Collectors.toList());
                        if(liAll.size()>=300){
                            li2 = liAll.stream().map(MeiTuanComment::toRestautantComment).collect(Collectors.toList());
                            log.info("餐饮场所评论更新:{}", restaurantCommentInfoMapper.batchInsertInfo(li2));
                            liAll.clear();
                        }
                        if(count==300){
                            li2 = liAll.stream().map(MeiTuanComment::toRestautantComment).collect(Collectors.toList());
                            log.info("餐饮场所评论更新:{}", restaurantCommentInfoMapper.batchInsertInfo(li2));
                            liAll.clear();
                            break;
                        }
                        Thread.sleep(3000L);
                    }
                }
            }else if(meituanCate.equals(PlaceType.MEITUAN_CATE_SCENIC)){
                ScenicCommentInfo ci = scenicCommentInfoMapper.selectNewestCommentInfo();
                if(ci!=null){
                    compareDate = DateUtil.offsetDay(ci.getCommentTime(),-1);
                }
                for (MeiTuanPlace place : list) {
                    int count = 0;
                    for (int i = 0; i <= 300; i++) {
                        li = QueryCrawlerClient.getMeiTuanCommentsByPlaceId(place, i * 50, 50, isFirst,compareDate);
                        if (li == null || li.size() < 1) {
                            count++;
                            continue;
                        }
                        if(!li.isEmpty()){
                            liAll.addAll(li);
                        }
                        liAll = liAll.stream().distinct().collect(Collectors.toList());
                        if(liAll.size()>=300){
                            li3 = liAll.stream().map(MeiTuanComment::toScenicComment).collect(Collectors.toList());
                            log.info("景区场所评论更新:{}", scenicCommentInfoMapper.batchInsertInfo(li3));
                            liAll.clear();
                        }
                        if(count==300){
                            li3 = liAll.stream().map(MeiTuanComment::toScenicComment).collect(Collectors.toList());
                            log.info("景区场所评论更新:{}", scenicCommentInfoMapper.batchInsertInfo(li3));
                            liAll.clear();
                            break;
                        }
                        Thread.sleep(3000L);
                    }
                }
            }
        }catch (Exception e){
            log.error("mtCrawler-error",e);
        }

    }

    private Set<String> getDbPlaceNames(Map<String, Object> p) {
        return placeInfoMapper.selectPlaceNames(p);
    }


    /**
     * ok-done  只根据300多家场所
     */
    public void collectMeiTuanRestaurantData(MeiTuanConstant constant,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
//            for (int i = 0; i < 20; i++) {
//                pool.submit(new MeiTuanThread(i*32, 32, null, restaurantCommentInfoMapper, PlaceType.MEITUAN_CATE_RESTAURANT, constant).setFirst(isFirst));
//                final int t = i;
                mtCrawler(PlaceType.MEITUAN_CATE_SCENIC,constant,isFirst);
                Thread.sleep(35000L);
//            }
        } catch (Exception e) {
            log.error("collectMeiTuanRestaurantData异常>>>>>",e);
        }
        MeiTuanThread.setStr.clear();
    }

    public void collectMeiTuanCommentTagData(MeiTuanConstant constant,PlaceType type) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
            for (int i = 0; i < 20; i++) {
                pool.submit(new MeiTuanThread(i*35, 35, commentContentKeyWordMapper,type,constant));
                Thread.sleep(5000L);
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectMeiTuanCommentTagData-{},爬取结束",constant.getCode());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectMeiTuanCommentTagData异常>>>>>",e);
        }
    }



    /**
     * ok-done
     */
    public void collectXieChengRestaurantData(XieChengLocateHtml hml,boolean isFirst) {
        List<XieChengPlace> list = QueryCrawlerClient.getXieChengPlaceList(PlaceType.XIECHENG_RESTAURANT_PATH, hml);
        if (list == null || list.size() < 1) {
            return;
        }
        if(true){
            List<PlaceInfo> places = list.stream().map(XieChengPlace::toDataBasePlace).collect(Collectors.toList());
            placeInfoMapper.batchInsertInfo(places);
            return;
        }
        int res = 0;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        CountDownLatch latch = new CountDownLatch(list.size());
        Set<String> set = null;
        try {
            set = baseNames(restaurantCommentInfoMapper,hml.getDbKw(),"XIECHENG");
            for (XieChengPlace place : list) {
                if (res == pool.getMaximumPoolSize()) break;
                if (!set.contains(place.getTitle())) continue;
                pool.execute(new XieChengCommentThread(place, latch, restaurantCommentInfoMapper).setFirst(isFirst));
                res++;
                Thread.sleep(3000L);
            }
            long r = pool.getTaskCount()-latch.getCount();
            if(latch.getCount() < pool.getTaskCount()){
                for (int i = 0; i < r; i++) {
                    latch.countDown();
                }
            }
            latch.await();
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectXieChengRestaurantData-{},爬取结束",hml.getPath());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectXieChengRestaurantData异常>>>>>",e);
        }
    }


    /**
     * ok-done
     */
    public void collectXieChengScenicData(XieChengLocateHtml html,boolean isFirst) {
        List<XieChengPlace> list = QueryCrawlerClient.getXieChengPlaceList(PlaceType.XIECHENG_SIGHT_PATH, html);
        if (list == null || list.size() < 1) {
            return;
        }
        if(true){
            List<PlaceInfo> places = list.stream().map(XieChengPlace::toDataBasePlace).collect(Collectors.toList());
            placeInfoMapper.batchInsertInfo(places);
            return;
        }
        int res = 0;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        CountDownLatch latch = new CountDownLatch(list.size());
        try {
            Set<String> set = baseNames(scenicCommentInfoMapper,html.getDbKw(),"XIECHENG");
            for (XieChengPlace place : list) {
                if (res == pool.getMaximumPoolSize()) break;
                if (!set.contains(place.getTitle())) continue;
                if(place.getComments() <1) continue;
                pool.execute(new XieChengCommentThread(place, latch, scenicCommentInfoMapper).setFirst(isFirst));
                res++;
                Thread.sleep(3000L);
            }
            long r = pool.getTaskCount()-latch.getCount();
            if(latch.getCount() < pool.getTaskCount()){
                for (int i = 0; i < r; i++) {
                    latch.countDown();
                }
            }
            latch.await();
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectXieChengScenicData-{},爬取结束",html.getPath());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectXieChengScenicData异常>>>>>",e);
        }
    }


    /**
     * keyword
     * @param html
     * @param kw
     */
    public void collectXieChengRestaurantListData(XieChengLocateHtml html,XieChengLocateHtml kw,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
            List<XieChengPlace> list = QueryCrawlerClient.getXieChengRestaurantList(PlaceType.XIECHENG_RESTAURANT_PATH, html,kw);
//            if(true){
//                List<PlaceInfo> places = list.stream().map(XieChengPlace::toDataBasePlace).collect(Collectors.toList());
//                placeInfoMapper.batchInsertInfo(places);
//                return;
//            }
            CountDownLatch latch = new CountDownLatch(list.size());
            int res = 0;
            Set<String> set = baseNames(restaurantCommentInfoMapper,html.getDbKw(),"XIECHENG");
            for (XieChengPlace place : list) {
                if (res == pool.getMaximumPoolSize()) break;
                if (!set.contains(place.getTitle())) continue;
                if(place.getComments() <1) continue;
                pool.execute(new XieChengCommentThread(place, latch, restaurantCommentInfoMapper).setFirst(isFirst));
                res++;
                Thread.sleep(15763L);
            }
            long r = pool.getTaskCount()-latch.getCount();
            if(latch.getCount() < pool.getTaskCount()){
                for (int i = 0; i < r; i++) {
                    latch.countDown();
                }
            }
            latch.await();
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectXieChengRestaurantListData-{},爬取结束",html.getPath());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectXieChengRestaurantListData异常>>>>>",e);
        }
    }


    /**
     * keyword版
     * @param html
     */
    public void collectXieChengScenicListData(XieChengLocateHtml html,XieChengLocateHtml kw,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
//            List<XieChengPlace> list = QueryCrawlerClient.getXieChengScenicList(PlaceType.XIECHENG_SIGHT_PATh_2, html,kw);
            Map<String,Object> m = new HashMap<>();
            m.put("keyWord","jiuzhaigou");
            m.put("placeType", "SCENIC");
            m.put("placeSource", "XIECHENG");
            List<XieChengPlace> list = placeInfoMapper.selectHrefPlaceByMap(m).stream().map(x->x.toXieChengPlace()).collect(Collectors.toList());
            if(list==null || list.size() <1) return;
            int res = 0;
//            Set<String> set = baseNames(scenicCommentInfoMapper,html.getDbKw(),"XIECHENG");
            for (XieChengPlace place : list) {
//                if (res == pool.getMaximumPoolSize()) break;
//                if (!set.contains(place.getTitle())) continue;
//                pool.execute(new XieChengCommentThread(place, null, scenicCommentInfoMapper).setFirst(isFirst));
                xcCrawler(place,"SCENIC",isFirst);
                Thread.sleep(6000L);
//                res++;
            }
//            pool.shutdown();
//            while (true){
//                if (pool.isTerminated()) {
//                    log.info("collectXieChengScenicListData-{},爬取结束",html.getPath());
//                    return;
//                }
//                Thread.sleep(1000L);
//            }
        } catch (Exception e) {
            log.error("collectXieChengScenicListData异常>>>>>",e);
        }
    }

    private void xcCrawler(XieChengPlace place, String placeType, boolean isFirst) throws InterruptedException {
        if("scenic".equals(placeType)){
            int pages = DomainUtil.getPages(place.getComments(), 50);
            List<ScenicCommentInfo> li2 = null;
            List<XieChengComment> li = null;
            Date compareDate = DateUtil.offsetDay(new Date(),-1);
            ScenicCommentInfo ci = scenicCommentInfoMapper.selectNewestCommentInfo();
            if(ci!=null){
                compareDate = DateUtil.offsetDay(ci.getCommentTime(),-1);
            }
            for (int i = 1; i <= pages; i++) {
                li = QueryCrawlerClient.getXieChengCommentByScenic(place, i, isFirst);
                Thread.sleep(3000L);
                if (li == null || li.size() < 1) continue;
                li2 = li.stream().map(XieChengComment::toScenicComment).collect(Collectors.toList());
                log.info("携程景区评论更新:{}", scenicCommentInfoMapper.batchInsertInfo(li2));

            }
        }else {

        }

    }


    private Set<String> baseNames(ScenicCommentInfoMapper scenicCommentInfoMapper,String kw,String source) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", source);
        m.put("placeType", "SCENIC");
        m.put("keyWord",kw);
        Set<String> list = null;
        list = scenicCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }

    private Set<String> baseNames(RestaurantCommentInfoMapper restaurantCommentInfoMapper,String kw,String source) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", source);
        m.put("placeType", "RESTAURANT");
        m.put("keyWord", kw);
        Set<String> list = null;
        list = restaurantCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }


    /**
     * ok-done
     */
    public void collectTuNiuScenicData(TuNiuConstant constant,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
            List<TuNiuScenic> sces = null;
            List<PlaceInfo> l = null;
            int tmp = 0;//todo 正式将70替换为pageNo,不需要tmp
            for (int i = 1; i <= 30; i++) {//30页
                sces = QueryCrawlerClient.getTuNiuScenicList(constant,i);
                Thread.sleep(3000L);
                if(sces!=null && sces.size()>0){
                    pool.submit(new TuNiuThread(null, scenicCommentInfoMapper, sces,constant).setFirst(isFirst));
                }
                tmp++;
                if(tmp>3){
                    break;
                }
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectTuNiuScenicData-{},爬取结束",constant.getCode());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            log.error("collectTuNiuScenicData异常>>>>>",e);
        }
    }


    /**
     * 途牛酒店评论数据无时间
     * 评分人数远多于评论人数
     * ok-done
     */
    public void collectTuNiuHotelData(TuNiuConstant cityCode,TuNiuAreaReq req,TuNiuConstant kw) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
            List<TuNiuHotel> hotels = null;
            int tmp = 0;//todo 正式可替换为pageNo,不需要tmp
            for (int i = 1; i <= 50; i++) {//i页,每页50条,最多收集1000家
                hotels = QueryCrawlerClient.getTuNiuHotelList(cityCode,i,20,req,kw);
                if(hotels!=null && hotels.size() >0){
                    pool.submit(new TuNiuThread(null, hotelCommentInfoMapper, hotels,kw));
                }
                tmp++;
                if(tmp>3){
                    break;
                }
                Thread.sleep(5000L);
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectTuNiuScenicData-{},爬取结束",kw.getDbCode());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            pool.shutdown();
        }
        if(pool.isShutdown()){
            log.info("collectMeiTuanRestaurantData running end...");
        }
    }


    /**
     * ok-done
     */
    public void collectTongChengScenicData(TongChengConstant cityId,TongChengConstant kw,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        List<TongChengScenic> list = QueryCrawlerClient.getTongChengSecnicList(cityId,kw);
        try {
            if(list==null || list.size() <1) return;
//            if(true){
//                List<PlaceInfo> places = list.stream().map(TongChengScenic::toDataBasePlace).collect(Collectors.toList());
//                placeInfoMapper.batchInsertInfo(places);
//                return;
//            }
            Set<String> set = baseNames(scenicCommentInfoMapper,kw.toDbCode(),"TONGCHENGLVYOU");
            for (TongChengScenic scenic : list) {
                if(scenic!=null && scenic.getCommentCount() > 0){
                    if (!set.contains(scenic.getTitle())) continue;
                    pool.submit(new TongChengThread(scenic,scenicCommentInfoMapper).setFirst(isFirst));
                    Thread.sleep(5000L);
                }
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectTongChengScenicData-{},爬取结束",kw.getV());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectTongChengScenicData异常>>>>>",e);
        }
    }


    /**
     * ok-done
     */
    public void collectTongChengHotelData(TongChengConstant cityId,TongChengConstant sectionId,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        try {
            for (int i = 1; i <= 35; i++) {
                pool.submit(new TongChengThread(null,i,cityId,sectionId,hotelCommentInfoMapper).setFirst(isFirst));
                Thread.sleep(5000L);
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectTongChengHotelData-{},爬取结束",sectionId.getV());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectTongChengHotelData异常>>>>>",e);
        }
    }


    /**
     * ok-done
     */
    public void collectLvmmScenicData(LvmmConstant county,LvmmConstant kw,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        List<LvmmScenic> li = null;
        try {
            for (int i = 1; i <= 10; i++) {
                li = QueryCrawlerClient.getLvmmScenicList(county,kw,i);
                if(li==null || li.size()<1) continue;
//                if(true){
//                    List<PlaceInfo> places = li.stream().map(LvmmScenic::toDataBasePlace).collect(Collectors.toList());
//                    placeInfoMapper.batchInsertInfo(places);
//                    return;
//                }
                pool.submit(new CrawlerThread(li,scenicCommentInfoMapper,kw).setFirst(isFirst));
                Thread.sleep(3000L);
            }
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectLvmmScenicData-{},爬取结束",kw.getCode());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectLvmmScenicData异常>>>>>",e);
        }

    }


    /**
     * ok-done
     */
    public void collectLvmmHotelData(LvmmConstant ucode,boolean isFirst) {
        List<LvmmHotel> hotel = null;
        List<PlaceInfo> tmp = null;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2));
        try {
//            for (int i = 1; i <= 20 ; i++) {
//                hotel = QueryCrawlerClient.getLvMMHotelList(i,ucode);
//                Thread.sleep(2500L);
//                if(hotel==null || hotel.size() <1)continue;
////                List<PlaceInfo> places = hotel.stream().map(LvmmHotel::toDataBasePlace).collect(Collectors.toList());
////                placeInfoMapper.batchInsertInfo(places);
//                pool.submit(new CrawlerThread(hotel,hotelCommentInfoMapper,ucode).setFirst(isFirst));
//            }
//            if (true) {
//                return;
//            }
            Map<String,Object> m = new HashMap<>();
            m.put("keyWord",ucode.toDbCode());
            m.put("placeType","HOTEL");
            m.put("placeSource","LVMAMA");
            tmp = placeInfoMapper.selectHrefPlaceByMap(m);
            hotel = placeInfoToLmmHotel(tmp);
            pool.submit((new CrawlerThread(hotel,hotelCommentInfoMapper,ucode).setFirst(isFirst)));
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectLvmmHotelData-{},爬取结束",ucode.toDbCode());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            log.error("collectLvmmHotelData异常>>>>>",e);
        }
    }

    private List<LvmmHotel> placeInfoToLmmHotel(List<PlaceInfo> tmp) {
        List<LvmmHotel> li = new ArrayList<>();
        LvmmHotel h = null;
        for (PlaceInfo info : tmp) {
            h = new LvmmHotel();
            h.setHref(info.getSiteHref());
            h.setKw(info.getKeyWord());
            h.setName(info.getPlaceName());
            li.add(h);
        }
        return li;
    }


    /**
     * ok-done
     */
    public void collectQuNarScenicData(QuNarConstant kw,boolean isFirst) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 200, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        List<QuNarScenic> li  = null;
        List<QuNarScenic> liAll = new CopyOnWriteArrayList<>();
        List<PlaceInfo> tmp = null;
        int t = 0;
        try {
            for (int i = 1; i <= 20; i++) {
                if(t==3){
                    break;
                }
                li = QueryCrawlerClient.getQuNarScenicList(kw,i);
                Thread.sleep(3000L);
                if(li==null || li.size()<1){
                    t++;
                    continue;
                }
                liAll.addAll(li);
//                if(true){
//                    List<PlaceInfo> places = li.stream().map(QuNarScenic::toDataBasePlace).collect(Collectors.toList());
//                    placeInfoMapper.batchInsertInfo(places);
//                }
            }
//            if(true){
//                return;
//            }
            liAll = liAll.stream().distinct().collect(Collectors.toList());
            pool.submit(new CrawlerThread(true,liAll,scenicCommentInfoMapper,kw).setFirst(isFirst));
            pool.shutdown();
            while (true){
                if (pool.isTerminated()) {
                    log.info("collectQuNarScenicData-{},爬取结束",kw.getVal());
                    return;
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            log.error("collectQuNarScenicData-异常",e);
        }
    }



    /**
     * ok-done
     */
    public void collectQuNarHotelList(QuNarConstant url,QuNarConstant query){
        List<QuNarHotel> li  = null;
        List<PlaceInfo> tmp = null;
        int t = 0;
        try {
                li = QueryCrawlerClient.getQuNarHotelList(url,query);
                tmp = li.stream().map(QuNarHotel::toDataBasePlace).collect(Collectors.toList());
                if(tmp==null || tmp.size()<1){
                    return;
                }
                log.info("去哪儿酒店更新数量:{}",placeInfoMapper.batchInsertInfo(tmp));
        } catch (Exception e) {
            log.error("collectQuNarHotelList-异常",e);
        }
    }












}
