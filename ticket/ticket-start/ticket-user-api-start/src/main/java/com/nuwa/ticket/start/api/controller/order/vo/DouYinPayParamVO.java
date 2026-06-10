package com.nuwa.ticket.start.api.controller.order.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * 抖音支付返回参数
 *
 * @author hy
 */
@Data
public class DouYinPayParamVO {
    private Boolean needPay;
    private String orderId;
    private String orderToken;
    private JSONObject payParams;
}
