package com.nuwa.hotel.start.dispatch.controller.notify.gateway;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.util.Map;

/**
 * GatewayPaymentNotifyParam 异步回调
 *
 * @author hy
 * @date 2020/10/27 14:26
 * @since 1.0.0
 */

@Data
public class GatewayPaymentNotifyParam {
    private String mchId;
    private String orderNo;
    private String amount;
    private String platOrderNo;
    private String status;
    private String bankSerialNo;
    private String sign;
}
