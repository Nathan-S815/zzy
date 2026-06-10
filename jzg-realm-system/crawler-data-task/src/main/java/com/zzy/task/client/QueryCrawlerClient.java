package com.zzy.task.client;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.zzy.core.utils.ApiCrawlerUtil;
import com.zzy.task.common.util.OkHttpCli;
import com.zzy.core.utils.JsonUtil;
import com.zzy.core.utils.JsoupUtil;
import com.zzy.task.common.constant.*;
import com.zzy.task.common.db.dao.HotelCommentInfoMapper;
import com.zzy.task.common.db.entity.HotelCommentInfo;
import com.zzy.task.common.thread.XieChengThread;
import com.zzy.task.common.util.DomainUtil;
import com.zzy.task.client.domain.*;
import okhttp3.Cookie;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Component
public class QueryCrawlerClient {

    private QueryCrawlerClient(){}
    private static final Logger log = LoggerFactory.getLogger(QueryCrawlerClient.class);

    public static final String meituan_placelist_url = "https://apimobile.meituan.com";
    public static final String meituan_comment_url= "https://www.meituan.com";
    public static final String xiecheng_placelist_url = "https://you.ctrip.com";
    public static final String xiecheng_comment_url = "https://sec-m.ctrip.com";


    @Resource
    private OkHttpCli httpClis;

    private static OkHttpCli httpCli;

    @PostConstruct
    public void init() {
        QueryCrawlerClient.httpCli = httpClis;
    }


    public static List<MeiTuanComment> testMeituanCommentList(String placeTitle,String placeId,int offset) {
        String url = "https://www.meituan.com/ptapi/poi/getcomment?id="+placeId+"&offset="+offset+"&pageSize=10&mode=0&starRange=&userId=&sortType=1";
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("Cookie","_lxsdk_cuid=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8; t_lxid=171a0714c01c8-05fdbd39f42aa8-4313f6a-15f900-171a0714c01c8-tid; _hc.v=4e05c57c-8b56-814e-66f1-a7fd5836a902.1587608253; iuuid=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; cityname=%E8%88%9F%E5%B1%B1; _lxsdk=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; uuid=462843a2670846e0a721.1589176517.1.0.0; ci=190; rvct=190%2C726%2C319%2C50; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; IJSESSIONID=m0y6arph0vdp1v4rvteuv4gjl; __utma=74597006.479807747.1590030534.1590030534.1590030534.1; __utmc=74597006; __utmz=74597006.1590030534.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmb=74597006.7.9.1590030546857; _lxsdk_s=1723530037d-f35-1cb-608%7C%7C26");
        header.put("Content-Type","application/json");
        header.put("Connection","keep-alive");
//        header.put("Accept-Encoding","gzip, deflate, br");
//        header.put("Accept-Language","zh-CN,zh;q=0.9");
        List<MeiTuanComment> list = new CopyOnWriteArrayList<>();
        try {
            String res = httpGet(url,null,header, null);
            log.info("已发出美团http请求:{},{}",placeTitle,offset);
            Thread.sleep(3500L);
            JSONObject jo = JSON.parseObject(res);
            JSONArray ja = jo.getJSONArray("comments");
            if(ja==null || ja.isEmpty()){
                return null;
            }
            ja.forEach(o->list.add(((JSONObject)o).toJavaObject(MeiTuanComment.class).setPlaceKw("putuo").setPlaceTitle(placeTitle)));
            return list;
        } catch (Exception e) {
            log.error("testMeituan异常",e);
        }
        return null;
    }

    public static List<MeiTuanComment> testMeituanDaZhongDianping(int i,String placeTitle) {
//        String url = "http://www.dianping.com/shop/H6VipTOI96krv6cY/review_all"; //杉杉普陀天地
//        String url = "http://www.dianping.com/shop/G9Qn2dK2Gy5y01qK/review_all"; //东极岛景区
//        String url = "http://www.dianping.com/shop/H924libg7liYpaVe/review_all"; //蚂蚁岛旅游区
//        String url = "http://www.dianping.com/shop/l9pafxj96iMr0oga/review_all"; // 桃花岛风景区
//        String url = "http://www.dianping.com/shop/98962208/review_all"; //冠素堂(慈航广场店)
//        String url = "http://www.dianping.com/shop/19690114/review_all"; //冠素堂观音饼(朱家尖码头店)
        String url = "http://www.dianping.com/shop/l7iAE46djKKuNc9R/review_all"; //沈家门渔港(滨港路店)
        if(i>1){
            url = url+"/p"+i;
        }
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("Cookie","_lxsdk_cuid=171a071babfc8-06fa769c77e21d-4313f6a-15f900-171a071babfc8; _lxsdk=171a071babfc8-06fa769c77e21d-4313f6a-15f900-171a071babfc8; _hc.v=6cb655a3-01a4-b02e-f616-2fc830c4f23a.1587534741; t_lxid=171a071bc7fc8-076e6916830484-4313f6a-15f900-171a071bc7fc8-tid; fspop=test; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; Hm_lvt_602b80cf8079ae6591966cc70a3940e7=1590030762; s_ViewType=10; dplet=16a449443cdbb59976ef8189b93625b9; dper=c59ff325c5101eb315e57f1fba51e4124524deb2639ef7c393d8e243605d2d0b99a89f1369fe3aca0f6a9722f8fa1c40d7be46574421e1946dde978943466cc9009e80a6169e9afb600cd4220476378ce908512d135b8c5fcb491991a35f940b; ll=7fd06e815b796be3df069dec7836c3df; ua=Line.; ctu=dae416e9532ffb61bff41896ca4441d324401eed9fbd06902bc6f8e26467c8de; cy=107; cye=zhoushan; Hm_lpvt_602b80cf8079ae6591966cc70a3940e7=1590034796; _lxsdk_s=17235720ccb-33a-bfd-df8%7C%7C332");
        header.put("Content-Type","application/json");
        header.put("Connection","keep-alive");
//        header.put("Accept-Encoding","gzip, deflate, br");
//        header.put("Accept-Language","zh-CN,zh;q=0.9");
        List<MeiTuanComment> list = null;
        try {
            Document doc = JsoupUtil.doReq(url,header,null, JsoupUtil.MethodOption.GET);
            log.info("已发出大众点评http请求:{},p{}",placeTitle,i);
            Thread.sleep(3500L);
            Element e = doc.getElementById("review-list");
            if(e==null){
                return null;
            }
            Elements es = e.select(".review-list-container");
            es = e.select(".review-list-main");
            es = es.select(".reviews-wrapper");
            es = es.select(".reviews-items");
            if(es==null || es.isEmpty()){
                return null;
            }
            es = es.first().getElementsByTag("ul").first().getElementsByTag("li");
            list = dzdpLiTagToMeiTuanComment(es,placeTitle);
            return list;
        } catch (Exception e) {
            log.error("testMeituan异常",e);
        }
        return null;
    }

    private static List<MeiTuanComment> dzdpLiTagToMeiTuanComment(Elements es,String placeTitle) {
        List<MeiTuanComment> list = new CopyOnWriteArrayList<>();
        MeiTuanComment mt = null;
        Elements ess = null;
        Element tmp = null;
        for (Element e : es) {
            ess = e.select(".main-review");
            if(ess==null|| ess.isEmpty()){
                continue;
            }
            tmp = ess.first();
            mt = new MeiTuanComment();
            mt.setAnonymous(true);
            ess = tmp.select(".dper-info");
            if(ess==null){
                mt.setUserName("美团网友");
            }else{
                mt.setUserName(ess.text());
            }
            mt.setCommentTime(tmp.select(".misc-info").first().select(".time").text());
            mt.setComment(tmp.select(".review-truncated-words").text());
            mt.setPlaceTitle(placeTitle);
            tmp = tmp.select(".review-rank").first();
            ess = tmp.getElementsByTag("span");
            if(!ess.attr("class").contains("sml-str")){
                mt.setStar("50");
            }else{
                mt.setStar(ess.attr("class").split("sml-str")[1].replace(" star", ""));
            }
            mt.setPlaceKw("putuo");
            list.add(mt);
        }
        return list;
    }

    public static List<MeiTuanComment> testRestaurantMeituanCommentList(String placeTitle, String placeId, int i) {
        String url = "https://www.meituan.com/meishi/api/poi/getMerchantComment?uuid=462843a2670846e0a721.1589176517.1.0.0&platform=1&partner=126&originUrl=https%3A%2F%2Fwww.meituan.com%2Fmeishi%2F6347974%2F&riskLevel=1&optimusCode=10&id="+placeId+"&userId=&offset="+i+"&pageSize=50&sortType=0";
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("Cookie","_lxsdk_cuid=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8; t_lxid=171a0714c01c8-05fdbd39f42aa8-4313f6a-15f900-171a0714c01c8-tid; _hc.v=4e05c57c-8b56-814e-66f1-a7fd5836a902.1587608253; iuuid=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; cityname=%E8%88%9F%E5%B1%B1; _lxsdk=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; uuid=462843a2670846e0a721.1589176517.1.0.0; ci=190; rvct=190%2C726%2C319%2C50; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; IJSESSIONID=m0y6arph0vdp1v4rvteuv4gjl; __utma=74597006.479807747.1590030534.1590030534.1590030534.1; __utmc=74597006; __utmz=74597006.1590030534.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; client-id=3ec54775-27be-4071-9e6a-f6c3d594549c; lat=29.916184; lng=122.389055; _lxsdk_s=17236aad944-165-a09-eee%7C%7C2");
        header.put("Content-Type","application/json");
        header.put("Connection","keep-alive");
//        header.put("Accept-Encoding","gzip, deflate, br");
//        header.put("Accept-Language","zh-CN,zh;q=0.9");
        List<MeiTuanComment> list = new CopyOnWriteArrayList<>();
        try {
            String res = httpGet(url,null,header, null);
            log.info("已发出美团http请求:{},{}",placeTitle,i);
            Thread.sleep(3500L);
            JSONObject jo = JSON.parseObject(res);
            if(jo==null){
                log.error("返回空,{}",res);
                return null;
            }
            jo = jo.getJSONObject("data");
            if(jo==null){
                log.error("返回空,{}",res);
                return null;
            }
            JSONArray  ja = jo.getJSONArray("comments");
            if(ja==null || ja.isEmpty()){
                return null;
            }
            ja.forEach(o->list.add(((JSONObject)o).toJavaObject(MeiTuanComment.class).setPlaceKw("putuo").setPlaceTitle(placeTitle)));
            return list;
        } catch (Exception e) {
            log.error("testMeituan异常",e);
        }
        return null;
    }

    public static List<MeiTuanComment> testHotelMeituanCommentList(String placeTitle, String placeId, int i) {
        String url = "https://www.meituan.com/ptapi/poi/getcomment?id="+placeId+"&offset="+i+"&pageSize=50&mode=0&sortType=0";
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("Cookie","_lxsdk_cuid=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8; t_lxid=171a0714c01c8-05fdbd39f42aa8-4313f6a-15f900-171a0714c01c8-tid; _hc.v=4e05c57c-8b56-814e-66f1-a7fd5836a902.1587608253; iuuid=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; cityname=%E8%88%9F%E5%B1%B1; _lxsdk=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; __utma=74597006.479807747.1590030534.1590030534.1590030534.1; __utmz=74597006.1590030534.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; client-id=41d1ca8b-18dc-4764-8e2d-c5572db4c328; uuid=db00cfba82a5435eb01c.1590060876.1.0.0; ci=190; rvct=190%2C726%2C319%2C50; IJSESSIONID=ri56zhu8v7r71mghic7yg7a8b; __mta=244037306.1587867963532.1590109204871.1590110530543.14; _lxsdk_s=17239e4efe5-a05-321-2f%7C%7C113");
        header.put("Content-Type","application/json");
        header.put("Connection","keep-alive");
//        header.put("Accept-Encoding","gzip, deflate, br");
//        header.put("Accept-Language","zh-CN,zh;q=0.9");
        List<MeiTuanComment> list = new CopyOnWriteArrayList<>();
        try {
            String res = httpGet(url,null,header, null);
            log.info("已发出美团http请求:{},{}",placeTitle,i);
            Thread.sleep(3500L);
            JSONObject jo = JSON.parseObject(res);
            if(jo==null){
                log.error("返回空,{}",res);
                return null;
            }
//            jo = jo.getJSONObject("data");
//            if(jo==null){
//                log.error("返回空,{}",res);
//                return null;
//            }
            JSONArray  ja = jo.getJSONArray("comments");
            if(ja==null || ja.isEmpty()){
                return null;
            }
            ja.forEach(o->list.add(((JSONObject)o).toJavaObject(MeiTuanComment.class).setPlaceKw("putuo").setPlaceTitle(placeTitle)));
            return list;
        } catch (Exception e) {
            log.error("testMeituan异常",e);
        }
        return null;
    }

    public static List<MeiTuanComment> testScenicMeituanCommentList(String placeTitle, String placeId, int i) {
        String url = "https://www.meituan.com/ptapi/poi/getcomment?id="+placeId+"&offset="+i+"&pageSize=50&mode=0&sortType=0";
        Map<String,String> header = new HashMap<>();
        header.put("Accept","*/*");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        header.put("Cookie","_lxsdk_cuid=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8; t_lxid=171a0714c01c8-05fdbd39f42aa8-4313f6a-15f900-171a0714c01c8-tid; _hc.v=4e05c57c-8b56-814e-66f1-a7fd5836a902.1587608253; iuuid=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; _lxsdk=F2EA92F8CC554790A90A7B47BA34116325A9C9A44F8795166783F73FB22AD5E9; __utma=74597006.479807747.1590030534.1590030534.1590030534.1; __utmz=74597006.1590030534.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; _ga=GA1.2.479807747.1590030534; mtcdn=K; cityname=%E9%98%BF%E5%9D%9D; uuid=dbaa6857a5b942daa3b3.1590576113.1.0.0; ci=726; rvct=726%2C190%2C319%2C50; _lxsdk_s=17258c72c3f-80c-fe3-9d6%7C%7C11");
        header.put("Content-Type","application/json");
        header.put("Connection","keep-alive");
//        header.put("Accept-Encoding","gzip, deflate, br");
//        header.put("Accept-Language","zh-CN,zh;q=0.9");
        List<MeiTuanComment> list = new CopyOnWriteArrayList<>();
        List<MeiTuanComment> listALL= new CopyOnWriteArrayList<>();
        try {
            String res = httpGet(url,null,header, null);
            log.info("已发出美团http请求:{},{},结果>>>{}",placeTitle,i,res);
            Thread.sleep(3500L);
            JSONObject jo = JSON.parseObject(res);
            if(jo==null){
                log.error("返回空,{}",res);
                return null;
            }
//            jo = jo.getJSONObject("data");
//            if(jo==null){
//                log.error("返回空,{}",res);
//                return null;
//            }
            JSONArray  ja = jo.getJSONArray("comments");
            if(ja==null || ja.isEmpty()){
                return null;
            }
            ja.forEach(o->list.add(((JSONObject)o).toJavaObject(MeiTuanComment.class).setPlaceKw("jiuzhaigou").setPlaceTitle(placeTitle)));
            listALL.addAll(list);
//            listALL = listALL.stream().filter(o->dt.before(TypeUtils.castToDate(o.getCommentTime()))).collect(Collectors.toList());
            return listALL;
        } catch (Exception e) {
            log.error("testMeituan异常",e);
        }
        return null;
    }











    /**
     * 获取美团场所列表(json)35-pageSize
     * @param place:eg-海盐县
     * @param cateId
     * @return
     * ok-done
     */
    public static List<MeiTuanPlace> getPlaceListByMeiTuan(MeiTuanConstant place, PlaceType cateId,int pNo,int size){
        String code = "726";
        if(place.toDbCode().equals("haiyan")){
            code = "726";
        }else if(place.toDbCode().equals("putuo")){
            code = "190";
        }else if(place.toDbCode().equals("jiuzhaigou")){
            code = "319";
        }
        String urlPath = "/group/v4/poi/pcsearch/"+code;
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=utf-8");
        header.put("Origin","https://www.meituan.com");
        header.put("Referer","https://www.meituan.com/s/"+getDecodeUrlStr(place.getCode()));
        header.put("Sec-Fetch-Dest", "document");
        header.put("Sec-Fetch-Mode", "navigate");
        header.put("Sec-Fetch-Site", "none");
        header.put("Sec-Fetch-User", "?1");
        header.put("Upgrade-Insecure-Requests", "1");
        header.put("Cookie", "uuid=d00571b134c84cb6a060.1587534710.1.0.0; _lxsdk_cuid=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8; t_lxid=171a0714c01c8-05fdbd39f42aa8-4313f6a-15f900-171a0714c01c8-tid; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; ci=319; rvct=319%2C50%2C726; _hc.v=4e05c57c-8b56-814e-66f1-a7fd5836a902.1587608253; lat=33.244096; lng=104.247352; _lxsdk=171a0714b08c8-0fbcb354d3b08f-4313f6a-15f900-171a0714b08c8");
//        header.put("Accept-Encoding", "gzip, deflate, br");
//        header.put("Connection", "keep-alive");
//        header.put("Host", "apimobile.meituan.com");
        header.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
        Map<String,Object> params = new HashMap<>();
        params.put("offset",pNo);
        params.put("limit",size);
        params.put("cateId",cateId.getId());
        params.put("uuid", "d00571b134c84cb6a060.1587534710.1.0.0");
        params.put("q",getDecodeUrlStr(place.getCode()));
        try {
            String res = httpGet(meituan_placelist_url,urlPath,header,params);
            if(res.contains("403 Forbidden")) {
                log.error("爬取失败，权限被限");
                return null;
            }else if(res.contains("验证中心")){
                log.error("请求地址:{}", ApiCrawlerUtil.toGetRequestUrl(meituan_placelist_url+urlPath,params));
                log.error("验证中心:{}", res);
                return null;
            }
            JSONObject jo = JSON.parseObject(res).getJSONObject("data");
            if(jo==null){
                return null;
            }
            JSONArray jar = jo.getJSONArray("searchResult");
            log.info("http返回数据量:{}",jar.size());
            List<MeiTuanPlace> list = new ArrayList<>();
            jar.forEach((ob->list.add(((JSONObject)ob).toJavaObject(MeiTuanPlace.class).setItem(cateId).setKw(place.toDbCode()))));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 美团评论获取
     * @param offset
     * @param pageSize
     * @return
     */
    public static List<MeiTuanComment> getMeiTuanCommentsByPlaceId(MeiTuanPlace place, int offset, int pageSize, boolean isFirst,Date dt){
        try {
            String path = "/ptapi/poi/getcomment";
            Map<String, Object> para = new HashMap<>();
            para.put("id", place.getId());
            para.put("offset", offset);
            para.put("pageSize", pageSize);
            para.put("mode", 0);
            para.put("sortType", 1);
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json;charset=utf-8");
            header.put("Cookie", "__mta=249910585.1590039934103.1590039934103.1590039934103.1; _lxsdk_cuid=171a071babfc8-06fa769c77e21d-4313f6a-15f900-171a071babfc8; _lxsdk=171a071babfc8-06fa769c77e21d-4313f6a-15f900-171a071babfc8; _hc.v=6cb655a3-01a4-b02e-f616-2fc830c4f23a.1587534741; t_lxid=171a071bc7fc8-076e6916830484-4313f6a-15f900-171a071bc7fc8-tid; fspop=test; s_ViewType=10; ua=Line.; ctu=dae416e9532ffb61bff41896ca4441d324401eed9fbd06902bc6f8e26467c8de; cy=107; cye=zhoushan; ll=7fd06e815b796be3df069dec7836c3df; Hm_lvt_602b80cf8079ae6591966cc70a3940e7=1590030762,1590116810; cityInfo=%7B%22cityId%22%3A107%2C%22cityName%22%3A%22%E8%88%9F%E5%B1%B1%22%2C%22provinceId%22%3A0%2C%22parentCityId%22%3A0%2C%22cityOrderId%22%3A0%2C%22isActiveCity%22%3Afalse%2C%22cityEnName%22%3A%22zhoushan%22%2C%22cityPyName%22%3Anull%2C%22cityAreaCode%22%3Anull%2C%22cityAbbrCode%22%3Anull%2C%22isOverseasCity%22%3Afalse%2C%22isScenery%22%3Afalse%2C%22TuanGouFlag%22%3A0%2C%22cityLevel%22%3A0%2C%22appHotLevel%22%3A0%2C%22gLat%22%3A0%2C%22gLng%22%3A0%2C%22directURL%22%3Anull%2C%22standardEnName%22%3Anull%7D; dplet=999e33bc56967a056c4a75197aadce80; dper=c59ff325c5101eb315e57f1fba51e412bdb56339de229b6992885c61d69ed3fe82bf2798e697992512052198b20ef794dc27dc4fa1d2abba5b6c21d1aa4a34e80147ffe3e7ce8fa836412deef0d4746889fa304e753310bb931181457cab949e; _lx_utm=utm_source%3Ddp_pc_index; Hm_lpvt_602b80cf8079ae6591966cc70a3940e7=1590117202; _lxsdk_s=1723a58f93b-2e7-67e-720%7C%7C946");
            header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
            String res = httpGet(meituan_comment_url, path, header, para);
            log.info("发起美团评论http请求,title:{},p{}", place.getTitle(),offset);
            Thread.sleep(5000L);
            if (res != null && res.contains("403 Forbidden")) {
                log.error("美团Comment被禁");
                return Collections.EMPTY_LIST;
            }
            JSONObject tp = JsonUtil.strToJSONObject(res);
            if (tp == null) return Collections.EMPTY_LIST;
            JSONArray ja = tp.getJSONArray("comments");
            if (ja == null || ja.size() < 1) return Collections.EMPTY_LIST;
            List<MeiTuanComment> li = new ArrayList<>();
            MeiTuanComment mc = null;
            for (Object ob : ja) {
                if (ob == null) continue;
                mc = ((JSONObject) ob).toJavaObject(MeiTuanComment.class).setPlaceTitle(place.getTitle()).setPlaceKw(place.getKw());
                //过滤掉小于dt时间的对象
                if (!TypeUtils.castToDate(mc.getCommentTime()).after(dt)) {
                    if (!isFirst) continue;
                }
                li.add(mc);
            }
            return li;
        } catch (InterruptedException e) {
            log.error("mt评论爬取异常",e);
            return Collections.EMPTY_LIST;
        }
    }


    /**
     * 获取场所评论关键字标签
     * @param place
     * @return
     */
    public static List<MeiTuanPlaceCommentTag> getPlaceCommentTagByMeiTuan(MeiTuanPlace place){
        String path = "/ptapi/poi/getcomment";
        Map<String,Object> para = new HashMap<>();
        para.put("id", place.getId());
        para.put("offset",1);
        para.put("pageSize",10);
        para.put("mode",0);
        para.put("sortType",1);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=utf-8");
        String res = httpGet(meituan_comment_url,path,header,para);
        JSONArray ja = JsonUtil.strToJSONObject(res).getJSONArray("tags");
        if(ja==null || ja.size()<1) return null;
        List<MeiTuanPlaceCommentTag> li = new ArrayList<>();
        MeiTuanPlaceCommentTag mc = null;
        for(Object ob:ja){
            if(ob==null) continue;
            mc = ((JSONObject) ob).toJavaObject(MeiTuanPlaceCommentTag.class).setPlaceTitle(place.getTitle());
            li.add(mc);
        }
        return li;
    }

//    public static List<XieChengPlace> getPlaceListByXieCheng(PlaceType placeType, XieChengLocateHtml placeHtml){
//        String urlPath = getWholeUrl(placeType.getPath(),placeHtml.getPath()+".html");
//        try {
//            Document doc = JsoupUtil.doReq(getWholeUrl(xiecheng_placelist_url,urlPath),null,null, JsoupUtil.MethodOption.GET);
//            return getXieChengPlaceListByDocument(doc);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    public static List<XieChengPlace> getPlaceListByXieCheng(String placeUrlHtml, PlaceType type, XieChengLocateHtml locate){
        try {
            Document doc = JsoupUtil.doReq(getWholeUrl(xiecheng_placelist_url,placeUrlHtml),null,null, JsoupUtil.MethodOption.GET);
            Thread.sleep(2500L);
            return getXieChengPlaceListByDocument(doc,type,locate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * done
     * @param pageNo--size上限20
     * @param cityId
     * @param sectionId
     * @return
     */
    public static List<TongChengHotel> getTongchengHotelListByJson(int pageNo,TongChengConstant cityId,TongChengConstant sectionId){
        String  url = "https://www.ly.com/hotel/api/tmapi/search/hotellist/";
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/json, text/javascript, */*; q=0.01");
        headers.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Origin","https://www.ly.com");
        headers.put("Referer","https://www.ly.com/searchlist.html?cityid=&sectionid=&word=&wordid=0&wordtype=0&HotelType=0&BDPageWidth=920&IsShowSearchBox=1&IsBDChannel=1");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("Cookie","__tccgd=144323752.1; __tctmb=144323752.3207217960540243.1576653104122.1576653104122.1; wangba=1576653509879");
        Map<String,Object> params = new HashMap<>();
        params.put("CityId",cityId.getV());
        params.put("BizSectionId","0");
        if(sectionId!=null && !StrUtil.isBlankOrUndefined(sectionId.getV())){
            params.put("SectionId",sectionId.getV());
        }
        params.put("HasStandBack","0");
        params.put("LabelId","0");
        params.put("WordType","0");
        params.put("PageSize",20);
        params.put("Page",pageNo);
        params.put("antitoken","4da604d8b9638e57b6f7229b2d6e514c");
        String res = httpPost(url,headers,params);
        if(res==null){
            log.error("同程数据酒店列表post请求空");
            return null;
        }
        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray ja = JsonUtil.strToJSONObject(res).getJSONArray("HotelInfo");
        if(ja==null|| ja.size()<1){
            log.info("同程数据酒店列表http返回为空,pageNo:{}",pageNo);
            return null;
        }
        JSONObject jo = null;
        TongChengHotel hotel = null;
        List<TongChengHotel> list = new ArrayList<>();
        for (Object o:ja) {
            jo = ((JSONObject)o);
            hotel=jo.toJavaObject(TongChengHotel.class);
            hotel.setLatitude(jo.getJSONObject("PCHotelInfo").getString("Lat"));
            hotel.setLongitude(jo.getJSONObject("PCHotelInfo").getString("Lon"));
            hotel.setKw(sectionId.toDbCode());
            list.add(hotel);
        }
        return list;
    }


    /**
     * 暂用- 实际景区列表需要另一条网址
     * @param locate
     * @param keyword
     * @return
     */
    public static List<TongChengScenic> getTongChengSecnicList(TongChengConstant locate,TongChengConstant keyword){
        String url = "https://so.ly.com/commonAjax/AjaxHandler/GetSearchResult";
        Map<String,Object> params = new HashMap<>();
        params.put("keyword",keyword.getV());
        params.put("startCityId",locate.getV());
        String res = httpGet(url,"",new HashMap<String,String>(),params);
        JSONObject jo2 = JsonUtil.strToJSONObject(res);
        if(jo2==null){
            log.info("同城景区空:{},", res);
            return null;
        }
        JSONArray ja = jo2.getJSONObject("ReturnValue").getJSONArray("Records");
        if(ja==null || ja.isEmpty()){
            log.info("同城景区空:{},", res);
            return null;
        }
        log.info("同程景区http数据量:{}",ja.size());
        List<TongChengScenic> li = new ArrayList<>();
        JSONObject jo = null;
        for(Object ob:ja){
            if(ob==null) continue;
            jo = ((JSONObject)ob);
            if(jo.containsKey("GradeId")){
                li.add(jo.toJavaObject(TongChengScenic.class).setKw(keyword.toDbCode()));
            }
        }
        return li;
    }

    /**
     *  done
     * @param pageNo
     * @param pageSize-上限100
     * @return
     */
    public static List<TongChengScenicComment> getTongChengSecnicComment(int pageNo,int pageSize,TongChengScenic scenic,boolean isFirst){
        //?action=GetDianPingList&sid=1964&page=1&pageSize=10&labId=1&sort=0&iid=0.8815409422475804
        String url = "https://www.ly.com/scenery/AjaxHelper/DianPingAjax.aspx";
        Map<String,Object> param = new HashMap<>();
        param.put("action","GetDianPingList");
        param.put("sid",scenic.getProductId());
        param.put("page",pageNo);
        param.put("pageSize",pageSize);
        param.put("labId","1");
        param.put("sort","0");
        param.put("iid","0.8815409422475804");
        String res = httpGet(url,"",new HashMap<String,String>(),param);
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray ja = JsonUtil.strToJSONObject(res).getJSONArray("dpList");
        if(ja==null || ja.size() < 1){
            log.info("同程景区{}评论数据为空,pageNo:{},pageSize:{}",scenic.getTitle(),pageNo,pageSize);
            return null;
        }
        log.info("同程景区http评论数据量:{}",ja.size());
        List<TongChengScenicComment> list = new ArrayList<>();
        TongChengScenicComment ts = null;
        for(Object ob:ja){
            ts = ((JSONObject)ob).toJavaObject(TongChengScenicComment.class);
            if(!DateUtil.isSameDay(DateUtil.parse(ts.getDpDate(),"yyyy-MM-dd"),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            list.add(ts);
        }
        return list;
    }


    /**
     * done
     * @param pageNo--size上限10
     * @return
     */
    public static List<TongChengHotelComment> getTongchengHotelCommentByJson(TongChengHotel hotel,int pageNo,boolean isFirst){
        String url = "https://www.ly.com/hotel/api/tmapi/comment/list/";
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/json, text/javascript, */*; q=0.01");
        headers.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Origin","https://www.ly.com");
        headers.put("Referer","https://www.ly.com/HotelInfo-"+hotel.getId()+".html");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("Host","www.ly.com");
        headers.put("Cookie","__tccgd=144323752.1; __tctmb=144323752.3207217960540243.1576653104122.1576653104122.1; wangba=1576653509879");
        Map<String,Object> params = new HashMap<>();
        params.put("hotelid",hotel.getId());
        params.put("RankType","1");
        params.put("commentType","0");
        params.put("pageSize",10);
        params.put("page",pageNo);
        params.put("antitoken","4da604d8b9638e57b6f7229b2d6e514c");
        String res = httpGet(url,"",headers,params);
        JSONArray ja = JsonUtil.strToJSONObject(res).getJSONObject("response").getJSONObject("body").getJSONArray("dpList");
        if(ja==null || ja.size() < 1){
            log.info("同程酒店{}评论数据为空,pageNo:{},pageSize:{}",hotel.getName(),pageNo);
            return null;
        }
        log.info("同程酒店评论http数据量:{}",ja.size());
        JSONObject jo = null;
        TongChengHotelComment hc = null;
        List<TongChengHotelComment> li = new ArrayList<>();
        for (Object o:ja) {
            jo = (JSONObject) o;
            hc = jo.toJavaObject(TongChengHotelComment.class);
            if("昨天".equals(hc.getDpDate())){
                hc.setDpDate(DateUtil.format(DateUtil.offsetDay(new Date(),-1),"yyyy-MM-dd"));
            }else if("今天".equals(hc.getDpDate())){
                hc.setDpDate(DateUtil.format(new Date(),"yyyy-MM-dd"));
            }else if("前天".equals(hc.getDpDate())){
                hc.setDpDate(DateUtil.format(DateUtil.offsetDay(new Date(),-2),"yyyy-MM-dd"));
            }
            if(!DateUtil.isSameDay(DateUtil.parse(hc.getDpDate(),"yyyy-MM-dd"),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            hc.setPlaceTitle(hotel.getName());
            hc.setPlaceKw(hotel.getKw());
            li.add(hc);
        }
        return li;
    }


    public static List<TongchengPlace> getTongChengHotelListByDom(){
        String url = "https://www.ly.com/searchlist.html";
        Map<String,String> m = new HashMap<>();
        m.put("cityid","385");
        m.put("sectionid","564");
        m.put("BDPageWidth","920");
        m.put("IsBDChannel","1");
        Document doc = JsoupUtil.doReq(url,null,m, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            log.error("同城异常",e);
        }
        Elements li = doc.getElementById("hotel-list").getElementsByClass("list-li");
        Elements tmp = null;
        List<TongchengPlace> pl = new ArrayList<>();
        TongchengPlace p = null;
        String tp = null;
        for(Element ob:li){
            tmp = ob.getElementsByClass("comment");
            if(!tmp.html().contains("条点评") && !tmp.html().contains("分")) continue;
            p = new TongchengPlace();
            p.setTitle(ob.attr("data-hotel-name"));
            p.setAddress(ob.getElementsByClass("info").select(".detail-info").select(".add").text());
            p.setHref(tmp.select(".comment-detail").attr("href"));
            tp = tmp.first().getElementsByTag("dd").text();
            p.setComments(Integer.parseInt(tp.replace("条点评","")));
            if(tmp.first().html().contains("分")){
                p.setScore((tmp.first().getElementsByTag("dt").text().split("/")[0]));
            }
            pl.add(p);
        }
        return pl;
    }




    /**
     * 获取途牛酒店列表,no从1开始,size可定
     * @param cityCode
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    public static List getTuNiuHotelList(TuNiuConstant cityCode,int pageNo,int pageSize,TuNiuAreaReq req,TuNiuConstant kw){
        JSONObject tmp = new JSONObject();
        tmp.put("ct",20000);
        String url = "https://hotel.tuniu.com/hotel-api/hotel/list?c="+getDecodeUrlStr(tmp.toJSONString());
        JSONObject jo = new JSONObject();
        jo.put("cityCode",cityCode.getCode());
        jo.put("cityType",0);
        Date now = new Date();
        jo.put("checkIn" ,DateUtil.format(now,"yyyy-MM-dd"));
        jo.put("checkOut",DateUtil.format(DateUtil.offsetDay(now,2),"yyyy-MM-dd"));
        jo.put("roomNum" ,1);
        jo.put("adultNum",2);
        jo.put("childNum",0);
        Map<String,Object> params = new HashMap<>();
        params.put("primary",jo);
        JSONObject jo2 = new JSONObject();
        jo2.put("locationType",2);
        JSONArray ar =new JSONArray();
        ar.add(req);
        jo2.put("pois", ar);
        jo= new JSONObject();
        jo.put("poi",jo2);
        params.put("secondary",jo);
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        params.put("sort",0);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=UTF-8");
        String res = httpPost(url,"",header,JsonUtil.toJsonStr(params));
        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jo = JsonUtil.strToJSONObject(res);
        if(jo==null) return null;
        List<TuNiuHotel> lis = new ArrayList<>();
        if(jo.getBoolean("success")){
            ar = jo.getJSONObject("data").getJSONArray("hotels");
            if(ar==null || ar.size() <1){
                log.info("途牛酒店数据为空");
                return null;
            }
            log.info("途牛酒店http数据量:{}",ar.size());
            ar.forEach(o->lis.add(((JSONObject) o).toJavaObject(TuNiuHotel.class).setKw(kw.getCode())));
        }
        return lis;
    }


    /**
     * 获取途牛酒店评论  pageNo从1开始,size可定
     * 途牛酒店评论数据无时间字段
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static List<TuNiuComment>  getTuNiuHotelCommentList(TuNiuHotel hotel,int pageNo,int pageSize){
        JSONObject tmp = new JSONObject();
        tmp.put("ct",20000);
        String url = "https://hotel.tuniu.com/hotel-api/comment/list?c="+getDecodeUrlStr(tmp.toJSONString());
        Map<String,Object> params = new HashMap<>();
        params.put("hotelId",hotel.getHotel().getHotelId());
        params.put("grade","ALL");
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=UTF-8");
        String res = httpPost(url,"",header,JsonUtil.toJsonStr(params));
        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp = JsonUtil.strToJSONObject(res);
        if(tmp==null) return null;
        List<TuNiuComment> lis = new ArrayList<>();
        if(tmp.getBoolean("success")){
            int count = 0;
            JSONArray ar = tmp.getJSONObject("data").getJSONArray("comments");
            if(ar==null || ar.size() < 1) {
                log.info("途牛酒店{}评论数据为空,pageNo:{},pageSize:{}",hotel.getHotel().getChineseName(),pageNo,pageSize);
                return null;
            }
            count = tmp.getJSONObject("data").getInteger("count");
            log.info("途牛酒店评论数据量:{}",ar.size());
            for(Object o:ar){
                lis.add(((JSONObject) o).toJavaObject(TuNiuComment.class)
                        .setContentsCommentNum(count)
                        .setTitleName(hotel.getHotel().getChineseName()));
            }
        }
        return lis;
    }


    /**
     * 获取途牛景点(门票)列表
     * @param location
     * @param pageNo
     * @return
     */
    public static List<TuNiuScenic> getTuNiuScenicList(TuNiuConstant location,int pageNo){
        //https://s.tuniu.com/search_complex/ticket-hz-0-%E6%9D%AD%E5%B7%9E/
        String tmp = "https://s.tuniu.com/search_complex/ticket-hz-0-"+getDecodeUrlStr(location.getCode())+"/"+pageNo;
        Document doc = JsoupUtil.doReq(tmp,null,null, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(2500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements es = doc.getElementById("niuren_list").getElementsByClass("contentcontainer").select(".content_bottom").select(".main");
        if(es==null || es.size() <1) {
            log.info("TuNiu Scenic List Root Dom is null.pageNo:{},locate:{}",pageNo,location);
            return null;
        }
        Elements li = es.select(".thelist");
        if(li.first()==null){
            log.info("TuNiu Scenic 无数据.pageNo:{},locate:{}",pageNo,location);
            return null;
        }
        li = li.first().getElementsByTag("li");
        if(li==null || li.size()<1) return null;
        log.info("http途牛景区数据量:{}",li.size());
        TuNiuScenic scenic = null;
        List<TuNiuScenic> list = new ArrayList<>();
        for (Element ele : li) {
            es = ele.getElementsByClass("theinfo").first().getElementsByTag("a");
            scenic = new TuNiuScenic();
            scenic.setHref(es.attr("href"));
            if(StrUtil.isBlank(scenic.getHref())) continue;
            scenic.setTitle(es.first().getElementsByClass("detail").select(".title").text());
            scenic.setAddress(es.first().getElementsByClass("detail").first().getElementsByTag("dd").first().text().replace("地　　址：",""));
            tmp = es.select(".priceinfo").select(".manyi").select(".comments").text().replaceAll("[（人点评）]","");
            if(StrUtil.isBlank(tmp) || tmp.equals("0") || tmp.contains("新产品")){
                tmp = "0";
            }
            scenic.setComments(Integer.parseInt(tmp));
            scenic.setKw(location.getDbCode());
            list.add(scenic);
        }
        return list;

    }



    public static List<TuNiuScenicComment> getTuNiuScenicCommentsList(int pageNo, TuNiuScenic scenic,boolean isFirst){
        String specId = scenic.getHref().split("bysid&id=")[1];
        String url = "https://www.tuniu.com/resp-detail/api/menpiao/getMenpiaoComment?currentPage="+pageNo+"&specId="+specId;
        String res = httpGet(url,"",null,null);
        JSONObject jo = JsonUtil.strToJSONObject(res);
        if(jo==null || jo.size() <1){
            log.info("途牛景区爬取为空:{}",scenic.getTitle());
            return null;
        }
        List<TuNiuScenicComment> list = new ArrayList<>();
        if(jo.getBoolean("success")){
            JSONArray ja = jo.getJSONObject("data").getJSONArray("remarkList");
            TuNiuScenicComment sc = null;
            for(Object o:ja){
                sc = ((JSONObject)o).toJavaObject(TuNiuScenicComment.class).setTitleName(scenic.getTitle());
                if(!DateUtil.isSameDay(TypeUtils.castToDate(sc.getRemarkTime()),DateUtil.offsetDay(new Date(),-1))){
                    if(!isFirst) continue;
                }
                list.add(sc);
            }
        }
        return list;
    }








    private static List<XieChengPlace> getXieChengPlaceListByDocument(Document doc,PlaceType type,XieChengLocateHtml locate){
        if(doc==null || doc.getElementsByClass("list_wide_mod2")==null ||doc.getElementsByClass("list_wide_mod2").select(".list_mod2")==null){
            return null;
        }
        Elements places  = doc.getElementsByClass("list_wide_mod2").select(".list_mod2").select(".rdetailbox");
        log.info("XieChengPlace数据量:{}",places.size());
        XieChengPlace xp = null;
        Element tmp = null;
        String num = null;
        List<XieChengPlace> list = new ArrayList<>();
        for(Element ob: places){
            tmp = ob.getElementsByTag("dt").first().getElementsByTag("a").first();
            xp = new XieChengPlace();
            xp.setTitle(tmp.text());
            xp.setHref(tmp.attr("href"));
            xp.setAddress(ob.getElementsByClass("ellipsis").text());
            num = ob.getElementsByClass("score").first().getElementsByTag("strong").text();
            if(StrUtil.isBlank(num)) continue;
            xp.setScore(num);
            num = ob.getElementsByClass("recomment").text().replaceAll("[(\\(条|暂无\\)点评)]","");
            if(StrUtil.isBlank(num)) num = "0";
            xp.setComments(Integer.parseInt(num));
            xp.setItem(type);
            xp.setKw(locate.getDbKw());
            list.add(xp);
        }
        log.info("返回数据量:{}",list.size());
        return list;
    }


    /**
     *  获取携程all场所列表(景点,饭店) ok
     * @param type 场所类型
     * @param locate 查询地区
     * @return
     */
    public static List<XieChengPlace> getXieChengPlaceList(PlaceType type,XieChengLocateHtml locate){
        Document doc = JsoupUtil.doReq(getWholeUrl(xiecheng_placelist_url+type.getPath(),locate.getPath()+".html"),null,null, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(RandomUtil.randomInt(3,6)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements page = doc.getElementsByClass("list_wide_mod2").select(".ttd_pager").select(".pager_v1");
        if(page==null || page.size()<1){
            log.info(doc.html());
            return null;
        }
        String pages = page.first().getElementsByTag("span").first().getElementsByTag("b").text();
        Set<String> urls = getPageUrl(Integer.parseInt(pages),type,locate);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 20, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        CompletionService<List<XieChengPlace>> cs = new ExecutorCompletionService<>(pool);
        int c = 1;
        for (String url:urls) {
            cs.submit(new XieChengThread(url,type,locate));
            if(c==pool.getMaximumPoolSize()){
                break;
            }
            c++;
        }
        List<XieChengPlace> tmp = getXieChengPlaceListByDocument(doc,type,locate);
        try {
            for (int i = 0;i<c;i++) {
                tmp.addAll(cs.take().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return tmp;
    }


    public static List<XieChengPlace> getXieChengScenicList(PlaceType type,XieChengLocateHtml locate,XieChengLocateHtml kw) throws InterruptedException {
        Document doc = JsoupUtil.doReq(getWholeUrl(xiecheng_placelist_url+type.getPath(),locate.getPath()+".html"),null,null, JsoupUtil.MethodOption.GET);
        Thread.sleep(3000L);
        Elements page = doc.getElementsByClass("list_wide_mod2").select(".ttd_pager").select(".pager_v1");
        if(page==null || page.size()<1){
            log.info(doc.html());
            return null;
        }
        String pages = page.first().getElementsByTag("span").first().getElementsByTag("b").text();
        Set<String> urls = getPageUrl(Integer.parseInt(pages),type,locate,kw.getPath());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 20, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        CompletionService<List<XieChengPlace>> cs = new ExecutorCompletionService<>(pool);
        int c = 1;
        for (String url:urls) {
            cs.submit(new XieChengThread(url,type,locate));
            Thread.sleep(3000L);
            if(c==pool.getMaximumPoolSize()){
                break;
            }
            c++;
        }
        List<XieChengPlace> tmp = getXieChengPlaceListByDocument(doc,type,locate);
        try {
            for (int i = 0;i<c;i++) {
                tmp.addAll(cs.take().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return tmp;
    }


    public static List<XieChengPlace> getXieChengRestaurantList(PlaceType type,XieChengLocateHtml locate,XieChengLocateHtml kw) throws InterruptedException {
        return getXieChengScenicList(type,locate,kw);
    }








    /**
     * 每页最多50条
     * @param place
     * @param pageNo
     * @return
     */
    public static List<XieChengComment> getXieChengCommentByScenic(XieChengPlace place,int pageNo,boolean isFirst){
        String tmp = place.getHref().replaceAll("^/.*/.*/","").replace(".html","");
        if(StrUtil.isBlank(tmp)) return null;
        XieChengCommentJsonReq json = new XieChengCommentJsonReq(Integer.parseInt(tmp),pageNo,50);
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        try {
            tmp = httpPost(xiecheng_comment_url,"/restapi/soa2/12530/json/viewCommentList",header,JsonUtil.toJsonStr(json));
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(tmp.contains("403 – Forbidden")){
            log.error("携程爬取权限被禁,场所:{},pNo:{}",place.getTitle(),pageNo);
            return null;
        }
        JSONArray jar = JsonUtil.strToJSONObject(tmp).getJSONObject("data").getJSONArray("comments");
        if(jar==null || jar.size()<1){
            log.info("场所:{}评论数据空,pno:{}:",place.getTitle(),pageNo);
            return null;
        }
        List<XieChengComment> li = new ArrayList<>();
        XieChengComment xc = null;
        log.info("携程获取景区评论：{}",jar.size());
        for(Object ob:jar){
            xc = ((JSONObject)ob).toJavaObject(XieChengComment.class).setTitle(place.getTitle());
            if(!DateUtil.isSameDay(xc.getDate(),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            li.add(xc);
        };
        return li;
    }


    /**
     * 获取携程餐馆评论数据(false过滤：只保留昨天的数据)
     * @param place
     * @return
     */
    public static List<XieChengComment> getXieChengCommentByRestaurant(XieChengPlace place,boolean isFirst) throws InterruptedException {
        String url = "https://you.ctrip.com"+place.getHref();
        Document doc = JsoupUtil.doReq(url,null,null,JsoupUtil.MethodOption.GET);
        Thread.sleep(5000L);
        String poiId = doc.getElementsByTag("input").select("#poi_id").attr("value");
        String resId = place.getHref().split("/")[3].replace(".html","");
        String re = doc.getElementsByClass("c_detail_title").select("h2").select("span.f_orange").text();
        if(StrUtil.isBlankOrUndefined(re)){
            re = "0";
        }
        int count = Integer.parseInt(re);
        int pages = count%10==0?count/10:(count/10)+1;
        Map<String,String> params = null;
        Elements es = null;
        XieChengComment com = null;
        List<XieChengComment> li = new ArrayList<>();
        String tmp = null;
        for (int i = 1; i <= pages; i++) {
            params = new HashMap<>();
            params.put("poiID",poiId);
            params.put("pagenow",String.valueOf(i));
            params.put("order","3");
            params.put("resourceId",resId);
            url = "https://you.ctrip.com/destinationsite/TTDSecond/SharedView/AsynCommentView";
            doc = JsoupUtil.doReq(url,null,params, JsoupUtil.MethodOption.POST);
            Thread.sleep(1500L);
            if(doc==null ||doc.getElementsByClass("comment_ctrip")==null || doc.getElementsByClass("comment_ctrip").select("div.comment_single")==null){
                log.error("携程餐馆评论空:{}",url);
                return null;
            }
            es = doc.getElementsByClass("comment_ctrip").select("div.comment_single");
            for (Element e : es) {
                com = new XieChengComment();
                com.setUid(e.select("div.userimg").select("span.ellipsis").select("a[title]").text());
                com.setScore(e.select("li.title").select("meta[content]").attr("content"));
                com.setContent(e.select("li.main_con").select("span.heightbox").text());
                tmp = e.select("li.from_link").select("span.time_line").select("em[itemprop]").text();
                com.setDate(DateUtil.parse(tmp,"yyyy-MM-dd"));
                if(!DateUtil.isSameDay(com.getDate(),DateUtil.offsetDay(new Date(),-1))){
                    if(!isFirst) continue;
                }
                com.setTitle(place.getTitle());
                li.add(com);
            }
        }
        return li;
    }



    /**
     * 获取驴妈妈酒店列表
     * @param pageNo
     * @param hotelUCode
     * @return
     */
    public static List<LvmmHotel> getLvMMHotelList(int pageNo, LvmmConstant hotelUCode){
        Date d = new Date();
        StringBuilder sb = new StringBuilder("http://s.lvmama.com/hotel/").append("C")
                .append(DateUtil.format(DateUtil.offsetDay(d,1),"yyyyMMdd")).append("O")
                .append(DateUtil.format(DateUtil.offsetDay(d,2),"yyyyMMdd")).append("P");
        String tmp = sb.append(pageNo).append(hotelUCode.getCode()).toString();
//        String tmp = "http://s.lvmama.com/hotel/C20191220O20191221P3U678";
        Document doc = JsoupUtil.doReq(tmp,null,null, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements es = doc.getElementById("ajaxLoadWrap").getElementById("mainHotelLeft").getElementsByClass("prdLi");
        List<LvmmHotel> li = new ArrayList<>();
        LvmmHotel lv = null;
        Elements emp = null;
        for (Element e : es) {
            lv = new LvmmHotel();
            lv.setName(e.attr("name"));
            lv.setLatitude(e.attr("point").split(",")[1]);
            lv.setLongitude(e.attr("point").split(",")[0]);
            lv.setAddress(e.getElementsByClass("proInfo").select(".proInfo-address").first().getElementsByTag("i").text());
            emp = e.getElementsByClass("priceInfo").first().getElementsByTag("div").select(".product-number").first().getElementsByTag("a");
            if(emp==null || emp.size() < 1) {
                lv.setHref("-");
            }else{
                lv.setHref(emp.attr("href"));
                lv.setScore(emp.first().getElementsByTag("b").text().replace("分",""));
            }
            lv.setKw(hotelUCode.toDbCode());
            li.add(lv);
        }
        return li;
    }


    /**
     * 获取驴妈妈酒店评论数据<br/>
     * warning:内部有多次请求<br/>
     * @param hotel
     * @param pageNo(size:10)
     * @return
     */
    public static List<LvmmHotelComment> getLvmmHotelComment(LvmmHotel hotel,int pageNo,boolean isFirst) throws InterruptedException {
//        String url = "http://hotels.lvmama.com/hotel/396256.html";
        log.info("getLvmmHotelComment running-hotel:{},pNo:{}",hotel.getName(),pageNo);
        String url = hotel.getHref().replace("#comments","");
        if("-".equals(url)){
            return null;
        }
        String hotelId = url.split("/hotel/")[1].replace(".html","");
        Document doc = JsoupUtil.doReq(url,null,null, JsoupUtil.MethodOption.POST);
        Thread.sleep(3000L);
        if(doc==null) return null;
        Elements es = doc.getElementsByTag("script");
        String destId = es.html().split("var productId = "+hotelId+";")[1].split("var features = ")[0].split("destId = ")[1].replaceAll("[';}.*]","").trim();
        String urlGetNums = "http://hotels.lvmama.com/prod/newHotel/asynCommentNum.do";
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Host","hotels.lvmama.com");
        header.put("Origin","http://hotels.lvmama.com");
        header.put("X-Requested-With","XMLHttpRequest");
        header.put("Referer",url);
        Map<String,Object> param = new HashMap<>();
        param.put("productId",hotelId);
        param.put("category","category_hotel");
        param.put("destId",destId);
        String res = httpPost(urlGetNums,header,param);
        Thread.sleep(3000L);
        String commentNum = JsonUtil.strToJSONObject(res).getString("message");
        Map<String,String> params = new HashMap<>();
        params.put("type","all");
        params.put("currentPage",String.valueOf(pageNo));
        params.put("totalCount",commentNum);
        params.put("placeId",destId);
        params.put("placeIdType","PLACE");
        params.put("isPOI","Y");
        params.put("isELong","Y");
        url = "http://hotels.lvmama.com/lvmm_dest_front/comment/newPaginationOfComments";
        doc = JsoupUtil.doReq(url,header,params, JsoupUtil.MethodOption.POST);
        Thread.sleep(3000L);
        es = doc.getElementsByClass("comment-li");
        if(es==null || es.size() <1) return null;
        LvmmHotelComment comment = null;
        Elements tmp = null;
        List<LvmmHotelComment> li = new ArrayList<>();
        for (Element e : es) {
            comment = new LvmmHotelComment();
            comment.setScore(e.getElementsByClass("ufeed-info").select(".ufeed-score").select(".ufeed-level").first().getElementsByTag("i").first().attr("data-level"));
            comment.setContent(e.getElementsByClass("ufeed-content").text().replace("查看全部",""));
            tmp = e.getElementsByClass("com-userinfo").first().getElementsByTag("p");
            comment.setUserName(tmp.first().getElementsByTag("a").first().text());
            comment.setCommentTime(tmp.first().getElementsByTag("em").text());
            if(!DateUtil.isSameDay(TypeUtils.castToDate(comment.getCommentTime()),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            comment.setTitleName(hotel.getName());
            comment.setCommentNums(Integer.parseInt(commentNum));
            comment.setPlaceKw(hotel.getKw());
            li.add(comment);
        }
        return li;
    }


    public static int getLvmmHotelComment(LvmmHotel hol, int i, HotelCommentInfoMapper hotelCommentInfoMapper,boolean isFirst) {
        try {
            List<LvmmHotelComment> li = getLvmmHotelComment(hol,i,isFirst);
            Thread.sleep(2500L);
            if(li==null || li.size() <1) return 0;
            List<HotelCommentInfo> hcs = null;
            hcs = li.stream().map(LvmmHotelComment::toHotelComment).collect(Collectors.toList());
            if(hol.getKw().equals("putuo")){
                log.info("驴妈妈普陀酒店评论更新:{}",hotelCommentInfoMapper.batchInsertPutuoInfo(hcs));
            }else{
                log.info("驴妈妈酒店评论更新:{}",hotelCommentInfoMapper.batchInsertInfo(hcs));
            }
            int pages = DomainUtil.getPages(li.get(0).getCommentNums(),10);
            for (int j = 2; j <= pages; i++) {
                li = getLvmmHotelComment(hol,j,isFirst);
                Thread.sleep(3500L);
                if(li==null || li.size() <1) continue;
                hcs = li.stream().map(LvmmHotelComment::toHotelComment).collect(Collectors.toList());
                if(hol.getKw().equals("putuo")){
                    log.info("驴妈妈普陀酒店评论更新:{}",hotelCommentInfoMapper.batchInsertPutuoInfo(hcs));
                }else{
                    log.info("驴妈妈酒店评论更新:{}",hotelCommentInfoMapper.batchInsertInfo(hcs));
                }

            }
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取驴妈妈景区列表(size:上限9条)
     * @param county
     * @param keyword
     * @param pageNo
     * @return
     */
    public static List<LvmmScenic> getLvmmScenicList(LvmmConstant county,LvmmConstant keyword,int pageNo){
        StringBuilder sb = new StringBuilder("http://s.lvmama.com/ticket");
        sb.append("/").append(county.getCode()).append("P").append(pageNo).append("?keyword=")
                .append(getDecodeUrlStr(keyword.getCode()))
                .append("&tabType=ticket#list");
        String url = sb.toString();
        Document doc = JsoupUtil.doReq(url,null,null, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements es = doc.getElementsByClass("everything").select("div.main")
                .select("div.main-content").select("div.product-list")
                .select("div.product-ticket").select("div.product-regular");
        if(es==null || es.size() <1) {
            log.info("Lvmm Scenic List Root Dom is null.pageNo:{},locate:{}",pageNo,keyword.getCode());
            return null;
        }
        LvmmScenic sc = null;
        Elements tmp = null;
        String t = null;
        List<LvmmScenic> list = new ArrayList<>();
        for (Element e : es) {
            sc = new LvmmScenic();
            sc.setTitle(e.select("div.product-section")
                    .select("h3.product-ticket-title").select("a[title]").attr("title"));
            sc.setAddress(e.select("div.product-section").select("span.city").text());
            tmp = e.select("div.product-info").select("ul.product-number");
            if(tmp.select("li").first()!=null){
                sc.setScore(tmp.select("li").first().getElementsByTag("b").text());
            }else{
                sc.setScore("-1");
            }
            sc.setHref(tmp.select("li").select("a[href]").attr("href"));
            t = tmp.select("li").select("a[href]").text().replaceAll("[来自条点评]","");
            if(StrUtil.isBlankOrUndefined(t)){
                t = "0";
            }
            sc.setComments(Integer.parseInt(t));
            sc.setKw(keyword.toDbCode());
            list.add(sc);
        }
        return list;
    }


    /**
     * 获取驴妈妈景区评论(size:固定10条)
     * @param sci
     * @param pageNo
     * @return
     */
    public static List<LvmmScenicComment> getLvmmScenicCommentList(LvmmScenic sci,int pageNo,boolean isFirst){
        String url = "http://ticket.lvmama.com/scenic_front/comment/newPaginationOfComments";
        String placeId = sci.getHref().split("scenic-")[1].replace("#comments","");
        Map<String,String> params = new HashMap<>();
        params.put("type","all");
        params.put("currentPage",String.valueOf(pageNo));
        params.put("totalCount",String.valueOf(sci.getComments()));
        params.put("placeId",placeId);
        params.put("placeIdType","PLACE");
        params.put("isPOI","Y");
        params.put("isELong","N");
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        Document doc = JsoupUtil.doReq(url,header,params, JsoupUtil.MethodOption.POST);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(doc==null){
            log.info("LvmmScenicComment-http返回空:{},p{}",sci.getTitle(),pageNo);
            return null;
        }
        Elements es = doc.getElementsByClass("comment-li");
        LvmmScenicComment cm = null;
        Elements tmp = null;
        List<LvmmScenicComment> li = new ArrayList<>();
        for (Element e : es) {
            cm = new LvmmScenicComment();
            if(e.getElementsByClass("ufeed-info").select(".ufeed-score").select(".ufeed-level").first()==null
             ||e.getElementsByClass("ufeed-info").select(".ufeed-score").select(".ufeed-level").first().getElementsByTag("i").first()==null){
                cm.setScore("-1");
            }else{
                cm.setScore(e.getElementsByClass("ufeed-info").select(".ufeed-score").select(".ufeed-level").first().getElementsByTag("i").first().attr("data-level"));
            }
            cm.setContent(e.getElementsByClass("ufeed-content").text().replace("查看全部",""));
            tmp = e.getElementsByClass("com-userinfo").first().getElementsByTag("p");
            cm.setUserName(tmp.first().getElementsByTag("a").first().text());
            cm.setCommentTime(tmp.first().getElementsByTag("em").text());
            if(!DateUtil.isSameDay(TypeUtils.castToDate(cm.getCommentTime()),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            cm.setTitleName(sci.getTitle());
            li.add(cm);
        }
        return li;
    }


//    public static void getQuNarHotelList(String city){
//        StringBuilder sb = new StringBuilder("https://hotel.qunar.com/city");
//        Date d = new Date();
//        sb.append("/haiyan/#fromDate=").append(DateUtil.format(DateUtil.offsetDay(d,2),"yyyy-MM-dd"))
//                .append("&cityurl=").append("haiyan").append("&toDate=")
//                .append(DateUtil.format(DateUtil.offsetDay(d,5),"yyyy-MM-dd")).append("&from=qunarHotel");
//        String url = sb.toString();
//        Document doc = JsoupUtil.doReq(url,null,null, JsoupUtil.MethodOption.GET);
//    }


    /**
     * 获取去哪儿景区列表
     * @param keyword
     * @param pageNo
     * @return
     */
    public static List<QuNarScenic> getQuNarScenicList(QuNarConstant keyword,int pageNo){
        Map<String,Object> params = new HashMap<>();
        params.put("keyword",keyword.getVal());
        params.put("from","mps_search_suggest");
        params.put("page",pageNo);
        params.put("region","");
        params.put("from","");
        Map<String,String> head = new HashMap<>();
        head.put(":authority","piao.qunar.com");
        head.put(":method","GET");
        head.put("accept","application/json, text/javascript, */*; q=0.01");
        head.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
        String res = httpGet("https://piao.qunar.com","/ticket/list.json",head,params);
        if(res==null){
            log.error("去哪儿景区列表空:{}",JSON.toJSONString(params));
            return null;
        }
        JSONObject jo = JsonUtil.strToJSONObject(res).getJSONObject("data");
        if(jo==null) return null;
        List<QuNarScenic> li = new ArrayList<>();
        JSONArray ja = jo.getJSONArray("sightList");
        ja.forEach(o->li.add(((JSONObject)o).toJavaObject(QuNarScenic.class).setKw(keyword.toDBcode())));
        return li;
    }


    /**
     * 获取去哪景区评论
     * @param sci
     * @param pageNo
     * @param pageSize (可调，定时器按100)
     * @return
     */
    public static List<QuNarScenicComment> getQuNarScenicCommentList(QuNarScenic sci,int pageNo,int pageSize,boolean isFirst){
        Map<String,Object> params = new HashMap<>();
        params.put("sightId",sci.getSightId());
        params.put("index",pageNo);
        params.put("page",pageNo);
        params.put("pageSize",pageSize);
        params.put("tagType",0);
        String res = httpGet("https://piao.qunar.com","/ticket/detailLight/sightCommentList.json",null,params);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo = JsonUtil.strToJSONObject(res).getJSONObject("data");
        int count = jo.getInteger("commentCount");
        List<QuNarScenicComment> li = new ArrayList<>();
        JSONArray ja = jo.getJSONArray("commentList");
        QuNarScenicComment sc = null;
        if(ja==null){
            return null;
        }
        for(Object o:ja){
            sc = ((JSONObject)o).toJavaObject(QuNarScenicComment.class).setTitleName(sci.getSightName()).setCmtNums(count);
            if(!DateUtil.isSameDay(TypeUtils.castToDate(sc.getDate()),DateUtil.offsetDay(new Date(),-1))){
                if(!isFirst) continue;
            }
            li.add(sc);
        }
        return li;

    }

    public static List<QuNarHotel> getQuNarHotelList(QuNarConstant cityurl,QuNarConstant query){
        StringBuilder sb = new StringBuilder("https://hotel.qunar.com/city");
        sb.append("/").append(cityurl.getVal()).append("/");
        if(query!=null){
            sb.append("q-").append(query.getVal());
        }
        sb.append("#fromDate=").append(DateUtil.format(new Date(),"yyyy-MM-dd"))
                .append("&cityurl=").append(cityurl.getVal()).append("&toDate=")
                .append(DateUtil.format(DateUtil.offsetDay(new Date(),1),"yyyy-MM-dd")).append("&from=qunarHotel");
        Document doc = JsoupUtil.doReq(sb.toString(),null,null, JsoupUtil.MethodOption.GET);
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Elements es = doc.getElementsByClass("jxDataArea").select("ul").select("li");
        QuNarHotel ho = null;
        List<QuNarHotel> li = new ArrayList<>();
        for (Element e : es) {
            ho = new QuNarHotel();
            ho.setAddress("-");
            ho.setComments(-1);
            ho.setHref(e.select("a[href]").get(1).attr("href"));
            ho.setKw(cityurl.toDBcode());
            ho.setScore("-1");
            ho.setTitle(e.select("a[href]").get(0).text().replace("[",""));
            li.add(ho);
        }
        return li;
    }





    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {

//        meiTuanTest(); //ok
//        xieChnegTest(); //ok
//        tuNiutest(); //ok
//        tongChengTest(); //ok
//        lvMMtest(); //ok
        quNartest();

    }

    public static void tuNiutest(){
        List o = null;
//        o = getTuNiuHotelList(TuNiuConstant.cityCode_jiaXing,1,30, TuNiuConstant.getTuNiuAreaReq_HaiYan());
//        System.out.println(o.size());
//        System.out.println(JsonUtil.toJsonStr(o));
        TuNiuHotel h = new TuNiuHotel();
        HotelInfo i = new HotelInfo();
        i.setChineseName("海盐海利开元名都大酒店");
        i.setHotelId("214391258");
        h.setHotel(i);
        o = getTuNiuHotelCommentList(h,1,80);
//        o = getTuNiuScenicList("海盐",0);
//        System.out.println(JsonUtil.toJsonStr(o.get(0)));
//        o = getTuNiuScenicCommentsList(1, (TuNiuScenic) o.get(0));
//        System.out.println(o.size());
    }

    public static void quNartest(){
        List o = null;
//        List<QuNarScenic> l = getQuNarScenicList(QuNarConstant.KEY_WORD_HAIYAN,1);
//        System.out.println(JsonUtil.toJsonStr(l.get(0)));
//        o = getQuNarScenicCommentList(l.get(0),1,100);
       o = getQuNarHotelList(QuNarConstant.getQuNarHotelList_CITY_URL_HOTEL_HAIYAN,QuNarConstant.query_word_putuo);
        System.out.println(o.size());
        System.out.println(JsonUtil.toJsonStr(o));
    }

    public static void lvMMtest() throws InterruptedException {
        List o = null;
        List<LvmmHotel> t = getLvMMHotelList(1,LvmmConstant.HOTEL_UCODE_HAIYAN);
        System.out.println(JsonUtil.toJsonStr(t.get(0)));
        o = getLvmmHotelComment(t.get(0),1,false);
        System.out.println(JsonUtil.toJsonStr(o.get(0)));
        System.out.println(o.size());
//        List o = null;
//        o=getLvmmScenicList(LvmmConstant.getLvmmScenicList_SCENIC_COUNTY_HAIYAN,LvmmConstant.getLvmmScenicList_KEYWORD_HAIYAN,1);
//        System.out.println(o.size());
//        System.out.println(JsonUtil.toJsonStr(o.get(0)));
//        o = getLvmmScenicCommentList(((List<LvmmScenic>)o).get(0),1);
//        System.out.println(o.size());
//        System.out.println(JsonUtil.toJsonStr(o.get(0)));
    }




    public static void tongChengTest(){
        List o = null;
        //        List<TongchengPlace> l = getXieChengHotelList();
//        l.forEach(ob-> System.out.println(JsonUtil.toJsonStr(ob)));
//        getTongchengHotelListByJson(1,10, TongChengConstant.city_id_haiyan,TongChengConstant.section_id_haiyan);
//        System.out.println(res);

        o = getTongchengHotelListByJson(1,TongChengConstant.start_city_id_haiyan,TongChengConstant.section_id_haiyan);
//        System.out.println(o.size());
        System.out.println(JsonUtil.toJsonStr(o.get(0)));
//        TongChengHotel hotel = new TongChengHotel();
        List<TongChengHotelComment> li = getTongchengHotelCommentByJson((TongChengHotel) o.get(0),1,false);
        System.out.println(li.size());
//        System.out.println(li.size());
//        System.out.println(JsonUtil.toJsonStr(li.get(1)));
//        o = getTongChengSecnicList(TongChengConstant.start_city_id_haiyan,TongChengConstant.getTongChengSecnicList_key_word_haiyan);
//        System.out.println(JsonUtil.toJsonStr(o.get(2)));
//        o = getTongChengSecnicComment(2,100, (TongChengScenic) o.get(2));
//        System.out.println(o.size());

    }


    public static void meiTuanTest(){
       List<MeiTuanPlace> list = getPlaceListByMeiTuan(MeiTuanConstant.getPlaceListByMeiTuan_haiyan,PlaceType.MEITUAN_CATE_SCENIC,0,10);
        System.out.println(JsonUtil.toJsonStr(list.get(1)));
        List<MeiTuanComment> tmp = getMeiTuanCommentsByPlaceId(list.get(1),0,10,false,new Date());
        System.out.println(JsonUtil.toJsonStr(tmp));
    }

    public static void xieChnegTest(){
        //  https://you.ctrip.com/restaurantlist/Haiyan529/list-p1.html?ordertype=0
        //        List<XieChengPlace> li = getPlaceListByXieCheng(PlaceType.XIECHEN_SIGHT_PATH,XieChengPlaceHtml.HAIYAN_HTML);
        List<XieChengPlace> li = null;
//        li = getPlaceListByXieCheng("/sightlist/haiyan529/s0-p2.html");
//        li=  getXieChengPlaceList(PlaceType.XIECHENG_RESTAURANT_PATH,XieChengLocateHtml.HAIYAN_HTML);
//        System.out.println(li.size());
//        for (XieChengPlace xieChenPlace : li) {
//            System.out.println(JsonUtil.toJsonStr(xieChenPlace));
//        }
        // https://piao.ctrip.com/ticket/dest/t16166.html
//        String r = "<a rel=\"nofollow\" target=\"_blank\" href=\"/sight/haiyan529/16166.html#comment\" class=\"recomment\"> (1319条点评)</a>";
//        String r = "(1319条点评)";
//        String rs = "(暂无点评)";
        String href = null;
//             href  =  "/sight/haiyan529/16166.html";
        href= "/sight/haiyan529/16166.html";
        List t = null;
        XieChengPlace v = new XieChengPlace();
        v.setHref(href);
        v.setTitle("五芳斋");
        t =  getXieChengCommentByScenic(v,1,false);
//        List t =  getXieChengCommentByRestaurant(v);
//        t.forEach(o->{
//            System.out.println(JsonUtil.toJsonStr(o));
//        });
        System.out.println(t.size());
//
//        System.out.println();
//        System.out.println(r.replaceAll("[(\\(条|暂无\\)点评)]",""));
//        System.out.println(rs.replaceAll("[(\\(条|暂无\\)点评)]",""));
//        System.out.println(Integer.parseInt(""));
    }




    private static Set<String> getPageUrl(int parseInt, PlaceType type, XieChengLocateHtml locate) {
        //   /sightlist/haiyan529/s0-p2.html
        parseInt = 30;
        Set<String> set = new HashSet<>();
        for (int i = 2; i <= parseInt; i++) {
            set.add(new StringBuilder(type.getPath())
                    .append(locate.getPath())
                    .append("/s0-p")
                    .append(i).append(".html").toString());
        }
        return set;
    }

    private static Set<String> getPageUrl(int parseInt, PlaceType type, XieChengLocateHtml locate,String kw) {
        //   /sightlist/haiyan529/s0-p2.html
        parseInt = 30;
        Set<String> set = new HashSet<>();
        for (int i = 2; i <= parseInt; i++) {
            set.add(new StringBuilder(type.getPath())
                    .append(locate.getPath())
                    .append("/s0-p")
                    .append(i).append(".html").append("?keywords=").append(getDecodeUrlStr(kw)).toString());
        }
        return set;
    }

    private static Set<String> getRestaurantPageUrl(int parseInt, PlaceType type, XieChengLocateHtml locate,String kw) {
        parseInt = 30;
        Set<String> set = new HashSet<>();
        for (int i = 2; i <= parseInt; i++) {
            set.add(new StringBuilder(type.getPath())
                    .append(locate.getPath())
                    .append("/s0-p")
                    .append(i).append(".html").append("?keywords=").append(getDecodeUrlStr(kw)).toString());
        }
        return set;
    }


    private static String getDecodeUrlStr(String speChar){
        try {
            return URLEncoder.encode(speChar, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("getDecodeUrlStr-error-speChar:{},msg:{}",speChar,e.getMessage());
        }
        return "";
    }


//    static Set<String> set = new HashSet<>();
//    static {
//        set.add("124.152.32.140");
//        set.add("119.57.108.53");
//        set.add("119.57.105.25");
//        set.add("124.237.83.14");
//        set.add("122.136.212.132");
//        set.add("58.243.50.184");
//        set.add("61.145.182.27");53281
//        set.add("58.253.158.61");
//        set.add("60.167.135.198");
//        set.add("60.167.132.218");
//        set.add("183.166.162.124");
//        set.add("118.212.105.56");
//        set.add("60.167.132.102");
//
//    }

    private static String httpGet(String baseUrl,String path,Map<String,String> headers,Map<String,Object> params){
//        InetSocketAddress sa = new InetSocketAddress("163.125.156.216",8888);
//        Proxy pro = new Proxy(Proxy.Type.HTTP,sa);
        try {
//            return HttpUtil.createGet(getWholeUrl(baseUrl,path))
//                     .form(params).timeout(65000).addHeaders(headers)
//                    .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
//                    .execute().body();
            if(headers==null){
                headers = new HashMap<>();
            }
            headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
            return httpCli.doGet(getWholeUrl(baseUrl,path),params, (HashMap<String, String>) headers);
        } catch (Exception e) {
            log.error("http请求异常url:{},path:{},msg:{}",baseUrl,path,e.getMessage());
            return null;
        }
    }


    private static String httpPost(String baseUrl,String path,Map<String,String> headers, String params){
        return httpPost(baseUrl,path,headers,null,params);
    }


    private static String httpPost(String url,Map<String,String> headers, Map<String,Object> params){
        return httpPost(url,"",headers,params,null);
    }

    private static String httpPost(String baseUrl,String path,Map<String,String> headers,Map<String,Object> param,String params){
        try {
//            InetSocketAddress sa = new InetSocketAddress("111.160.169.54",42626);
//            Proxy pro = new Proxy(Proxy.Type.HTTP,sa);
            if(StrUtil.isNotBlank(params)){
//                return HttpUtil.createPost(getWholeUrl(baseUrl,path))
//                        .setProxy(pro).body(params).setReadTimeout(85000).execute().body();
                       return HttpUtil.post(getWholeUrl(baseUrl,path),params,65000);
            }
//            return HttpUtil.createPost(getWholeUrl(baseUrl,path))
//                    .setProxy(pro).execute().body();
            return httpCli.doPostForm(getWholeUrl(baseUrl,path),param, (HashMap<String, String>) headers);
        } catch (Exception e) {
            log.error("http请求异常",e);
            return null;
        }
    }



    private static String getWholeUrl(String baseUrl,String path){
        return new StringBuilder(baseUrl).append(path).toString();
    }

}
