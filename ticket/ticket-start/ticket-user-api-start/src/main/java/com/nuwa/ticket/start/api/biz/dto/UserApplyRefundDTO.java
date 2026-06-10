package com.nuwa.ticket.start.api.biz.dto;

import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户退款申请
 *
 * @author hy
 */
@Data
public class UserApplyRefundDTO {
    private UserAware userAware;

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("退款原因")
    private String reason;
}
