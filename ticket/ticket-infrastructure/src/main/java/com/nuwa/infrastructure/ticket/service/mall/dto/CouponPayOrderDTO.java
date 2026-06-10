package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

/**
 *
 * @author hy
 * @date 2021/5/2 17:11
 * @since 1.0.0
 */
@Data
public class CouponPayOrderDTO {

    /**
     * 所属商户id
     */
    private Long mchId;

    /**
     * 所属商户appId
     */
    private Long mchAppId;

    private Long userId;

    private String orderNo;

    private String appId;

    private String buyerId;

    private String appType;

}
