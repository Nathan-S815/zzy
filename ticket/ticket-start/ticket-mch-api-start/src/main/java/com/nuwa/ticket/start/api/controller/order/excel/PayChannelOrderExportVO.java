package com.nuwa.ticket.start.api.controller.order.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nuwa.ticket.start.api.controller.order.excel.convert.OrderStatusConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单导出VO
 * </p>
 *
 * @author ROOT
 * @since 2020-11-05
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class PayChannelOrderExportVO {

    @ApiModelProperty("总金额")
    @ExcelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("单价")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("数量")
    @ExcelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("已退数量")
    @ExcelProperty("已退数量")
    private Integer refundedQuantity;

    @ApiModelProperty("已核销数量")
    @ExcelProperty("已核销数量")
    private Integer alreadyConsumeQuantity;

    @ApiModelProperty("状态 创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5")
    @ExcelProperty(value = "订单状态", converter = OrderStatusConverter.class)
    private Integer status;

    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    @ExcelProperty("产品id")
    private Long productId;

    @ApiModelProperty("游玩日期")
    @ExcelProperty("游玩日期")
    private Date visitDate;

    @ApiModelProperty("创建时间")
    @ExcelProperty("下单时间")
    private Date createTime;

    @ApiModelProperty("平台订单号")
    @ExcelProperty("平台订单号")
    private String orderNo;

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelProperty("支付流水号")
    private String mchPayOrderNo;

    @ApiModelProperty("支付渠道商户号")
    @ExcelProperty("支付渠道商户号")
    private String channelMchNo;

    @ApiModelProperty("支付方式")
    @ExcelProperty("支付方式")
    private String payType;

    @ApiModelProperty("支付时间")
    @ExcelProperty("支付时间")
    private Date timePaid;
}
