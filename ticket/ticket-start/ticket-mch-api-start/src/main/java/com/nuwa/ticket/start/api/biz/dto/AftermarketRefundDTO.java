package com.nuwa.ticket.start.api.biz.dto;

import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 售后退款DTO
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AftermarketRefundDTO extends NuwaCommand {
    private UserAware userAware;

    private Long orderId;

    @ApiModelProperty("退款金额")
    private BigDecimal amount;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("退款原因")
    private String reason;
}
