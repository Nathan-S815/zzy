package com.nuwa.ticket.start.api.controller.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取订单退款规则
 *
 * @author hy
 */
@Data
public class GetOrderRefundRuleParam {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;
}
