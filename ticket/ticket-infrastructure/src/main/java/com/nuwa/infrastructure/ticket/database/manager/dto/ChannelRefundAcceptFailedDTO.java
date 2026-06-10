package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付通道退款受理失败
 *
 * @author hy
 */
@Data
public class ChannelRefundAcceptFailedDTO {

    @ApiModelProperty("渠道退款订单")
    private ChannelRefundOrder channelRefundOrder;

    private String errCode;

    private String errMsg;
}
