package com.nuwa.ticket.start.api.controller.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 订单退款
 *
 * @author hy
 */
@Data
public class OrderRefundParam {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("退款原因")
    private String reason;
}
