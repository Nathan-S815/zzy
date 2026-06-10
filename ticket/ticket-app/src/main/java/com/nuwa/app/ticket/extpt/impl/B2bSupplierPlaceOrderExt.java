package com.nuwa.app.ticket.extpt.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.cola.extension.Extension;
import com.nuwa.app.ticket.constant.ExtensionConstant;
import com.nuwa.app.ticket.extpt.SupplierPlaceOrderExtPt;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TouristInfo;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TouristInfoService;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductDayTime;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductDayTimeService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductPriceEverydayService;
import com.nuwa.infrastructure.ticket.enums.SupplierOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.service.OrderNoService;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.log.dto.ApiLogDTO;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.SupplierLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.third.b2b.SupplierB2bHttpSender;
import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bCreateOrderReqModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.ContactPersonInfo;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.VisitPersonInfo;
import com.nuwa.infrastructure.ticket.third.b2b.model.resp.B2bCreateOrderRespModel;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bCreateOrderReq;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bCreateOrderResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * B2b供应商下单扩展点实现
 *
 * @author hy
 */
@Slf4j
@Extension(bizId = ExtensionConstant.BIZ_ID_SUPPLIER, useCase = ExtensionConstant.USE_CASE_PLACE_ORDER, scenario = ExtensionConstant.SCENARIO_B2B)
public class B2bSupplierPlaceOrderExt implements SupplierPlaceOrderExtPt {

    @Autowired
    private LogBizService logBizService;

    @Autowired
    private OrderNoService orderNoService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Override
    public SingleResponse<?> placeOrder(Long orderId) {
        log.info(">>>>供应商下单,orderId:{}", orderId);
        Date current = new Date();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(ticketOrder.getSupplierId());

        OrderLogDTO orderLog = new OrderLogDTO();
        orderLog.setOrderId(orderId);
        orderLog.setType(TicketOrderLogTypeEnum.created.getCode());
        orderLog.setResult("创建订单");
        orderLog.setBizOrderId(orderId);
        logBizService.saveOrderLog(orderLog);

        SupplierPaymentOrder supplierOrder = new SupplierPaymentOrder();
        supplierOrder.setOrderNo(orderNoService.snowflakeId());
        supplierOrder.setCreateById(ticketOrder.getCreateById());
        supplierOrder.setUserId(ticketOrder.getUserId());
        supplierOrder.setCreateTime(current);
        supplierOrder.setOrderNo(ticketOrder.getOrderNo());
        supplierOrder.setOrderId(ticketOrder.getId());
        supplierOrder.setAmount(ticketOrder.getRealAmount());
        supplierOrder.setStatus(SupplierOrderEnum.created.getCode());
        supplierOrder.setPushStatus(0);
        supplierOrder.setPaymentNo(orderNoService.snowflakeId());
        supplierOrder.setClientIp(ticketOrder.getClientIp());
        supplierOrder.setRetryTimes(0);
        supplierOrder.setMchSupplierId(supplierConfig.getId() + "");
        supplierOrder.setSupplierMch(supplierConfig.getChannelMerchantId());
        supplierOrder.setSupplierType(ExtensionConstant.SCENARIO_B2B);
        supplierOrder.setSupplierProductCode(ticketOrder.getSupplierProductCode());
        boolean saveSupplierOrder = supplierOrder.insert();

        B2bCreateOrderReq req = getB2bCreateOrderReq(ticketOrder, supplierConfig, supplierOrder);
        log.info(">>>> SupplierB2bHttpSender.createOrder req:{}", req);
        ChannelResult<B2bCreateOrderResp> channelResult = SupplierB2bHttpSender.createOrder(req);
        log.info("SupplierB2bHttpSender.createOrder channelResult:{}", channelResult);

        ApiLogDTO requestDTO = ApiLogDTO.of(channelResult);
        requestDTO.setOrderId(orderId);
        requestDTO.setBizOrderId(supplierOrder.getId());
        requestDTO.setType(SupplierLogTypeEnum.created.getCode());
        logBizService.saveApiLog(requestDTO);

        if (channelResult.isSuccessful()) {
            B2bCreateOrderResp b2bCreateOrderResp = channelResult.getData();
            if (b2bCreateOrderResp.checkSuccessRet()) {
                B2bCreateOrderRespModel createOrderRespModel = b2bCreateOrderResp.getModel();
                String supplierOrderId = createOrderRespModel.getOrderId();
                boolean updateSupplierPaymentOrder = supplierPaymentOrderService.lambdaUpdate()
                        .set(SupplierPaymentOrder::getStatus, 2)
                        .set(SupplierPaymentOrder::getSupplierOrderNo, supplierOrderId)
                        .eq(SupplierPaymentOrder::getId, supplierOrder.getId())
                        .eq(SupplierPaymentOrder::getStatus, 0)
                        .update();
                if (updateSupplierPaymentOrder) {
                    boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                            .set(TicketOrder::getStatus, 1)
                            .set(TicketOrder::getLastUpdateTime, new Date())
                            .eq(TicketOrder::getId, supplierOrder.getOrderId())
                            .eq(TicketOrder::getStatus, 0)
                            .update();
                    if (updateTicketOrder) {
                        log.info("update TicketOrder[orderId:{}] status:4 success.", supplierOrder.getOrderId());
                    }
                }
                OrderLogDTO supplierCreateOrderLog = new OrderLogDTO();
                supplierCreateOrderLog.setOrderId(orderId);
                supplierCreateOrderLog.setType(TicketOrderLogTypeEnum.supplierCreateOrderSuccess.getCode());
                supplierCreateOrderLog.setResult("供应商下单成功,orderId:" + supplierOrderId);
                supplierCreateOrderLog.setBizOrderId(supplierOrder.getId());
                logBizService.saveOrderLog(supplierCreateOrderLog);
                log.info("供应商下单成功");
                return SingleResponse.buildSuccess();
            }
        } else {
            supplierPaymentOrderService.lambdaUpdate()
                    .set(SupplierPaymentOrder::getStatus, 1)
                    .set(SupplierPaymentOrder::getFailureCode, channelResult.getCode())
                    .set(SupplierPaymentOrder::getFailureMsg, channelResult.getMsg())
                    .eq(SupplierPaymentOrder::getId, supplierOrder.getId())
                    .eq(SupplierPaymentOrder::getStatus, 0)
                    .update();
            log.error("供应商下单失败,SupplierPaymentOrder[id:{}],code:{}.msg:{}", supplierOrder.getId(), channelResult.getCode(), channelResult.getMsg());

            boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                    .set(TicketOrder::getStatus, TicketOrderEnum.closed.getCode())
                    .set(TicketOrder::getFailureCode, channelResult.getCode())
                    .set(TicketOrder::getFailureMsg, channelResult.getMsg())
                    .set(TicketOrder::getLastUpdateTime, new Date())
                    .eq(TicketOrder::getId, supplierOrder.getOrderId())
                    .update();
            if (updateTicketOrder) {
                log.info("update TicketOrder[orderId:{}] FailureCode:{},FailureMsg:{}", supplierOrder.getOrderId(), channelResult.getCode(), channelResult.getMsg());

                ProductPriceEveryday productPriceEveryday = productPriceEverydayService.lambdaQuery()
                        .eq(ProductPriceEveryday::getScenicspotProductId, ticketOrder.getProductId())
                        .eq(ProductPriceEveryday::getStatus, 0)
                        .eq(ProductPriceEveryday::getDate, ticketOrder.getVisitDate())
                        .one();
                if (Objects.nonNull(productPriceEveryday)) {
                    log.info(">>>> decrease Stock");
                    if (!productPriceEveryday.getStockNumber().equals(-1L)) {
                        boolean updateStock = productPriceEverydayService
                                .incrementUpdate(ProductPriceEveryday::getStockNumber, ticketOrder.getQuantity())
                                .eq(ProductPriceEveryday::getId, productPriceEveryday.getId())
                                .update();
                        Assert.isTrue(updateStock);
                        log.info("increment Stock success.");

                        if (productPriceEveryday.getStockModel().equals(1) && Objects.nonNull(ticketOrder.getSessionId())) {
                            boolean updateSessionStock = productDayTimeService
                                    .incrementUpdate(ProductDayTime::getStockNumber, ticketOrder.getQuantity())
                                    .eq(ProductDayTime::getId, ticketOrder.getSessionId())
                                    .update();
                            Assert.isTrue(updateSessionStock);
                            log.info("increment Session Stock success.");
                        }
                    }
                }
            }

            OrderLogDTO orderFailLog = new OrderLogDTO();
            orderFailLog.setOrderId(orderId);
            orderFailLog.setType(TicketOrderLogTypeEnum.supplierCreateOrderSuccess.getCode());
            orderFailLog.setResult("供应商下单失败(" + channelResult.getMsg() + ")");
            orderFailLog.setBizOrderId(supplierOrder.getId());
            logBizService.saveOrderLog(orderFailLog);
        }
        return SingleResponse.buildFailure("9856", "下单失败");
    }

    private B2bCreateOrderReq getB2bCreateOrderReq(TicketOrder ticketOrder, MerchantSupplierConfig supplierConfig, SupplierPaymentOrder supplierOrder) {
        B2bCreateOrderReq req = new B2bCreateOrderReq();
        B2bConfigModel config = new B2bConfigModel();
        config.setApiUrl(supplierConfig.getApiUrl());
        config.setPartnerId(supplierConfig.getChannelMerchantId());
        config.setKey(supplierConfig.getChannelSecretKey());
        req.setConfig(config);

        B2bCreateOrderReqModel model = new B2bCreateOrderReqModel();
        model.setOrderQuantity(ticketOrder.getQuantity());
        model.setOrderRemark(ticketOrder.getProductName());
        model.setProductId(ticketOrder.getSupplierProductCode());
        model.setVisitDate(DateUtil.format(ticketOrder.getVisitDate(), "yyyy-MM-dd"));
        model.setOriginOrderId(supplierOrder.getPaymentNo() + "");
        Long sessionId = ticketOrder.getSessionId();
        if (Objects.nonNull(sessionId)) {
            ProductDayTime productDayTime = productDayTimeService.getById(sessionId);
            if (Objects.nonNull(productDayTime)) {
                model.setBeginPlayTime(DateUtil.format(productDayTime.getStart(),"HH:mm"));
                model.setEndPlayTime(DateUtil.format(productDayTime.getEnd(),"HH:mm"));
            }
        }

        ContactPersonInfo contactPerson = new ContactPersonInfo();
        contactPerson.setMobile(ticketOrder.getLinkMobile());
        contactPerson.setIdCard(ticketOrder.getLinkIdCard());
        contactPerson.setName(ticketOrder.getLinkName());
        model.setContactPerson(contactPerson);

        List<VisitPersonInfo> visitPersonItems = touristInfoService.lambdaQuery().eq(TouristInfo::getOrderId, ticketOrder.getId())
                .list()
                .stream()
                .map(x -> {
                    VisitPersonInfo visitPersonInfo = new VisitPersonInfo();
                    visitPersonInfo.setMobile(x.getMobile());
                    visitPersonInfo.setIdCard(x.getIdCard());
                    visitPersonInfo.setName(x.getName());
                    return visitPersonInfo;
                }).collect(Collectors.toList());
        model.setVisitPerson(visitPersonItems);
        req.setModel(model);
        return req;
    }
}
