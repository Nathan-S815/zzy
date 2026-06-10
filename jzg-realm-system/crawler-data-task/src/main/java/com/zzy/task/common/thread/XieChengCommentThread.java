package com.zzy.task.common.thread;

import com.zzy.task.common.db.dao.RestaurantCommentInfoMapper;
import com.zzy.task.common.db.dao.ScenicCommentInfoMapper;
import com.zzy.task.common.db.entity.RestaurantCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.XieChengComment;
import com.zzy.task.client.domain.XieChengPlace;
import com.zzy.task.common.util.DomainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class XieChengCommentThread implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(XieChengCommentThread.class);


    private CountDownLatch latch;

    private XieChengPlace place;

    private RestaurantCommentInfoMapper restaurantCommentInfoMapper;

    private ScenicCommentInfoMapper scenicCommentInfoMapper;

    private boolean isFirst = false;


    public boolean isFirst() {
        return isFirst;
    }

    public XieChengCommentThread setFirst(boolean first) {
        isFirst = first;
        return this;
    }


    public XieChengCommentThread(XieChengPlace place, CountDownLatch latch, RestaurantCommentInfoMapper mapper) {
        this.place = place;
        this.latch = latch;
        this.restaurantCommentInfoMapper = mapper;
    }

    public XieChengCommentThread(XieChengPlace place, CountDownLatch latch, ScenicCommentInfoMapper scenicCommentInfoMapper) {
        this.place = place;
        this.latch = latch;
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
    }

    static Set<String> s = new HashSet<>();

    @Override
    public void run() {
        try {
            int res = 0;
            List<XieChengComment> li = null;
            if (place == null) return;
            if (scenicCommentInfoMapper == null && restaurantCommentInfoMapper != null) {
                li = QueryCrawlerClient.getXieChengCommentByRestaurant(place, isFirst);
                Thread.sleep(3000L);
                if (li == null || li.size() < 1) return;
                List<RestaurantCommentInfo> t = li.stream().map(XieChengComment::toRestautantComment).collect(Collectors.toList());
                if (place.getKw().equals("putuo")) {
                    log.info("携程普陀餐馆评论更新数量:{}", restaurantCommentInfoMapper.batchInsertPutuoInfo(t));
                } else {
                    log.info("携程餐馆评论更新数量:{}", restaurantCommentInfoMapper.batchInsertInfo(t));
                }
            } else {
                s = getUncommentPlaces(scenicCommentInfoMapper);
                if (!s.contains(place.getTitle())) {
                    return;
                }
                int pages = DomainUtil.getPages(place.getComments(), 50);
                List<ScenicCommentInfo> li2 = null;
                for (int i = 1; i <= pages; i--) {
                    li = QueryCrawlerClient.getXieChengCommentByScenic(place, i, isFirst);
                    Thread.sleep(3000L);
                    if (li == null || li.size() < 1) continue;
                    li2 = li.stream().map(XieChengComment::toScenicComment).collect(Collectors.toList());
                    log.info("携程景区评论更新:{}", scenicCommentInfoMapper.batchInsertInfo(li2));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(latch!=null){
                latch.countDown();
            }

        }
    }


    private Set<String> getUncommentPlaces(ScenicCommentInfoMapper scenicCommentInfoMapper) {
        Map<String, Object> m = new HashMap<>();
        m.put("placeSource", "XIECHENG");
        m.put("placeType", "SCENIC");
        m.put("keyWord", place.getKw());
        Set<String> list = null;
        list = scenicCommentInfoMapper.selectBaseNamesByMap(m);
        return list;
    }


}
