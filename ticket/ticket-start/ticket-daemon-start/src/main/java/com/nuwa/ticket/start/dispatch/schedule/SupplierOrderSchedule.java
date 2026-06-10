package com.nuwa.ticket.start.dispatch.schedule;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.mallorder.MallOrderClientI;
import com.nuwa.client.ticket.api.order.OrderClientI;
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
public class SupplierOrderSchedule {

    @Autowired
    private OrderClientI orderClientI;

    @Autowired
    private MallOrderClientI mallOrderClientI;

    @Scheduled(fixedDelay = 5 * 1000)
    public void takeAndPushSupplierOrder() {
        log.info(">>>> takeAndPushSupplierOrder");
        SingleResponse<?> singleResponse = orderClientI.takeAndPushSupplierOrder();
        log.info("<<<< takeAndPushSupplierOrder[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void supplierTakeAndDoRefund() {
        log.info(">>>> supplierTakeAndDoRefund");
        SingleResponse<?> singleResponse = orderClientI.supplierTakeAndDoRefund();
        log.info("<<<< supplierTakeAndDoRefund[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 20 * 1000)
    public void channelTakeAndDoRefund() {
        log.info(">>>> channelTakeAndDoRefund");
        SingleResponse<?> singleResponse = orderClientI.channelTakeAndDoRefund();
        log.info("<<<< channelTakeAndDoRefund[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void douyinTakeAndDoSettle() {
        log.info(">>>> douyinTakeAndDoSettle");
        SingleResponse<?> singleResponse = orderClientI.douyinTakeAndDoSettle();
        log.info("<<<< douyinTakeAndDoSettle[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 3 * 1000)
    public void takeAndCloseNotPayOrder() {
        log.info(">>>> takeAndPushSupplierOrder");
        SingleResponse<?> singleResponse = orderClientI.takeAndCloseNotPayOrder();
        log.info("<<<< takeAndCloseNotPayOrder[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 20 * 1000)
    public void takeAndSetMinPrice() {
        log.info(">>>> takeAndSetMinPrice");
        SingleResponse<?> singleResponse = orderClientI.takeAndSetMinPrice();
        log.info("<<<< takeAndSetMinPrice[size:{}]", singleResponse.getData());
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void pobRefundDelayJob() {
        log.info(">>>> pobRefundDelayJob");
        SingleResponse<?> singleResponse = orderClientI.pobRefundDelayJob();
        SingleResponse<?> singleResponse1 = mallOrderClientI.orderCancelDelayJobProcess();
        log.info("<<<< pobRefundDelayJob[size:{}]", singleResponse.getData());
    }
}
