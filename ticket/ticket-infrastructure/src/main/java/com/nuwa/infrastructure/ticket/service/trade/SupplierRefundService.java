package com.nuwa.infrastructure.ticket.service.trade;

import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.infrastructure.ticket.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.ticket.database.manager.TradeTransactionManager;
import com.nuwa.infrastructure.ticket.database.manager.dto.SupplierRefundPassDTO;
import com.nuwa.infrastructure.ticket.database.manager.dto.SupplierRefundRejectDTO;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierRefundOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketRefundService;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.enums.PushSwitchEnum;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.log.dto.ApiLogDTO;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.SupplierLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.trade.dto.SupplierApplyRefundDTO;
import com.nuwa.infrastructure.ticket.third.b2b.SupplierB2bHttpSender;
import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bRefundReqModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bRefundRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bRefundReq;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bRefundResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 供应商退款服务
 *
 * @author hy
 */
@Service
@Slf4j
public class SupplierRefundService {

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private SupplierRefundOrderService supplierRefundOrderService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private TradeTransactionManager tradeManager;

    @Autowired
    private LogBizService asyncLogService;

    public void applyRefund(SupplierApplyRefundDTO dto) {
        log.info("dto:{}", dto);
        Long supplierRefundOrderId = dto.getSupplierRefundOrderId();
        if (Objects.nonNull(supplierRefundOrderId)) {
            SupplierRefundOrder supplierRefundOrder = supplierRefundOrderService.getById(supplierRefundOrderId);
            SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.getById(supplierRefundOrder.getSupplierPaymentOrderId());
            MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(supplierRefundOrder.getSupplierId());

            if ("1001".equals(supplierPaymentOrder.getSupplierType())) {
                doSupplierB2bRefundApply(supplierRefundOrderId, supplierRefundOrder, supplierPaymentOrder, supplierConfig);
            }
        }
        log.info("applyRefund[{}] success", dto.getSupplierRefundOrderId());
    }

    private void doSupplierB2bRefundApply(Long supplierRefundOrderId, SupplierRefundOrder supplierRefundOrder, SupplierPaymentOrder supplierPaymentOrder, MerchantSupplierConfig supplierConfig) {
        log.info(">>>> doB2bRefundApply");
        ChannelResult<B2bRefundResp> channelResult = doB2bRefundApply(supplierRefundOrder, supplierPaymentOrder, supplierConfig);
        log.info("<<<< doB2bRefundApply channelResult:{}", channelResult);

        ApiLogDTO requestDTO = ApiLogDTO.of(channelResult);
        requestDTO.setOrderId(supplierRefundOrder.getOrderId());
        requestDTO.setBizOrderId(supplierRefundOrder.getId());
        requestDTO.setType(SupplierLogTypeEnum.refundNotice.getCode());
        asyncLogService.saveApiLog(requestDTO);

        if (channelResult.isSuccessful()) {
            B2bRefundResp data = channelResult.getData();
            B2bRefundRespModel refundModel = data.getModel();
            String applyId = refundModel.getApplyId();
            String refundStatus = refundModel.getRefundStatus();
            if ("0".equalsIgnoreCase(refundStatus)) {
                boolean update = supplierRefundOrderService.lambdaUpdate()
                        .set(SupplierRefundOrder::getPushStatus, PushSwitchEnum.off.getCode())
                        .set(SupplierRefundOrder::getSupplierRefundOrderNo, applyId)
                        .set(SupplierRefundOrder::getAuditStatus, 1)
                        .set(SupplierRefundOrder::getLastUpdateTime, new Date())
                        .eq(SupplierRefundOrder::getId, supplierRefundOrderId)
                        .update();
            } else if ("1".equals(refundStatus)) {
                supplierAuditPassProcess(supplierRefundOrder, supplierPaymentOrder);
            } else if ("2".equals(refundStatus)) {
                supplierAuditRejectProcess(supplierRefundOrder, supplierPaymentOrder, data);
            }
        } else {
            Integer retryCnt = supplierRefundOrder.getRetryTimes();
            ++retryCnt;
            if (!"9002".equalsIgnoreCase(channelResult.getCode())) {
                supplierRefundOrderService.lambdaUpdate()
                        .set(SupplierRefundOrder::getPushStatus, PushSwitchEnum.off.getCode())
                        .set(SupplierRefundOrder::getFailureCode, channelResult.getCode())
                        .set(SupplierRefundOrder::getFailureMsg, channelResult.getMsg())
                        .set(SupplierRefundOrder::getRetryTimes, retryCnt)
                        .set(SupplierRefundOrder::getTimeNext, SupplierRetryTimesUtil.getRetryCountTime(retryCnt))
                        .set(SupplierRefundOrder::getLastUpdateTime, new Date())
                        .eq(SupplierRefundOrder::getId, supplierRefundOrderId)
                        .update();
                return;
            }

            doRetryProcess(supplierRefundOrderId, channelResult.getCode(), channelResult.getMsg(), retryCnt);
            log.error("SupplierRefundOrder:[id:{}] retryCnt:{}推送失败", supplierRefundOrderId, retryCnt);
        }
    }

    private void doRetryProcess(Long supplierRefundOrderId, String failCode, String failMsg, Integer retryCnt) {
        //失败
        if (retryCnt > SupplierRetryTimesUtil.MAX_RETRY_COUNT) {
            //todo 推送失败,退款处理
            supplierRefundOrderService.lambdaUpdate()
                    .set(SupplierRefundOrder::getPushStatus, PushSwitchEnum.off.getCode())
                    .set(SupplierRefundOrder::getFailureCode, failCode)
                    .set(SupplierRefundOrder::getFailureMsg, failMsg)
                    .set(SupplierRefundOrder::getRetryTimes, retryCnt)
                    .set(SupplierRefundOrder::getTimeNext, SupplierRetryTimesUtil.getRetryCountTime(retryCnt))
                    .set(SupplierRefundOrder::getLastUpdateTime, new Date())
                    .eq(SupplierRefundOrder::getId, supplierRefundOrderId)
                    .update();
        } else {
            supplierRefundOrderService.lambdaUpdate()
                    .set(SupplierRefundOrder::getFailureCode, failCode)
                    .set(SupplierRefundOrder::getFailureMsg, failMsg)
                    .set(SupplierRefundOrder::getRetryTimes, retryCnt)
                    .set(SupplierRefundOrder::getTimeNext, SupplierRetryTimesUtil.getRetryCountTime(retryCnt))
                    .set(SupplierRefundOrder::getLastUpdateTime, new Date())
                    .eq(SupplierRefundOrder::getId, supplierRefundOrderId)
                    .update();
        }
    }

    private ChannelResult<B2bRefundResp> doB2bRefundApply(SupplierRefundOrder supplierRefundOrder, SupplierPaymentOrder supplierPaymentOrder, MerchantSupplierConfig supplierConfig) {
        B2bRefundReq req = new B2bRefundReq();
        B2bConfigModel config = new B2bConfigModel();
        config.setApiUrl(supplierConfig.getApiUrl());
        config.setPartnerId(supplierConfig.getChannelMerchantId());
        config.setKey(supplierConfig.getChannelSecretKey());
        req.setConfig(config);

        B2bRefundReqModel model = new B2bRefundReqModel();
        model.setOrderId(supplierPaymentOrder.getSupplierOrderNo() + "");
        model.setRefundCount(supplierRefundOrder.getQuantity());
        model.setRefundId(supplierRefundOrder.getRefundSerialNo() + "");
        req.setModel(model);

        return SupplierB2bHttpSender.refund(req);
    }

    private void supplierAuditRejectProcess(SupplierRefundOrder supplierRefundOrder, SupplierPaymentOrder supplierPaymentOrder, B2bRefundResp data) {
        SupplierRefundRejectDTO dto = new SupplierRefundRejectDTO();
        dto.setSupplierRefundOrder(supplierRefundOrder);
        dto.setReason(data.getMessage());
        tradeManager.supplierRefundReject(dto);

        OrderLogDTO orderLogDTO = new OrderLogDTO();
        orderLogDTO.setOrderId(supplierRefundOrder.getOrderId());
        orderLogDTO.setType(TicketOrderLogTypeEnum.supplierRefundReject.getCode());
        orderLogDTO.setResult("供应商退款审核失败(" + data.getMessage() + ")");
        orderLogDTO.setBizOrderId(supplierPaymentOrder.getOrderId());
        asyncLogService.saveOrderLog(orderLogDTO);
    }

    private void supplierAuditPassProcess(SupplierRefundOrder supplierRefundOrder, SupplierPaymentOrder supplierPaymentOrder) {
        SupplierRefundPassDTO dto = new SupplierRefundPassDTO();
        dto.setSupplierRefundOrder(supplierRefundOrder);
        dto.setTimeRefund(new Date());
        tradeManager.supplierRefundPass(dto);

        OrderLogDTO orderLog = new OrderLogDTO();
        orderLog.setOrderId(supplierRefundOrder.getOrderId());
        orderLog.setType(TicketOrderLogTypeEnum.supplierRefundPass.getCode());
        orderLog.setResult("供应商退款审核通过");
        orderLog.setBizOrderId(supplierPaymentOrder.getOrderId());
        asyncLogService.saveOrderLog(orderLog);
    }
}
