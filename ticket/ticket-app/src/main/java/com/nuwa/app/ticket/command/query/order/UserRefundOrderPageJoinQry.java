package com.nuwa.app.ticket.command.query.order;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户退款订单分页查询")
public class UserRefundOrderPageJoinQry extends NuwaPageQry {

    @ApiModelProperty(value = "用户Id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty("状态 退款中:1 退款成功:2 退款失败:3")
    private Integer status;
}
