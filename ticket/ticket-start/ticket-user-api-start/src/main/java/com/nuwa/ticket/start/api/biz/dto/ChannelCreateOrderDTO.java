package com.nuwa.ticket.start.api.biz.dto;

import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付通道创建订单
 *
 * @author hy
 */
@Data
public class ChannelCreateOrderDTO {
    private UserAware userAware;

    @ApiModelProperty("订单")
    private TicketOrder ticketOrder;

    @ApiModelProperty("支付方式")
    private String payType;

    private MerchantAppPayConf payConf;
}
