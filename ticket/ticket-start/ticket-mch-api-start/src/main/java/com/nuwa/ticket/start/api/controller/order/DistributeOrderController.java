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
import com.nuwa.client.ticket.dto.clientobject.order.qry.MchTicketOrderPageQry;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.MerchantRefundOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.MerchantRefundPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.param.MchTicketOrderPageParam;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.order.vo.MchRefundOrderPageVO;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSaasSupplier;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.enums.TicketRefundEnum;
import com.nuwa.ticket.start.api.biz.OrderBiz;
import com.nuwa.ticket.start.api.biz.dto.AftermarketRefundDTO;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditPassDTO;
import com.nuwa.ticket.start.api.biz.dto.RefundAuditRejectDTO;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.order.excel.OrderExportVO;
import com.nuwa.ticket.start.api.controller.order.param.AftermarketRefundParam;
import com.nuwa.ticket.start.api.controller.order.param.RefundAuditPassParam;
import com.nuwa.ticket.start.api.controller.order.param.RefundAuditRejectParam;
import com.nuwa.ticket.start.api.controller.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("distribute/order")
@Api(tags = {"分销商-订单相关"})
public class DistributeOrderController {

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

    @Autowired
    private MerchantSaasSupplierService merchantSaasSupplierService;

    @ApiOperation(value = "分销商-订单分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MchOrderPageVO>> page(@Valid MchTicketOrderPageQry pageQry, UserAware userAware) {
        pageQry.setDistributeMerchantId(userAware.getMchId());
        MchTicketOrderPageParam pageParam = new MchTicketOrderPageParam(pageQry);
        IPage<MchOrderPageVO> pageData = ticketOrderService.paginateAndConvert(pageParam, MchOrderPageVO::toVO);
        Set<Long> supplierMerchantIds = pageData.getRecords().stream().map(MchOrderPageVO::getSupplierMerchantId).collect(Collectors.toSet());
        List<MerchantSaasSupplier> supplierConfigList = null;
        if (supplierMerchantIds.size() > 0) {
             supplierConfigList = merchantSaasSupplierService.lambdaQuery()
                    .in(MerchantSaasSupplier::getSupplierMerchantId, supplierMerchantIds)
                    .list();
        }else{
            supplierConfigList = new ArrayList<>();
        }
        Map<Long, MerchantSaasSupplier> mapMerchantSupplierConfig = supplierConfigList.stream()
                .collect(Collectors.toMap(MerchantSaasSupplier::getSupplierMerchantId, x -> x, (newVal, oldVal) -> newVal));
        pageData.getRecords().forEach(x -> {
            MerchantSaasSupplier merchantSupplierConfig = mapMerchantSupplierConfig.get(x.getSupplierMerchantId());
            if (Objects.nonNull(merchantSupplierConfig)) {
                x.setSupplierName(merchantSupplierConfig.getSupplierName());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "分销商-订单导出")
    @RequestMapping(value = "/merchant/export", method = RequestMethod.GET)
    @ResponseBody
    public void merchantExport(@Valid MchTicketOrderPageQry pageQry, UserAware userAware, HttpServletResponse response) throws IOException {
        pageQry.setLimit(10000);
        pageQry.setExportFlag(true);
        pageQry.setDistributeMerchantId(userAware.getMchId());
        MchTicketOrderPageParam pageParam = new MchTicketOrderPageParam(pageQry);
        List<TicketOrder> listTicketOrder = ticketOrderService.list(pageParam.toQueryWrapper());
        List<MchOrderPageVO> mchOrderPageVOList = listTicketOrder.stream().map(MchOrderPageVO::toVO).collect(Collectors.toList());
        Set<Long> supplierMerchantIds = mchOrderPageVOList.stream().map(MchOrderPageVO::getSupplierMerchantId).collect(Collectors.toSet());
        List<MerchantSaasSupplier> supplierConfigList = null;
        if (supplierMerchantIds.size() > 0) {
            supplierConfigList = merchantSaasSupplierService.lambdaQuery()
                    .in(MerchantSaasSupplier::getSupplierMerchantId, supplierMerchantIds)
                    .list();
        }else{
            supplierConfigList = new ArrayList<>();
        }
        Map<Long, MerchantSaasSupplier> mapMerchantSupplierConfig = supplierConfigList.stream()
                .collect(Collectors.toMap(MerchantSaasSupplier::getSupplierMerchantId, x -> x, (newVal, oldVal) -> newVal));

        List<OrderExportVO> exportVOList = mchOrderPageVOList.stream().map(x -> {
            OrderExportVO vo = new OrderExportVO();
            BeanUtils.copyProperties(x, vo);
            vo.setOrderNo(x.getOrderNo() + "");
            MerchantSaasSupplier merchantSupplierConfig = mapMerchantSupplierConfig.get(x.getSupplierMerchantId());
            if (Objects.nonNull(merchantSupplierConfig)) {
                vo.setSupplierName(merchantSupplierConfig.getSupplierName());
            }
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

    @ApiOperation(value = "退款订单分页查询")
    @RequestMapping(value = "refund/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MchRefundOrderPageVO>> refundPage(MchRefundOrderPageJoinQry query, UserAware userAware) {
        MerchantRefundPageJoinQuery pageByMchRefundWrapper = new MerchantRefundPageJoinQuery();
        BeanUtils.copyProperties(query, pageByMchRefundWrapper);
        pageByMchRefundWrapper.setMchId(userAware.getMchId());
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
}
