package com.nuwa.ticket.start.api.controller.order;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.client.ticket.dto.clientobject.order.qry.MchTicketOrderPageQry;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.MerchantRefundOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.param.MchTicketOrderPageParam;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.order.vo.RefundOrderDetailVO;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductDayTime;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.enums.TicketRefundEnum;
import com.nuwa.ticket.start.api.biz.OrderBiz;
import com.nuwa.ticket.start.api.biz.dto.AftermarketRefundDTO;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.order.param.AftermarketRefundParam;
import com.nuwa.ticket.start.api.controller.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("order")
@Api(tags = {"订单相关"})
public class OrderController {

    @Autowired
    private ScenicspotBaseTypeService scenicspotBaseTypeService;

    @Autowired
    private ScenicspotTypeService scenicspotTypeService;

    @Autowired
    private ScenicspotLabelService scenicspotLabelService;

    @Autowired
    private ScenicspotMaterialService scenicspotMaterialService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ScenicspotJoinMapper scenicspotJoinMapper;

    @Autowired
    private MerchantCanSelectScenicspotJoinMapper merchantCanSelectScenicspotJoinMapper;

    @Autowired
    private UserScenicspotJoinMapper userScenicspotJoinMapper;

    @Autowired
    private MerchantScenicspotPoiService merchantScenicspotPoiService;

    @Autowired
    private ScenicspotBaseLabelService scenicspotBaseLabelService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private MerchantProductJoinMapper merchantProductJoinMapper;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ScenicspotProductBookRuleConfigService scenicspotProductBookRuleConfigService;

    @Autowired
    private ScenicspotProductVerificationConfigService scenicspotProductVerificationConfigService;

    @Autowired
    private ScenicspotProductRefundRuleConfigService scenicspotProductRefundRuleConfigService;

    @Autowired
    private ScenicspotProductValidPeriodConfigService scenicspotProductValidPeriodConfigService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private OrderVoucherService orderVoucherService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private ConsumerRecordService consumerRecordService;

    @Autowired
    private TicketOrderLogService ticketOrderLogService;

    @Autowired
    private MerchantRefundOrderJoinMapper merchantRefundOrderJoinMapper;

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private SupplierRequestLogService supplierRequestLogService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private MerchantClientI merchantClientI;

    @ApiOperation(value = "平台用分页查询")
    @RequestMapping(value = "/plat/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<PlatOrderPageVO>> platPage(@Valid MchTicketOrderPageQry pageQry, UserAware userAware) {
        MchTicketOrderPageParam pageParam = new MchTicketOrderPageParam(pageQry);
        IPage<MchOrderPageVO> pageData = ticketOrderService.paginateAndConvert(pageParam, MchOrderPageVO::toVO);
        IPage<PlatOrderPageVO> platPageData = new Page<>();
        BeanUtils.copyProperties(pageData, platPageData);

        Set<Long> setMapMchId = pageData.getRecords().stream().map(MchOrderPageVO::getMchId).collect(Collectors.toSet());
        Map<Long, MerchantSimpleVO> merchantMapData = loadMerchantInfoByIds(setMapMchId);
        List<PlatOrderPageVO> platOrderPageVOList = pageData.getRecords().stream().map(x -> {
            PlatOrderPageVO platOrderPageVO = new PlatOrderPageVO();
            BeanUtils.copyProperties(x, platOrderPageVO);
            MerchantSimpleVO merchantSimpleVO = merchantMapData.get(x.getMchId());
            if (Objects.nonNull(merchantSimpleVO)) {
                platOrderPageVO.setMchLinkMan(merchantSimpleVO.getLinkMan());
                platOrderPageVO.setMchName(merchantSimpleVO.getMchName());
                platOrderPageVO.setMchLinkMobile(merchantSimpleVO.getLinkMobile());
            }
            return platOrderPageVO;
        }).collect(Collectors.toList());
        platPageData.setRecords(platOrderPageVOList);
        return SingleResponse.of(platPageData);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MchOrderDetailVO> detail(@PathVariable("id") Long id, UserAware userAware) {
        MchOrderDetailVO detailVO = new MchOrderDetailVO();
        TicketOrder ticketOrder = ticketOrderService.getById(id);
        BeanUtils.copyProperties(ticketOrder, detailVO);
        MerchantSupplierConfig merchantSupplierConfig = merchantSupplierConfigService.getById(ticketOrder.getSupplierId());
        if (Objects.nonNull(merchantSupplierConfig)) {
            detailVO.setSupplierName(merchantSupplierConfig.getName());
        }

        Set<Long> setMerchantIds = new HashSet<>();
        setMerchantIds.add(ticketOrder.getMchId());
        detailVO.setDistributeMerchantId(ticketOrder.getMchId());
        Map<Long, MerchantSimpleVO> merchantInfoMap = loadMerchantInfoByIds(setMerchantIds);
        MerchantSimpleVO merchantSimpleVO = merchantInfoMap.get(ticketOrder.getMchId());
        if (Objects.nonNull(merchantSimpleVO)) {
            detailVO.setDistributeName(merchantSimpleVO.getMchName());
        }

        List<TouristInfo> touristInfoList = touristInfoService.lambdaQuery().eq(TouristInfo::getOrderId, id).list();
        List<TouristDTO> touristDTOList = touristInfoList.stream().map(x -> {
            TouristDTO dto = new TouristDTO();
            BeanUtils.copyProperties(x, dto);
            return dto;
        }).collect(Collectors.toList());
        detailVO.setTouristList(touristDTOList);

        //创建:0  待支付:1  待出票:2  已出票:3  已完成:4  已取消:5
        Integer status = ticketOrder.getStatus();

        List<OrderFlowItemVO> bookFlowItems = new ArrayList<>();
        bookFlowItems.add(new OrderFlowItemVO("用户下单", ticketOrder.getCreateTime(), ""));
        if (Objects.nonNull(ticketOrder.getTimePaid())) {
            bookFlowItems.add(new OrderFlowItemVO("用户支付", ticketOrder.getTimePaid(), "￥" + ticketOrder.getRealAmount() + " " + "(" + ticketOrder.getQuantity() + ")"));
        }
        OrderVoucher orderVoucher = orderVoucherService.lambdaQuery().eq(OrderVoucher::getOrderId, ticketOrder.getId()).last("limit 1").one();
        if (Objects.nonNull(orderVoucher)) {
            bookFlowItems.add(new OrderFlowItemVO("商家出票", orderVoucher.getCreateTime(), ticketOrder.getQuantity() + "张"));
        }
        detailVO.setBookFlowItems(bookFlowItems);

        List<OrderFlowItemVO> consumerFlowItems = consumerRecordService.lambdaQuery().eq(ConsumerRecord::getOrderId, ticketOrder.getId())
                .list()
                .stream()
                .map(x -> new OrderFlowItemVO("已验证", x.getCreateTime(), "实到: " + x.getQuantity() + "张")).collect(Collectors.toList());
        detailVO.setConsumerFlowItems(consumerFlowItems);

        List<OrderFlowItemVO> refundFlowItems = ticketRefundService.lambdaQuery().eq(TicketRefund::getOrderId, ticketOrder.getId()).list()
                .stream().map(x -> {
                    //创建:1  退款中:2  已退款:3  退款失败:4
                    Integer refundStatus = x.getStatus();
                    String statusName = "";
                    if (refundStatus.equals(TicketRefundEnum.created.getCode()) || refundStatus.equals(TicketRefundEnum.refunding.getCode())) {
                        statusName = "退款中";
                    } else if (refundStatus.equals(TicketRefundEnum.refunded.getCode())) {
                        statusName = "退款完成";
                    } else if (refundStatus.equals(TicketRefundEnum.refundFailed.getCode())) {
                        statusName = "退款失败";
                    }
                    return new OrderFlowItemVO(statusName, x.getCreateTime(), x.getAmount() + "");
                }).collect(Collectors.toList());
        detailVO.setRefundFlowItems(refundFlowItems);
        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery()
                .eq(SupplierPaymentOrder::getOrderId, ticketOrder.getId())
                .one();
        if (Objects.nonNull(supplierPaymentOrder) && Objects.nonNull(supplierPaymentOrder.getSupplierOrderNo())) {
            detailVO.setSupplierOrderNo(supplierPaymentOrder.getSupplierOrderNo() + "");
        }
        if (Objects.nonNull(ticketOrder.getSessionId())) {
            ProductDayTime productDayTime = productDayTimeService.getById(ticketOrder.getSessionId());
            if (Objects.nonNull(productDayTime)) {
                if (Objects.nonNull(productDayTime.getStart())) {
                    detailVO.setStartTime(DateUtil.format(productDayTime.getStart(), "HH:mm"));
                } else {
                    detailVO.setStartTime("00:00");
                }
                if (Objects.nonNull(productDayTime.getEnd())) {
                    detailVO.setEndTime(DateUtil.format(productDayTime.getEnd(), "HH:mm"));
                } else {
                    detailVO.setEndTime("00:00");
                }
            }
        }
        return SingleResponse.of(detailVO);
    }

    @ApiOperation(value = "退款详情")
    @RequestMapping(value = "refund/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<RefundOrderDetailVO> refundDetail(Long id, UserAware userAware) {
        RefundOrderDetailVO detailVO = new RefundOrderDetailVO();
        TicketRefund ticketRefund = ticketRefundService.getById(id);
        Long orderId = ticketRefund.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        detailVO.setTicketOrder(ticketOrder);
        detailVO.setTicketRefund(ticketRefund);
        return SingleResponse.of(detailVO);
    }

    @ApiOperation(value = "订单接口日志")
    @RequestMapping(value = "{orderId}/api/log", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<SupplierRequestLog>> listApiLog(@PathVariable("orderId") Long orderId, UserAware userAware) {
        List<SupplierRequestLog> list = supplierRequestLogService.lambdaQuery()
                .eq(SupplierRequestLog::getOrderId, orderId)
                .orderByAsc(SupplierRequestLog::getId)
                .last("limit 50")
                .list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "订单日志")
    @RequestMapping(value = "{orderId}/order/log", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<TicketOrderLog>> listOrderLog(@PathVariable("orderId") Long orderId, UserAware userAware) {
        List<TicketOrderLog> list = ticketOrderLogService.lambdaQuery()
                .eq(TicketOrderLog::getOrderId, orderId)
                .orderByAsc(TicketOrderLog::getId)
                .last("limit 50")
                .list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "获取订单操作日志")
    @RequestMapping(value = "{orderId}/operateLog/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<OperateLog>> operateLogList(@PathVariable("orderId") Long orderId, UserAware userAware) {
        List<OperateLog> list = operateLogService.lambdaQuery()
                .select(OperateLog::getOperatorUserName, OperateLog::getAction, OperateLog::getCreateTime)
                .eq(OperateLog::getBizKey, LogRecordPrefixConstant.TICKET_ORDER + "_" + orderId)
                .orderByDesc(OperateLog::getId)
                .last("limit 10")
                .list();
        return SingleResponse.of(list);
    }

    @LogRecordAnnotation(
            fail = "售后退款操作失败，原因：「{{#_errorMsg}}」",
            success = "售后退款操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.TICKET_ORDER,
            bizNo = "{{#param.orderId}}",
            detail = "{{#param.toJson()}}")
    @ApiOperation(value = "售后退款")
    @RequestMapping(value = "/AftermarketRefund", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> aftermarketRefund(@RequestBody @Valid AftermarketRefundParam param, UserAware userAware) {
        log.info(">>>> TicketRefund aftermarketRefund [{}]", param);
        AftermarketRefundDTO dto = new AftermarketRefundDTO();
        dto.setUserAware(userAware);
        dto.setOrderId(param.getOrderId());
        dto.setAmount(param.getAmount());
        dto.setReason(param.getReason());
        dto.setQuantity(param.getQuantity());
        orderBiz.aftermarketRefund(dto);
        log.info("<<<< TicketRefund[{}] aftermarketRefund success.", param);
        return SingleResponse.buildSuccess();
    }

    private Map<Long, MerchantSimpleVO> loadMerchantInfoByIds(Set<Long> setMapMchId) {
        Map<Long, MerchantSimpleVO> merchantMapData = new HashMap<>(10);
        if (setMapMchId.size() > 0) {
            SingleResponse<List<MerchantSimpleVO>> merchantResult = merchantClientI.getMerchantListByIds(setMapMchId);
            if (merchantResult.isSuccess()) {
                merchantMapData = merchantResult.getData().stream().collect(Collectors.toMap(MerchantSimpleVO::getMchId, x -> x));
            }
        }
        return merchantMapData;
    }
}
