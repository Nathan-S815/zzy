package com.nuwa.hotel.start.dispatch.controller.notify.gateway;

import lombok.Data;

/**
 * GatewayRefundNotifyParam 异步回调
 *
 * @author hy
 * @date 2020/10/27 14:26
 * @since 1.0.0
 */

@Data
public class GatewayRefundNotifyParam {
    private String mchId;
    private String orderNo;
    private String amount;
    private String platOrderNo;
    private String status;
    private String sign;
}
