package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

/**
 * 核销处理
 *
 * @author hy
 */
@Data
public class B2bSupplierConsumerNotifyParam {
    /**
     * 供应商平台订单号
     */
    private String paymentNo;

    /**
     * 核销数量
     */
    private Integer checkedNumber;

    /**
     * 可核销数量
     */
    private Integer totalNumber;

    private String requestStr;

    private String responseStr;
}
