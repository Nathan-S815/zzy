package com.nuwa.client.ticket.api.mallorder;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.param.AddRefundDelayJobParam;
import com.nuwa.client.ticket.api.order.param.ChannelPaymentSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.ChannelRefundSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.WenChuangOrderCancelDelayJobParam;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.ticket.util.ChannelResultCodeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;

@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = MallOrderClientFallBack.class
)
public interface MallOrderClientI {
    String API_PREFIX = "/mallorder";
    /**
     * 支付渠道 支付成功处理
     *
     * @param param ChannelPaymentSuccessNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/paymentSuccess")
    @ResponseBody
    public SingleResponse<?> paymentSuccess(@RequestBody ChannelPaymentSuccessNotifyParam param);

    @PostMapping(API_PREFIX + "/refundSuccess")
    @ResponseBody
    public SingleResponse<?> refundSuccess(@RequestBody ChannelRefundSuccessNotifyParam param);

    @PostMapping(API_PREFIX + "/orderCancelDelayJobProcess")
    @ResponseBody
    public SingleResponse<?> orderCancelDelayJobProcess();

    @PostMapping(API_PREFIX + "/newOrderCancelDelayJob")
    @ResponseBody
    public SingleResponse<?> newOrderCancelDelayJob(@RequestBody WenChuangOrderCancelDelayJobParam param);

}
