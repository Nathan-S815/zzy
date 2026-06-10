package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayPrePaidCardOrderNotifyDTO {

    private String orderNo;

    private BigDecimal amount;

    private String remark;
}
