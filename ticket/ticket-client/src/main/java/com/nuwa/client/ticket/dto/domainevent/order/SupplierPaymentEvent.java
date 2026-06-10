package com.nuwa.client.ticket.dto.domainevent.order;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 供应商支付事件
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierPaymentEvent extends BaseEvent {
    private Long supplierPaymentOrderId;
}
