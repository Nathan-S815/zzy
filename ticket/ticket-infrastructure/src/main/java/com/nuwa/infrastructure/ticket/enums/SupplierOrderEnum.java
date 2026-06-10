package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 支付渠道订单状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum SupplierOrderEnum {
    /**
     * 待下单:0
     * 下单失败:1
     * 待支付:2
     * 支付失败:3
     * 出票中:4
     * 出票失败:5
     * 出票成功:10
     */
    created(0, "待下单"),
    createFailed(1, "下单失败"),
    waitPay(2, "待支付"),
    payFailed(3, "支付失败"),
    ticketing(4, "出票中"),
    ticketFailed(5, "出票失败"),
    ticketed(10, "已出票"),;

    private final Integer code;
    private final String message;

    SupplierOrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
