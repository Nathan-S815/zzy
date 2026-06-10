package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
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
@ApiModel(value = "支付通道分页订单列表参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PayChannelOrderJoinPageJoinQuery extends BaseJoinPagingQuery<PayChannelOrderJoinPageJoinQuery> {
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

    @ApiModelProperty("支付通道订单状态- 创建:0;待支付:1;已支付:2;已关闭:3;失败:4")
    @JoinColumn(tableClass = ChannelPaymentOrder.class, column = ChannelPaymentOrder.STATUS)
    private Integer payStatus;

    @ApiModelProperty("支付方式- douyin|weixin_mini|alipay_mini")
    @JoinColumn(tableClass = ChannelPaymentOrder.class, column = ChannelPaymentOrder.PAY_TYPE)
    private String payType;

    @Override
    public void where(JoinQueryBuilder<PayChannelOrderJoinPageJoinQuery> wrapper) {
        wrapper.orderByDesc(PayChannelOrderJoinPageJoinQuery::getCreateDateEnd);
        wrapper.eq(Objects.nonNull(supplierMerchantId), PayChannelOrderJoinPageJoinQuery::getSupplierMerchantId, supplierMerchantId);
        wrapper.eq(Objects.nonNull(scenicspotId), PayChannelOrderJoinPageJoinQuery::getScenicspotId, getScenicspotId());
        wrapper.eq(StrUtil.isNotBlank(payType), PayChannelOrderJoinPageJoinQuery::getPayType, getPayType());
        wrapper.eq(Objects.nonNull(status), PayChannelOrderJoinPageJoinQuery::getStatus, status);
        wrapper.eq(Objects.nonNull(payStatus), PayChannelOrderJoinPageJoinQuery::getPayStatus, payStatus);
        wrapper.eq(Objects.nonNull(orderNo), PayChannelOrderJoinPageJoinQuery::getOrderNo, orderNo);

        wrapper.le(Objects.nonNull(visitDateEnd), PayChannelOrderJoinPageJoinQuery::getVisitDateEnd, visitDateEnd);
        if (Objects.nonNull(visitDateStart)) {
            wrapper.ge(PayChannelOrderJoinPageJoinQuery::getVisitDateStart, DateUtil.beginOfDay(visitDateStart));
        }
        if (Objects.nonNull(visitDateEnd)) {
            wrapper.le(PayChannelOrderJoinPageJoinQuery::getVisitDateEnd, DateUtil.endOfDay(visitDateEnd));
        }
        if (Objects.nonNull(createDateEnd)) {
            wrapper.le(PayChannelOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.endOfDay(createDateEnd));
        }

        if (Objects.nonNull(createDateStart)) {
            wrapper.ge(PayChannelOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.beginOfDay(createDateStart));
        }
        if (Objects.nonNull(createDateEnd)) {
            wrapper.le(PayChannelOrderJoinPageJoinQuery::getCreateDateEnd, DateUtil.endOfDay(createDateEnd));
        }
    }
}
