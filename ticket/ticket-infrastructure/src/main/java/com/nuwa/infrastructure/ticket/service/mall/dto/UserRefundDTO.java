package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * UserPayDTO 用户退款
 *
 * @author hy
 * @date 2021/5/2 17:11
 * @since 1.0.0
 */
@Data
public class UserRefundDTO {

    private Long mchId;

    private Long appId;

    private Long userId;

    private String orderNo;

    private String remark;

    private BigDecimal amount;
}
