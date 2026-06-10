package com.nuwa.infrastructure.ticket.service.trade.dto;

import lombok.Data;

/**
 * 供应上退款处理DTO
 *
 * @author hy
 */
@Data
public class SupplierApplyRefundDTO {
    private Long ticketRefundId;
    private Long supplierRefundOrderId;
}
