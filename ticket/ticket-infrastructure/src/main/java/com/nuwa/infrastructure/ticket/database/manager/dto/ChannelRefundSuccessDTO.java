package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 支付通道退款成功
 *
 * @author hy
 */
@Data
public class ChannelRefundSuccessDTO {

    @ApiModelProperty("渠道退款订单")
    private ChannelRefundOrder channelRefundOrder;

    private Date timeRefund;
}
