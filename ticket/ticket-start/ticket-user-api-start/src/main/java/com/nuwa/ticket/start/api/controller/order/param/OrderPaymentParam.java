package com.nuwa.ticket.start.api.controller.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 支付
 *
 * @author hy
 */
@Data
public class OrderPaymentParam {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @ApiModelProperty("支付方式[douyin_mini weixin_mini]")
    @NotNull(message = "支付方式不能为空")
    private String payType;

    @ApiModelProperty("appId")
    @NotBlank(message = "appId不能为空")
    private String appId;

    @ApiModelProperty("支付成功跳转地址")
    @NotBlank(message = "frontUrl不能为空")
    private String frontUrl;
}
