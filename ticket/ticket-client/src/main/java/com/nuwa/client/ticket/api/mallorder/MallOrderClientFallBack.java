package com.nuwa.client.ticket.api.mallorder;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.OrderClientI;
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
public class MallOrderClientFallBack implements MallOrderClientI {
    @Override
    public SingleResponse<?> paymentSuccess(ChannelPaymentSuccessNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> refundSuccess(ChannelRefundSuccessNotifyParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> orderCancelDelayJobProcess() {
        return SingleResponse.buildFailure("9999", "FallBack");
    }

    @Override
    public SingleResponse<?> newOrderCancelDelayJob(WenChuangOrderCancelDelayJobParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
