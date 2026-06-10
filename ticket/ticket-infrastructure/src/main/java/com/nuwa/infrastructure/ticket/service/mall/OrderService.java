package com.nuwa.infrastructure.ticket.service.mall;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.exception.Assert;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallPaymentOrder;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.database.order.entity.DouyinSettleOrder;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.service.OrderNoService;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderPaySuccessDTO;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderRefundSuccessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * VideoOrderBiz 视频订单业务逻辑
 *
 * @author hy
 * @date 2020/10/27 17:15
 * @since 1.0.0
 */
@Slf4j

@Service
public class OrderService {

    @Autowired
    private MallPaymentOrderService paymentOrderService;

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private MallProductSkuStockService productSkuStockService;

    @Autowired
    private MallProductService productService;

    @Autowired
    private OrderNoService orderNoService;

    public Boolean paySuccess(OrderPaySuccessDTO dto) {
        log.info("paySuccess[dto:{}]", JSONUtil.toJsonPrettyStr(dto));
        String orderNo = dto.getOrderNo();

        MallTrade trade = tradeService.lambdaQuery().eq(MallTrade::getOrderNo, orderNo).one();
        if (Objects.isNull(trade) || trade.getPayStatus().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getCode())) {
            log.info("[dto:{}] 已经支付，但收到回调.", JSONUtil.toJsonPrettyStr(dto));
            return Boolean.FALSE;
        }
        MallProductSkuStock productSkuStock = productSkuStockService.getById(trade.getSpecificationsId());
        if (Objects.isNull(productSkuStock)) {
            log.error("产品[id:{}]不存在!", trade.getSpecificationsId());
            return Boolean.FALSE;
        }
        boolean updateTrade = paymentOrderService.lambdaUpdate()
                .set(MallPaymentOrder::getPaySuccessTime, dto.getPaySuccessTime())
                .set(MallPaymentOrder::getTradeStatus, PaymentStatusEnum.PAYMENT_SUCCESS.getCode())
                .set(MallPaymentOrder::getTradeStatus, PaymentStatusEnum.PAYMENT_SUCCESS.getCode())
                .eq(MallPaymentOrder::getPaymentOrderNo, orderNo)
                .eq(MallPaymentOrder::getTradeStatus, PaymentStatusEnum.CREATE.getCode())
                .update();

        boolean updateOrder = tradeService.lambdaUpdate()
                .set(MallTrade::getPayStatus, PaymentStatusEnum.PAYMENT_SUCCESS.getCode())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SEND.getCode())
                .set(MallTrade::getPaySuccessTime, dto.getPaySuccessTime())
                .eq(MallTrade::getOrderNo, orderNo)
                .eq(MallTrade::getPayStatus, PaymentStatusEnum.CREATE.getCode())
                .update();

        productService.incrementUpdate(MallProduct::getSales,trade.getProductNum()).eq(MallProduct::getId,trade.getProductId()).update();

        if (trade.getPayChannel().equals(10)) {
            log.info("save settle order");
            Date current = new Date();
            DouyinSettleOrder douyinSettleOrder = new DouyinSettleOrder();
            douyinSettleOrder.setOrderId(trade.getId());
            douyinSettleOrder.setOrderNo(Long.parseLong(trade.getTradeNo()));
            douyinSettleOrder.setSettleAmount(new BigDecimal(trade.getPayAmount()));
            douyinSettleOrder.setStatus(1);
            douyinSettleOrder.setOutOrderNo(orderNoService.snowflakeId());
            douyinSettleOrder.setChannelPaymentId(trade.getId());
            douyinSettleOrder.setChannelPaymentOrderNo(Long.parseLong(trade.getTradeNo()));
            douyinSettleOrder.setTimeExecuteSettle(DateUtil.offsetDay(current, 8));
            douyinSettleOrder.setCreateTime(current);
            boolean insert = douyinSettleOrder.insert();
            Assert.isTrue(insert, "save DouyinSettleOrder orderId:" + trade.getId() + " 失败");
            log.info("save DouyinSettleOrder[{}] orderId:{} success", douyinSettleOrder.getId(), trade.getId());
        }

        if (updateTrade && updateOrder) {
            log.info("paySuccess[orderNo:{}] success.", orderNo);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public Boolean refundSuccess(OrderRefundSuccessDTO dto) {
        log.info("refundSuccess[dto:{}]", JSONUtil.toJsonPrettyStr(dto));
        String tradeNo = dto.getOrderNo();
        MallTrade trade = tradeService.lambdaQuery().eq(MallTrade::getOrderNo, tradeNo).one();
        if (Objects.isNull(trade)){
            log.info("[dto:{}] 订单不存在.", JSONUtil.toJsonPrettyStr(dto));
            return Boolean.FALSE;
        }
        if (!trade.getOrderStatus().equals(PaymentStatusEnum.REFUNDING.getCode())) {
            log.info("[dto:{}] 订单状态不正确.", JSONUtil.toJsonPrettyStr(dto));
            return Boolean.FALSE;
        }
        boolean updateOrder = tradeService.lambdaUpdate()
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.REFUNDED.getCode())
                .set(MallTrade::getRefundTime, dto.getRefundTime())
                .set(MallTrade::getRefundAmount, dto.getAmount())
                .set(MallTrade::getRefundOrderNo, dto.getPlatOrderNo())
                .eq(MallTrade::getOrderNo, tradeNo)
                .eq(MallTrade::getOrderStatus, PaymentStatusEnum.REFUNDING.getCode())
                .update();

        productService.decreaseUpdate(MallProduct::getSales,trade.getProductNum())
                .eq(MallProduct::getId,trade.getProductId()).update();

        if (updateOrder) {
            log.info("paySuccess[orderNo:{}] success.", tradeNo);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
