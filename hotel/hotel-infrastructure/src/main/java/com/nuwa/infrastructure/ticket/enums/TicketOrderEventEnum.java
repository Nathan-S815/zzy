package com.nuwa.infrastructure.ticket.enums;

import lombok.AllArgsConstructor;

/**
 * TicketOrderEventEnum 事件
 *
 * @author hy
 * @date 2021/4/19 14:52
 * @since 1.0.0
 */
@AllArgsConstructor
public enum TicketOrderEventEnum {
    /**
     * 渠道下单成功
     */
    channelPlaceOrderSucceeded,

    /**
     * 供应商下单成功
     */
    supplierPlaceOrderSucceeded,
    /**
     * 供应商下单失败
     */
    supplierPlaceOrderFailed,

    /**
     * 渠道支付成功
     */
    channelPaymentSucceeded,

    /**
     * 供应商支付成功
     */
    supplierPaymentSucceeded,

    /**
     * 订单已关闭
     */
    orderClosed,

    /**
     * 订单已完成
     */
    completed,
    ;

}
