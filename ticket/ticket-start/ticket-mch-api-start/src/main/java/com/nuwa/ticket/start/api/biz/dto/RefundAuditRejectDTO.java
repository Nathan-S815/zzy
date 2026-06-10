package com.nuwa.ticket.start.api.biz.dto;

import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.cola.starter.dto.NuwaCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 退款审核拒绝
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefundAuditRejectDTO extends NuwaCommand {
    private TicketRefund ticketRefund;
    private UserAware userAware;
    private String reason;
}
