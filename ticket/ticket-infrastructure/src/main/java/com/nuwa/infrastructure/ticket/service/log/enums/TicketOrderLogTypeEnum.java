package com.nuwa.infrastructure.ticket.service.log.enums;


import lombok.Getter;

/**
 * 供应商日志类型
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum TicketOrderLogTypeEnum {
    /**
     * 订单日志类型
     */
    created(1, "创建订单"),
    channelCreateOrder(2, "支付通道下单"),
    channelPaySuccess(3, "支付通道下单"),
    supplierCreateOrderSuccess(4, "供应商下单成功"),
    supplierCreateOrderFailed(5, "供应商下单失败"),
    supplierPaymentSuccess(6, "供应商支付成功"),
    supplierRefundPass(7, "供应商退款审核通过"),
    supplierRefundReject(8, "供应商退款审核拒绝"),
    supplierConsumerSuccess(9, "供应商核销成功"),
    channelRefundSuccess(10, "通道退款成功"),
    channelSettle(11, "通道结算"),
    channelRefundFailed(12, "通道退款失败"),
    closed(13, "订单已关闭"),
    supplierTicketSuccess(14, "供应商出票成功"),
    supplierOrderCancel(15, "供应商取消订单"),
    supplierTicketFailed(16, "供应商出票失败"),

    orderCancel(17, "订单取消"),
    ;

    private final Integer code;
    private final String message;

    TicketOrderLogTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
