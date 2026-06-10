package com.zzy.task.common.task;

import com.zzy.task.common.constant.*;
import com.zzy.task.common.db.dao.MethodRunningStateMapper;
import com.zzy.task.common.db.entity.MethodRunningState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * 初始化进程:
 * 美团scenic评论-finished,hotel评论-finished,restaurant评论-none
 *
 * 携程scenic评论-part,restaurant评论-none(降速爬取后还是会频繁被锁ip)
 *
 * 同程旅游scenic评论-finishd,hotel评论-finished(景区爬取完美)
 *
 * 驴妈妈scenic评论-finished,hotel评论finished
 *
 * 途牛scenic评论-finished,hotel评论finished
 *
 * 去哪儿scenic评论-finished
 *
 *
 *
 *
 *
 *
 *
 *
 */
@SuppressWarnings("ALL")
@Component
@PropertySource("classpath:cron.properties")
public class JiuZhaiGouTask {


    private static final Logger log = LoggerFactory.getLogger(JiuZhaiGouTask.class);


    @Autowired
    private TaskDataCollect taskDataCollect;

    @Autowired
    private MethodRunningStateMapper mapper;


    private boolean isTest = false;


    /**
     * ok
     */
//    @Scheduled(fixedDelay  =1000*3600*8)
    @Scheduled(cron = "${meituanJzgHotelTask}")
    public void meituanJzgHotelTask(){
        log.info("jzg-美团酒店数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("MEITUAN");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取美团:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgHotelTask");
                tmp.setSource("MEITUAN");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgHotelTask");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectMeiTuanHotelData(MeiTuanConstant.getPlaceListByMeiTuan_jiuzhaigou,isTest);
        } catch (Exception e) {
            log.error("美团酒店数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("meituanJzgHotelTask");
        tmp.setSource("MEITUAN");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-美团酒店数据定时器执行结束");
    }


    /**
     * ok
     */
    @Scheduled(cron = "${meituanJzgScenicTask}")
    public void meituanJzgScenicTask(){
        log.info("jzg-美团景区定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("MEITUAN");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取美团:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgScenicTask");
                tmp.setSource("MEITUAN");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgScenicTask");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectMeiTuanScenicData(MeiTuanConstant.getPlaceListByMeiTuan_jiuzhaigou,isTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tmp.setEndTime(new Date());
        tmp.setMethodName("meituanJzgScenicTask");
        tmp.setSource("MEITUAN");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-美团景区定时器执行结束");
    }


    /**
     * ok
     */
    @Scheduled(cron = "${meituanJzgRestaurantTask}")
    public void meituanJzgRestaurantTask(){
        log.info("jzg-美团餐馆数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("MEITUAN");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取美团:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgRestaurantTask");
                tmp.setSource("MEITUAN");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgRestaurantTask");
                tmp.setSource("MEITUAN");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectMeiTuanRestaurantData(MeiTuanConstant.getPlaceListByMeiTuan_jiuzhaigou,isTest);
        } catch (Exception e) {
            log.error("美团餐馆数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("meituanJzgRestaurantTask");
        tmp.setSource("MEITUAN");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-美团餐馆定时器执行结束");
    }


    @Scheduled(cron = "${meituanJzgScenicTagTask}")
    public void meituanJzgScenicTagTask(){
        log.info("jzg-美团标签数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("MEITUAN");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取美团:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgScenicTagTask");
                tmp.setSource("MEITUAN");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("meituanJzgScenicTagTask");
                tmp.setSource("MEITUAN");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectMeiTuanCommentTagData(MeiTuanConstant.getPlaceListByMeiTuan_jiuzhaigou,PlaceType.MEITUAN_CATE_SCENIC);
        } catch (Exception e) {
            log.error("美团标签数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("meituanJzgScenicTagTask");
        tmp.setSource("MEITUAN");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-美团标签定时器执行结束");
    }



    /**
     * ok
     */
    @Scheduled(cron = "${xiechengJzgScenicTask}")
//    @Scheduled(fixedDelay = 1000*3600*8)
    public void xiechengJzgScenic(){
        log.info("jzg-携程景区数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("XIECHENG");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取携程:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("xiechengJzgScenic");
                tmp.setSource("XIECHENG");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("xiechengJzgScenic");
                tmp.setSource("XIECHENG");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectXieChengScenicListData(XieChengLocateHtml.JIUZHAIGOU_HTML,XieChengLocateHtml.getXieChengList_KEYWORD_JZG,isTest);
        } catch (Exception e) {
            log.error("携程景区数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("xiechengJzgScenic");
        tmp.setSource("XIECHENG");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-携程景区定时器执行结束");
    }


    /**
     * ok
     */
    @Scheduled(cron = "${xiechengJzgRestaurantTask}")
    public void xiechengJzgRestaurant(){
        log.info("jzg-携程餐馆数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("XIECHENG");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取携程:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("xiechengJzgRestaurant");
                tmp.setSource("XIECHENG");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("xiechengJzgRestaurant");
                tmp.setSource("XIECHENG");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectXieChengRestaurantListData(XieChengLocateHtml.JIUZHAIGOU_HTML,XieChengLocateHtml.getXieChengList_KEYWORD_JZG,isTest);
        } catch (Exception e) {
            log.error("携程餐馆数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("xiechengJzgRestaurant");
        tmp.setSource("XIECHENG");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-携程餐馆定时器执行结束");
    }

    /**
     * ok
     */
    @Scheduled(cron = "${tongchengJzgScenicTask}")
    public void tongchengJzgScenic(){
        log.info("jzg-同程景区数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("TONGCHENGLVYOU");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取同程:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tongchengJzgScenic");
                tmp.setSource("TONGCHENGLVYOU");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tongchengJzgScenic");
                tmp.setSource("TONGCHENGLVYOU");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectTongChengScenicData(TongChengConstant.start_city_id_jzg,TongChengConstant.getTongChengSecnicList_key_word_jzg,isTest);
        } catch (Exception e) {
            log.error("同程景区数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("tongchengJzgScenic");
        tmp.setSource("TONGCHENGLVYOU");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-同程景区定时器执行结束");
    }


    @Scheduled(cron = "${tongchengJzgHotelTask}")
    public void tongchengJzgHotel(){
        log.info("jzg-同程酒店数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("TONGCHENGLVYOU");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取同程:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tongchengJzgHotel");
                tmp.setSource("TONGCHENGLVYOU");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tongchengJzgHotel");
                tmp.setSource("TONGCHENGLVYOU");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectTongChengHotelData(TongChengConstant.start_city_id_jzg,TongChengConstant.section_id_jzg,isTest);
        } catch (Exception e) {
            log.error("同程酒店数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("tongchengJzgHotel");
        tmp.setSource("TONGCHENGLVYOU");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-同程酒店定时器执行结束");
    }




    @Scheduled(cron = "${tuNiuJzgScenicTask}")
    public void tuNiuJzgScenic(){
        log.info("jzg-途牛景区数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("TUNIU");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取途牛:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tuNiuJzgScenic");
                tmp.setSource("TUNIU");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("tuNiuJzgScenic");
                tmp.setSource("TUNIU");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectTuNiuScenicData(TuNiuConstant.getTuNiuScenicList_location_jzg,isTest);
        } catch (Exception e) {
            log.error("途牛景区数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("tuNiuJzgScenic");
        tmp.setSource("TUNIU");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-途牛景区定时器执行结束");
    }


    /**
     * 评论没有时间，无法入库判断，可能会造成大量重复内容
     */
//    @Scheduled(cron = "${tuNiuJzgHotelTask}")
//    public void tuNiuJzgHotel(){
//        log.info("jzg-途牛酒店数据定时器执行开始");
//        MethodRunningState tmp = mapper.selectBySource("TUNIU");
//        try {
//            if(tmp!=null && tmp.getIsRunning()==1){
//                log.info("当前爬取途牛:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
//                return;
//            }
//            if(tmp==null){
//                tmp = new MethodRunningState();
//                tmp.setStartTime(new Date());
//                tmp.setIsRunning(1);
//                tmp.setMethodName("tuNiuJzgHotel");
//                tmp.setSource("TUNIU");
//                mapper.insertSelective(tmp);
//            }else {
//                tmp.setStartTime(new Date());
//                tmp.setIsRunning(1);
//                tmp.setMethodName("tuNiuJzgHotel");
//                tmp.setSource("TUNIU");
//                mapper.updateBySource(tmp);
//            }
//            taskDataCollect.collectTuNiuHotelData(TuNiuConstant.cityCode_jzg,TuNiuConstant.getTuNiuAreaReq_JZG(),TuNiuConstant.key_word_jzg);
//        } catch (Exception e) {
//            log.error("途牛酒店数据定时器异常:",e);
//        }
//        tmp = new MethodRunningState();
//        tmp.setEndTime(new Date());
//        tmp.setMethodName("tuNiuJzgHotel");
//        tmp.setSource("TUNIU");
//        tmp.setIsRunning(0);
//        mapper.updateBySource(tmp);
//        log.info("jzg-途牛酒店定时器执行结束");
//    }


    @Scheduled(cron = "${lvmmJzgScenicTask}")
    public void lvmmJzgScenic(){
        log.info("jzg-驴妈妈景区数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("LVMAMA");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取驴妈妈:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("lvmmJzgScenic");
                tmp.setSource("LVMAMA");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("lvmmJzgScenic");
                tmp.setSource("LVMAMA");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectLvmmScenicData(LvmmConstant.getLvmmScenicList_SCENIC_COUNTY_JZG,LvmmConstant.getLvmmScenicList_KEYWORD_JZG,isTest);
        } catch (Exception e) {
            log.error("驴妈妈景区数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("lvmmJzgScenic");
        tmp.setSource("LVMAMA");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-驴妈妈景区定时器执行结束");
    }

    @Scheduled(cron = "${lvmmJzgHotelTask}")
    public void lvmmJzgHotel(){
        log.info("jzg-驴妈妈酒店数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("LVMAMA");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取驴妈妈:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("lvmmJzgHotel");
                tmp.setSource("LVMAMA");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("lvmmJzgHotel");
                tmp.setSource("LVMAMA");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectLvmmHotelData(LvmmConstant.HOTEL_UCODE_JZG,isTest);
        } catch (Exception e) {
            log.error("驴妈妈酒店数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("lvmmJzgHotel");
        tmp.setSource("LVMAMA");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-驴妈妈酒店定时器执行结束");
    }



    @Scheduled(cron = "${quNarJzgScenicTask}")
    public void quNarJzgScenic(){
        log.info("jzg-去哪儿景区数据定时器执行开始");
        MethodRunningState tmp = mapper.selectBySource("QUNAR");
        try {
            if(tmp!=null && tmp.getIsRunning()==1){
                log.info("当前爬取去哪儿:{}方法正在执行,结束本次定时任务",tmp.getMethodName());
                return;
            }
            if(tmp==null){
                tmp = new MethodRunningState();
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("quNarJzgScenic");
                tmp.setSource("QUNAR");
                mapper.insertSelective(tmp);
            }else {
                tmp.setStartTime(new Date());
                tmp.setIsRunning(1);
                tmp.setMethodName("quNarJzgScenic");
                tmp.setSource("QUNAR");
                mapper.updateBySource(tmp);
            }
            taskDataCollect.collectQuNarScenicData(QuNarConstant.KEY_WORD_JZG,isTest);
        } catch (Exception e) {
            log.error("去哪儿景区数据定时器异常:",e);
        }
        tmp = new MethodRunningState();
        tmp.setEndTime(new Date());
        tmp.setMethodName("quNarJzgScenic");
        tmp.setSource("QUNAR");
        tmp.setIsRunning(0);
        mapper.updateBySource(tmp);
        log.info("jzg-去哪儿景区定时器执行结束");
    }




}
