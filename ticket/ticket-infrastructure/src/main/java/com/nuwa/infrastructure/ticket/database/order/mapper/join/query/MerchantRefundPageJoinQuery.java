package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "商户端退款订单分页Query")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MerchantRefundPageJoinQuery extends BaseJoinPagingQuery<MerchantRefundPageJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.ID)
    private Long id;

    @ApiModelProperty(value = "refundId", hidden = true)
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.ID)
    private Long refundId;

    @ApiModelProperty(value = "refundSerialNo", hidden = true)
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.REFUND_SERIAL_NO)
    private Long refundSerialNo;

    @ApiModelProperty("orderNo")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.ORDER_NO)
    private Long orderNo;

    @ApiModelProperty("分销商-商户id")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.MCH_ID)
    private Long mchId;

    @ApiModelProperty("供应商-商户id")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.SUPPLIER_MERCHANT_ID)
    private Long supplierMerchantId;

    @ApiModelProperty("产品id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.PRODUCT_ID)
    private Long productId;

    @ApiModelProperty("产品名称")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.PRODUCT_NAME)
    private String productName;

    @ApiModelProperty("供应商产品编码")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.SUPPLIER_PRODUCT_CODE)
    private String productCode;

    @ApiModelProperty("审核状态 待审核:1;审核拒绝:5;审核通过:10")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.AUDIT_STATUS)
    private Integer auditStatus;

    @ApiModelProperty("状态 创建:1  退款中:2  已退款:3  退款失败:4")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.STATUS)
    private Integer status;

    @ApiModelProperty("联系人手机号")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.LINK_MOBILE)
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.LINK_NAME)
    private String linkName;

    @ApiModelProperty("申请时间-开始")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.CREATE_TIME)
    private Date applyDateStart;

    @ApiModelProperty("申请时间-结束")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.CREATE_TIME)
    private Date applyDateEnd;

    @ApiModelProperty("游玩时间-开始")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateStart;

    @ApiModelProperty("游玩时间-结束")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateEnd;

    @Override
    public void where(JoinQueryBuilder<MerchantRefundPageJoinQuery> wrapper) {
        wrapper.orderByDesc(MerchantRefundPageJoinQuery::getRefundId);
        wrapper.eq(Objects.nonNull(mchId), MerchantRefundPageJoinQuery::getMchId, mchId);
        wrapper.eq(Objects.nonNull(supplierMerchantId), MerchantRefundPageJoinQuery::getSupplierMerchantId, supplierMerchantId);
        wrapper.eq(Objects.nonNull(id), MerchantRefundPageJoinQuery::getId, id);
        wrapper.eq(Objects.nonNull(refundId), MerchantRefundPageJoinQuery::getRefundId, refundId);
        wrapper.eq(Objects.nonNull(refundSerialNo), MerchantRefundPageJoinQuery::getRefundSerialNo, refundSerialNo);
        wrapper.eq(Objects.nonNull(orderNo), MerchantRefundPageJoinQuery::getOrderNo, orderNo);
        wrapper.eq(Objects.nonNull(productId), MerchantRefundPageJoinQuery::getProductId, productId);
        wrapper.like(StrUtil.isNotBlank(productName), MerchantRefundPageJoinQuery::getProductName, productName);
        wrapper.eq(StrUtil.isNotBlank(productCode), MerchantRefundPageJoinQuery::getProductCode, productName);
        wrapper.eq(Objects.nonNull(auditStatus), MerchantRefundPageJoinQuery::getAuditStatus, auditStatus);
        wrapper.eq(Objects.nonNull(status), MerchantRefundPageJoinQuery::getStatus, status);
        wrapper.eq(StrUtil.isNotBlank(linkMobile), MerchantRefundPageJoinQuery::getLinkMobile, linkMobile);
        wrapper.eq(StrUtil.isNotBlank(linkName), MerchantRefundPageJoinQuery::getLinkName, linkName);
        if (Objects.nonNull(applyDateStart) && Objects.nonNull(applyDateEnd)) {
            wrapper.between(MerchantRefundPageJoinQuery::getApplyDateStart, DateUtil.beginOfDay(applyDateStart), DateUtil.endOfDay(applyDateEnd));
        }
        if (Objects.nonNull(visitDateStart) && Objects.nonNull(visitDateEnd)) {
            wrapper.between(MerchantRefundPageJoinQuery::getVisitDateStart, DateUtil.beginOfDay(visitDateStart), DateUtil.endOfDay(visitDateEnd));
        }
    }
}
