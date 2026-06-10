package com.nuwa.infrastructure.ticket.database.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户退款订单分页查询
 *
 * @author hy
 */
@Data
public class MchRefundOrderPageVO {
    @ApiModelProperty("订单id")
    private Integer id;

    @ApiModelProperty("orderNo")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("申请时间")
    private Date createTime;

    @ApiModelProperty("下单时间")
    private Date createOrderTime;

    @ApiModelProperty("审核时间")
    private Date timeAudit;

    @ApiModelProperty("游玩时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("退款成功时间")
    private Date timeRefund;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("退款流水号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long refundSerialNo;

    @ApiModelProperty("退款订单id")
    private Long refundId;

    @ApiModelProperty("总张数")
    private Integer quantity;

    @ApiModelProperty("可核销数量")
    private Integer availableConsumeQuantity;

    @ApiModelProperty("退款张数")
    private Integer refundQuantity;

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
    /**
     * 创建:1  退款中:2  已退款:3  退款失败:4
     */
    @ApiModelProperty("状态 (1,2->退款中) 3->退款成功 4->退款失败")
    private Integer status;

    @ApiModelProperty("状态描述 退款中  退款成功 退款失败")
    private String statusName;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

    @ApiModelProperty("退款原因")
    private String refundReason;
}
