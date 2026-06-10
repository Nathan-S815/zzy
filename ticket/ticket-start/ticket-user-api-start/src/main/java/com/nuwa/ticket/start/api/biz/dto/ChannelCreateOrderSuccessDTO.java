package com.nuwa.ticket.start.api.biz.dto;

import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付通道创建成功
 *
 * @author hy
 */
@Data
public class ChannelCreateOrderSuccessDTO {
    private UserAware userAware;

    @ApiModelProperty("订单")
    private TicketOrder ticketOrder;

    @ApiModelProperty("渠道支付订单")
    private ChannelPaymentOrder channelPaymentOrder;

    @ApiModelProperty("渠道支付订单号")
    private String channelOrderNo;

    private String extJson;
}
