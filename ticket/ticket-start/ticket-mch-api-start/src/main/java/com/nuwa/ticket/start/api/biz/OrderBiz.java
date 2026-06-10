package com.nuwa.ticket.start.api.biz;

import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import com.nuwa.infrastructure.ticket.database.order.service.ChannelPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketRefundService;
import com.nuwa.infrastructure.ticket.service.OrderNoService;
import com.nuwa.ticket.start.api.biz.dto.AftermarketRefundDTO;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditPassDTO;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditRejectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hy
 */
@Slf4j
@Service
public class OrderBiz {

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private OrderNoService orderNoService;

    @Transactional(rollbackFor = Exception.class)
    public void refundAuditPass(RefundAuditPassDTO param) {
        log.info(">>>> refundAuditPass");
        log.info("param:{}", param);
        UserAware userAware = param.getUserAware();
        TicketRefund ticketRefund = param.getTicketRefund();
        TicketOrder ticketOrder = ticketOrderService.getById(ticketRefund.getOrderId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getAuditStatus, 10)
                .set(TicketRefund::getStatus, 2)
                .set(TicketRefund::getLastUpdateTime, new Date())
                .set(TicketRefund::getLastUpdateById, userAware.getUserId() + "")
                .set(TicketRefund::getLastUpdateByName, userAware.getUserName() + "")
                .eq(TicketRefund::getAuditStatus, 1)
                .eq(TicketRefund::getId, ticketRefund.getId())
                .update();
        Assert.isTrue(updateTicketRefund, "修改退款订单状态失败");

        ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.getById(ticketOrder.getChannelPaymentOrderId());

        ChannelRefundOrder channelRefundOrder = new ChannelRefundOrder();
        channelRefundOrder.setMchRefundNo(orderNoService.snowflakeId());
        channelRefundOrder.setUserId(ticketRefund.getUserId());
        channelRefundOrder.setCreateTime(new Date());
        channelRefundOrder.setCreateByName(userAware.getUserName());
        channelRefundOrder.setCreateById(userAware.getUserId() + "");
        channelRefundOrder.setAmount(ticketRefund.getAmount());
        channelRefundOrder.setRefundOrderId(ticketRefund.getId());
        channelRefundOrder.setOrderId(ticketRefund.getOrderId());
        channelRefundOrder.setOrderNo(ticketRefund.getOrderNo());
        channelRefundOrder.setChannelPayOrderId(channelPayOrder.getId());
        channelRefundOrder.setChannelPayOrderNo(channelPayOrder.getMchPayOrderNo());
        channelRefundOrder.setStatus(1);
        boolean insert = channelRefundOrder.insert();
        Assert.isTrue(insert, "添加ChannelRefundOrder失败");

        log.info("<<<< refundAuditPass");
    }

    @Transactional(rollbackFor = Exception.class)
    public void refundAuditReject(RefundAuditRejectDTO param) {
        log.info(">>>> refundAuditReject");
        log.info("param:{}", param);
        UserAware userAware = param.getUserAware();
        TicketRefund ticketRefund = param.getTicketRefund();
        TicketOrder ticketOrder = ticketOrderService.getById(ticketRefund.getOrderId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getAuditStatus, 5)
                .set(TicketRefund::getStatus, 4)
                .set(TicketRefund::getRefundRejectReason, param.getReason())
                .set(TicketRefund::getLastUpdateTime, new Date())
                .set(TicketRefund::getLastUpdateById, userAware.getUserId() + "")
                .set(TicketRefund::getLastUpdateByName, userAware.getUserName() + "")
                .eq(TicketRefund::getAuditStatus, 1)
                .eq(TicketRefund::getId, ticketRefund.getId())
                .update();
        Assert.isTrue(updateTicketRefund, "修改退款订单状态失败");

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.decreaseUpdate(TicketOrder::getRefundingQuantity, ticketRefund.getQuantity())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "减少退款中数量失败");

        log.info("<<<< refundAuditPass");
    }

    @Transactional(rollbackFor = Exception.class)
    public void aftermarketRefund(AftermarketRefundDTO dto) {
        log.info(">>>> aftermarketRefund");
        log.info("dto:{}", dto);
        Date current = new Date();
        UserAware userAware = dto.getUserAware();
        TicketOrder ticketOrder = ticketOrderService.getById(dto.getOrderId());

        int refundCount = ticketOrder.getRefundedQuantity() + ticketOrder.getRefundingQuantity();
        Assert.isTrue(refundCount + dto.getQuantity() <= ticketOrder.getQuantity(), "可退票数不足");

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.incrementUpdate(TicketOrder::getRefundingQuantity, dto.getQuantity())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "增加退款中数量失败");


        TicketRefund ticketRefund = new TicketRefund();
        ticketRefund.setOrderNo(ticketOrder.getOrderNo());
        ticketRefund.setOrderId(ticketOrder.getId());
        ticketRefund.setCreateById(ticketOrder.getUserId() + "");
        ticketRefund.setUserId(ticketOrder.getUserId());
        ticketRefund.setCreateByName(userAware.getUserName());
        ticketRefund.setCreateTime(current);
        ticketRefund.setAmount(dto.getAmount());
        ticketRefund.setRealAmount(dto.getAmount());
        ticketRefund.setStatus(2);
        ticketRefund.setQuantity(dto.getQuantity());
        ticketRefund.setRealQuantity(dto.getQuantity());
        ticketRefund.setRefundSerialNo(orderNoService.snowflakeId());
        ticketRefund.setRefundReason(dto.getReason());
        ticketRefund.setRefundBizCode(3);
        ticketRefund.setAuditStatus(10);
        ticketRefund.setMchId(ticketOrder.getMchId());
        ticketRefund.setSupplierMerchantId(ticketOrder.getSupplierMerchantId());
        boolean ticketRefundSave = ticketRefund.insert();
        Assert.isTrue(ticketRefundSave);
        log.info("save TicketRefund[id:{}] success.", ticketRefund.getId());

        ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.getById(ticketOrder.getChannelPaymentOrderId());
        Assert.notNull(channelPayOrder, "支付订单为空");

        boolean canRefund = channelPayOrder.getStatus().equals(2);
        Assert.isTrue(canRefund, "订单未支付不允许退款");

        ChannelRefundOrder channelRefundOrder = new ChannelRefundOrder();
        channelRefundOrder.setMchRefundNo(orderNoService.snowflakeId());
        channelRefundOrder.setUserId(ticketRefund.getUserId());
        channelRefundOrder.setCreateTime(new Date());
        channelRefundOrder.setCreateByName(userAware.getUserName());
        channelRefundOrder.setCreateById(userAware.getUserId() + "");
        channelRefundOrder.setAmount(ticketRefund.getAmount());
        channelRefundOrder.setRefundOrderId(ticketRefund.getId());
        channelRefundOrder.setOrderId(ticketRefund.getOrderId());
        channelRefundOrder.setOrderNo(ticketRefund.getOrderNo());
        channelRefundOrder.setChannelPayOrderId(channelPayOrder.getId());
        channelRefundOrder.setChannelPayOrderNo(channelPayOrder.getMchPayOrderNo());
        channelRefundOrder.setStatus(1);
        boolean insert = channelRefundOrder.insert();
        Assert.isTrue(insert, "添加ChannelRefundOrder失败");
        log.info("save ChannelRefundOrder[id:{}] success.", channelRefundOrder.getId());

        log.info("<<<< aftermarketRefund");
    }
}
