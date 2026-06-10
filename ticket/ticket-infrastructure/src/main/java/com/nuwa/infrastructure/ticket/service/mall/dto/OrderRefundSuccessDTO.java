package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

import java.util.Date;

/**
 * VideoOrderPaySuccessDTO
 *
 * @author hy
 * @date 2020/10/27 17:20
 * @since 1.0.0
 */
@Data
public class OrderRefundSuccessDTO {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private String amount;

    /**
     * 平台订单号
     */
    private String platOrderNo;

    /**
     * 退款时间
     */
    private Date refundTime;

}
