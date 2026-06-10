package com.zzy.task.common.thread;


import com.zzy.core.utils.JsonUtil;
import com.zzy.task.common.constant.LvmmConstant;
import com.zzy.task.common.constant.QuNarConstant;
import com.zzy.task.common.db.dao.HotelCommentInfoMapper;
import com.zzy.task.common.db.dao.ScenicCommentInfoMapper;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CrawlerThread implements Runnable  {

    private static final Logger log = LoggerFactory.getLogger(CrawlerThread.class);

    List<LvmmScenic> lvmmScenics;

    ScenicCommentInfoMapper scenicCommentInfoMapper;

    List<LvmmHotel> hotel;
    HotelCommentInfoMapper hotelCommentInfoMapper;
    List<QuNarScenic> quNarScenics;
    LvmmConstant kw;
    QuNarConstant qkw;



    private boolean isFirst = false;

    public boolean isFirst() {
        return isFirst;
    }

    public CrawlerThread setFirst(boolean first) {
        isFirst = first;
        return this;
    }

    public CrawlerThread(List<LvmmScenic> li, ScenicCommentInfoMapper scenicCommentInfoMapper, LvmmConstant kw) {
        this.lvmmScenics = li;
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
        this.kw = kw;
    }

    public CrawlerThread(List<LvmmHotel> hotel, HotelCommentInfoMapper hotelCommentInfoMapper, LvmmConstant ucode) {
        this.hotel = hotel;
        this.hotelCommentInfoMapper = hotelCommentInfoMapper;
        this.kw = ucode;
    }

    public CrawlerThread(boolean tmp, List<QuNarScenic> li, ScenicCommentInfoMapper scenicCommentInfoMapper, QuNarConstant kw) {
        this.quNarScenics = li;
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
        this.qkw = kw;
    }

    @Override
    public void run() {
        int pages = 0;
        try {
            if(scenicCommentInfoMapper!=null && hotelCommentInfoMapper==null){
                if(lvmmScenics!=null && quNarScenics == null){
                    Set<String> set = baseNames(scenicCommentInfoMapper,kw.toDbCode(),"LVMAMA");
                    List<LvmmScenicComment> li = null;
                    List<ScenicCommentInfo> scs = null;
                    for (LvmmScenic scenic : lvmmScenics) {
                        if (!set.contains(scenic.getTitle())) continue;
                        if(scenic.getComments() < 1) continue;
                        pages = DomainUtil.getPages(scenic.getComments(),10);
                        for (int i = 1; i <= pages; i++) {
                            li = QueryCrawlerClient.getLvmmScenicCommentList(scenic,i,isFirst);
                            if(li==null || li.size() <1) continue;
                            scs = li.stream().map(LvmmScenicComment::toScenicComment).collect(Collectors.toList());
                            if(scenic.getKw().equals("putuo")){
                                log.info("驴妈妈普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(scs));
                            }else{
                                log.info("驴妈妈景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(scs));
                            }
                            Thread.sleep(1500L);
                        }
                    }
                }else if(quNarScenics!=null && lvmmScenics == null){
                    List<QuNarScenicComment> li = null;
                    List<ScenicCommentInfo> scs = null;
                    Set<String> set = baseNames(scenicCommentInfoMapper,qkw.toDBcode(),"QUNAR");
                    ExecutorService pool = Executors.newSingleThreadExecutor();
                    for (QuNarScenic scenic : quNarScenics) {
                        if (!set.contains(scenic.getSightName())) continue;
                        pool.submit(()->actionSce(scenic,scenicCommentInfoMapper));
//                        actionSce(scenic,scenicCommentInfoMapper);
                    }
                }
            }else if(hotelCommentInfoMapper!=null && hotel!=null && scenicCommentInfoMapper==null){
                List<LvmmHotelComment> li = null;
                List<HotelCommentInfo> hcs = null;
                Set<String>  set = getUncommentPlaces(hotelCommentInfoMapper);
                ExecutorService pool = Executors.newSingleThreadExecutor();
                for (LvmmHotel hol : hotel) {
                    if(!set.contains(hol.getName())) continue;
                    pool.submit(()->actions(hol,1,hotelCommentInfoMapper));
//                    actions(hol,1,hotelCommentInfoMapper);
                    Thread.sleep(5000L);
                }
            }
        } catch (Exception e) {
            log.error("异常:{}", e);
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

    List<QuNarScenicComment> quSceList = new CopyOnWriteArrayList<>();
    private void actionSce(QuNarScenic scenic, ScenicCommentInfoMapper scenicCommentInfoMapper) {
        try {
            List<ScenicCommentInfo> li = null;
            List<QuNarScenicComment> scs = QueryCrawlerClient.getQuNarScenicCommentList(scenic,1,100,isFirst);
            if(scs==null || scs.size() <1) return;
            li = scs.stream().map(QuNarScenicComment::toScenicComment).collect(Collectors.toList());
            if(scenic.getKw().equals("putuo")){
                log.info("去哪儿普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(li));
            }else{
                log.info("去哪儿景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(li));
            }
            int pages = DomainUtil.getPages(scs.get(0).getCmtNums(),100);
            for (int i = 2; i <=pages; i++) {
                scs = QueryCrawlerClient.getQuNarScenicCommentList(scenic,i,100,isFirst);
                Thread.sleep(1500L);
                if(scs==null || scs.size()<1) continue;
                quSceList.addAll(scs);
                if(quSceList.size()>900){
                    li = quSceList.stream().distinct().map(QuNarScenicComment::toScenicComment).collect(Collectors.toList());
                    if(scenic.getKw().equals("putuo")){
                        log.info("去哪儿普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(li));
                    }else{
                        log.info("去哪儿景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(li));
                    }
                    quSceList.clear();
                }
                if(i==pages && quSceList.size()<900){
                    li = quSceList.stream().distinct().map(QuNarScenicComment::toScenicComment).collect(Collectors.toList());
                    if(scenic.getKw().equals("putuo")){
                        log.info("去哪儿普陀景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(li));
                    }else{
                        log.info("去哪儿景区场所评论更新:{}",scenicCommentInfoMapper.batchInsertInfo(li));
                    }
                    quSceList.clear();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Set<String> getUncommentPlaces(HotelCommentInfoMapper hotelCommentInfo){
        Map<String,Object> m = new HashMap<>();
        m.put("placeSource","LVMAMA");
        m.put("placeType","HOTEL");
        m.put("keyWord",kw.toDbCode());
        Set<String> list = null;
        list = hotelCommentInfo.selectBaseNamesByMap(m);
        return list;

    }

    private void actions(LvmmHotel hol, int i, HotelCommentInfoMapper hotelCommentInfoMapper) {
        QueryCrawlerClient.getLvmmHotelComment(hol,i,hotelCommentInfoMapper,isFirst);
    }
}
