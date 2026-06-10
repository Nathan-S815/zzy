package com.zzy.client.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzy.client.api.JzgApiClient;
import com.zzy.client.api.NewsClient;
import com.zzy.client.common.JzgApiConstant;
import com.zzy.db.entity.carpark.CarDriver;
import com.zzy.db.entity.hotmap.News;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NewsClientService {
    public static void main(String[] args) {
        System.out.println(NewsClientService.getNews());
    }

    public static List<News> getNews() {
        String result = NewsClient.getNews();
        if (StrUtil.isBlankOrUndefined(JSON.parseObject(result).getString("data"))) {
            log.error("jzg采集CarDriver-api数据为空:{}", result);
            return null;
        }
        JSONObject jo = JSON.parseObject(result);
        List<News> list = new ArrayList<>();
        if (jo.getString("msg").equals("获取昨日数据成功")) {
            if (jo != null) {
                JSONArray ja = jo.getJSONArray("data");
                ja.forEach(o -> list.add(jsonToNews(((JSONObject) o))));
            }
        }
        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    private static News jsonToNews(JSONObject o) {
        News news = new News();
        news.setId(o.getString("id")).setTitle(o.getString("title")).setCreateTime(o.getString("create_at")).setContent(o.getString("content"));
        return news;
    }


}
