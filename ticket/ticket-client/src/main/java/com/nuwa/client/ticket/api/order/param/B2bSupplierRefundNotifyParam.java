package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

/**
 * 供应商退款处理
 *
 * @author hy
 */
@Data
public class B2bSupplierRefundNotifyParam {
    /**0
     * 退款流水号
     */
    private String refundNo;

    private String orderId;

    private String reason;
    /**
     * 1 通过 =2 不通过
     */
    private Integer status;

    private String requestStr;
}
