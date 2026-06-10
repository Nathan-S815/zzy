package com.nuwa.ticket.start.dispatch.schedule;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.pubsystem.PubSystemClientI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 供应商订单
 *
 * @author hy
 */
@Component
@Slf4j
public class PubSystemSchedule {

    @Autowired
    private PubSystemClientI pubSystemClientI;

    @Scheduled(fixedDelay = 5 * 1000)
    public void takeAndUpdateVersionUploadStatus() {
        log.info(">>>> takeAndUpdateVersionUploadStatus");
        SingleResponse<?> singleResponse = pubSystemClientI.takeAndUpdateVersionUploadStatus();
        log.info("<<<< takeAndUpdateVersionUploadStatus[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void takeAndUpdateExpStatus() {
        log.info(">>>> takeAndUpdateVersionUploadStatus");
        SingleResponse<?> singleResponse = pubSystemClientI.takeAndUpdateExpStatus();
        log.info("<<<< takeAndUpdateExpStatus[size:{}]", singleResponse.getData());
    }
}
