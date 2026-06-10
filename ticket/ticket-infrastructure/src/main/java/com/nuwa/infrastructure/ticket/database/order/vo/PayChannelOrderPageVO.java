package com.nuwa.infrastructure.ticket.database.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付渠道订单分页VO
 *
 * @author hy
 */
@Data
public class PayChannelOrderPageVO {

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("平台订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("已退数量")
    private Integer refundedQuantity;

    @ApiModelProperty("已核销数量")
    private Integer alreadyConsumeQuantity;

    @ApiModelProperty("状态 创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5")
    private Integer status;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("游玩日期")
    private Date visitDate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("支付流水号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mchPayOrderNo;

    @ApiModelProperty("支付渠道商户号")
    private String channelMchNo;

    @ApiModelProperty("支付方式")
    private String payType;

    @ApiModelProperty("支付时间")
    private Date timePaid;

    @ApiModelProperty("支付状态 [创建:0;待支付:1;已支付:2;已关闭:3;失败:4]")
    private Integer payStatus;
}
