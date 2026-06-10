package com.zzy.task.common.thread;

import com.zzy.task.common.constant.PlaceType;
import com.zzy.task.common.constant.XieChengLocateHtml;
import com.zzy.task.client.QueryCrawlerClient;
import com.zzy.task.client.domain.XieChengPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class XieChengThread implements Callable<List<XieChengPlace>> {


    private static final Logger log = LoggerFactory.getLogger(XieChengThread.class);

    private String url;
    private CountDownLatch count;
    private PlaceType type;
    XieChengLocateHtml locate;

    public XieChengThread(String url){
        this.url = url;
    }

    public XieChengThread(String url,PlaceType type){
        this.url = url;
        this.type = type;
    }

    public XieChengThread(String url, CountDownLatch latch){
        this.url = url;
        this.count = latch;
    }

    public XieChengThread(String url, PlaceType type, XieChengLocateHtml locate) {
        this.url = url;
        this.type = type;
        this.locate = locate;
    }


    @Override
    public List<XieChengPlace> call() throws Exception {
        log.info("XieChengPlace runnning...");
        return QueryCrawlerClient.getPlaceListByXieCheng(url, type,locate);
    }
}
