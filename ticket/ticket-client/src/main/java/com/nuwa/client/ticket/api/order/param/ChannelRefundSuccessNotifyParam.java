package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

/**
 * 支付通道退款成功处理
 *
 * @author hy
 */
@Data
public class ChannelRefundSuccessNotifyParam {
    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 金额（分）
     */
    private String amount;

    /**
     * 平台订单号
     */
    private String platOrderNo;

    /**
     * 状态
     */
    private String status;
}
