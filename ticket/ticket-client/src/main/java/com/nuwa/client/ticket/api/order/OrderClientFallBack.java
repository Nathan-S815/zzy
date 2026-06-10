package com.nuwa.client.ticket.api.order;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.param.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OrderClientFallBack
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class OrderClientFallBack implements OrderClientI {

    @Override
    public SingleResponse<?> paymentSuccess(ChannelPaymentSuccessNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> douyinSettleProcess(DouyinSettleNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> refundSuccess(ChannelRefundSuccessNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> b2bRefundNoticeProcess(B2bSupplierRefundNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> b2bConsumerNoticeProcess(B2bSupplierConsumerNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> takeAndPushSupplierOrder() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> b2bTicketNoticeProcess(B2bSupplierTicketNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> channelTakeAndDoRefund() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> supplierTakeAndDoRefund() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> takeAndCloseNotPayOrder() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> takeAndSetMinPrice() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> douyinTakeAndDoSettle() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> pobRefundDelayJob() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> addRefundDelayJob(@RequestBody AddRefundDelayJobParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
