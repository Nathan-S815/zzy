package com.zzy.task;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzy.client.service.NewsClientService;
import com.zzy.db.dao.hotmap.NewsMapper;
import com.zzy.db.entity.hotmap.News;
import com.zzy.db.entity.hotmap.PandaFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NewsTask {

    @Autowired
    private NewsMapper newsMapper;

    @Scheduled(cron = "${yidong.task.cron}")
//    @Scheduled(fixedDelay = 1000*60*60)
    public void getNews() {
        log.info("新闻开始调用");
        try {
            List<News> news = NewsClientService.getNews();
            newsMapper.batchInsert(news);
        } catch (Exception e) {
            log.error("新闻数据异常", e);
        }

    }
}
