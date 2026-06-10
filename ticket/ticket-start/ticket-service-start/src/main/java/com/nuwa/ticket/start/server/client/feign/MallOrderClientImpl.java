package com.nuwa.ticket.start.server.client.feign;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.mallorder.MallOrderClientI;
import com.nuwa.client.ticket.api.order.param.ChannelPaymentSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.ChannelRefundSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.WenChuangOrderCancelDelayJobParam;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.ticket.util.ChannelResultCodeEnum;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.database.manager.dto.ChannelRefundAcceptFailedDTO;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayQueue;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayQueueJob;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.mall.MallOrderService;
import com.nuwa.infrastructure.ticket.service.mall.OrderService;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderPaySuccessDTO;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderRefundSuccessDTO;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppRefundQueryReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.MiniAppRefundQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * OrderClient实现
 *
 * @author hy
 */
@Slf4j
@RestController
public class MallOrderClientImpl implements MallOrderClientI {

    @Autowired
    private MallTradeService mallTradeService;

    @Autowired
    private OrderService orderService;

    @Override
    public SingleResponse<?> paymentSuccess(ChannelPaymentSuccessNotifyParam param) {
        log.info("paymentSuccess:{}", JSONUtil.toJsonPrettyStr(param));
        String orderNo = param.getOrderNo();
        MallTrade trade = mallTradeService.lambdaQuery().eq(MallTrade::getOrderNo, orderNo).one();
        if (Objects.nonNull(trade)) {
            if (trade.getPayStatus().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getCode())) {
                return SingleResponse.of("ret=success");
            }
            OrderPaySuccessDTO dto = new OrderPaySuccessDTO();
            dto.setOrderNo(orderNo);
            dto.setOutOrderNo(param.getPlatOrderNo());
            dto.setAmount(trade.getPayAccount());
            dto.setPaySuccessTime(new Date());
            dto.setBankSerialNo(param.getBankSerialNo());
            Boolean ret = orderService.paySuccess(dto);
            if (ret) {
                return SingleResponse.of("ret=success");
            }
        }
        return SingleResponse.buildFailure("987", "处理失败");
    }

    @Override
    public SingleResponse<?> refundSuccess(ChannelRefundSuccessNotifyParam param) {
        String tradeNo = param.getOrderNo();
        MallTrade trade = mallTradeService.lambdaQuery().eq(MallTrade::getRefundSerialNo, tradeNo).one();
        if (Objects.nonNull(trade)) {
            if (trade.getPayStatus().equals(PaymentStatusEnum.REFUNDED.getCode())) {
                return SingleResponse.of("ret=success");
            }
            OrderRefundSuccessDTO dto = new OrderRefundSuccessDTO();
            dto.setOrderNo(trade.getOrderNo());
            dto.setAmount(param.getAmount());
            dto.setPlatOrderNo(param.getPlatOrderNo());
            dto.setRefundTime(new Date());
            Boolean ret = orderService.refundSuccess(dto);
            if (ret) {
                return SingleResponse.of("ret=success");
            }
        }
        return SingleResponse.buildFailure("987", "处理失败");
    }

    @Override
    public SingleResponse<?> orderCancelDelayJobProcess() {
        mallTradeService.lambdaUpdate()
                .lt(MallTrade::getExpireTime, new Date())
                .eq(MallTrade::getOrderStatus, PaymentStatusEnum.CREATE.getCode())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.CANCLED_ORDER.getCode())
                .set(MallTrade::getPayStatus, PaymentStatusEnum.CANCELED.getCode())
                .update();
        return SingleResponse.of(null);
    }

    @Override
    public SingleResponse<?> newOrderCancelDelayJob(WenChuangOrderCancelDelayJobParam param) {
        Date now = new Date();
        DelayQueueJob delayQueueJob = new DelayQueueJob();
        delayQueueJob.setTopic("delayQueue:wenchuang:orderCancel");
        delayQueueJob.setDelayTime(DateUtil.offsetMinute(now, 30).getTime());
        delayQueueJob.setMessage(param.getOrderId() + "");
        delayQueueJob.setTtrTime(30);
        delayQueueJob.setId(Long.parseLong(param.getOrderId()));
        DelayQueue.push(delayQueueJob);
        return SingleResponse.buildSuccess();
    }
}
