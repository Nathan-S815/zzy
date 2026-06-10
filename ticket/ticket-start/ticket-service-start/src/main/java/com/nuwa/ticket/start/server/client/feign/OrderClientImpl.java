package com.nuwa.ticket.start.server.client.feign;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.order.param.*;
import com.nuwa.client.ticket.dto.domainevent.order.SupplierApplyRefundEvent;
import com.nuwa.client.ticket.dto.domainevent.order.SupplierPaymentEvent;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.ticket.util.ChannelResultCodeEnum;
import com.nuwa.infrastructure.ticket.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.ticket.database.manager.AlipayDataManager;
import com.nuwa.infrastructure.ticket.database.manager.TradeTransactionManager;
import com.nuwa.infrastructure.ticket.database.manager.dto.*;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductDayTime;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.service.ProductDayTimeService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductPriceEverydayService;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayQueue;
import com.nuwa.infrastructure.ticket.delayqueue.core.DelayQueueJob;
import com.nuwa.infrastructure.ticket.enums.PushSwitchEnum;
import com.nuwa.infrastructure.ticket.enums.SupplierOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.log.dto.ApiLogDTO;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.SupplierLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinSender;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateRefundReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.SettleReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateRefundResp;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.SettleResp;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppOrderRefundReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppRefundQueryReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.MiniAppOrderRefundResp;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.MiniAppRefundQueryResp;
import com.nuwa.ticket.start.server.constants.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * OrderClient实现
 *
 * @author hy
 */
@Slf4j
@RestController
public class OrderClientImpl implements OrderClientI {

    public static final int B2B_REFUND_AUDIT_PASS_STATUS = 1;
    public static final int B2B_REFUND_AUDIT_REJECT_STATUS = 2;

    @Autowired
    private TradeTransactionManager tradeManager;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private SupplierRefundOrderService supplierRefundOrderService;

    @Autowired
    private ChannelRefundOrderService channelRefundOrderService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private LogBizService asyncLogService;

    @Autowired
    private DouyinSettleOrderService douyinSettleOrderService;

    @Autowired
    private AlipayDataManager alipayDataManager;

    @Value("${douyin.refund.notify.url}")
    private String douyinRefundNotifyUrl;

    @Value("${douyin.settle.notify.url}")
    private String douyinSettleNotifyUrl;

    @Value("${gateway.refund.notify.url}")
    private String gatewayRefundNotifyUrl;

    @Autowired
    private OrderClientI orderClientI;

    @Override
    public SingleResponse<?> paymentSuccess(ChannelPaymentSuccessNotifyParam param) {
        log.info("paymentSuccess:{}", JSONUtil.toJsonPrettyStr(param));
        ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.lambdaQuery().eq(ChannelPaymentOrder::getMchPayOrderNo, param.getOrderNo()).one();
        if (Objects.isNull(channelPayOrder)) {
            return SingleResponse.buildFailure("9063", "订单号[" + param.getOrderNo() + "]不存在");
        }
        ChannelPaySuccessDTO dto = new ChannelPaySuccessDTO();
        dto.setChannelPaymentOrder(channelPayOrder);
        dto.setTimePaid(new Date());
        tradeManager.channelPaySuccess(dto);

        alipayDataManager.orderUpdate(channelPayOrder.getOrderId());

        OrderLogDTO lifecycleDTO = new OrderLogDTO();
        lifecycleDTO.setOrderId(channelPayOrder.getOrderId());
        lifecycleDTO.setType(TicketOrderLogTypeEnum.channelPaySuccess.getCode());
        lifecycleDTO.setResult("通道支付成功(金额:" + channelPayOrder.getAmount() + ")");
        lifecycleDTO.setBizOrderId(channelPayOrder.getId());
        asyncLogService.saveOrderLog(lifecycleDTO);
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> douyinSettleProcess(DouyinSettleNotifyParam param) {
        DouyinSettleOrder douyinSettleOrder = douyinSettleOrderService.lambdaQuery().eq(DouyinSettleOrder::getOutOrderNo, param.getOrderNo()).one();
        if (Objects.nonNull(douyinSettleOrder)) {
            String status = param.getStatus();
            if ("SUCCESS".equalsIgnoreCase(status)) {
                boolean update = douyinSettleOrderService.lambdaUpdate()
                        .set(DouyinSettleOrder::getStatus, 3)
                        .set(DouyinSettleOrder::getTimeSettle, new Date())
                        .set(DouyinSettleOrder::getUpdateTime, param.getSettledAt())
                        .eq(DouyinSettleOrder::getId, douyinSettleOrder.getId())
                        .update();
                if (update) {
                    log.info("update DouyinSettleOrder[{}] status(2) to 已结算(3)", douyinSettleOrder.getId());
                    OrderLogDTO lifecycleDTO = new OrderLogDTO();
                    lifecycleDTO.setOrderId(douyinSettleOrder.getOrderId());
                    lifecycleDTO.setType(TicketOrderLogTypeEnum.channelSettle.getCode());
                    lifecycleDTO.setResult("抖音结算成功");
                    lifecycleDTO.setBizOrderId(douyinSettleOrder.getId());
                    asyncLogService.saveOrderLog(lifecycleDTO);
                }
            } else if ("FAIL".equalsIgnoreCase(status)) {
                boolean update = douyinSettleOrderService.lambdaUpdate()
                        .set(DouyinSettleOrder::getStatus, 4)
                        .set(DouyinSettleOrder::getUpdateTime, new Date())
                        .eq(DouyinSettleOrder::getId, douyinSettleOrder.getId())
                        .update();
                if (update) {
                    log.info("update DouyinSettleOrder[{}] status(2) to 结算失败(4)", douyinSettleOrder.getId());
                    OrderLogDTO lifecycleDTO = new OrderLogDTO();
                    lifecycleDTO.setOrderId(douyinSettleOrder.getOrderId());
                    lifecycleDTO.setType(TicketOrderLogTypeEnum.channelSettle.getCode());
                    lifecycleDTO.setResult("抖音结算失败");
                    lifecycleDTO.setBizOrderId(douyinSettleOrder.getId());
                    asyncLogService.saveOrderLog(lifecycleDTO);
                }
            }
        }
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> refundSuccess(ChannelRefundSuccessNotifyParam param) {
        ChannelRefundOrder channelRefundOrder = channelRefundOrderService.lambdaQuery().eq(ChannelRefundOrder::getMchRefundNo, param.getOrderNo()).one();
        ChannelRefundSuccessDTO dto = new ChannelRefundSuccessDTO();
        dto.setTimeRefund(new Date());
        dto.setChannelRefundOrder(channelRefundOrder);
        tradeManager.channelRefundSuccess(dto);

        alipayDataManager.orderUpdate(channelRefundOrder.getOrderId());

        OrderLogDTO lifecycleDTO = new OrderLogDTO();
        lifecycleDTO.setOrderId(channelRefundOrder.getOrderId());
        lifecycleDTO.setType(TicketOrderLogTypeEnum.channelRefundSuccess.getCode());
        lifecycleDTO.setResult("通道退款成功(金额:" + channelRefundOrder.getAmount() + ")");
        lifecycleDTO.setBizOrderId(channelRefundOrder.getId());
        asyncLogService.saveOrderLog(lifecycleDTO);

        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> b2bRefundNoticeProcess(B2bSupplierRefundNotifyParam param) {
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery().eq(SupplierPaymentOrder::getSupplierOrderNo, param.getOrderId()).one();
        if (Objects.isNull(supplierPaymentOrder)) {
            log.warn("SupplierPaymentOrder SupplierOrderNo:{} 不存在 ", param.getOrderId());
            return SingleResponse.buildSuccess();
        }

        Integer status = param.getStatus();
        //主动发起退款处理逻辑
        if (status.equals(B2B_REFUND_AUDIT_PASS_STATUS) && StrUtil.isBlank(param.getRefundNo())) {
            OrderCancelDTO dto = new OrderCancelDTO();
            dto.setSupplierOrderNo(param.getOrderId());
            OrderCancelRet supplierLaunchRefundPass = tradeManager.orderCancel(dto);

            ApiLogDTO requestDTO = new ApiLogDTO();
            requestDTO.setOrderId(supplierPaymentOrder.getOrderId());
            requestDTO.setBizOrderId(supplierPaymentOrder.getId());
            requestDTO.setType(SupplierLogTypeEnum.cancel.getCode());
            requestDTO.setReq(param.getRequestStr());
            asyncLogService.saveApiLog(requestDTO);

            OrderLogDTO orderLogDTO = new OrderLogDTO();
            orderLogDTO.setOrderId(supplierPaymentOrder.getOrderId());
            orderLogDTO.setType(TicketOrderLogTypeEnum.supplierOrderCancel.getCode());
            orderLogDTO.setResult("供应商取消订单");
            orderLogDTO.setBizOrderId(supplierPaymentOrder.getId());
            asyncLogService.saveOrderLog(orderLogDTO);

            return SingleResponse.buildSuccess();
        }

        SupplierRefundOrder supplierRefundOrder = supplierRefundOrderService.lambdaQuery()
                .eq(SupplierRefundOrder::getRefundSerialNo, param.getRefundNo())
                .last("limit 1")
                .one();
        if (Objects.isNull(supplierRefundOrder)) {
            log.warn("SupplierRefundOrder RefundNo:{} 不存在 ", param.getRefundNo());
            return SingleResponse.buildSuccess();
        }

        if (supplierRefundOrder.getAuditStatus() != null && !supplierRefundOrder.getAuditStatus().equals(1)) {
            log.warn("SupplierRefundOrder[id:{}] 已处理,重复回调.", supplierRefundOrder.getId());
            return SingleResponse.buildSuccess();
        }

        /*
          1:通过  2:不通过
         */
        if (status.equals(B2B_REFUND_AUDIT_PASS_STATUS)) {

            ApiLogDTO requestDTO = new ApiLogDTO();
            requestDTO.setOrderId(supplierRefundOrder.getOrderId());
            requestDTO.setBizOrderId(supplierRefundOrder.getId());
            requestDTO.setType(SupplierLogTypeEnum.refundNotice.getCode());
            requestDTO.setReq(param.getRequestStr());
            asyncLogService.saveApiLog(requestDTO);

            SupplierRefundPassDTO dto = new SupplierRefundPassDTO();
            dto.setTimeRefund(new Date());
            dto.setSupplierRefundOrder(supplierRefundOrder);
            tradeManager.supplierRefundPass(dto);

            OrderLogDTO orderLogDTO = new OrderLogDTO();
            orderLogDTO.setOrderId(supplierRefundOrder.getOrderId());
            orderLogDTO.setType(TicketOrderLogTypeEnum.supplierRefundPass.getCode());
            orderLogDTO.setResult("供应商退款审核通过");
            orderLogDTO.setBizOrderId(supplierRefundOrder.getId());
            asyncLogService.saveOrderLog(orderLogDTO);

        } else if (status.equals(B2B_REFUND_AUDIT_REJECT_STATUS)) {

            SupplierRefundRejectDTO dto = new SupplierRefundRejectDTO();
            dto.setReason(param.getReason());
            dto.setSupplierRefundOrder(supplierRefundOrder);
            tradeManager.supplierRefundReject(dto);

            OrderLogDTO orderLifecycleDTO = new OrderLogDTO();
            orderLifecycleDTO.setOrderId(supplierRefundOrder.getOrderId());
            orderLifecycleDTO.setType(TicketOrderLogTypeEnum.supplierRefundReject.getCode());
            orderLifecycleDTO.setResult("供应商退款审核失败(" + param.getReason() + ")");
            orderLifecycleDTO.setBizOrderId(supplierRefundOrder.getId());
            asyncLogService.saveOrderLog(orderLifecycleDTO);
        }
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> b2bConsumerNoticeProcess(B2bSupplierConsumerNotifyParam param) {
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery()
                .eq(SupplierPaymentOrder::getPaymentNo, param.getPaymentNo())
                .last("limit 1")
                .one();
        Assert.notNull(supplierPaymentOrder);

        SupplierConsumerSuccessDTO dto = new SupplierConsumerSuccessDTO();
        dto.setTimeConsumer(new Date());
        dto.setTotalCount(param.getTotalNumber());
        dto.setCheckCount(param.getCheckedNumber());
        dto.setSupplierPaymentOrder(supplierPaymentOrder);
        tradeManager.supplierConsumerSuccess(dto);

        alipayDataManager.orderUpdate(supplierPaymentOrder.getOrderId());

        ApiLogDTO requestDTO = new ApiLogDTO();
        requestDTO.setOrderId(supplierPaymentOrder.getOrderId());
        requestDTO.setBizOrderId(supplierPaymentOrder.getId());
        requestDTO.setType(SupplierLogTypeEnum.ticketNotice.getCode());
        requestDTO.setReq(param.getRequestStr());
        asyncLogService.saveApiLog(requestDTO);

        OrderLogDTO lifecycleDTO = new OrderLogDTO();
        lifecycleDTO.setOrderId(supplierPaymentOrder.getOrderId());
        lifecycleDTO.setType(TicketOrderLogTypeEnum.supplierConsumerSuccess.getCode());
        lifecycleDTO.setResult("供应商核销成功(" + param.getCheckedNumber() + " 张)");
        lifecycleDTO.setBizOrderId(supplierPaymentOrder.getId());
        asyncLogService.saveOrderLog(lifecycleDTO);

        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> b2bTicketNoticeProcess(B2bSupplierTicketNotifyParam param) {
        log.info("param:{}", JSONUtil.toJsonPrettyStr(param));
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery()
                .eq(SupplierPaymentOrder::getPaymentNo, param.getPaymentNo())
                .last("limit 1")
                .one();
        Assert.notNull(supplierPaymentOrder);

        if (supplierPaymentOrder.getStatus().equals(10)) {
            log.warn("重复出票通知,supplierPaymentOrder:{}", JSONUtil.toJsonPrettyStr(supplierPaymentOrder));
            return SingleResponse.buildSuccess();
        }

        TicketOrder ticketOrder = ticketOrderService.getById(supplierPaymentOrder.getOrderId());
        String ticketStatus = param.getTicketStatus();
        if (ticketStatus.equalsIgnoreCase("1")) {
            List<SupplierTicketSuccessDTO.VoucherItem> voucherItems = voucherItems = param.getVoucherItems().stream().map(x -> {
                SupplierTicketSuccessDTO.VoucherItem item = new SupplierTicketSuccessDTO.VoucherItem();
                BeanUtil.copyProperties(x, item);
                return item;
            }).collect(Collectors.toList());

            SupplierTicketSuccessDTO dto = new SupplierTicketSuccessDTO();
            dto.setSupplierPaymentOrder(supplierPaymentOrder);
            dto.setVoucherItems(voucherItems);
            tradeManager.supplierTieketSuccess(dto);

            ApiLogDTO requestDTO = new ApiLogDTO();
            requestDTO.setOrderId(supplierPaymentOrder.getOrderId());
            requestDTO.setBizOrderId(supplierPaymentOrder.getId());
            requestDTO.setType(SupplierLogTypeEnum.ticketed.getCode());
            requestDTO.setReq(param.getRequestStr());
            asyncLogService.saveApiLog(requestDTO);

            OrderLogDTO lifecycleDTO = new OrderLogDTO();
            lifecycleDTO.setOrderId(supplierPaymentOrder.getOrderId());
            lifecycleDTO.setType(TicketOrderLogTypeEnum.supplierTicketSuccess.getCode());
            lifecycleDTO.setResult("供应商出票成功(" + ticketOrder.getQuantity() + " 张)");
            lifecycleDTO.setBizOrderId(supplierPaymentOrder.getId());
            asyncLogService.saveOrderLog(lifecycleDTO);
        } else if (ticketStatus.equalsIgnoreCase("2")) {
            //出票失败
            SupplierTicketFailedDTO dto = new SupplierTicketFailedDTO();
            dto.setSupplierPaymentOrder(supplierPaymentOrder);
            tradeManager.supplierTieketFailed(dto);

            ApiLogDTO requestDTO = new ApiLogDTO();
            requestDTO.setOrderId(supplierPaymentOrder.getOrderId());
            requestDTO.setBizOrderId(supplierPaymentOrder.getId());
            requestDTO.setType(SupplierLogTypeEnum.ticketed.getCode());
            requestDTO.setReq(param.getRequestStr());
            asyncLogService.saveApiLog(requestDTO);

            OrderLogDTO lifecycleDTO = new OrderLogDTO();
            lifecycleDTO.setOrderId(supplierPaymentOrder.getOrderId());
            lifecycleDTO.setType(TicketOrderLogTypeEnum.supplierTicketFailed.getCode());
            lifecycleDTO.setResult("供应商出票失败");
            lifecycleDTO.setBizOrderId(supplierPaymentOrder.getId());
            asyncLogService.saveOrderLog(lifecycleDTO);

            SystemDoRefundDTO refundDTO = new SystemDoRefundDTO();
            refundDTO.setOrderId(ticketOrder.getId());
            refundDTO.setRefundBizCode(5);
            refundDTO.setAuditStatus(10);
            refundDTO.setReason("供应商出票失败,退款");
            tradeManager.systemDoRefund(refundDTO);
            log.warn("ticketOrder[id{}]出票失败,自动退款处理", ticketOrder.getId());

        }

        alipayDataManager.orderUpdate(supplierPaymentOrder.getOrderId());
        return SingleResponse.buildSuccess();
    }

    @Override
    public SingleResponse<?> takeAndPushSupplierOrder() {
        List<SupplierPaymentOrder> supplierPaymentOrders = supplierPaymentOrderService.lambdaQuery()
                .eq(SupplierPaymentOrder::getStatus, SupplierOrderEnum.waitPay.getCode())
                .eq(SupplierPaymentOrder::getPushStatus, PushSwitchEnum.on.getCode())
                .le(SupplierPaymentOrder::getRetryTimes, OrderConstant.MAX_RETRY_TIMES)
                .le(SupplierPaymentOrder::getTimeNext, new Date())
                .orderByAsc(SupplierPaymentOrder::getRetryTimes)
                .last("limit 5")
                .list();
        supplierPaymentOrders.forEach(this::doPushPaymentOrder);
        return SingleResponse.of(supplierPaymentOrders.size());
    }

    @Override
    public SingleResponse<?> channelTakeAndDoRefund() {
        List<ChannelRefundOrder> refundOrders = channelRefundOrderService.lambdaQuery()
                .eq(ChannelRefundOrder::getStatus, 1)
                .orderByAsc(ChannelRefundOrder::getId)
                .last("limit 5")
                .list();
        refundOrders.forEach(this::doChannelRefund);
        return SingleResponse.of(refundOrders.size());
    }

    @Override
    public SingleResponse<?> supplierTakeAndDoRefund() {
        List<SupplierRefundOrder> supplierRefundOrderList = supplierRefundOrderService.lambdaQuery()
                .eq(SupplierRefundOrder::getPushStatus, PushSwitchEnum.on.getCode())
                .le(SupplierRefundOrder::getRetryTimes, OrderConstant.MAX_RETRY_TIMES)
                .le(SupplierRefundOrder::getTimeNext, new Date())
                .orderByAsc(SupplierRefundOrder::getRetryTimes)
                .last("limit 5")
                .list();
        supplierRefundOrderList.forEach(x -> {
            SupplierApplyRefundEvent event = new SupplierApplyRefundEvent();
            try {
                event.setTicketRefundId(x.getTicketRefundId());
                event.setSupplierRefundOrderId(x.getId());
                domainEventPublisher.publishEvent(event);
            } catch (Exception ex) {
                log.error("publishEvent SupplierApplyRefundEvent[{}] error", event, ex);
            }
        });
        return SingleResponse.of(supplierRefundOrderList.size());
    }

    @Override
    public SingleResponse<?> takeAndCloseNotPayOrder() {
        List<TicketOrder> ticketOrders = ticketOrderService.lambdaQuery()
                .eq(TicketOrder::getStatus, TicketOrderEnum.paying.getCode())
                .le(TicketOrder::getTimeExpire, new Date())
                .last("limit 15")
                .list();
        ticketOrders.forEach(x -> {
            try {
                boolean update = ticketOrderService.lambdaUpdate()
                        .set(TicketOrder::getStatus, TicketOrderEnum.closed.getCode())
                        .set(TicketOrder::getLastUpdateTime, new Date())
                        .eq(TicketOrder::getId, x.getId())
                        .update();
                if (update) {
                    log.info("closed TicketOrder[id:{}] success.", x.getId());

                    alipayDataManager.orderUpdate(x.getId());

                    ProductPriceEveryday productPriceEveryday = productPriceEverydayService.lambdaQuery()
                            .eq(ProductPriceEveryday::getScenicspotProductId, x.getProductId())
                            .eq(ProductPriceEveryday::getStatus, 0)
                            .eq(ProductPriceEveryday::getDate, x.getVisitDate())
                            .one();
                    if (Objects.nonNull(productPriceEveryday)) {
                        log.info(">>>> decrease Stock");
                        if (!productPriceEveryday.getStockNumber().equals(-1L)) {
                            boolean updateStock = productPriceEverydayService
                                    .incrementUpdate(ProductPriceEveryday::getStockNumber, x.getQuantity())
                                    .eq(ProductPriceEveryday::getId, productPriceEveryday.getId())
                                    .update();
                            Assert.isTrue(updateStock);
                            log.info("increment Stock success.");

                            if (productPriceEveryday.getStockModel().equals(1) && Objects.nonNull(x.getSessionId())) {
                                boolean updateSessionStock = productDayTimeService
                                        .incrementUpdate(ProductDayTime::getStockNumber, x.getQuantity())
                                        .eq(ProductDayTime::getId, x.getSessionId())
                                        .update();
                                Assert.isTrue(updateSessionStock);
                                log.info("increment Session Stock success.");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("closed TicketOrder[id:{}] failed", x.getId(), ex);
            }
        });
        return SingleResponse.of(ticketOrders.size());
    }

    @Override
    public SingleResponse<?> takeAndSetMinPrice() {
        List<ScenicspotProduct> productList = scenicspotProductService.lambdaQuery()
                .eq(ScenicspotProduct::getStatus, 1)
                .orderByAsc(ScenicspotProduct::getLastUpdatePriceTime)
                .last("limit 50")
                .list();
        productList.forEach(x -> {
            updateSalePrice(x);
            updateMarketPrice(x);
            updatePurchasePrice(x);
        });

        List<Scenicspot> scenicspotList = scenicspotService.lambdaQuery()
                .eq(Scenicspot::getStatus, 1)
                .eq(Scenicspot::getVersionFlag, 1)
                .orderByAsc(Scenicspot::getLastUpdatePriceTime)
                .last("limit 50")
                .list();
        scenicspotList.forEach(x -> {
            try {
                this.modifyPoiMinPrice(x.getId());
            } catch (Exception ex) {
                log.warn("modifyPoiMinPrice id:{} error", x.getId(), ex);
            }
        });

        return SingleResponse.of(productList.size());
    }


    @Override
    public SingleResponse<?> douyinTakeAndDoSettle() {
        List<DouyinSettleOrder> douyinSettleOrders = douyinSettleOrderService.lambdaQuery()
                .eq(DouyinSettleOrder::getStatus, 1)
                .le(DouyinSettleOrder::getTimeExecuteSettle, new Date())
                .last("limit 10")
                .list();
        douyinSettleOrders.forEach(x -> {
            try {
                Long channelPayOrderId = x.getChannelPaymentId();
                ChannelPaymentOrder channelPaymentOrder = channelPaymentOrderService.getById(channelPayOrderId);
                Long payConfigId = channelPaymentOrder.getPayConfigId();
                MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.getById(payConfigId);

                SettleReq req = new SettleReq();
                DouYinPayConfig config = new DouYinPayConfig();
                config.setSalt(merchantAppPayConf.getSalt());
                req.setConfig(config);
                req.setAppId(merchantAppPayConf.getOutAppId());
                req.setOutOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
                req.setOutSettleNo(x.getOutOrderNo() + "");
                req.setSettleDesc("T+7结算(" + channelPaymentOrder.getAmount() + ")");
                req.setNotifyUrl(douyinSettleNotifyUrl);
                try {
                    ChannelResult<SettleResp> channelResult = DouYinSender.settle(req);
                    if (channelResult.isSuccessful()) {
                        //结算发起成功
                        log.info("抖音结算发起成功,DouyinSettleOrder:{}", x);
                        boolean update = douyinSettleOrderService.lambdaUpdate()
                                .set(DouyinSettleOrder::getDouyinSettleNo, channelResult.getData().getSettle_no())
                                .set(DouyinSettleOrder::getStatus, 2)
                                .set(DouyinSettleOrder::getUpdateTime, new Date())
                                .eq(DouyinSettleOrder::getId, x.getId())
                                .update();
                        if (update) {
                            log.info("update DouyinSettleOrder[{}] status(1) to 结算中(2)", x.getId());
                        }

                    } else if (channelResult.getCode().equals(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode())) {
                        log.warn("抖音结算发起请求通道超时,DouyinSettleOrder:{}", x);
                    } else {
                        //抖音结算发起失败
                        log.info("抖音结算发起失败,DouyinSettleOrder[{}]", x.getId());
                        boolean update = douyinSettleOrderService.lambdaUpdate()
                                .set(DouyinSettleOrder::getStatus, 4)
                                .set(DouyinSettleOrder::getFailureCode, channelResult.getCode())
                                .set(DouyinSettleOrder::getFailureMsg, channelResult.getMsg())
                                .set(DouyinSettleOrder::getUpdateTime, new Date())
                                .eq(DouyinSettleOrder::getId, x.getId())
                                .update();
                        if (update) {
                            log.info("update DouyinSettleOrder[{}] status(1) to 结算中(2)", x.getId());
                        }
                    }
                } catch (Exception ex) {
                    log.error("orderId:{},抖音结算请求发生异常", x.getOrderId(), ex);
                }

            } catch (Exception ex) {
                log.error("do DouyinSettle[{}] failed", x.getId());
            }
        });
        return SingleResponse.of(douyinSettleOrders.size());
    }

    @Override
    public SingleResponse<?> pobRefundDelayJob() {
        DelayQueueJob job = DelayQueue.pop("delayQueue:channelRefund");
        if (Objects.nonNull(job)) {
            String channelRefundId = job.getMessage();
            log.info("pobRefundDelayJob channelRefundId:{}", channelRefundId);
            try {
                ChannelRefundOrder channelRefundOrder = channelRefundOrderService.getById(channelRefundId);
                if (Objects.nonNull(channelRefundOrder)) {
                    if (channelRefundOrder.getStatus().equals(2)) {
                        Long orderId = channelRefundOrder.getOrderId();
                        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
                        Long channelPayOrderId = ticketOrder.getChannelPaymentOrderId();
                        ChannelPaymentOrder channelPaymentOrder = channelPaymentOrderService.getById(channelPayOrderId);
                        Long payConfigId = channelPaymentOrder.getPayConfigId();
                        MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.getById(payConfigId);

                        MiniAppRefundQueryReq refundQueryReq = new MiniAppRefundQueryReq();
                        RequestHead head = new RequestHead(merchantAppPayConf.getChannelMchId(), new Date());
                        refundQueryReq.setHead(head);
                        MiniAppRefundQueryReq.Body body = new MiniAppRefundQueryReq.Body();
                        body.setMchOrderNo(channelRefundOrder.getMchRefundNo() + "");
                        body.setPlatOrderNo(channelRefundOrder.getChannelRefundNo() + "");
                        body.setMchId(Integer.parseInt(merchantAppPayConf.getChannelMchId()));
                        refundQueryReq.setBody(body);
                        String sign = refundQueryReq.buildSign(merchantAppPayConf.getSalt());
                        head.setSign(sign);
                        ChannelResult<MiniAppRefundQueryResp> channelResult = PaymentGatewaySender.refundOrderQuery(refundQueryReq, "http://apipayment.zhongzhiyou.cn");
                        if (Objects.nonNull(channelResult) && channelResult.isSuccessful()) {
                            MiniAppRefundQueryResp data = channelResult.getData();
                            if (Objects.nonNull(data)) {
                                if (data.getStatus().equalsIgnoreCase("REFUND_FAILED")) {
                                    //退款成功
                                    log.info("退款结果查询,data:{}", data);
                                    ChannelRefundAcceptFailedDTO dto = new ChannelRefundAcceptFailedDTO();
                                    dto.setChannelRefundOrder(channelRefundOrder);
                                    dto.setErrMsg(data.getErrMsg());
                                    tradeManager.channelRefundAcceptFailed(dto);

                                    OrderLogDTO lifecycleDTO = new OrderLogDTO();
                                    lifecycleDTO.setOrderId(ticketOrder.getId());
                                    lifecycleDTO.setType(TicketOrderLogTypeEnum.channelRefundSuccess.getCode());
                                    lifecycleDTO.setResult("通道退款失败(金额:" + channelRefundOrder.getAmount() + ")");
                                    lifecycleDTO.setBizOrderId(channelRefundOrder.getId());
                                    asyncLogService.saveOrderLog(lifecycleDTO);

                                    DelayQueue.finish(Long.parseLong(channelRefundId));
                                } else if (data.getStatusDesc().equalsIgnoreCase("REFUND_SUCCEEDED")) {
                                    DelayQueue.finish(Long.parseLong(channelRefundId));
                                }
                            } else {
                                DelayQueue.finish(Long.parseLong(channelRefundId));
                            }
                        } else if (channelResult.getCode().equals(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode())) {
                            log.warn("退款请求通道超时,ChannelRefundOrder:{}", channelRefundOrder);
                            //todo 退款超时处理
                        }
                    } else {
                        DelayQueue.finish(Long.parseLong(channelRefundId));
                    }
                }
            } catch (Exception ex) {
                log.error("退款查询结果延迟任务消费失败，channelRefundId:{}", channelRefundId, ex);
            }
            return SingleResponse.of(channelRefundId);
        }
        return SingleResponse.of(null);
    }

    @Override
    public SingleResponse<?> addRefundDelayJob(@RequestBody AddRefundDelayJobParam param) {
        Date now = new Date();
        DelayQueueJob delayQueueJob = new DelayQueueJob();
        delayQueueJob.setTopic("delayQueue:channelRefund");
        delayQueueJob.setDelayTime(DateUtil.offsetSecond(now, 120).getTime());
        delayQueueJob.setMessage(param.getChannelRefundId() + "");
        delayQueueJob.setTtrTime(30);
        delayQueueJob.setId(param.getChannelRefundId());
        DelayQueue.push(delayQueueJob);
        return SingleResponse.buildSuccess();
    }

    public void doPushPaymentOrder(SupplierPaymentOrder order) {
        try {
            log.info("doPushPaymentOrder[id:{}]", order.getId());
            SupplierPaymentEvent event = new SupplierPaymentEvent();
            event.setSupplierPaymentOrderId(order.getId());
            this.domainEventPublisher.publishEvent(event);
            log.info("doPushPaymentOrder[id:{}] success", order.getId());
        } catch (Exception ex) {
            log.info("doPushPaymentOrder error", ex);
            log.error("domainEventPublisher.publishEvent SupplierPaymentEvent error SupplierRefundOrderId:{}", order.getId(), ex);
        }
    }

    public void doChannelRefund(ChannelRefundOrder order) {
        try {
            Long channelPayOrderId = order.getChannelPayOrderId();
            ChannelPaymentOrder channelPaymentOrder = channelPaymentOrderService.getById(channelPayOrderId);
            Long payConfigId = channelPaymentOrder.getPayConfigId();
            MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.getById(payConfigId);
            Long refundOrderId = order.getRefundOrderId();
            TicketRefund ticketRefund = ticketRefundService.getById(refundOrderId);
            if (ticketRefund.getAmount().compareTo(new BigDecimal("0")) == 0) {
                doZeroRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
                return;
            }
            Long orderId = ticketRefund.getOrderId();
            String payChannel = channelPaymentOrder.getPayType();
            if ("douyin_mini".equalsIgnoreCase(payChannel)) {
                doDouyinRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
            } else if ("weixin_mini".equalsIgnoreCase(payChannel)) {
                doGatewayRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
            } else if ("alipay_mini".equalsIgnoreCase(payChannel)) {
                doGatewayRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
            } else if ("weixin_mp".equalsIgnoreCase(payChannel)) {
                doGatewayRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
            } else if ("alipay_h5".equalsIgnoreCase(payChannel)) {
                doGatewayRefund(order, channelPaymentOrder, merchantAppPayConf, ticketRefund);
            }
        } catch (Exception ex) {
            log.error("orderNo:{},处理异常", order.getMchRefundNo(), ex);
        }
    }


    private void updateSalePrice(ScenicspotProduct x) {
        ProductPriceEveryday priceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, x.getId())
                .ge(ProductPriceEveryday::getDate, DateUtil.beginOfDay(new Date()))
                .eq(ProductPriceEveryday::getStatus, 0)
                .orderByAsc(ProductPriceEveryday::getSalePrice)
                .last("limit 1")
                .one();

        if (Objects.nonNull(priceEveryday)) {

            BigDecimal salePrice = priceEveryday.getSalePrice();
            scenicspotService.lambdaUpdate()
                    .set(Scenicspot::getPriceMin, salePrice)
                    .set(Scenicspot::getLastUpdatePriceTime, new Date())
                    .eq(Scenicspot::getId, x.getScenicspotId())
                    .update();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPrice, salePrice)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        } else {
            scenicspotService.lambdaUpdate()
                    .set(Scenicspot::getPriceMin, null)
                    .set(Scenicspot::getLastUpdatePriceTime, new Date())
                    .eq(Scenicspot::getId, x.getScenicspotId())
                    .update();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPrice, null)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        }
    }

    private void updateMarketPrice(ScenicspotProduct x) {
        ProductPriceEveryday priceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, x.getId())
                .ge(ProductPriceEveryday::getDate, DateUtil.beginOfDay(new Date()))
                .eq(ProductPriceEveryday::getStatus, 0)
                .orderByAsc(ProductPriceEveryday::getMarketPrice)
                .last("limit 1")
                .one();

        if (Objects.nonNull(priceEveryday)) {

            BigDecimal marketPrice = priceEveryday.getMarketPrice();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getMarketPrice, marketPrice)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        } else {
            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getMarketPrice, null)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        }
    }

    private void updatePurchasePrice(ScenicspotProduct x) {
        ProductPriceEveryday priceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, x.getId())
                .ge(ProductPriceEveryday::getDate, DateUtil.beginOfDay(new Date()))
                .eq(ProductPriceEveryday::getStatus, 0)
                .orderByAsc(ProductPriceEveryday::getPurchasePrice)
                .last("limit 1")
                .one();

        if (Objects.nonNull(priceEveryday)) {

            BigDecimal purchasePrice = priceEveryday.getPurchasePrice();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPurchasePrice, purchasePrice)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        } else {
            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPurchasePrice, null)
                    .set(ScenicspotProduct::getLastUpdatePriceTime, new Date())
                    .eq(ScenicspotProduct::getId, x.getId())
                    .update();
        }
    }

    private void doDouyinRefund(ChannelRefundOrder order, ChannelPaymentOrder channelPaymentOrder, MerchantAppPayConf merchantAppPayConf, TicketRefund ticketRefund) {
        CreateRefundReq req = new CreateRefundReq();
        DouYinPayConfig config = new DouYinPayConfig();
        config.setSalt(merchantAppPayConf.getSalt());
        req.setConfig(config);
        req.setAppId(merchantAppPayConf.getOutAppId());
        req.setBody("退款");
        req.setSubject("退款");
        req.setTotalAmount(order.getAmount().longValue());
        req.setOutOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        req.setOutRefundNo(order.getMchRefundNo() + "");
        req.setAllSettle(0);
        req.setReason(ticketRefund.getRefundReason());
        req.setNotifyUrl(douyinRefundNotifyUrl);
        try {
            ChannelResult<CreateRefundResp> channelResult = DouYinSender.createRefund(req);
            if (channelResult.isSuccessful()) {
                //退款成功
                log.info("退款申请成功,refundOrder:{}", order);
                ChannelRefundAcceptSuccessDTO dto = new ChannelRefundAcceptSuccessDTO();
                dto.setChannelRefundOrder(order);
                dto.setChannelRefundOrderNo(channelResult.getData().getRefund_no());
                tradeManager.channelRefundAcceptSuccess(dto);
            } else if (channelResult.getCode().equals(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode())) {
                log.warn("退款请求通道超时,ChannelRefundOrder:{}", order);
                //todo 退款超时处理
            } else {
                //退款申请失败
                log.info("退款失败,refundOrder:{}", order);
                ChannelRefundAcceptFailedDTO dto = new ChannelRefundAcceptFailedDTO();
                dto.setChannelRefundOrder(order);
                dto.setErrCode(channelResult.getCode());
                dto.setErrMsg(channelResult.getMsg());
                tradeManager.channelRefundAcceptFailed(dto);

                OrderLogDTO lifecycleDTO = new OrderLogDTO();
                lifecycleDTO.setOrderId(channelPaymentOrder.getOrderId());
                lifecycleDTO.setType(TicketOrderLogTypeEnum.channelRefundSuccess.getCode());
                lifecycleDTO.setResult("通道退款失败(金额:" + order.getAmount() + ")");
                lifecycleDTO.setBizOrderId(order.getId());
                asyncLogService.saveOrderLog(lifecycleDTO);
            }
        } catch (Exception ex) {
            log.error("orderNo:{},请求发生异常", order.getMchRefundNo(), ex);
        }
    }

    private void doGatewayRefund(ChannelRefundOrder order, ChannelPaymentOrder channelPaymentOrder, MerchantAppPayConf merchantAppPayConf, TicketRefund ticketRefund) {
        MiniAppOrderRefundReq createReq = new MiniAppOrderRefundReq();
        RequestHead head = new RequestHead(merchantAppPayConf.getChannelMchId(), new Date());
        createReq.setHead(head);
        MiniAppOrderRefundReq.Body body = new MiniAppOrderRefundReq.Body();
        body.setOrderNo(order.getMchRefundNo() + "");
        body.setClientIp("127.0.0.1");
        body.setRefundAmount(order.getAmount().multiply(new BigDecimal("100")).longValue() + "");
        body.setNotifyUrl(gatewayRefundNotifyUrl);
        body.setRefundReason(StrUtil.isEmpty(ticketRefund.getRefundReason()) ? "取消退款" : ticketRefund.getRefundReason());
        body.setOrigMchOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        createReq.setBody(body);
        String sign = createReq.buildSign(merchantAppPayConf.getSalt());
        head.setSign(sign);
        ChannelResult<MiniAppOrderRefundResp> channelResult = PaymentGatewaySender.refundOrder(createReq, "http://apipayment.zhongzhiyou.cn");
        if (Objects.nonNull(channelResult) && channelResult.isSuccessful()) {
            MiniAppOrderRefundResp data = channelResult.getData();
            if (Objects.nonNull(data)) {
                //退款成功
                log.info("退款申请成功,refundOrder:{}", order);
                ChannelRefundAcceptSuccessDTO dto = new ChannelRefundAcceptSuccessDTO();
                dto.setChannelRefundOrder(order);
                dto.setChannelRefundOrderNo(data.getPlatOrderNo());
                tradeManager.channelRefundAcceptSuccess(dto);

                AddRefundDelayJobParam delayJobParam = new AddRefundDelayJobParam();
                delayJobParam.setChannelRefundId(order.getId());
                SingleResponse<?> singleResponse = orderClientI.addRefundDelayJob(delayJobParam);
                if (singleResponse.isSuccess()) {
                    log.info("addRefundDelayJob id:{} success", order.getId());
                }
            }
        } else if (channelResult.getCode().equals(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode())) {
            log.warn("退款请求通道超时,ChannelRefundOrder:{}", order);
            //todo 退款超时处理
        } else {
            //退款申请失败
            log.info("退款失败,refundOrder:{}", order);
            ChannelRefundAcceptFailedDTO dto = new ChannelRefundAcceptFailedDTO();
            dto.setChannelRefundOrder(order);
            dto.setErrCode(channelResult.getCode());
            dto.setErrMsg(channelResult.getMsg());
            tradeManager.channelRefundAcceptFailed(dto);

            OrderLogDTO lifecycleDTO = new OrderLogDTO();
            lifecycleDTO.setOrderId(channelPaymentOrder.getOrderId());
            lifecycleDTO.setType(TicketOrderLogTypeEnum.channelRefundSuccess.getCode());
            lifecycleDTO.setResult("通道退款失败(金额:" + order.getAmount() + ")");
            lifecycleDTO.setBizOrderId(order.getId());
            asyncLogService.saveOrderLog(lifecycleDTO);
        }
    }

    private void modifyPoiMinPrice(Long scenicspotId) {
        ScenicspotProduct scenicspotProduct = scenicspotProductService.lambdaQuery()
                .eq(ScenicspotProduct::getScenicspotId, scenicspotId)
                .eq(ScenicspotProduct::getStatus, 1)
                .eq(ScenicspotProduct::getDeleteFlag, 0)
                .isNotNull(ScenicspotProduct::getPrice)
                .orderByAsc(ScenicspotProduct::getPrice)
                .last("limit 1")
                .one();
        if (Objects.nonNull(scenicspotProduct)) {
            BigDecimal salePrice = scenicspotProduct.getPrice();
            scenicspotService.lambdaUpdate()
                    .set(Scenicspot::getPriceMin, salePrice)
                    .set(Scenicspot::getLastUpdatePriceTime, new Date())
                    .eq(Scenicspot::getId, scenicspotId)
                    .update();

        } else {
            scenicspotService.lambdaUpdate()
                    .set(Scenicspot::getPriceMin, null)
                    .set(Scenicspot::getLastUpdatePriceTime, new Date())
                    .eq(Scenicspot::getId, scenicspotId)
                    .update();
        }
    }

    private void doZeroRefund(ChannelRefundOrder order, ChannelPaymentOrder channelPaymentOrder, MerchantAppPayConf merchantAppPayConf, TicketRefund ticketRefund) {
        //退款成功
        log.info(">>>> 零元退款,refundOrderId:{}", order.getId());

        ChannelRefundAcceptSuccessDTO dto = new ChannelRefundAcceptSuccessDTO();
        dto.setChannelRefundOrder(order);
        dto.setChannelRefundOrderNo(RandomUtil.randomNumbers(10));
        tradeManager.channelRefundAcceptSuccess(dto);

        ChannelRefundSuccessNotifyParam refundNotifyDTO = new ChannelRefundSuccessNotifyParam();
        refundNotifyDTO.setOrderNo(order.getMchRefundNo() + "");
        refundSuccess(refundNotifyDTO);

        log.info("<<<< 零元退款成功,refundOrderId:{}", order.getId());
    }


}
