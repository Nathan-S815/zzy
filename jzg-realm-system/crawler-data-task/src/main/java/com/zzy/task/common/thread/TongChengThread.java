package com.zzy.task.common.thread;

import com.zzy.core.utils.JsonUtil;
import com.zzy.task.common.constant.TongChengConstant;
import com.zzy.task.common.constant.XieChengLocateHtml;
import com.zzy.task.common.db.dao.HotelCommentInfoMapper;
import com.zzy.task.common.db.dao.PlaceInfoMapper;
import com.zzy.task.common.db.dao.ScenicCommentInfoMapper;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.db.entity.PlaceInfo;
import com.zzy.task.common.db.entity.ScenicCommentInfo;
import com.zzy.task.common.util.DomainUtil;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.TongChengHotel;
import com.zzy.task.client.domain.TongChengHotelComment;
import com.zzy.task.client.domain.TongChengScenic;
import com.zzy.task.client.domain.TongChengScenicComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class TongChengThread  implements Runnable {


    private static final Logger log = LoggerFactory.getLogger(TongChengThread.class);


    PlaceInfoMapper placeInfoMapper;
    HotelCommentInfoMapper hotelCommentInfoMapper;
    ScenicCommentInfoMapper scenicCommentInfoMapper;
    TongChengScenic scenic;
    int pageNo;
    TongChengConstant cityId;
    TongChengConstant sectionId;
//    private static volatile Set<String> setStr = new CopyOnWriteArraySet<>();


    private boolean isFirst = false;

    public boolean isFirst() {
        return isFirst;
    }

    public TongChengThread setFirst(boolean first) {
        isFirst = first;
        return this;
    }


    public TongChengThread(TongChengScenic scenic,ScenicCommentInfoMapper scenicCommentInfoMapper) {
        this.scenicCommentInfoMapper = scenicCommentInfoMapper;
        this.scenic = scenic;
    }


    public TongChengThread(PlaceInfoMapper placeInfoMapper, int i, TongChengConstant start_city_id, TongChengConstant section_id, HotelCommentInfoMapper hotelCommentInfoMapper) {
        this.placeInfoMapper = placeInfoMapper;
        this.pageNo = i;
        this.cityId = start_city_id;
        this.sectionId = section_id;
        this.hotelCommentInfoMapper = hotelCommentInfoMapper;
    }


    @Override
    public void run() {
        try {
            int pages = 0;
            if(placeInfoMapper!=null){
                List<TongChengHotel>  list =  QueryCrawlerClient.getTongchengHotelListByJson(pageNo,cityId,sectionId);
                List<PlaceInfo> li = null;
                if(list==null || list.size()<1) return;
                li = list.stream().map(TongChengHotel::toDataBasePlace).collect(Collectors.toList());
                log.info("同城酒店更新数量:{}",placeInfoMapper.batchInsertInfo(li));
            } else if(scenicCommentInfoMapper!=null && scenic!=null && hotelCommentInfoMapper==null && placeInfoMapper==null){
                pages = DomainUtil.getPages(scenic.getCommentCount(),100);
                List<TongChengScenicComment> li1 = null;
                List<ScenicCommentInfo> scs = null;
                for (int i = 1; i <= pages; i++) {
                    li1 =  QueryCrawlerClient.getTongChengSecnicComment(i,100,scenic,isFirst);
                    if(li1!=null && li1.size() > 0){
                        scs = li1.stream().map(TongChengScenicComment::toScenicComment).collect(Collectors.toList());
                        if(scenic.getKw().equals("putuo")){
                            log.info("同程普陀旅游景区评论数量更新:{}",scenicCommentInfoMapper.batchInsertPutuoInfo(scs));
                        }else{
                            log.info("同程旅游景区评论数量更新:{}",scenicCommentInfoMapper.batchInsertInfo(scs));
                        }
                    }
                    Thread.sleep(3500L);
                }
            }else if(hotelCommentInfoMapper!=null && scenicCommentInfoMapper==null && placeInfoMapper==null){
                List<TongChengHotel>  list =  QueryCrawlerClient.getTongchengHotelListByJson(pageNo,cityId,sectionId);
                List<TongChengHotelComment> li = null;
                List<HotelCommentInfo> ci = null;
                if(list==null || list.size() <1) return;
                Set<String> set = getUncommentPlaces(hotelCommentInfoMapper);
                for (TongChengHotel hotel : list) {
                    if(hotel==null) continue;
                    if(!set.contains(hotel.getName())) continue;
                    if(hotel.getCmtNum() < 1) continue;
                    pages = DomainUtil.getPages(hotel.getCmtNum(),10);
                    for (int i = 1; i <=pages; i++) {
                        li = QueryCrawlerClient.getTongchengHotelCommentByJson(hotel,i,isFirst);
                        if(li!=null && li.size()>0) {
                            ci=li.stream().map(TongChengHotelComment::toHotelComment).collect(Collectors.toList());
                            if(hotel.getKw().equals("putuo")){
                                log.info("同程普陀旅游酒店评论数量更新:{}",hotelCommentInfoMapper.batchInsertPutuoInfo(ci));
                            }else{
                                log.info("同程旅游酒店评论数量更新:{}", hotelCommentInfoMapper.batchInsertInfo(ci));
                            }
                        }
                        Thread.sleep(1200L);
                    }
                }
            }
        } catch (Exception e) {
            log.error("异常>>>>>", e);
        }

    }

    private Set<String> getUncommentPlaces(HotelCommentInfoMapper hotelCommentInfoMapper){
        Map<String,Object> m = new HashMap<>();
        m.put("placeSource","TONGCHENGLVYOU");
        m.put("placeType","HOTEL");
        m.put("keyWord",sectionId.toDbCode());
        Set<String> list = null;
        list = hotelCommentInfoMapper.selectBaseNamesByMap(m);
        return list;

    }
}
