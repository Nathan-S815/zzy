package com.nuwa.ticket.start.server.client.feign;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayBusinessItemTicketSyncRequest;
import com.alipay.api.request.AlipayBusinessOrderOrderinfoSyncRequest;
import com.alipay.api.response.AlipayBusinessItemTicketSyncResponse;
import com.alipay.api.response.AlipayBusinessOrderOrderinfoSyncResponse;
import com.nuwa.client.ticket.api.alipaydata.AlipayDataClientI;
import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataOrder;
import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataOrderRecord;
import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataProduct;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataOrderService;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataProductService;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TouristInfo;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TouristInfoService;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.infrastructure.ticket.util.AlipayClientWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
public class AlipayDataClientImpl implements AlipayDataClientI {

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private AlipayDataProductService alipayDataProductService;

    @Autowired
    private AlipayDataOrderService alipayDataOrderService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @Override
    public SingleResponse<?> takeAndSyncProduct() {
        List<AlipayDataProduct> list = alipayDataProductService.lambdaQuery()
                .eq(AlipayDataProduct::getStatus, "init")
                .last("limit 5")
                .orderByDesc(AlipayDataProduct::getGmtModified)
                .list();
        list.forEach(this::doUpdateProduct);
        return SingleResponse.of(list.size());
    }

    @Override
    public SingleResponse<?> takeAndSyncOrder() {
        List<AlipayDataOrder> list = alipayDataOrderService.lambdaQuery()
                .apply(AlipayDataOrder.LAST_VERSION + " > " + AlipayDataOrder.VERSION)
                .orderByDesc(AlipayDataOrder::getLastUpdateTime)
                .last("limit 5")
                .list();
        list.forEach(this::doUpdateOrder);
        return SingleResponse.of(list.size());
    }

    public void doUpdateOrder(AlipayDataOrder dataOrder) {
        try {
            String outerOrderId = dataOrder.getOuterOrderId();
            TicketOrder ticketOrder = ticketOrderService.getById(outerOrderId);
            String alipayAppId = dataOrder.getAlipayAppId();
            String scenicspotId = dataOrder.getOuterScenicId();
            MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, alipayAppId).one();
            String appId = merchantAppBaseConf.getOutAppId();
            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery()
                    .eq(PsTemplateInfo::getTemplateId, merchantAppBaseConf.getAppTemplateId())
                    .one();

            AlipayDataProduct alipayDataProduct = alipayDataProductService.lambdaQuery()
                    .eq(AlipayDataProduct::getProductId, dataOrder.getProductId())
                    .eq(AlipayDataProduct::getOuterScenicId, dataOrder.getOuterScenicId())
                    .eq(AlipayDataProduct::getAlipayAppId, dataOrder.getAlipayAppId())
                    .eq(AlipayDataProduct::getStatus, "sync_success")
                    .one();
            if (Objects.isNull(alipayDataProduct)) {
                log.warn("AlipayDataProduct[appId:{},productId:{} 不存在]", appId, dataOrder.getProductId());
                return;
            }

            PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                    .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                    .one();

            AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
            AlipayBusinessOrderOrderinfoSyncRequest request = new AlipayBusinessOrderOrderinfoSyncRequest();
            AlipayBusinessOrderOrderinfoSyncModel bizModel = new AlipayBusinessOrderOrderinfoSyncModel();
            bizModel.setPaymentMethod("ALIPAY");
            // bizModel.setPaymentStatus("SUCCESS");
            bizModel.setRefundAmout(ticketOrder.getRefundedAmount() + "");
            bizModel.setScenicAppId(appId);
            bizModel.setOuterScenicId(scenicspotId + "");
            bizModel.setSourceSystem("ZHONGZHIYOUXIN");
            bizModel.setBuyerId(dataOrder.getBuyerId());
            bizModel.setSource(dataOrder.getSource());
            bizModel.setAmount(dataOrder.getAmount());
            bizModel.setPayAmount(dataOrder.getAmount());
            bizModel.setOrderCreateTime(dataOrder.getOrderCreateTime());
            bizModel.setOrderModifiedTime(dataOrder.getOrderModifiedTime());
            bizModel.setOrderStatus(dataOrder.getOrderStatus());
            bizModel.setOrderLink(dataOrder.getOrderLink());
            bizModel.setOrderType(dataOrder.getOrderType());
            if (StrUtil.isNotBlank(dataOrder.getAlipayOrderId())) {
                bizModel.setOrderId(dataOrder.getAlipayOrderId());
            }
            bizModel.setOuterOrderId(dataOrder.getOuterOrderId());
            List<ScenicTicketInfo> scenicTicketInfos = new ArrayList<>();
            String ticketJson = dataOrder.getTicketJson();
            JSONArray jsonArray = JSONUtil.parseArray(ticketJson);
            jsonArray.forEach(x -> {
                JSONObject obj = (JSONObject) x;
                ScenicTicketInfo ticketInfo = new ScenicTicketInfo();
                //[
                //    {
                //        "use_start_date": 1652803200000,
                //        "ticket_count": 1,
                //        "ticketType": "NORMAL",
                //        "ticket_name": "中智游大门票",
                //        "use_end_date": 1652803200000,
                //        "price": 0.02,
                //        "ticket_no": 4728,
                //        "ticket_use_code": "20220518110844800031",
                //        "status": "USED"
                //    }
                //]
                ticketInfo.setTicketType(obj.getStr("ticketType"));
                ticketInfo.setUseStartDate(obj.getDate("use_start_date"));
                ticketInfo.setUseEndDate(obj.getDate("use_end_date"));
                ticketInfo.setPrice(obj.getStr("price"));
                ticketInfo.setTicketCount(obj.getLong("ticket_count"));
                ticketInfo.setTicketNo(obj.getStr("ticket_no"));
                ticketInfo.setTicketUseCode(obj.getStr("ticket_use_code"));
                ticketInfo.setStatus(obj.getStr("status"));
                ticketInfo.setTicketName(ticketOrder.getProductName());
                scenicTicketInfos.add(ticketInfo);
            });
            bizModel.setTicketInfo(scenicTicketInfos);
            log.info("scenicTicketInfos:{}", JSONUtil.toJsonPrettyStr(scenicTicketInfos));

            List<Contact> contacts = new ArrayList<>();
            Contact contact = new Contact();
            contact.setName(ticketOrder.getLinkName());
            contact.setMobile(ticketOrder.getLinkMobile());
            contact.setCertNo(ticketOrder.getLinkIdCard());
            contact.setCertType("身份证");
            contacts.add(contact);
            bizModel.setContact(contacts);

            List<Passengers> passengers = touristInfoService.lambdaQuery().eq(TouristInfo::getOrderId, outerOrderId)
                    .list().stream().map(x -> {
                        Passengers passenger = new Passengers();
                        passenger.setName(x.getName());
                        passenger.setCertNo(x.getIdCard());
                        passenger.setMobile(x.getMobile());
                        passenger.setCertType("身份证");
                        return passenger;
                    }).collect(Collectors.toList());
            bizModel.setPassengers(passengers);

            request.setBizModel(bizModel);
            try {
                AlipayBusinessOrderOrderinfoSyncResponse response = alipayClient.execute(request);
                log.info("AlipayBusinessOrderOrderinfoSyncResponse:{}", JSONUtil.toJsonPrettyStr(response));
                if (response.isSuccess()) {
                    String objectId = response.getObjectId();
                    String status = response.getStatus();
                    String remark = "[" + DateUtil.now() + "] 同步状态[" + status + "] 成功!";
                    boolean update = alipayDataOrderService.lambdaUpdate()
                            .set(AlipayDataOrder::getOrderStatus, dataOrder.getOrderStatus())
                            .set(AlipayDataOrder::getVersion, dataOrder.getLastVersion())
                            .set(AlipayDataOrder::getAlipayOrderId, objectId)
                            .set(AlipayDataOrder::getRemark, remark)
                            .eq(AlipayDataOrder::getId, dataOrder.getId())
                            .update();
                    if (update) {
                        AlipayDataOrderRecord record = new AlipayDataOrderRecord();
                        record.setOrderStatus(status);
                        record.setAlipayAppId(alipayAppId);
                        record.setAlipayOrderId(dataOrder.getAlipayOrderId());
                        record.setCreateTime(new Date());
                        record.setOuterOrderId(dataOrder.getOuterOrderId());
                        record.setRemark("同步成功");
                        record.insert();
                        log.info("AlipayBusinessOrderOrderinfoSyncResponse[id:{}] success", dataOrder.getId());
                    }
                } else {
                    String subCode = response.getSubCode();
                    String subMsg = response.getSubMsg();
                    String remark = "[" + DateUtil.now() + "] 同步状态[" + dataOrder.getOrderStatus() + "] 失败,原因:" + subMsg;
                    alipayDataOrderService.lambdaUpdate()
                            .set(AlipayDataOrder::getOrderStatus, dataOrder.getOrderStatus())
                            .set(AlipayDataOrder::getVersion, dataOrder.getLastVersion())
                            .set(AlipayDataOrder::getRemark, remark)
                            .eq(AlipayDataOrder::getId, dataOrder.getId())
                            .update();

                    AlipayDataOrderRecord record = new AlipayDataOrderRecord();
                    record.setOrderStatus(dataOrder.getOrderStatus());
                    record.setAlipayAppId(alipayAppId);
                    record.setAlipayOrderId(dataOrder.getAlipayOrderId());
                    record.setCreateTime(new Date());
                    record.setOuterOrderId(dataOrder.getOuterOrderId());
                    record.setRemark(subCode + "|" + subMsg);
                    record.insert();
                    log.error("支付宝订单同步失败[orderId:{}] ({})", dataOrder.getOuterOrderId(), subMsg);
                }
            } catch (Exception ex) {
                log.error("orderId:{},处理异常", dataOrder.getOuterOrderId(), ex);
            }
        } catch (Exception ex) {
            log.error("orderId:{},处理异常", dataOrder.getOuterOrderId(), ex);
        }
    }

    public void doUpdateProduct(AlipayDataProduct alipayDataProduct) {
        try {
            String alipayAppId = alipayDataProduct.getAlipayAppId();
            String scenicspotId = alipayDataProduct.getOuterScenicId();
            ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(alipayDataProduct.getProductId());
            MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, alipayAppId).one();
            String appId = merchantAppBaseConf.getOutAppId();

            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, merchantAppBaseConf.getAppTemplateId()).one();


            PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                    .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                    .one();

            AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
            AlipayBusinessItemTicketSyncRequest request = new AlipayBusinessItemTicketSyncRequest();
            AlipayBusinessItemTicketSyncModel bizModel = new AlipayBusinessItemTicketSyncModel();
            bizModel.setSourceSystem("ZHONGZHIYOUXIN");
            bizModel.setScenicAppId(appId);
            bizModel.setOuterScenicId(scenicspotId + "");
            bizModel.setOuterTicketId(alipayDataProduct.getProductId() + "");
            bizModel.setStatus(1L);
            bizModel.setTicketType("NORMAL");
            bizModel.setName(scenicspotProduct.getName());
            bizModel.setTicketModifiedTime(new Date());
            request.setBizModel(bizModel);
            try {
                AlipayBusinessItemTicketSyncResponse response = alipayClient.execute(request);
                log.info("AlipayBusinessItemTicketSyncResponse:{}", JSONUtil.toJsonPrettyStr(response));
                if (response.isSuccess()) {
                    String objectId = response.getObjectId();
                    //    String status = response.getStatus();
                    boolean update = alipayDataProductService.lambdaUpdate()
                            .set(AlipayDataProduct::getStatus, "sync_success")
                            .set(AlipayDataProduct::getAlipayProductId, objectId)
                            .set(AlipayDataProduct::getGmtModified, new Date())
                            .eq(AlipayDataProduct::getId, alipayDataProduct.getId())
                            .update();
                    if (update) {
                        log.info("AlipayBusinessItemTicketSyncResponse[id:{}] success", alipayDataProduct.getId());
                    }
                } else {
                    //String subCode = response.getSubCode();
                    String subMsg = response.getSubMsg();
                    alipayDataProductService.lambdaUpdate()
                            .set(AlipayDataProduct::getStatus, "sync_failed")
                            .set(AlipayDataProduct::getRemark, subMsg)
                            .set(AlipayDataProduct::getGmtModified, new Date())
                            .eq(AlipayDataProduct::getId, alipayDataProduct.getId())
                            .update();
                    log.error("支付宝产品同步失败[productId:{},scenicspotId:{}] ({})", alipayDataProduct.getProductId(), scenicspotId, subMsg);
                }
            } catch (Exception exception) {
                assert log != null;
                log.error("do AlipayCommerceDataScenicQueryRequest error", exception);
            }
        } catch (Exception ex) {
            log.error("productId:{},处理异常", alipayDataProduct.getProductId(), ex);
        }
    }
}
