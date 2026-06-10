package com.nuwa.infrastructure.ticket.database.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单列表返回VO
 *
 * @author hy
 */
@Data
public class UserVoucherOrderListVO {
    @ApiModelProperty("订单id")
    private Long id;

    @ApiModelProperty("订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("状态 创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5")
    private Integer status;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("场次id")
    private Long sessionId;

    @ApiModelProperty("产品类型")
    private Integer productType;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    private String linkName;
}
