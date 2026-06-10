package com.nuwa.infrastructure.ticket.database.manager;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataOrder;
import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataProduct;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataOrderService;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataProductService;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
import com.nuwa.infrastructure.ticket.database.member.service.ThirdUserService;
import com.nuwa.infrastructure.ticket.database.order.entity.OrderVoucher;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.service.OrderVoucherService;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.infrastructure.ticket.lock.DistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AlipayDataManager {

    @Autowired
    private AlipayDataProductService alipayDataProductService;

    @Autowired
    private AlipayDataOrderService alipayDataOrderService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private ThirdUserService thirdUserService;

    @Autowired
    private OrderVoucherService orderVoucherService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @Autowired
    private DistributedLocker distributedLocker;

    @Async
    public void orderUpdate(Long orderId) {
        log.info(">>>> orderUpdate[id:{}]", orderId);
        try {
            String lockKey = "orderUpdate:orderId:" + orderId;
            try {
                boolean getLock = distributedLocker.tryLock(lockKey, TimeUnit.SECONDS, 3, 2);
                if (!getLock) {
                    log.error("获取锁失败,key:{}", lockKey);
                }

                TicketOrder ticketOrder = ticketOrderService.getById(orderId);
                if (!ticketOrder.getClientSrc().equalsIgnoreCase("alipay_mini_template")) {
                    return;
                }
                Long srcAppId = ticketOrder.getSrcAppId();
                MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(srcAppId);
                PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, merchantAppBaseConf.getAppTemplateId()).one();
                if (!psTemplateInfo.getTemplateId().equalsIgnoreCase("2021003129609036")) {
                    log.error("目前支付宝同步数据只支持小程序模板[{}]", "2021003129609036");
                    return;
                }
                String appId = merchantAppBaseConf.getOutAppId();
                Long scenicspotId = ticketOrder.getScenicspotId();
                Long productId = ticketOrder.getProductId();
                ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(productId);

                AlipayDataProduct alipayDataProduct = alipayDataProductService.lambdaQuery()
                        .eq(AlipayDataProduct::getOuterScenicId, scenicspotId)
                        .eq(AlipayDataProduct::getAlipayAppId, appId)
                        .eq(AlipayDataProduct::getProductId, productId)
                        .one();

                PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                        .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                        .one();

                if (Objects.isNull(alipayDataProduct)) {
                    AlipayDataProduct dataProduct = new AlipayDataProduct();
                    dataProduct.setProductId(productId.intValue());
                    dataProduct.setAlipayAppId(appId);
                    dataProduct.setProductName(scenicspotProduct.getName());
                    dataProduct.setGmtCreate(new Date());
                    dataProduct.setIsvId(alipayConfig.getIsvId());
                    dataProduct.setStatus("init");
                    dataProduct.setOuterScenicId(scenicspotId + "");
                    dataProduct.setTicketType("NORMAL");
                    dataProduct.setSourceSystem("ZHONGZHIYOUXIN");
                    boolean insert = dataProduct.insert();
                    if (insert) {
                        log.info("save AlipayDataProduct[id:{}] success", dataProduct.getId());
                    }
                }
                AlipayDataOrder alipayDataOrder = alipayDataOrderService
                        .lambdaQuery().eq(AlipayDataOrder::getOuterOrderId, ticketOrder.getId())
                        .one();
                if (Objects.isNull(alipayDataOrder)) {
                    ThirdUser thirdUser = thirdUserService.lambdaQuery()
                            .eq(ThirdUser::getUserId, ticketOrder.getUserId())
                            .eq(ThirdUser::getOutAppId, appId)
                            .one();
                    AlipayDataOrder dataOrder = new AlipayDataOrder();
                    dataOrder.setAlipayOrderId(null);
                    dataOrder.setAlipayAppId(appId);
                    dataOrder.setOuterScenicId(scenicspotId + "");
                    dataOrder.setAmount(ticketOrder.getAmount() + "");
                    dataOrder.setProductId(ticketOrder.getProductId().intValue());
                    dataOrder.setOrderLink("alipays://platformapi/startapp?appId=" + appId + "&page=" + URLEncoder.ALL.encode("pages/orderDetails/index?id=" + ticketOrder.getId(), StandardCharsets.UTF_8));
                    dataOrder.setOuterOrderId(orderId + "");
                    dataOrder.setBuyerId(thirdUser.getOpenId());
                    dataOrder.setSourceSystem("ZHONGZHIYOUXIN");
                    dataOrder.setSource("ALIPAY_MINI_APP");
                    dataOrder.setOrderType("TICKET");
                    dataOrder.setVersion(0L);
                    dataOrder.setLastVersion(1L);
                    dataOrder.setLastUpdateTime(new Date());
                    dataOrder.setOrderCreateTime(new Date());
                    dataOrder.setOrderModifiedTime(new Date());
                    dataOrder.setLastUpdateTime(new Date());
                    dataOrder.setOrderStatus(getDataOrderStatus(ticketOrder));
                    dataOrder.setTicketJson(getDataOrderTicketInfo(ticketOrder));
                    boolean insert = dataOrder.insert();
                    if (insert) {
                        log.info("save AlipayDataOrder[id:{}] success", dataOrder.getId());
                    }
                } else {
                    boolean update = alipayDataOrderService.lambdaUpdate()
                            .set(AlipayDataOrder::getOrderStatus, getDataOrderStatus(ticketOrder))
                            .set(AlipayDataOrder::getTicketJson, getDataOrderTicketInfo(ticketOrder))
                            .set(AlipayDataOrder::getLastVersion, alipayDataOrder.getVersion() + 1)
                            .set(AlipayDataOrder::getLastUpdateTime, new Date())
                            .set(AlipayDataOrder::getOrderModifiedTime, new Date())
                            .eq(AlipayDataOrder::getId, alipayDataOrder.getId())
                            .update();
                    if (update) {
                        log.info("update AlipayDataOrder[id:{}] success", alipayDataOrder.getId());
                    }
                }
                log.info("<<<< orderUpdate[id:{}]", orderId);
            } catch (Exception ex) {
                log.error("获取锁失败,key:{}", lockKey);
            } finally {
                distributedLocker.unlock(lockKey);
            }
        } catch (Exception ex) {
            log.error("orderUpdate orderId{} error", orderId, ex);
        }
    }

    public String getDataOrderTicketInfo(TicketOrder order) {
        //创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5
        Integer status = order.getStatus();
        Integer quantity = order.getQuantity();

        if (status.equals(0) || status.equals(1) || status.equals(2)) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("price", order.getAmount());
            jsonObject.putOpt("status", "INIT");
            jsonObject.putOpt("ticket_count", order.getQuantity());
            jsonObject.putOpt("ticket_name", order.getProductName());
            jsonObject.putOpt("ticket_no", order.getId());
            jsonObject.putOpt("ticketType", "NORMAL");
            jsonObject.putOpt("use_start_date", order.getVisitDate());
            jsonObject.putOpt("use_end_date", order.getVisitDate());
            jsonArray.add(jsonObject);
            return JSONUtil.toJsonPrettyStr(jsonArray);
        } else if (status.equals(3)) {
            List<OrderVoucher> orderVouchers = orderVoucherService.lambdaQuery()
                    .eq(OrderVoucher::getOrderId, order.getId())
                    .list();
            int orderVoucherSize = orderVouchers.size();
            int ticketCount = 1;
            if (quantity > orderVoucherSize) {
                ticketCount = quantity;
            }
            int finalTicketCount = ticketCount;
            JSONArray jsonArray = new JSONArray();
            if (orderVouchers.size() == 0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt("price", order.getAmount());
                jsonObject.putOpt("status", "TICKET_SUCCESS");
                jsonObject.putOpt("ticket_count", finalTicketCount);
                jsonObject.putOpt("ticket_name", order.getProductName());
                jsonObject.putOpt("ticket_no", order.getId());
                jsonObject.putOpt("ticketType", "NORMAL");
                jsonObject.putOpt("ticket_use_code", RandomUtil.randomString(15));
                jsonObject.putOpt("use_start_date", order.getVisitDate());
                jsonObject.putOpt("use_end_date", order.getVisitDate());
                jsonArray.add(jsonObject);
            } else {
                orderVouchers.forEach(x -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("price", order.getAmount());
                    jsonObject.putOpt("status", "TICKET_SUCCESS");
                    jsonObject.putOpt("ticket_count", finalTicketCount);
                    jsonObject.putOpt("ticket_name", order.getProductName());
                    jsonObject.putOpt("ticket_no", x.getId());
                    jsonObject.putOpt("ticketType", "NORMAL");
                    jsonObject.putOpt("ticket_use_code", x.getVoucherCode());
                    jsonObject.putOpt("use_start_date", order.getVisitDate());
                    jsonObject.putOpt("use_end_date", order.getVisitDate());
                    jsonArray.add(jsonObject);
                });
            }

            return JSONUtil.toJsonPrettyStr(jsonArray);
        } else if (status.equals(4)) {
            List<OrderVoucher> orderVouchers = orderVoucherService.lambdaQuery()
                    .eq(OrderVoucher::getOrderId, order.getId())
                    .list();
            int orderVoucherSize = orderVouchers.size();
            int ticketCount = 1;
            if (quantity > orderVoucherSize) {
                ticketCount = quantity;
            }
            int finalTicketCount = ticketCount;
            JSONArray jsonArray = new JSONArray();
            orderVouchers.forEach(x -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt("price", order.getAmount());
                if (order.getRefundedQuantity() > 0) {
                    jsonObject.putOpt("status", "REFUND_SUCCESS");
                } else if (order.getAlreadyConsumeQuantity() > 0) {
                    jsonObject.putOpt("status", "USED");
                }
                jsonObject.putOpt("ticket_count", finalTicketCount);
                jsonObject.putOpt("ticket_name", order.getProductName());
                jsonObject.putOpt("ticket_no", order.getId());
                jsonObject.putOpt("ticketType", "NORMAL");
                jsonObject.putOpt("ticket_use_code", x.getVoucherCode());
                jsonObject.putOpt("use_start_date", order.getVisitDate());
                jsonObject.putOpt("use_end_date", order.getVisitDate());
                jsonArray.add(jsonObject);
            });
            return JSONUtil.toJsonPrettyStr(jsonArray);
        } else if (status.equals(5)) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("price", order.getAmount());
            jsonObject.putOpt("status", "CLOSED");
            jsonObject.putOpt("ticket_count", order.getQuantity());
            jsonObject.putOpt("ticket_name", order.getProductName());
            jsonObject.putOpt("ticket_no", order.getId());
            jsonObject.putOpt("ticketType", "NORMAL");
            jsonObject.putOpt("use_start_date", order.getVisitDate());
            jsonObject.putOpt("use_end_date", order.getVisitDate());
            jsonArray.add(jsonObject);
            return JSONUtil.toJsonPrettyStr(jsonArray);
        }
        return "[]";
    }

    public String getDataOrderStatus(TicketOrder order) {
        //创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5
        // 订单状态 CHECK_WAITING("CHECK_WAITING",待检票)，
        // CHECKED("CHECKED",已检票)，
        // FINISHED("FINISHED",已完结)，
        // TICKET_RUNNING("TICKET_RUNNING",出票中)，
        // PAY_WAITING("PAY_WAITING",待付款)，
        // REFUND_AUDITING("REFUND_AUDITING",退单审核中)，
        // REFUND_SUCCESS("REFUND_SUCCESS",已退单)，
        // CLOSED("CLOSED",已关闭),
        // REFUND_RUNNING("REFUND_RUNNING","退单中")
        Integer refundingQuantity = order.getRefundingQuantity();
        if (refundingQuantity > 0) {
            return "REFUND_AUDITING";
        }
        Integer status = order.getStatus();
        if (status.equals(0) || status.equals(1)) {
            return "PAY_WAITING";
        } else if (status.equals(2)) {
            return "TICKET_RUNNING";
        } else if (status.equals(3)) {
            return "CHECK_WAITING";
        } else if (status.equals(4)) {
            if (order.getRefundedQuantity() > 0) {
                return "REFUND_SUCCESS";
            } else if (order.getAlreadyConsumeQuantity() > 0) {
                return "CHECKED";
            }
        } else if (status.equals(5)) {
            return "CLOSED";
        }
        return "CLOSED";
    }
}
