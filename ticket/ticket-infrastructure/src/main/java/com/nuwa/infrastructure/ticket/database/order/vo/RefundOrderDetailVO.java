package com.nuwa.infrastructure.ticket.database.order.vo;

import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import lombok.Data;

/**
 * 退款详情
 *
 * @author hy
 */
@Data
public class RefundOrderDetailVO {
    private TicketOrder ticketOrder;
    private TicketRefund ticketRefund;
}
