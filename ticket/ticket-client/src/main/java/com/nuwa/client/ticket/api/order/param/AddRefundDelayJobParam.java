package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

/**
 * 通道退款任务
 *
 * @author hy
 */
@Data
public class AddRefundDelayJobParam {
    private Long channelRefundId;
}
