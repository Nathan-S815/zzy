package com.nuwa.infrastructure.ticket.database.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.exception.Assert;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.supper.NumberFiledUpdate;
import com.nuwa.infrastructure.ticket.database.manager.dto.*;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductDayTime;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductDayTimeService;
import com.nuwa.infrastructure.ticket.database.product.service.ProductPriceEverydayService;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.infrastructure.ticket.enums.PushSwitchEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.service.OrderNoService;
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
 * 交易事务管理
 *
 * @author hy
 */
@Slf4j
@Service
public class TradeTransactionManager {
    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private ChannelRefundOrderService channelRefundOrderService;

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private SupplierRefundOrderService supplierRefundOrderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderNoService orderNoService;

    @Autowired
    private OrderVoucherService orderVoucherService;

    /**
     * 下单
     *
     * @param dto PlaceOrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public Long placeOrder(PlaceOrderDTO dto) {
        log.info(">>>> placeOrder dto->[{}]", dto);
        ScenicspotProduct product = dto.getProduct();
        ProductPriceEveryday priceEveryday = dto.getPriceEveryday();
        MerchantSupplierConfig supplierConfig = dto.getSupplierConfig();

        Date current = new Date();
        UserAware userAware = dto.getUserAware();

        BigDecimal totalAmount = priceEveryday.getSalePrice().multiply(new BigDecimal(dto.getQuantity()));

        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setOrderNo(orderNoService.snowflakeId());
        ticketOrder.setQuantity(dto.getQuantity());
        ticketOrder.setCreateById(userAware.getUserId() + "");
        ticketOrder.setCreateByName(userAware.getUserName());
        ticketOrder.setUserId(userAware.getUserId());
        ticketOrder.setClientIp(userAware.getHostIp());
        ticketOrder.setCreateTime(current);
        ticketOrder.setUnitPrice(priceEveryday.getSalePrice());
        ticketOrder.setAmount(totalAmount);
        ticketOrder.setRealAmount(totalAmount);
        ticketOrder.setStatus(TicketOrderEnum.created.getCode());
        ticketOrder.setProductId(product.getId());
        ticketOrder.setProductName(product.getName());
        ticketOrder.setAlreadyConsumeQuantity(0);
        ticketOrder.setAvailableConsumeQuantity(0);
        ticketOrder.setClientIp(dto.getClientIp());
        ticketOrder.setLinkIdCard(dto.getLinkIdCard());
        ticketOrder.setLinkMobile(dto.getLinkMobile());
        ticketOrder.setLinkName(dto.getLinkName());
        ticketOrder.setVisitDate(dto.getVisitDate());
        ticketOrder.setTimeExpire(DateUtil.offsetMinute(current, 30));
        ticketOrder.setSupplierId(supplierConfig.getId());
        ticketOrder.setSupplierProductCode(product.getSupplierProductCode());
        ticketOrder.setSupplierChannelType(1);
        ticketOrder.setProductType(product.getTicketKind());
        ticketOrder.setScenicspotId(product.getScenicspotId());
        ticketOrder.setMchId(userAware.getMchId());
        ticketOrder.setSupplierMerchantId(product.getMerchantId());
        ticketOrder.setSnapshootVersion(product.getVersion().intValue());
        ticketOrder.setSrcAppId(dto.getSrcAppId());
        ticketOrder.setSrcAppName(dto.getSrcAppName());
        ticketOrder.setSessionId(dto.getSessionId());
        ticketOrder.setClientSrc(dto.getClientSrc());
        if (!StrUtil.isNullOrUndefined(dto.getPromoterUserId())) {
            ticketOrder.setPromoterCode(dto.getPromoterUserId());
            List<Member> listMembers = memberService.lambdaQuery().eq(Member::getShareCode, dto.getPromoterUserId()).last("limit 1").list();
            if (!listMembers.isEmpty()) {
                Integer userId = listMembers.get(0).getUserId();
                ticketOrder.setPromoterUserId(userId.longValue());
            }
        }
        boolean ticketOrderSave = ticketOrder.insert();
        Assert.isTrue(ticketOrderSave);
        log.info("save TicketOrder[id:{}] success.", ticketOrder.getId());

        //todo 库存扣减
        if (!priceEveryday.getStockNumber().equals(-1L)) {
            boolean updateStock = productPriceEverydayService
                    .decreaseUpdate(ProductPriceEveryday::getStockNumber, dto.getQuantity())
                    .eq(ProductPriceEveryday::getId, priceEveryday.getId())
                    .update();
            Assert.isTrue(updateStock);
            log.info("decrease Stock success.");

            if (priceEveryday.getStockModel().equals(1) && Objects.nonNull(ticketOrder.getSessionId())) {
                boolean updateSessionStock = productDayTimeService
                        .decreaseUpdate(ProductDayTime::getStockNumber, dto.getQuantity())
                        .eq(ProductDayTime::getId, ticketOrder.getSessionId())
                        .update();
                Assert.isTrue(updateSessionStock);
                log.info("decrease Session Stock success.");
            }
        }

        List<TouristInfo> batchTouristItems = dto.getTouristList().stream().map(x -> {
            TouristInfo touristInfo = new TouristInfo();
            BeanUtils.copyProperties(x, touristInfo);
            touristInfo.setCreateTime(current);
            touristInfo.setUserId(userAware.getUserId());
            touristInfo.setOrderId(ticketOrder.getId());
            return touristInfo;
        }).collect(Collectors.toList());
        boolean saveTourist = touristInfoService.saveBatch(batchTouristItems);
        Assert.isTrue(saveTourist);
        log.info("save TouristInfo[size:{}] success.", batchTouristItems.size());

        log.info("<<<< placeOrder");
        return ticketOrder.getId();
    }

    /**
     * 支付通道支付成功
     *
     * @param dto ChannelPaySuccessDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void channelPaySuccess(ChannelPaySuccessDTO dto) {
        log.info(">>>> channelPaySuccess dto->[{}]", dto);
        Date current = new Date();

        ChannelPaymentOrder channelPaymentOrder = dto.getChannelPaymentOrder();
        Assert.notNull(channelPaymentOrder);

        TicketOrder ticketOrder = ticketOrderService.getById(channelPaymentOrder.getOrderId());
        Assert.notNull(ticketOrder);

        boolean updateChannelPaymentOrder = channelPaymentOrderService.lambdaUpdate()
                .set(ChannelPaymentOrder::getStatus, 2)
                .set(ChannelPaymentOrder::getTimePaid, dto.getTimePaid())
                .set(ChannelPaymentOrder::getLastUpdateTime, current)
                .eq(ChannelPaymentOrder::getId, channelPaymentOrder.getId())
                .eq(ChannelPaymentOrder::getStatus, 1)
                .update();
        Assert.isTrue(updateChannelPaymentOrder, "修改ChannelPaymentOrder[" + channelPaymentOrder.getId() + "]");
        log.info("update ChannelPaymentOrder[id:{}] [status->已支付] success", channelPaymentOrder.getId());

        boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                .set(TicketOrder::getStatus, 2)
                .set(TicketOrder::getTimePaid, dto.getTimePaid())
                .set(TicketOrder::getLastUpdateTime, new Date())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .eq(TicketOrder::getStatus, 1)
                .update();
        Assert.isTrue(updateTicketOrder, "修改TicketOrder[id:" + ticketOrder.getId() + "]");
        log.info("update TicketOrder[id:{}] [status->待出票]  success.", ticketOrder.getId());

        boolean updateSupplierPaymentOrder = supplierPaymentOrderService.lambdaUpdate()
                .set(SupplierPaymentOrder::getStatus, 2)
                .set(SupplierPaymentOrder::getPushStatus, PushSwitchEnum.on.getCode())
                .set(SupplierPaymentOrder::getTimeNext, new Date())
                .set(SupplierPaymentOrder::getLastUpdateTime, current)
                .eq(SupplierPaymentOrder::getOrderId, channelPaymentOrder.getOrderId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder, "修改SupplierPaymentOrder[orderId:" + channelPaymentOrder.getOrderId() + "] [status->出票中,pushStatus->on]");
        log.info("update SupplierPaymentOrder[orderId:{}] [status->出票中,pushStatus->on] success", channelPaymentOrder.getOrderId());

        scenicspotProductService.incrementUpdate(ScenicspotProduct::getSellMoney, ticketOrder.getAmount())
                .eq(ScenicspotProduct::getId, ticketOrder.getProductId())
                .update();
        log.info("product[id:{}] increment sellMoney success", ticketOrder.getProductId());

        scenicspotProductService.incrementUpdate(ScenicspotProduct::getSellNumber, ticketOrder.getQuantity())
                .eq(ScenicspotProduct::getId, ticketOrder.getProductId())
                .update();
        log.info("product[id:{}] increment sellNumber success", ticketOrder.getProductId());

        scenicspotProductService.incrementUpdate(ScenicspotProduct::getSellOrder, 1)
                .eq(ScenicspotProduct::getId, ticketOrder.getProductId())
                .update();
        log.info("product[id:{}] increment sellOrder success", ticketOrder.getProductId());

        if ("douyin_mini".equalsIgnoreCase(channelPaymentOrder.getPayType())) {
            log.info("save settle order");
            DouyinSettleOrder douyinSettleOrder = new DouyinSettleOrder();
            douyinSettleOrder.setOrderId(ticketOrder.getId());
            douyinSettleOrder.setOrderNo(ticketOrder.getOrderNo());
            douyinSettleOrder.setSettleAmount(channelPaymentOrder.getAmount());
            douyinSettleOrder.setStatus(1);
            douyinSettleOrder.setOutOrderNo(orderNoService.snowflakeId());
            douyinSettleOrder.setChannelPaymentId(channelPaymentOrder.getId());
            douyinSettleOrder.setChannelPaymentOrderNo(channelPaymentOrder.getMchPayOrderNo());
            douyinSettleOrder.setTimeExecuteSettle(DateUtil.offsetDay(current, 8));
            douyinSettleOrder.setCreateTime(current);
            boolean insert = douyinSettleOrder.insert();
            Assert.isTrue(insert, "save DouyinSettleOrder orderId:" + ticketOrder.getId() + " 失败");
            log.info("save DouyinSettleOrder[{}] orderId:{} success", douyinSettleOrder.getId(), ticketOrder.getId());
        }

        log.info("<<<< channelPaySuccess");
    }

    /**
     * 支付通道退款成功
     *
     * @param dto ChannelRefundSuccessDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void channelRefundSuccess(ChannelRefundSuccessDTO dto) {
        log.info(">>>> channelRefundSuccess[{}]", dto);

        Date current = new Date();
        ChannelRefundOrder channelRefundOrder = dto.getChannelRefundOrder();
        Assert.notNull(channelRefundOrder);
        TicketOrder ticketOrder = ticketOrderService.getById(channelRefundOrder.getOrderId());
        TicketRefund ticketRefund = ticketRefundService.getById(channelRefundOrder.getRefundOrderId());

        boolean updateChannelRefundOrder = channelRefundOrderService.lambdaUpdate()
                .set(ChannelRefundOrder::getStatus, 10)
                .set(ChannelRefundOrder::getTimeRefund, dto.getTimeRefund())
                .set(ChannelRefundOrder::getLastUpdateTime, current)
                .eq(ChannelRefundOrder::getId, channelRefundOrder.getId())
                .update();
        Assert.isTrue(updateChannelRefundOrder);
        log.info("update ChannelRefundOrder[id:{}] (status->10) success.", channelRefundOrder.getId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getStatus, 3)
                .set(TicketRefund::getTimeRefund, dto.getTimeRefund())
                .set(TicketRefund::getLastUpdateTime, current)
                .eq(TicketRefund::getId, channelRefundOrder.getRefundOrderId())
                .update();
        Assert.isTrue(updateTicketRefund);
        log.info("update TicketRefund[id:{}] (status->3)  success.", channelRefundOrder.getRefundOrderId());

        if ((ticketOrder.getAvailableConsumeQuantity() > 0)) {
            boolean updateTicketOrderAvailableConsumeQuantity = ticketOrderService.decreaseUpdate(TicketOrder::getAvailableConsumeQuantity, ticketRefund.getQuantity())
                    .eq(TicketOrder::getId, ticketOrder.getId())
                    .update();
            Assert.isTrue(updateTicketOrderAvailableConsumeQuantity, "减少可核销数量失败");
            log.info("decreaseUpdate TicketOrder[id:{}] AvailableConsumeQuantity success.", ticketRefund.getId());
        }

        boolean updateNum = ticketOrderService.numberFieldUpdate(
                        new NumberFiledUpdate<TicketOrder>(TicketOrder::getRefundingQuantity, ticketRefund.getQuantity(), false),
                        new NumberFiledUpdate<TicketOrder>(TicketOrder::getRefundedQuantity, ticketRefund.getQuantity(), true),
                        new NumberFiledUpdate<TicketOrder>(TicketOrder::getRefundedAmount, ticketRefund.getRealAmount(), true))
                .eq(TicketOrder::getId, ticketOrder.getId()).update();
        Assert.isTrue(updateNum, "TicketOrder[id:{" + ticketOrder.getId() + "}] --RefundingQuantity,++RefundedQuantity,++RefundedAmount");
        log.info("TicketOrder[id:{}] --RefundingQuantity,++RefundedQuantity,++RefundedAmount success", ticketOrder.getId());

        Integer refundedQuantity = ticketOrder.getRefundedQuantity();
        Integer refundQuantity = ticketRefund.getQuantity();
        Integer quantity = ticketOrder.getQuantity();
        Integer alreadyConsumeQuantity = ticketOrder.getAlreadyConsumeQuantity();
        boolean isComplete = quantity.equals(refundedQuantity + refundQuantity + alreadyConsumeQuantity);

        boolean updateTicketOrderComplete = ticketOrderService.lambdaUpdate()
                .set(isComplete, TicketOrder::getStatus, TicketOrderEnum.completed.getCode())
                .set(TicketOrder::getLastUpdateTime, current)
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderComplete, "修改订单完成状态失败");

        ProductPriceEveryday productPriceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, ticketOrder.getProductId())
                .eq(ProductPriceEveryday::getStatus, 0)
                .eq(ProductPriceEveryday::getDate, ticketOrder.getVisitDate())
                .one();
        if (Objects.nonNull(productPriceEveryday)) {
            if (!productPriceEveryday.getStockNumber().equals(-1L)) {
                boolean updateStock = productPriceEverydayService
                        .incrementUpdate(ProductPriceEveryday::getStockNumber, ticketRefund.getQuantity())
                        .eq(ProductPriceEveryday::getId, productPriceEveryday.getId())
                        .update();
                Assert.isTrue(updateStock);
                log.info("increment Stock success.");

                if (productPriceEveryday.getStockModel().equals(1) && Objects.nonNull(ticketOrder.getSessionId())) {
                    boolean updateSessionStock = productDayTimeService
                            .incrementUpdate(ProductDayTime::getStockNumber, ticketRefund.getQuantity())
                            .eq(ProductDayTime::getId, ticketOrder.getSessionId())
                            .update();
                    Assert.isTrue(updateSessionStock);
                    log.info("increment Session Stock success.");
                }
            }


        }

        log.info("<<<< channelRefundSuccess");
    }

    /**
     * 支付通道退款受理成功
     *
     * @param dto ChannelRefundAcceptSuccessDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void channelRefundAcceptSuccess(ChannelRefundAcceptSuccessDTO dto) {
        ChannelRefundOrder channelRefundOrder = dto.getChannelRefundOrder();
        Date current = new Date();

        boolean updateChannelRefundOrder = channelRefundOrderService.lambdaUpdate()
                .set(ChannelRefundOrder::getStatus, 2)
                .set(ChannelRefundOrder::getChannelRefundNo, dto.getChannelRefundOrderNo())
                .set(ChannelRefundOrder::getLastUpdateTime, current)
                .eq(ChannelRefundOrder::getId, channelRefundOrder.getId())
                .update();
        Assert.isTrue(updateChannelRefundOrder);
        log.info("update ChannelRefundOrder status->10 [id:{}] success.", channelRefundOrder.getId());
    }

    /**
     * 支付通道退款受理失败
     *
     * @param dto PaySuccessForChannelDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void channelRefundAcceptFailed(ChannelRefundAcceptFailedDTO dto) {
        ChannelRefundOrder channelRefundOrder = dto.getChannelRefundOrder();
        TicketOrder ticketOrder = ticketOrderService.getById(channelRefundOrder.getOrderId());
        TicketRefund ticketRefund = ticketRefundService.getById(channelRefundOrder.getRefundOrderId());

        Date current = new Date();
        boolean updateChannelRefundOrder = channelRefundOrderService.lambdaUpdate()
                .set(ChannelRefundOrder::getStatus, 20)
                .set(ChannelRefundOrder::getFailureCode, dto.getErrCode())
                .set(ChannelRefundOrder::getFailureMsg, dto.getErrMsg())
                .set(ChannelRefundOrder::getLastUpdateTime, current)
                .eq(ChannelRefundOrder::getId, channelRefundOrder.getId())
                .update();
        Assert.isTrue(updateChannelRefundOrder);
        log.info("update ChannelRefundOrder status->20 [id:{}] success.", channelRefundOrder.getId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getStatus, 4)
                .set(TicketRefund::getFailureCode, dto.getErrCode())
                .set(TicketRefund::getFailureMsg, dto.getErrMsg())
                .set(TicketRefund::getLastUpdateTime, current)
                .eq(TicketRefund::getId, channelRefundOrder.getRefundOrderId())
                .update();
        Assert.isTrue(updateTicketRefund);
        log.info("update TicketRefund status->4 [orderId:{}] success.", channelRefundOrder.getRefundOrderId());

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.decreaseUpdate(TicketOrder::getRefundingQuantity, ticketRefund.getQuantity())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "减少退款中数量失败");
    }

    /**
     * 供应商支付成功
     *
     * @param dto PaySuccessForSupplierDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void supplierPaySuccess(SupplierPaySuccessDTO dto) {
        SupplierPaymentOrder supplierPaymentOrder = dto.getSupplierPaymentOrder();
        Long orderId = supplierPaymentOrder.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        Date current = new Date();
        boolean updateSupplierPaymentOrder = supplierPaymentOrderService.lambdaUpdate()
                .set(SupplierPaymentOrder::getStatus, 4)
                .set(SupplierPaymentOrder::getPushStatus, 0)
                .set(SupplierPaymentOrder::getRetryTimes, dto.getTimes())
                .set(SupplierPaymentOrder::getTimeNext, new Date())
                .set(SupplierPaymentOrder::getTimePaid, dto.getTimePaid())
                .set(SupplierPaymentOrder::getLastUpdateTime, current)
                .eq(SupplierPaymentOrder::getId, supplierPaymentOrder.getId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder);
        log.info("update SupplierPaymentOrder status->10 [id:{}] success.", supplierPaymentOrder.getId());

       /* OrderVoucher voucher = new OrderVoucher();
        voucher.setOrderId(supplierPaymentOrder.getOrderId());
        voucher.setOrderNo(supplierPaymentOrder.getOrderNo());
        voucher.setUserId(supplierPaymentOrder.getUserId());
        voucher.setVoucherCode(dto.getVoucherCode());
        voucher.setCreateTime(current);
        boolean saveOrderVoucher = voucher.insert();
        Assert.isTrue(saveOrderVoucher);
        log.info("save OrderVoucher [id:{}] success.", voucher.getId());

        boolean updateAvailableConsumeQuantity = ticketOrderService
                .incrementUpdate(TicketOrder::getAvailableConsumeQuantity, ticketOrder.getQuantity())
                .set(TicketOrder::getStatus, TicketOrderEnum.ticketed.getCode())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateAvailableConsumeQuantity, "增加可核销数量失败");*/

    }

    /**
     * 供应商出票成功处理
     *
     * @param dto SupplierRefundPassDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void
    supplierTieketSuccess(SupplierTicketSuccessDTO dto) {
        SupplierPaymentOrder supplierPaymentOrder = dto.getSupplierPaymentOrder();
        Long orderId = supplierPaymentOrder.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        Date current = new Date();

        boolean updateSupplierPaymentOrder = supplierPaymentOrderService.lambdaUpdate()
                .set(SupplierPaymentOrder::getStatus, 10)
                .set(SupplierPaymentOrder::getPushStatus, 0)
                .set(SupplierPaymentOrder::getTimeTicket, current)
                .set(SupplierPaymentOrder::getLastUpdateTime, current)
                .eq(SupplierPaymentOrder::getStatus, 4)
                .eq(SupplierPaymentOrder::getId, supplierPaymentOrder.getId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder);
        log.info("update SupplierPaymentOrder status->10 [id:{}] success.", supplierPaymentOrder.getId());

        List<SupplierTicketSuccessDTO.VoucherItem> voucherItems = dto.getVoucherItems();
        List<OrderVoucher> orderVoucherList = voucherItems.stream().map(x -> {
            OrderVoucher voucher = new OrderVoucher();
            voucher.setOrderId(supplierPaymentOrder.getOrderId());
            voucher.setOrderNo(supplierPaymentOrder.getOrderNo());
            voucher.setUserId(supplierPaymentOrder.getUserId());
            voucher.setVoucherCode(x.getVoucherCode());
            voucher.setIdCard(x.getIDCardNo());
            voucher.setMobile(x.getMobile());
            voucher.setName(x.getName());
            voucher.setCreateTime(current);
            return voucher;
        }).collect(Collectors.toList());

        if (orderVoucherList.size() > 0) {
            boolean saveOrderVoucher = orderVoucherService.saveBatch(orderVoucherList);
            Assert.isTrue(saveOrderVoucher);
            log.info("save OrderVoucher [{}] success.", JSONUtil.toJsonPrettyStr(orderVoucherList));
        }

        boolean updateAvailableConsumeQuantity = ticketOrderService
                .incrementUpdate(TicketOrder::getAvailableConsumeQuantity, ticketOrder.getQuantity())
                .set(TicketOrder::getStatus, TicketOrderEnum.ticketed.getCode())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .eq(TicketOrder::getStatus, TicketOrderEnum.ticketing.getCode())
                .update();
        Assert.isTrue(updateAvailableConsumeQuantity, "增加可核销数量失败");

    }

    @Transactional(rollbackFor = Exception.class)
    public void supplierTieketFailed(SupplierTicketFailedDTO dto) {
        SupplierPaymentOrder supplierPaymentOrder = dto.getSupplierPaymentOrder();
        Long orderId = supplierPaymentOrder.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        Date current = new Date();

        boolean updateSupplierPaymentOrder = supplierPaymentOrderService.lambdaUpdate()
                .set(SupplierPaymentOrder::getStatus, 5)
                .set(SupplierPaymentOrder::getPushStatus, 0)
                .set(SupplierPaymentOrder::getTimeTicket, current)
                .set(SupplierPaymentOrder::getLastUpdateTime, current)
                .eq(SupplierPaymentOrder::getStatus, 4)
                .eq(SupplierPaymentOrder::getId, supplierPaymentOrder.getId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder);
        log.info("update SupplierPaymentOrder status->5 [id:{}] success.", supplierPaymentOrder.getId());

    }

    /**
     * 供应商退款成功处理
     *
     * @param dto SupplierRefundPassDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void supplierRefundPass(SupplierRefundPassDTO dto) {
        SupplierRefundOrder supplierRefundOrder = dto.getSupplierRefundOrder();
        Long refundOrderId = supplierRefundOrder.getTicketRefundId();
        TicketRefund ticketRefund = ticketRefundService.getById(refundOrderId);
        TicketOrder ticketOrder = ticketOrderService.getById(supplierRefundOrder.getOrderId());

        Date current = new Date();
        boolean updateSupplierPaymentOrder = supplierRefundOrderService.lambdaUpdate()
                .set(SupplierRefundOrder::getAuditStatus, 10)
                .set(SupplierRefundOrder::getPushStatus, PushSwitchEnum.off.getCode())
                .set(SupplierRefundOrder::getTimePaid, dto.getTimeRefund())
                .set(SupplierRefundOrder::getLastUpdateTime, current)
                .eq(SupplierRefundOrder::getId, supplierRefundOrder.getId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder, "update SupplierRefundOrder[" + supplierRefundOrder.getId() + "] status->10");
        log.info("update SupplierRefundOrder status->10 [id:{}] success.", supplierRefundOrder.getId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getAuditStatus, 10)
                .set(TicketRefund::getTimeAudit, current)
                .set(TicketRefund::getFailureMsg, "供应商同意退款")
                .set(TicketRefund::getLastUpdateTime, current)
                .eq(TicketRefund::getId, refundOrderId)
                .update();
        Assert.isTrue(updateTicketRefund);
        log.info("update TicketRefund status->10 [id:{}] success.", refundOrderId);

        ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.getById(ticketOrder.getChannelPaymentOrderId());

        Integer quantity = ticketOrder.getQuantity();
        Integer alreadyConsumeQuantity = ticketOrder.getAlreadyConsumeQuantity();
        Integer refundedQuantity = ticketOrder.getRefundedQuantity();
        if (quantity > alreadyConsumeQuantity + refundedQuantity) {
            ChannelRefundOrder channelRefundOrder = new ChannelRefundOrder();
            channelRefundOrder.setMchRefundNo(orderNoService.snowflakeId());
            channelRefundOrder.setUserId(ticketRefund.getUserId());
            channelRefundOrder.setCreateTime(new Date());
            channelRefundOrder.setCreateByName(ticketOrder.getCreateByName());
            channelRefundOrder.setCreateById(ticketOrder.getCreateById());
            channelRefundOrder.setAmount(ticketRefund.getAmount());
            channelRefundOrder.setRefundOrderId(ticketRefund.getId());
            channelRefundOrder.setOrderId(ticketRefund.getOrderId());
            channelRefundOrder.setOrderNo(ticketRefund.getOrderNo());
            channelRefundOrder.setChannelPayOrderId(channelPayOrder.getId());
            channelRefundOrder.setChannelPayOrderNo(channelPayOrder.getMchPayOrderNo());
            channelRefundOrder.setStatus(1);
            boolean insert = channelRefundOrder.insert();
            Assert.isTrue(insert, "添加ChannelRefundOrder失败");
        }
    }

    /**
     * 供应商发起退款处理
     *
     * @param dto SupplierRefundPassDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderCancelRet orderCancel(OrderCancelDTO dto) {
        OrderCancelRet refundRet = new OrderCancelRet();
        Date current = new Date();
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery().eq(SupplierPaymentOrder::getSupplierOrderNo, dto.getSupplierOrderNo()).one();
        Long orderId = supplierPaymentOrder.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        log.info("orderCancel ticketOrder:{}", JSONUtil.toJsonPrettyStr(ticketOrder));
        if (ticketOrder.getStatus().equals(3) || ticketOrder.getStatus().equals(4)) {
            int quantity = ticketOrder.getQuantity() - ticketOrder.getAlreadyConsumeQuantity() - ticketOrder.getRefundingQuantity() - ticketOrder.getRefundedQuantity();
            if (quantity > 0) {
                boolean updateTicketOrderRefundingQuantity = ticketOrderService.incrementUpdate(TicketOrder::getRefundingQuantity, ticketOrder.getAvailableConsumeQuantity())
                        .eq(TicketOrder::getId, ticketOrder.getId())
                        .update();
                Assert.isTrue(updateTicketOrderRefundingQuantity, "增加退款中数量失败");
                BigDecimal totalAmount = ticketOrder.getUnitPrice().multiply(new BigDecimal(quantity));

                TicketRefund ticketRefund = new TicketRefund();
                ticketRefund.setOrderNo(ticketOrder.getOrderNo());
                ticketRefund.setOrderId(ticketOrder.getId());
                ticketRefund.setCreateById(ticketOrder.getUserId() + "");
                ticketRefund.setUserId(ticketOrder.getUserId());
                ticketRefund.setCreateTime(current);
                ticketRefund.setAmount(totalAmount);
                ticketRefund.setRealAmount(totalAmount);
                ticketRefund.setStatus(1);
                ticketRefund.setQuantity(quantity);
                ticketRefund.setRefundSerialNo(orderNoService.snowflakeId());
                ticketRefund.setRefundReason("取消订单发起退款");
                ticketRefund.setRefundBizCode(4);
                ticketRefund.setAuditStatus(10);
                ticketRefund.setMchId(ticketOrder.getMchId());
                ticketRefund.setSupplierMerchantId(ticketOrder.getSupplierMerchantId());
                boolean ticketRefundSave = ticketRefund.insert();
                Assert.isTrue(ticketRefundSave);
                log.info("save TicketRefund[id:{}] success.", ticketRefund.getId());

                SupplierRefundOrder supplierRefundOrder = new SupplierRefundOrder();
                supplierRefundOrder.setOrderNo(ticketOrder.getId());
                supplierRefundOrder.setCreateById(ticketOrder.getUserId() + "");
                supplierRefundOrder.setUserId(ticketOrder.getUserId());
                supplierRefundOrder.setCreateTime(current);
                supplierRefundOrder.setOrderNo(ticketOrder.getOrderNo());
                supplierRefundOrder.setOrderId(ticketOrder.getId());
                supplierRefundOrder.setAmount(ticketOrder.getAmount());
                supplierRefundOrder.setPushStatus(PushSwitchEnum.off.getCode());
                supplierRefundOrder.setRetryTimes(0);
                supplierRefundOrder.setTimeNext(DateUtil.offsetSecond(current, 20));
                supplierRefundOrder.setTicketRefundId(ticketRefund.getId());
                supplierRefundOrder.setRefundSerialNo(orderNoService.snowflakeId());
                supplierRefundOrder.setSupplierId(ticketOrder.getSupplierId() + "");
                supplierRefundOrder.setQuantity(ticketRefund.getQuantity());
                supplierRefundOrder.setRefundReason(ticketRefund.getRefundReason());
                supplierRefundOrder.setSupplierPaymentOrderId(supplierPaymentOrder.getId());
                supplierRefundOrder.setAuditStatus(10);

                boolean saveSupplierRefund = supplierRefundOrder.insert();
                Assert.isTrue(saveSupplierRefund);
                log.info("save SupplierRefundOrder[id:{}] success.", supplierRefundOrder.getId());

                ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.getById(ticketOrder.getChannelPaymentOrderId());

                Integer alreadyConsumeQuantity = ticketOrder.getAlreadyConsumeQuantity();
                Integer refundedQuantity = ticketOrder.getRefundedQuantity();
                if (ticketOrder.getQuantity() > alreadyConsumeQuantity + refundedQuantity) {
                    ChannelRefundOrder channelRefundOrder = new ChannelRefundOrder();
                    channelRefundOrder.setMchRefundNo(orderNoService.snowflakeId());
                    channelRefundOrder.setUserId(ticketRefund.getUserId());
                    channelRefundOrder.setCreateTime(new Date());
                    channelRefundOrder.setCreateByName(ticketOrder.getCreateByName());
                    channelRefundOrder.setCreateById(ticketOrder.getCreateById());
                    channelRefundOrder.setAmount(ticketRefund.getAmount());
                    channelRefundOrder.setRefundOrderId(ticketRefund.getId());
                    channelRefundOrder.setOrderId(ticketRefund.getOrderId());
                    channelRefundOrder.setOrderNo(ticketRefund.getOrderNo());
                    channelRefundOrder.setChannelPayOrderId(channelPayOrder.getId());
                    channelRefundOrder.setChannelPayOrderNo(channelPayOrder.getMchPayOrderNo());
                    channelRefundOrder.setStatus(1);
                    boolean insert = channelRefundOrder.insert();
                    Assert.isTrue(insert, "添加ChannelRefundOrder失败");
                }

                supplierPaymentOrderService.lambdaUpdate()
                        .set(SupplierPaymentOrder::getStatus, 30)
                        .set(SupplierPaymentOrder::getLastUpdateTime, new Date())
                        .eq(SupplierPaymentOrder::getId, supplierPaymentOrder.getId())
                        .eq(SupplierPaymentOrder::getStatus, 10)
                        .update();
            }
        }
        return refundRet;
    }

    /**
     * 供应商退款审核拒绝
     *
     * @param dto SupplierRefundRejectDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void supplierRefundReject(SupplierRefundRejectDTO dto) {
        SupplierRefundOrder supplierRefundOrder = dto.getSupplierRefundOrder();
        Long refundOrderId = supplierRefundOrder.getTicketRefundId();
        TicketRefund ticketRefund = ticketRefundService.getById(refundOrderId);

        Date current = new Date();
        boolean updateSupplierPaymentOrder = supplierRefundOrderService.lambdaUpdate()
                .set(SupplierRefundOrder::getAuditStatus, 5)
                .set(SupplierRefundOrder::getPushStatus, PushSwitchEnum.off.getCode())
                .set(SupplierRefundOrder::getRefundReason, dto.getReason())
                .set(SupplierRefundOrder::getLastUpdateTime, current)
                .eq(SupplierRefundOrder::getId, supplierRefundOrder.getId())
                .update();
        Assert.isTrue(updateSupplierPaymentOrder);
        log.info("update SupplierRefundOrder status->10 [id:{}] success.", supplierRefundOrder.getId());

        boolean updateTicketRefund = ticketRefundService.lambdaUpdate()
                .set(TicketRefund::getAuditStatus, 5)
                .set(TicketRefund::getStatus, 4)
                .set(TicketRefund::getTimeAudit, current)
                .set(TicketRefund::getRefundRejectReason, dto.getReason())
                .set(TicketRefund::getLastUpdateTime, current)
                .eq(TicketRefund::getId, refundOrderId)
                .update();
        Assert.isTrue(updateTicketRefund);
        log.info("update TicketRefund status->10 [orderId:{}] success.", refundOrderId);

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.decreaseUpdate(TicketOrder::getRefundingQuantity, ticketRefund.getQuantity())
                .eq(TicketOrder::getId, supplierRefundOrder.getOrderId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "减少退款中数量失败");
    }

    /**
     * 系统退款处理
     *
     * @param dto AutoRefundDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void systemDoRefund(SystemDoRefundDTO dto) {
        log.info(">>>> systemDoRefund dto:{}", dto);
        TicketOrder ticketOrder = ticketOrderService.getById(dto.getOrderId());

        Date current = new Date();

        boolean updateTicketOrderRefundingQuantity = ticketOrderService.incrementUpdate(TicketOrder::getRefundingQuantity, ticketOrder.getQuantity())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateTicketOrderRefundingQuantity, "增加退款中数量失败");

        BigDecimal totalAmount = ticketOrder.getUnitPrice().multiply(new BigDecimal(ticketOrder.getQuantity()));

        TicketRefund ticketRefund = new TicketRefund();
        ticketRefund.setOrderNo(ticketOrder.getOrderNo());
        ticketRefund.setOrderId(ticketOrder.getId());
        ticketRefund.setCreateById(ticketOrder.getUserId() + "");
        ticketRefund.setUserId(ticketOrder.getUserId());
        ticketRefund.setCreateTime(current);
        ticketRefund.setAmount(totalAmount);
        ticketRefund.setRealAmount(totalAmount);
        ticketRefund.setStatus(1);
        ticketRefund.setQuantity(ticketOrder.getQuantity());
        ticketRefund.setRefundSerialNo(orderNoService.snowflakeId());
        ticketRefund.setRefundReason(dto.getReason());
        ticketRefund.setRefundBizCode(dto.getRefundBizCode());
        ticketRefund.setAuditStatus(dto.getAuditStatus());
        ticketRefund.setMchId(ticketOrder.getMchId());
        ticketRefund.setSupplierMerchantId(ticketOrder.getSupplierMerchantId());
        boolean ticketRefundSave = ticketRefund.insert();
        Assert.isTrue(ticketRefundSave);
        log.info("save TicketRefund[id:{}] success.", ticketRefund.getId());

        ChannelPaymentOrder channelPayOrder = channelPaymentOrderService.getById(ticketOrder.getChannelPaymentOrderId());

        ChannelRefundOrder channelRefundOrder = new ChannelRefundOrder();
        channelRefundOrder.setMchRefundNo(orderNoService.snowflakeId());
        channelRefundOrder.setUserId(ticketRefund.getUserId());
        channelRefundOrder.setCreateTime(new Date());
        channelRefundOrder.setCreateByName(ticketOrder.getCreateByName());
        channelRefundOrder.setCreateById(ticketOrder.getUserId() + "");
        channelRefundOrder.setAmount(ticketRefund.getAmount());
        channelRefundOrder.setRefundOrderId(ticketRefund.getId());
        channelRefundOrder.setOrderId(ticketRefund.getOrderId());
        channelRefundOrder.setOrderNo(ticketRefund.getOrderNo());
        channelRefundOrder.setChannelPayOrderId(channelPayOrder.getId());
        channelRefundOrder.setChannelPayOrderNo(channelPayOrder.getMchPayOrderNo());
        channelRefundOrder.setStatus(1);
        boolean insert = channelRefundOrder.insert();
        Assert.isTrue(insert, "添加ChannelRefundOrder失败");

    }

    /**
     * 供应商核销成功处理
     *
     * @param dto SupplierConsumerSuccessDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void supplierConsumerSuccess(SupplierConsumerSuccessDTO dto) {
        SupplierPaymentOrder supplierPaymentOrder = dto.getSupplierPaymentOrder();
        TicketOrder ticketOrder = ticketOrderService.getById(supplierPaymentOrder.getOrderId());
        if (ticketOrder.getAvailableConsumeQuantity().equals(0)) {
            log.warn("TicketOrder[id:{}]已核销,供应商重复回调", ticketOrder.getId());
            return;
        }
        Date current = new Date();

        Integer checkCount = dto.getCheckCount();
        Integer quantity = ticketOrder.getQuantity();
        Integer refundedQuantity = ticketOrder.getRefundedQuantity();
        boolean isComplete = quantity.equals(checkCount + refundedQuantity);

        boolean updateConsumeQuantity = ticketOrderService
                .lambdaUpdate()
                .set(TicketOrder::getAlreadyConsumeQuantity, dto.getCheckCount())
                .set(TicketOrder::getAvailableConsumeQuantity, dto.getTotalCount() - dto.getCheckCount())
                .set(isComplete, TicketOrder::getStatus, TicketOrderEnum.completed.getCode())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        Assert.isTrue(updateConsumeQuantity, "修改核销数量");

        ConsumerRecord consumerRecord = new ConsumerRecord();
        consumerRecord.setCreateTime(current);
        consumerRecord.setOrderId(ticketOrder.getId());
        consumerRecord.setOrderNo(ticketOrder.getOrderNo());
        consumerRecord.setCreateByName(ticketOrder.getCreateById());
        consumerRecord.setConsumerType(1);
        consumerRecord.setTimeConsumer(dto.getTimeConsumer());
        consumerRecord.setQuantity(dto.getCheckCount() - ticketOrder.getAlreadyConsumeQuantity());
        boolean saveConsumerRecord = consumerRecord.insert();
        Assert.isTrue(saveConsumerRecord);
        log.info("save ConsumerRecord[id:{}] success.", consumerRecord.getId());

        if (!StrUtil.isNullOrUndefined(ticketOrder.getPromoterCode())) {
            UserPromoteSettleRecord settleRecord = new UserPromoteSettleRecord();
            settleRecord.setOrderId(ticketOrder.getId());
            settleRecord.setOrderNo(ticketOrder.getOrderNo());
            int consumerQuantity = dto.getCheckCount() - ticketOrder.getAlreadyConsumeQuantity();
            settleRecord.setQuantity(consumerQuantity);
            settleRecord.setAmount(ticketOrder.getUnitPrice().multiply(new BigDecimal(consumerQuantity)));
            settleRecord.setCreateTime(current);
            settleRecord.setStatus(1);
            settleRecord.setProductId(ticketOrder.getProductId());
            settleRecord.setPromoterCode(ticketOrder.getPromoterCode());
            settleRecord.setUserId(ticketOrder.getPromoterUserId());
            settleRecord.setScenicspotId(ticketOrder.getScenicspotId());
            settleRecord.setProductName(ticketOrder.getProductName());
            settleRecord.setVisitDate(ticketOrder.getVisitDate());
            boolean settleRecordSave = settleRecord.insert();
            Assert.isTrue(settleRecordSave);
            log.info("save UserPromoteSettleRecord[id:{}] orderId:{} success.", settleRecord.getId(), ticketOrder.getId());
        }
    }
}
