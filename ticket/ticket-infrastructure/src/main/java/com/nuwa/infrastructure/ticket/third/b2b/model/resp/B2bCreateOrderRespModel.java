package com.nuwa.infrastructure.ticket.third.b2b.model.resp;

import lombok.Data;
import lombok.ToString;

/**
 * 创建订单响应对象
 *
 * @author hy
 */
@Data
@ToString
public class B2bCreateOrderRespModel {

    private String originOrderId;

    private String distributorId;

    private String orderId;

    private String orderStatus;

    private String voucherCode;
}
