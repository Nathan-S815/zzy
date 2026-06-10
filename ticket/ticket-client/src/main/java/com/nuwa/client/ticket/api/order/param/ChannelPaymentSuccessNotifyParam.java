package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

/**
 * 支付通道支付成功处理
 *
 * @author hy
 */
@Data
public class ChannelPaymentSuccessNotifyParam {
    private String orderNo;
    private String amount;
    private String platOrderNo;
    private String status;
    private String bankSerialNo;
    private String sign;
}
