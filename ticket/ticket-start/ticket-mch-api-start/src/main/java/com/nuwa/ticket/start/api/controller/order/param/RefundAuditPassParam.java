package com.nuwa.ticket.start.api.controller.order.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 退款审核通过
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefundAuditPassParam extends NuwaCommand {
    private Long ticketRefundOrderId;
    private Long ticketOrderId;
}
