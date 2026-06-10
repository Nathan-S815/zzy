package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付通道退款受理成功
 *
 * @author hy
 */
@Data
public class ChannelRefundAcceptSuccessDTO {

    @ApiModelProperty("渠道退款订单")
    private ChannelRefundOrder channelRefundOrder;

    private String channelRefundOrderNo;
}
