package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

/**
 *
 * @author hy
 * @date 2021/5/2 17:11
 * @since 1.0.0
 */
@Data
public class MallRefundOrderDTO {

    /**
     * 所属商户id
     */
    private Long mchId;

    /**
     * 所属商户appId
     */
    private Long mchAppId;

    private Long tradeId;

    private Long refundAmount;
}
