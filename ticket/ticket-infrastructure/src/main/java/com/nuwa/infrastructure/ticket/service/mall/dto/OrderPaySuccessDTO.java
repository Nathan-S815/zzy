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
public class OrderPaySuccessDTO {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 外部订单号
     */
    private String outOrderNo;


    /**
     * 订单金额
     */
    private String amount;

    /**
     * 支付时间
     */
    private Date paySuccessTime;

    /**
     * 支付平台订单号
     */
    private String bankSerialNo;

}
