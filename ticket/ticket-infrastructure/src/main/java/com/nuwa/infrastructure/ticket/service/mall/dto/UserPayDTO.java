package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * UserPayDTO 用户支付
 *
 * @author hy
 * @date 2021/5/2 17:11
 * @since 1.0.0
 */
@Data
public class UserPayDTO {

    private Long mchId;

    private Long appId;

    private Long userId;

    private String orderNo;

    private Long cardId;

    private String productName;

    private BigDecimal amount;

    private String remark;
}
