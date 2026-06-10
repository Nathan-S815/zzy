package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import cn.hutool.core.date.DateUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinListQuery;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "获取供应商订单列表参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SupplierOrderJoinPageJoinQuery extends BaseJoinPagingQuery<SupplierOrderJoinPageJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("供应商商户id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.SUPPLIER_MERCHANT_ID)
    private Long supplierMerchantId;

    @ApiModelProperty("平台订单号")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.ORDER_NO)
    private Long orderNo;

    @ApiModelProperty("平台流水号")
    @JoinColumn(tableClass = SupplierPaymentOrder.class, column = SupplierPaymentOrder.PAYMENT_NO)
    private Long paymentNo;

    @ApiModelProperty("景区id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.SCENICSPOT_ID)
    private Long scenicspotId;

    @ApiModelProperty("订单状态")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.STATUS)
    private Integer status;

    @ApiModelProperty("供应商id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.SUPPLIER_ID)
    private Long supplierId;

    @ApiModelProperty("游玩日期-开始")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateStart;

    @ApiModelProperty("游玩日期-结束")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateEnd;

    @ApiModelProperty("下单日期-开始")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.CREATE_TIME)
    private Date createDateStart;

    @ApiModelProperty("下单日期-结束")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.CREATE_TIME)
    private Date createDateEnd;

    @ApiModelProperty("供应商订单状态-待下单:0 下单失败:1 待支付:2  支付失败:3  出票中:4 出票失败:5 出票成功:10")
    @JoinColumn(tableClass = SupplierPaymentOrder.class, column = SupplierPaymentOrder.STATUS)
    private Integer supplierOrderStatus;

    @Override
    public void where(JoinQueryBuilder<SupplierOrderJoinPageJoinQuery> wrapper) {
        wrapper.orderByDesc(SupplierOrderJoinPageJoinQuery::getCreateDateEnd);
        wrapper.eq(Objects.nonNull(supplierMerchantId), SupplierOrderJoinPageJoinQuery::getSupplierMerchantId, supplierMerchantId);
        wrapper.eq(Objects.nonNull(scenicspotId), SupplierOrderJoinPageJoinQuery::getScenicspotId, getScenicspotId());
        wrapper.eq(Objects.nonNull(supplierId), SupplierOrderJoinPageJoinQuery::getSupplierId, getSupplierId());
        wrapper.eq(Objects.nonNull(status), SupplierOrderJoinPageJoinQuery::getStatus, status);
        wrapper.eq(Objects.nonNull(supplierOrderStatus), SupplierOrderJoinPageJoinQuery::getSupplierOrderStatus, supplierOrderStatus);
        wrapper.eq(Objects.nonNull(orderNo), SupplierOrderJoinPageJoinQuery::getOrderNo, orderNo);

        wrapper.le(Objects.nonNull(visitDateEnd), SupplierOrderJoinPageJoinQuery::getVisitDateEnd, visitDateEnd);
        if (Objects.nonNull(visitDateStart)) {
            wrapper.ge(SupplierOrderJoinPageJoinQuery::getVisitDateStart, DateUtil.beginOfDay(visitDateStart));
        }
        if (Objects.nonNull(visitDateEnd)) {
            wrapper.le(SupplierOrderJoinPageJoinQuery::getVisitDateEnd, DateUtil.endOfDay(visitDateEnd));
        }
        if (Objects.nonNull(createDateEnd)) {
            wrapper.le(SupplierOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.endOfDay(createDateEnd));
        }

        if (Objects.nonNull(createDateStart)) {
            wrapper.ge(SupplierOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.beginOfDay(createDateStart));
        }
        if (Objects.nonNull(createDateEnd)) {
            wrapper.le(SupplierOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.endOfDay(createDateEnd));
        }
    }
}
