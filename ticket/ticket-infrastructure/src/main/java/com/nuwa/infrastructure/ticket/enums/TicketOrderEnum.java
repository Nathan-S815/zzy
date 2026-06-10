package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 订单状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum TicketOrderEnum {
    /**
     * 创建:0;
     * 待支付:1;
     * 待出票:2;
     * 已出票:3;
     * 已完成:4;
     * 已取消:5
     */
    created(0, "创建"),
    paying(1, "待支付"),
    ticketing(2, "待出票"),
    ticketed(3, "已出票"),
    completed(4, "已完成"),
    closed(5, "已取消"),
    ;

    private final Integer code;
    private final String message;

    TicketOrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TicketOrderEnum getByCode(Integer code) {
        for (TicketOrderEnum value : TicketOrderEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}
