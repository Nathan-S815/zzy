package com.nuwa.ticket.start.api.biz.ret;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class RefundApplyRet {
    private TicketRefund ticketRefund;
    private SupplierRefundOrder supplierRefundOrder;
}
