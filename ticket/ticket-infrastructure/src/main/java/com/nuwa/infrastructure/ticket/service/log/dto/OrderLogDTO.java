package com.nuwa.infrastructure.ticket.service.log.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 供应商日志事件
 *
 * @author hy
 */
@Data
public class OrderLogDTO{
    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单日志类型 1:创建订单 2:支付通道下单  3:供应商下单成功 4:供应商下单失败 5:退款成功 6:退款失败 7:订单已完成 8:订单已关闭")
    private Integer type;

    @ApiModelProperty("业务订单id")
    private Long bizOrderId;

    @ApiModelProperty("结果描述")
    private String result;
}
