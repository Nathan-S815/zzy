package com.nuwa.client.ticket.api.order;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.order.param.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author hy
 */
@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = OrderClientFallBack.class
)
public interface OrderClientI {
    String API_PREFIX = "/order";

    /**
     * 支付渠道 支付成功处理
     *
     * @param param ChannelPaymentSuccessNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/paymentSuccess")
    @ResponseBody
    public SingleResponse<?> paymentSuccess(@RequestBody ChannelPaymentSuccessNotifyParam param);

    /**
     * 支付渠道[抖音] 结算处理
     *
     * @param param DouyinSettleNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/douyinSettleProcess")
    @ResponseBody
    public SingleResponse<?> douyinSettleProcess(@RequestBody DouyinSettleNotifyParam param);

    /**
     * 支付渠道 退款成功处理
     *
     * @param param ChannelRefundSuccessNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/refundSuccess")
    @ResponseBody
    public SingleResponse<?> refundSuccess(@RequestBody ChannelRefundSuccessNotifyParam param);

    /**
     * 支付渠道 退款成功处理
     *
     * @param param ChannelRefundSuccessNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/supplier/b2bRefundNoticeProcess")
    @ResponseBody
    public SingleResponse<?> b2bRefundNoticeProcess(@RequestBody B2bSupplierRefundNotifyParam param);

    /**
     * 供应商核销处理
     *
     * @param param B2bSupplierConsumerNotifyParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/supplier/b2bConsumerNoticeProcess")
    @ResponseBody
    public SingleResponse<?> b2bConsumerNoticeProcess(@RequestBody B2bSupplierConsumerNotifyParam param);


    /**
     * 供应商订单 支付成功处理
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/supplier/order/takeAndPush")
    @ResponseBody
    public SingleResponse<?> takeAndPushSupplierOrder();

    /**
     * 供应商订单 出票成功处理
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/supplier/b2bTicketNoticeProcess")
    @ResponseBody
    public SingleResponse<?> b2bTicketNoticeProcess(@RequestBody B2bSupplierTicketNotifyParam param);


    /**
     * 支付渠道退款处理
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/channel/takeAndDoRefund")
    @ResponseBody
    public SingleResponse<?> channelTakeAndDoRefund();

    /**
     * 供应商退款处理
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/supplier/takeAndDoRefund")
    @ResponseBody
    public SingleResponse<?> supplierTakeAndDoRefund();


    /**
     * 关闭超时未支付订单
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/takeAndCloseNotPayOrder")
    @ResponseBody
    public SingleResponse<?> takeAndCloseNotPayOrder();

    /**
     * 设置最小价格
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/takeAndSetMinPrice")
    @ResponseBody
    public SingleResponse<?> takeAndSetMinPrice();

    /**
     * 抖音结算
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/douyinTakeAndDoSettle")
    @ResponseBody
    public SingleResponse<?> douyinTakeAndDoSettle();


    /**
     * 通道退款队列拉取
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/pobRefundDelayJob")
    @ResponseBody
    public SingleResponse<?> pobRefundDelayJob();

    /**
     * 加入通道退款延迟队列
     *
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/addRefundDelayJob")
    @ResponseBody
    public SingleResponse<?> addRefundDelayJob(@RequestBody AddRefundDelayJobParam param);

}
