package com.nuwa.ticket.start.server.event.handler;

import com.nuwa.app.ticket.util.RetryTimesUtil;
import com.nuwa.client.ticket.dto.domainevent.order.SupplierPaymentEvent;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.infrastructure.ticket.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.ticket.database.manager.TradeTransactionManager;
import com.nuwa.infrastructure.ticket.database.manager.dto.SupplierPaySuccessDTO;
import com.nuwa.infrastructure.ticket.database.manager.dto.SystemDoRefundDTO;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.enums.PushSwitchEnum;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.log.dto.ApiLogDTO;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.SupplierLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.third.b2b.SupplierB2bHttpSender;
import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bPaymentReqModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bPaymentRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bPaymentReq;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bPaymentResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 供应商请求日志事件处理
 *
 * @author hy
 */
@Slf4j
@Component
public class SupplierPaymentEventHandler /*extends AbstractEventHandler<SupplierPaymentEvent>*/ {

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private TradeTransactionManager tradeManager;

    @Autowired
    private LogBizService asyncLogService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Async
    @EventListener(SupplierPaymentEvent.class)
    public void handle(SupplierPaymentEvent event) {
        log.info("SupplierPaymentEvent handle event:{}", event);
        Long supplierPaymentOrderId = event.getSupplierPaymentOrderId();
        SupplierPaymentOrder order = supplierPaymentOrderService.getById(supplierPaymentOrderId);
        if ("1001".equals(order.getSupplierType())) {
            doSupplierB2bPaymentOrderPush(order);
        }
    }

    private void doSupplierB2bPaymentOrderPush(SupplierPaymentOrder order) {
        try {
            log.info(">>>> sendB2bPaymentRequest[orderId:{}]", order.getId());
            ChannelResult<B2bPaymentResp> b2bChannelResult = sendB2bPaymentRequest(order);
            log.info("<<<< sendB2bPaymentRequest");

            Integer retryCnt = order.getRetryTimes();
            ++retryCnt;

            ApiLogDTO requestDTO = new ApiLogDTO(b2bChannelResult, order.getOrderId(), order.getId(), SupplierLogTypeEnum.payment.getCode());
            asyncLogService.saveApiLog(requestDTO);

            boolean successful = b2bChannelResult.isSuccessful();
            if (successful) {
                //成功
                b2bPaymentSuccessProcess(order, retryCnt, b2bChannelResult.getData().getModel());
            } else {
                log.error("SupplierPaymentOrder:[id:{}] retryCnt:{}推送失败", order.getId(), retryCnt);
                //失败
                if (retryCnt > RetryTimesUtil.MAX_RETRY_COUNT) {
                    log.warn("SupplierPaymentOrder[id{}]推送供应商失败,自动退款处理", order.getId());
                    boolean push = supplierPaymentOrderService.lambdaUpdate()
                            .set(SupplierPaymentOrder::getPushStatus, PushSwitchEnum.off.getCode())
                            .set(SupplierPaymentOrder::getFailureCode, b2bChannelResult.getCode())
                            .set(SupplierPaymentOrder::getFailureMsg, b2bChannelResult.getMsg())
                            .set(SupplierPaymentOrder::getRetryTimes, retryCnt)
                            .set(SupplierPaymentOrder::getTimeNext, RetryTimesUtil.getRetryCountTime(retryCnt))
                            .set(SupplierPaymentOrder::getLastUpdateTime, new Date())
                            .eq(SupplierPaymentOrder::getId, order.getId())
                            .update();
                    if (push) {
                        SystemDoRefundDTO dto = new SystemDoRefundDTO();
                        dto.setOrderId(order.getOrderId());
                        dto.setRefundBizCode(2);
                        dto.setAuditStatus(10);
                        dto.setReason("推送供应商失败,退款");
                        tradeManager.systemDoRefund(dto);
                        log.warn("SupplierPaymentOrder[id{}]推送供应商失败,自动退款处理", order.getId());

                        boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                                .set(TicketOrder::getFailureCode, b2bChannelResult.getCode())
                                .set(TicketOrder::getFailureMsg, b2bChannelResult.getMsg())
                                .set(TicketOrder::getLastUpdateTime, new Date())
                                .eq(TicketOrder::getId, order.getOrderId())
                                .update();
                        if (updateTicketOrder) {
                            log.info("update TicketOrder[orderId:{}] FailureCode:{},FailureMsg:{}", order.getOrderId(), b2bChannelResult.getCode(), b2bChannelResult.getMsg());
                        }
                    }
                } else {
                    boolean push = supplierPaymentOrderService.lambdaUpdate()
                            .set(SupplierPaymentOrder::getFailureCode, b2bChannelResult.getCode())
                            .set(SupplierPaymentOrder::getFailureMsg, b2bChannelResult.getMsg())
                            .set(SupplierPaymentOrder::getRetryTimes, retryCnt)
                            .set(SupplierPaymentOrder::getTimeNext, RetryTimesUtil.getRetryCountTime(retryCnt))
                            .set(SupplierPaymentOrder::getLastUpdateTime, new Date())
                            .eq(SupplierPaymentOrder::getId, order.getId())
                            .update();
                }
            }
        } catch (Exception ex) {
            log.error("orderNo:{},推送异常", order.getPaymentNo(), ex);
        }
    }

    private ChannelResult<B2bPaymentResp> sendB2bPaymentRequest(SupplierPaymentOrder order) {
        String mchSupplierId = order.getMchSupplierId();
        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(mchSupplierId);

        B2bPaymentReq req = new B2bPaymentReq();
        B2bConfigModel config = new B2bConfigModel();
        config.setApiUrl(supplierConfig.getApiUrl());
        config.setPartnerId(supplierConfig.getChannelMerchantId());
        config.setKey(supplierConfig.getChannelSecretKey());
        req.setConfig(config);

        B2bPaymentReqModel model = new B2bPaymentReqModel();
        model.setOrderId(order.getSupplierOrderNo() + "");
        req.setModel(model);

        log.info(">>>> SupplierB2bHttpSender.payment[supplierPaymentOrderId:{}]", order.getId());
        ChannelResult<B2bPaymentResp> channelResult = SupplierB2bHttpSender.payment(req);
        log.info(">>>> SupplierB2bHttpSender.payment[supplierPaymentOrderId:{}] channelResult:{}", order.getId(), channelResult);
        return channelResult;
    }

    private void b2bPaymentSuccessProcess(SupplierPaymentOrder order, Integer retryCnt, B2bPaymentRespModel paymentRespModel) {
        SupplierPaySuccessDTO dto = new SupplierPaySuccessDTO();
        dto.setSupplierPaymentOrder(order);
        dto.setVoucherCode(paymentRespModel.getVoucherCode());
        dto.setTimes(retryCnt);
        dto.setTimePaid(new Date());
        tradeManager.supplierPaySuccess(dto);
        log.info("tradeManager.supplierPaySuccess invoke success");

        OrderLogDTO orderLog = new OrderLogDTO();
        orderLog.setOrderId(order.getOrderId());
        orderLog.setType(TicketOrderLogTypeEnum.supplierPaymentSuccess.getCode());
        orderLog.setResult("供应商支付成功");
        orderLog.setBizOrderId(order.getId());
        asyncLogService.saveOrderLog(orderLog);
    }
}
