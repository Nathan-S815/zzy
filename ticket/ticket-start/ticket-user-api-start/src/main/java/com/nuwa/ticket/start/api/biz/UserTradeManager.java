package com.nuwa.ticket.start.api.biz;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.service.ChannelPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TouristInfoService;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductPriceEverydayService;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.infrastructure.ticket.enums.PushSwitchEnum;
import com.nuwa.infrastructure.ticket.enums.SupplierOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.service.OrderNoService;
import com.nuwa.ticket.start.api.biz.dto.*;
import com.nuwa.ticket.start.api.biz.ret.PlaceOrderRet;
import com.nuwa.ticket.start.api.biz.ret.RefundApplyRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Service
public class UserTradeManager {

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private OrderNoService orderNoService;

    /**
     * 支付通道下单
     *
     * @param dto PlaceOrderByPayChannelDTO
     * @return PlaceOrderResponse
     */
    @Transactional(rollbackFor = Exception.class)
    public ChannelPaymentOrder channelCreateOrder(ChannelCreateOrderDTO dto) {
        TicketOrder ticketOrder = dto.getTicketOrder();
        UserAware userAware = dto.getUserAware();
        PlaceOrderRet response = new PlaceOrderRet();
        Date current = new Date();

        Long paymentOrderId = dto.getTicketOrder().getChannelPaymentOrderId();
        if (Objects.isNull(paymentOrderId)) {
            ChannelPaymentOrder channelOrder = new ChannelPaymentOrder();
            channelOrder.setOrderNo(ticketOrder.getOrderNo());
            channelOrder.setCreateById(userAware.getUserId() + "");
            channelOrder.setUserId(userAware.getUserId());
            channelOrder.setCreateTime(current);
            channelOrder.setOrderNo(ticketOrder.getOrderNo());
            channelOrder.setOrderId(ticketOrder.getId());
            channelOrder.setChannelMchNo(dto.getPayConf().getChannelMchId());
            channelOrder.setPayConfigId(dto.getPayConf().getId());
            channelOrder.setAmount(ticketOrder.getRealAmount());
            channelOrder.setPayType(dto.getPayType());
            channelOrder.setStatus(0);
            channelOrder.setMchPayOrderNo(orderNoService.snowflakeId());
            boolean channelOrderSave = channelOrder.insert();
            Assert.isTrue(channelOrderSave);
            log.info("save ChannelPaymentOrder[id:{}] success.", channelOrder.getId());
            boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                    .set(TicketOrder::getChannelPaymentOrderId, channelOrder.getId())
                    .set(TicketOrder::getLastUpdateTime, new Date())
                    .set(TicketOrder::getLastUpdateById, userAware.getUserId() + "")
                    .set(TicketOrder::getLastUpdateByName, userAware.getUserName() + "")
                    .eq(TicketOrder::getId, ticketOrder.getId())
                    .in(TicketOrder::getStatus, 0, 1)
                    .update();
            Assert.isTrue(updateTicketOrder, "支付失败,非法的订单状态.");
            return channelOrder;
        } else {
            return channelPaymentOrderService.getById(paymentOrderId);
        }
    }

    /**
     * 支付通道下单成功
     *
     * @param dto PlaceOrderSuccessByPayChannelDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void channelCreateOrderSuccess(ChannelCreateOrderSuccessDTO dto) {
        PlaceOrderRet response = new PlaceOrderRet();
        TicketOrder ticketOrder = dto.getTicketOrder();
        ChannelPaymentOrder channelPaymentOrder = dto.getChannelPaymentOrder();
        UserAware userAware = dto.getUserAware();
        Date current = new Date();

        boolean updateChannelPaymentOrder = channelPaymentOrderService.lambdaUpdate()
                .set(ChannelPaymentOrder::getStatus, 1)
                .set(ChannelPaymentOrder::getChannelPayOrderNo, dto.getChannelOrderNo())
                .set(ChannelPaymentOrder::getExtra, dto.getExtJson())
                .set(ChannelPaymentOrder::getLastUpdateTime, current)
                .eq(ChannelPaymentOrder::getId, channelPaymentOrder.getId())
                .update();
        Assert.isTrue(updateChannelPaymentOrder);
        log.info("update ChannelPaymentOrder status->1 [id:{}] success.", channelPaymentOrder.getId());

        boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                .set(TicketOrder::getStatus, 1)
                .set(TicketOrder::getLastUpdateTime, new Date())
                .set(TicketOrder::getLastUpdateById, userAware.getUserId() + "")
                .set(TicketOrder::getLastUpdateByName, userAware.getUserName() + "")
                .eq(TicketOrder::getId, ticketOrder.getId())
                .in(TicketOrder::getStatus, 0, 1)
                .update();
        Assert.isTrue(updateTicketOrder, "支付失败,非法的订单状态.");
    }

    /**
     * 用户退款申请
     *
     * @param dto UserRefundApplyDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public RefundApplyRet refundApply(UserApplyRefundDTO dto) {
        RefundApplyRet ret = new RefundApplyRet();
        TicketOrder ticketOrder = ticketOrderService.getById(dto.getOrderId());
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery().eq(SupplierPaymentOrder::getOrderId, ticketOrder.getId()).one();
        UserAware userAware = dto.getUserAware();
        Date current = new Date();

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.incrementUpdate(TicketOrder::getRefundingQuantity, dto.getQuantity())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "增加退款中数量失败");

        BigDecimal totalAmount = ticketOrder.getUnitPrice().multiply(new BigDecimal(dto.getQuantity()));

        TicketRefund ticketRefund = new TicketRefund();
        ticketRefund.setOrderNo(ticketOrder.getOrderNo());
        ticketRefund.setOrderId(ticketOrder.getId());
        ticketRefund.setCreateById(userAware.getUserId() + "");
        ticketRefund.setUserId(userAware.getUserId());
        ticketRefund.setCreateTime(current);
        ticketRefund.setAmount(totalAmount);
        ticketRefund.setRealAmount(totalAmount);
        ticketRefund.setStatus(1);
        ticketRefund.setQuantity(dto.getQuantity());
        ticketRefund.setRefundSerialNo(orderNoService.snowflakeId());
        ticketRefund.setRefundReason(dto.getReason());
        ticketRefund.setRefundBizCode(1);
        ticketRefund.setAuditStatus(1);
        ticketRefund.setMchId(userAware.getMchId());
        ticketRefund.setSupplierMerchantId(ticketOrder.getSupplierMerchantId());
        boolean ticketRefundSave = ticketRefund.insert();
        Assert.isTrue(ticketRefundSave);
        log.info("save TicketRefund[id:{}] success.", ticketRefund.getId());
        ret.setTicketRefund(ticketRefund);

        SupplierRefundOrder supplierRefundOrder = new SupplierRefundOrder();
        supplierRefundOrder.setOrderNo(ticketOrder.getId());
        supplierRefundOrder.setCreateById(userAware.getUserId() + "");
        supplierRefundOrder.setUserId(userAware.getUserId());
        supplierRefundOrder.setCreateTime(current);
        supplierRefundOrder.setOrderNo(ticketOrder.getOrderNo());
        supplierRefundOrder.setOrderId(ticketOrder.getId());
        supplierRefundOrder.setAmount(ticketOrder.getRealAmount());
        supplierRefundOrder.setPushStatus(PushSwitchEnum.on.getCode());
        supplierRefundOrder.setRetryTimes(0);
        supplierRefundOrder.setTimeNext(DateUtil.offsetSecond(current, 20));
        supplierRefundOrder.setTicketRefundId(ticketRefund.getId());
        supplierRefundOrder.setRefundSerialNo(orderNoService.snowflakeId());
        supplierRefundOrder.setSupplierId(ticketOrder.getSupplierId() + "");
        supplierRefundOrder.setQuantity(ticketRefund.getQuantity());
        supplierRefundOrder.setRefundReason(ticketRefund.getRefundReason());
        supplierRefundOrder.setSupplierPaymentOrderId(supplierPaymentOrder.getId());

        boolean saveSupplierRefund = supplierRefundOrder.insert();
        Assert.isTrue(saveSupplierRefund);
        log.info("save SupplierRefundOrder[id:{}] success.", supplierRefundOrder.getId());
        ret.setSupplierRefundOrder(supplierRefundOrder);
        return ret;
    }

}
