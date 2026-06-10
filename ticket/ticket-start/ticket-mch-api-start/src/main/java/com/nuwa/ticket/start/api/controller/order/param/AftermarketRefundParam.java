package com.nuwa.ticket.start.api.controller.order.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 售后退款
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AftermarketRefundParam extends NuwaCommand {
    private Long orderId;

    @ApiModelProperty("退款金额")
    private BigDecimal amount;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("退款原因")
    private String reason;
}
