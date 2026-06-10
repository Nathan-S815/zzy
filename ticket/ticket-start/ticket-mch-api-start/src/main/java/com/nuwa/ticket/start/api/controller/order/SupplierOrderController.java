package com.nuwa.ticket.start.api.controller.order;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.ticket.command.query.order.MchRefundOrderPageJoinQry;
import com.nuwa.app.ticket.command.query.order.PayChannelOrderPageJoinQry;
import com.nuwa.app.ticket.command.query.order.SupplierOrderPageJoinQry;
import com.nuwa.client.ticket.dto.clientobject.order.qry.MchTicketOrderPageQry;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.manager.TradeTransactionManager;
import com.nuwa.infrastructure.ticket.database.manager.dto.SystemDoRefundDTO;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.MerchantRefundOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.PayChannelOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.SupplierOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.MerchantRefundPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.PayChannelOrderJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.SupplierOrderJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.param.MchTicketOrderPageParam;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.order.vo.MchRefundOrderPageVO;
import com.nuwa.infrastructure.ticket.database.order.vo.PayChannelOrderPageVO;
import com.nuwa.infrastructure.ticket.database.order.vo.SupplierOrderPageVO;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.enums.TicketRefundEnum;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.third.b2b.SupplierB2bHttpSender;
import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bCancelReq;
import com.nuwa.infrastructure.ticket.third.b2b.req.B2bCreateOrderReq;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bCancelResp;
import com.nuwa.infrastructure.ticket.third.b2b.resp.B2bCreateOrderResp;
import com.nuwa.ticket.start.api.biz.OrderBiz;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditPassDTO;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditRejectDTO;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.order.excel.OrderExportVO;
import com.nuwa.ticket.start.api.controller.order.excel.PayChannelOrderExportVO;
import com.nuwa.ticket.start.api.controller.order.excel.SupplierOrderExportVO;
import com.nuwa.ticket.start.api.controller.order.param.OrderCancelParam;
import com.nuwa.ticket.start.api.controller.order.param.RefundAuditPassParam;
import com.nuwa.ticket.start.api.controller.order.param.RefundAuditRejectParam;
import com.nuwa.ticket.start.api.controller.order.vo.MchOrderPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("supplier/order")
@Api(tags = {"供应商-订单相关"})
public class SupplierOrderController {

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
    private SupplierOrderJoinMapper supplierOrderJoinMapper;

    @Autowired
    private PayChannelOrderJoinMapper payChannelOrderJoinMapper;

    @Autowired
    private LogBizService asyncLogService;

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private SupplierRequestLogService supplierRequestLogService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private MerchantClientI merchantClientI;

    @Autowired
    private TradeTransactionManager tradeManager;

    @ApiOperation(value = "供应商-订单分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MchOrderPageVO>> page(@Valid MchTicketOrderPageQry pageQry, UserAware userAware) {
        pageQry.setSupplierMerchantId(userAware.getMchId());
        MchTicketOrderPageParam pageParam = new MchTicketOrderPageParam(pageQry);
        IPage<MchOrderPageVO> pageData = ticketOrderService.paginateAndConvert(pageParam, MchOrderPageVO::toVO);
        List<MerchantSupplierConfig> supplierConfigList = merchantSupplierConfigService.lambdaQuery().eq(MerchantSupplierConfig::getMerchantId, userAware.getMchId()).list();
        Map<Long, MerchantSupplierConfig> mapMerchantSupplierConfig = supplierConfigList.stream().collect(Collectors.toMap(MerchantSupplierConfig::getId, x -> x));
        Set<Long> setMerchantIds = pageData.getRecords().stream().map(MchOrderPageVO::getMchId).filter(Objects::nonNull).collect(Collectors.toSet());
        Map<Long, MerchantSimpleVO> merchantInfoMap = loadMerchantInfoByIds(setMerchantIds);
        pageData.getRecords().forEach(x -> {
            MerchantSupplierConfig merchantSupplierConfig = mapMerchantSupplierConfig.get(x.getSupplierId());
            if (Objects.nonNull(merchantSupplierConfig)) {
                x.setSupplierName(merchantSupplierConfig.getName());
            }
            MerchantSimpleVO merchantSimpleVO = merchantInfoMap.get(x.getMchId());
            if (Objects.nonNull(merchantSimpleVO)) {
                x.setDistributeMerchantId(x.getMchId());
                x.setDistributeName(merchantSimpleVO.getMchName());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "供应商-订单导出")
    @RequestMapping(value = "/merchant/export", method = RequestMethod.GET)
    @ResponseBody
    public void merchantExport(@Valid MchTicketOrderPageQry pageQry, UserAware userAware, HttpServletResponse response) throws IOException {
        pageQry.setLimit(10000);
        pageQry.setExportFlag(true);
        pageQry.setSupplierMerchantId(userAware.getMchId());
        MchTicketOrderPageParam pageParam = new MchTicketOrderPageParam(pageQry);
        List<TicketOrder> listTicketOrder = ticketOrderService.list(pageParam.toQueryWrapper());
        List<MchOrderPageVO> mchOrderPageVOList = listTicketOrder.stream().map(MchOrderPageVO::toVO).collect(Collectors.toList());
        List<MerchantSupplierConfig> supplierConfigList = merchantSupplierConfigService.lambdaQuery().eq(MerchantSupplierConfig::getMerchantId, userAware.getMchId()).list();
        Map<Long, MerchantSupplierConfig> mapMerchantSupplierConfig = supplierConfigList.stream().collect(Collectors.toMap(MerchantSupplierConfig::getId, x -> x));
        mchOrderPageVOList.forEach(x -> {
            MerchantSupplierConfig merchantSupplierConfig = mapMerchantSupplierConfig.get(x.getSupplierId());
            if (Objects.nonNull(merchantSupplierConfig)) {
                x.setSupplierName(merchantSupplierConfig.getName());
            }
        });

        List<OrderExportVO> exportVOList = mchOrderPageVOList.stream().map(x -> {
            OrderExportVO vo = new OrderExportVO();
            BeanUtils.copyProperties(x, vo);
            vo.setOrderNo(x.getOrderNo() + "");
            return vo;
        }).collect(Collectors.toList());

        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);

        //头策略使用默认
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("订单", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OrderExportVO.class)
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .sheet("订单").doWrite(exportVOList);
    }

    @ApiOperation(value = "供应商-上游订单分页查询")
    @RequestMapping(value = "/merchant/upstream/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<SupplierOrderPageVO>> upstreamOrderPage(@Valid SupplierOrderPageJoinQry pageQry, UserAware userAware) {
        pageQry.setSupplierMerchantId(userAware.getMchId());
        SupplierOrderJoinPageJoinQuery supplierOrderPageJoinQry = new SupplierOrderJoinPageJoinQuery();
        BeanUtils.copyProperties(pageQry, supplierOrderPageJoinQry);
        supplierOrderPageJoinQry.setSupplierOrderStatus(10);
        Page<SupplierOrderPageVO> supplierOrderPageVOPage = supplierOrderJoinMapper.paginateByQuery(supplierOrderPageJoinQry);
        return SingleResponse.of(supplierOrderPageVOPage);
    }

    @ApiOperation(value = "支付渠道订单-分页查询")
    @RequestMapping(value = "/merchant/paychannel/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<PayChannelOrderPageVO>> paychannelPage(@Valid PayChannelOrderPageJoinQry pageQry, UserAware userAware) {
        pageQry.setSupplierMerchantId(userAware.getMchId());
        PayChannelOrderJoinPageJoinQuery supplierOrderPageJoinQry = new PayChannelOrderJoinPageJoinQuery();
        BeanUtils.copyProperties(pageQry, supplierOrderPageJoinQry);
        supplierOrderPageJoinQry.setPayStatus(2);
        Page<PayChannelOrderPageVO> payChannelOrderPageVOPage = payChannelOrderJoinMapper.paginateByQuery(supplierOrderPageJoinQry);
        return SingleResponse.of(payChannelOrderPageVOPage);
    }

    @ApiOperation(value = "支付渠道订单-导出")
    @RequestMapping(value = "/merchant/paychannel/order/export", method = RequestMethod.GET)
    @ResponseBody
    public void paychannelOrderExport(@Valid PayChannelOrderPageJoinQry pageQry, UserAware userAware, HttpServletResponse response) throws IOException {
        pageQry.setLimit(500);
        pageQry.setSupplierMerchantId(userAware.getMchId());

        List<PayChannelOrderPageVO> orders = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            pageQry.setPage(i);
            PayChannelOrderJoinPageJoinQuery supplierOrderPageJoinQry = new PayChannelOrderJoinPageJoinQuery();
            BeanUtils.copyProperties(pageQry, supplierOrderPageJoinQry);
            supplierOrderPageJoinQry.setPayStatus(2);
            Page<PayChannelOrderPageVO> supplierOrderPageVOPage = payChannelOrderJoinMapper.paginateByQuery(supplierOrderPageJoinQry);
            int size = supplierOrderPageVOPage.getRecords().size();
            if (size == 0) {
                break;
            }
            orders.addAll(supplierOrderPageVOPage.getRecords());
        }

        log.info("supplierOrderPageVOPage.getOrders() size:{}", orders.size());
        List<PayChannelOrderExportVO> exportVOList = orders.stream().map(x -> {
            PayChannelOrderExportVO vo = new PayChannelOrderExportVO();
            BeanUtils.copyProperties(x, vo);
            vo.setOrderNo(x.getOrderNo() + "");
            vo.setMchPayOrderNo(x.getMchPayOrderNo() + "");
            return vo;
        }).collect(Collectors.toList());

        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);

        //头策略使用默认
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("支付渠道订单", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PayChannelOrderExportVO.class)
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .sheet("支付渠道订单").doWrite(exportVOList);
    }

    @ApiOperation(value = "供应商-上游订单导出")
    @RequestMapping(value = "/merchant/upstream/export", method = RequestMethod.GET)
    @ResponseBody
    public void upstreamOrderPageExport(@Valid SupplierOrderPageJoinQry pageQry, UserAware userAware, HttpServletResponse response) throws IOException {
        pageQry.setLimit(500);
        pageQry.setSupplierMerchantId(userAware.getMchId());
        List<SupplierOrderPageVO> orders = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            pageQry.setPage(i);
            SupplierOrderJoinPageJoinQuery supplierOrderPageJoinQry = new SupplierOrderJoinPageJoinQuery();
            BeanUtils.copyProperties(pageQry, supplierOrderPageJoinQry);
            supplierOrderPageJoinQry.setSupplierOrderStatus(10);
            Page<SupplierOrderPageVO> supplierOrderPageVOPage = supplierOrderJoinMapper.paginateByQuery(supplierOrderPageJoinQry);
            int size = supplierOrderPageVOPage.getRecords().size();
            if (size == 0) {
                break;
            }
            orders.addAll(supplierOrderPageVOPage.getRecords());
        }

        List<SupplierOrderExportVO> exportVOList = orders.stream().map(x -> {
            SupplierOrderExportVO vo = new SupplierOrderExportVO();
            BeanUtils.copyProperties(x, vo);
            vo.setPaymentNo(x.getPaymentNo() + "");
            vo.setOrderNo(x.getOrderNo() + "");
            vo.setMchSupplierId(x.getMchSupplierId());
            return vo;
        }).collect(Collectors.toList());

        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);

        //头策略使用默认
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("订单", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), SupplierOrderExportVO.class)
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .sheet("订单").doWrite(exportVOList);
    }

    @ApiOperation(value = "退款订单分页查询")
    @RequestMapping(value = "refund/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MchRefundOrderPageVO>> refundPage(MchRefundOrderPageJoinQry query, UserAware userAware) {
        MerchantRefundPageJoinQuery pageByMchRefundWrapper = new MerchantRefundPageJoinQuery();
        BeanUtils.copyProperties(query, pageByMchRefundWrapper);
        pageByMchRefundWrapper.setSupplierMerchantId(userAware.getMchId());
        Page<MchRefundOrderPageVO> pageData = merchantRefundOrderJoinMapper.paginateByQuery(pageByMchRefundWrapper);
        pageData.getRecords().forEach(x -> {
            Integer status = x.getStatus();
            if (status.equals(TicketRefundEnum.refunding.getCode())) {
                x.setStatusName("退款中");
            } else if (status.equals(TicketRefundEnum.created.getCode())) {
                x.setStatusName("等待处理");
            } else if (status.equals(TicketRefundEnum.refunded.getCode())) {
                x.setStatusName("退款成功");
            } else if (status.equals(TicketRefundEnum.refundFailed.getCode())) {
                x.setStatusName("退款失败");
            }
        });
        return SingleResponse.of(pageData);
    }

    @LogRecordAnnotation(
            fail = "退款订单审核通过操作失败，原因：「{{#_errorMsg}}」",
            success = "退款订单审核通过操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.TICKET_ORDER,
            bizNo = "{{#param.ticketOrderId}}",
            detail = "{{#param.toJson()}}")
    @ApiOperation(value = "退款审核通过")
    @RequestMapping(value = "/refund/auditPass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> refundAuditPass(@RequestBody @Valid RefundAuditPassParam param, UserAware userAware) {
        log.info(">>>> TicketRefund refundAuditPass [{}]", param);
        TicketRefund ticketRefund = ticketRefundService.getById(param.getTicketRefundOrderId());
        Assert.notNull(ticketRefund, "退款订单不存在");
        Assert.isTrue(ticketRefund.getAuditStatus().equals(1), "当前订单不允许退款");

        RefundAuditPassDTO dto = new RefundAuditPassDTO();
        dto.setUserAware(userAware);
        dto.setTicketRefund(ticketRefund);
        orderBiz.refundAuditPass(dto);

        log.info("<<<< TicketRefund[{}] refundAuditPass success.", param);
        return SingleResponse.buildSuccess();
    }

    @LogRecordAnnotation(
            fail = "退款审核拒绝操作失败，原因：「{{#_errorMsg}}」",
            success = "退款审核拒绝操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.TICKET_ORDER,
            bizNo = "{{#param.ticketOrderId}}",
            detail = "{{#param.toJson()}}")
    @ApiOperation(value = "退款审核拒绝")
    @RequestMapping(value = "/refund/auditReject", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> refundAuditReject(@RequestBody @Valid RefundAuditRejectParam param, UserAware userAware) {
        log.info(">>>> TicketRefund refundAuditReject [{}]", param);
        TicketRefund ticketRefund = ticketRefundService.getById(param.getTicketRefundOrderId());
        Assert.notNull(ticketRefund, "退款订单不存在");
        Assert.isTrue(ticketRefund.getAuditStatus().equals(1), "当前订单不允许退款");

        RefundAuditRejectDTO dto = new RefundAuditRejectDTO();
        dto.setUserAware(userAware);
        dto.setTicketRefund(ticketRefund);
        dto.setReason(param.getReason());
        orderBiz.refundAuditReject(dto);

        log.info("<<<< TicketRefund[{}] refundAuditReject success.", param);
        return SingleResponse.buildSuccess();
    }


    @LogRecordAnnotation(
            fail = "订单取消操作失败，原因：「{{#_errorMsg}}」",
            success = "订单取消操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.TICKET_ORDER,
            bizNo = "{{#param.orderId}}",
            detail = "{{#param.toJson()}}")
    @ApiOperation(value = "订单取消")
    @RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> orderCancel(@RequestBody @Valid OrderCancelParam param, UserAware userAware) {
        log.info(">>>> TicketOrder orderCancel [{}]", param);
        TicketOrder ticketOrder = ticketOrderService.getById(param.getOrderId());
        Assert.notNull(ticketOrder, "订单不存在");
        Assert.isTrue(ticketOrder.getStatus().equals(3), "当前订单不允许取消");

        SupplierPaymentOrder supplierPaymentOrder = supplierPaymentOrderService.lambdaQuery().eq(SupplierPaymentOrder::getOrderId, ticketOrder.getId()).one();
        if (Objects.isNull(supplierPaymentOrder)) {
            return SingleResponse.buildFailure("9634", "取消订单失败,供应商订单不存在");
        }

        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(ticketOrder.getSupplierId());
        B2bConfigModel config = new B2bConfigModel();
        config.setApiUrl(supplierConfig.getApiUrl());
        config.setPartnerId(supplierConfig.getChannelMerchantId());
        config.setKey(supplierConfig.getChannelSecretKey());
        B2bCancelReq req = new B2bCancelReq();
        req.setOrderId(supplierPaymentOrder.getSupplierOrderNo() + "");
        req.setConfig(config);
        log.info(">>>> SupplierB2bHttpSender.cancel req:{}", req);
        ChannelResult<B2bCancelResp> channelResult = SupplierB2bHttpSender.cancel(req);
        log.info("SupplierB2bHttpSender.cancel channelResult:{}", channelResult);

        if (channelResult.isSuccessful()) {
            supplierPaymentOrderService.lambdaUpdate()
                    .set(SupplierPaymentOrder::getStatus, 20)
                    .set(SupplierPaymentOrder::getLastUpdateTime, new Date())
                    .eq(SupplierPaymentOrder::getId, supplierPaymentOrder.getId())
                    .update();
        } else {
            return SingleResponse.buildFailure("9634", channelResult.getMsg());
        }
        log.info("<<<< TicketOrder[{}] cancel success.", param);
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
