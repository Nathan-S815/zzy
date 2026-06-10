package com.nuwa.ticket.start.api.controller.order.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 订单取消
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderCancelParam extends NuwaCommand {
    @NotNull(message = "orderId不能为空")
    private Long orderId;
}
