package com.nuwa.client.ticket.dto.domainevent.order;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 供应商申请退款
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierApplyRefundEvent extends BaseEvent {
    private Long ticketRefundId;
    private Long supplierRefundOrderId;
}
