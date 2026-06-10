package com.nuwa.ticket.start.dispatch.schedule;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.alipaydata.AlipayDataClientI;
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
public class AlipayDataSchedule {

    @Autowired
    private AlipayDataClientI alipayDataClientI;

    @Scheduled(fixedDelay = 10 * 1000)
    public void takeAndSyncOrder() {
        log.info(">>>> takeAndSyncOrder");
        SingleResponse<?> singleResponse = alipayDataClientI.takeAndSyncOrder();
        log.info("<<<< takeAndSyncOrder[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void takeAndSyncProduct() {
        log.info(">>>> takeAndSyncProduct");
        SingleResponse<?> singleResponse = alipayDataClientI.takeAndSyncProduct();
        log.info("<<<< takeAndSyncProduct[size:{}]", singleResponse.getData());
    }
}
