package com.nuwa.infrastructure.ticket.database.order.vo;

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
public class UserRefundOrderPageVO {
    private Integer id;
    private String productName;
    private Date createTime;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long refundSerialNo;

    private Integer quantity;
    private BigDecimal amount;
    /**
     * 创建:1  退款中:2  已退款:3  退款失败:4
     */
    @ApiModelProperty("状态 (1,2->退款中) 3->退款成功 4->退款失败")
    private Integer status;

    @ApiModelProperty("状态描述 退款中  退款成功 退款失败")
    private String statusName;
}
