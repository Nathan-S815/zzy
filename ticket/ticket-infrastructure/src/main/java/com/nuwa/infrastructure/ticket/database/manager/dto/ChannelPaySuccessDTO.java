package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 支付通道支付成功
 *
 * @author hy
 */
@Data
@ToString
public class ChannelPaySuccessDTO {

    @ApiModelProperty("渠道支付订单")
    private ChannelPaymentOrder channelPaymentOrder;

    private Date timePaid;
}
