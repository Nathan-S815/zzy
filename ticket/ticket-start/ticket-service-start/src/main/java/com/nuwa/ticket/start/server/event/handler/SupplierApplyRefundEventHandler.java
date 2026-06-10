package com.nuwa.ticket.start.server.event.handler;

import com.nuwa.client.ticket.dto.domainevent.order.SupplierApplyRefundEvent;
import com.nuwa.infrastructure.ticket.service.trade.SupplierRefundService;
import com.nuwa.infrastructure.ticket.service.trade.dto.SupplierApplyRefundDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 供应商退款事件处理
 *
 * @author hy
 */
@Slf4j
@Component
public class SupplierApplyRefundEventHandler {

    @Autowired
    private SupplierRefundService supplerRefundService;

    @EventListener(SupplierApplyRefundEvent.class)
    @Async
    public void handle(SupplierApplyRefundEvent event) {
        log.info("SupplierApplyRefundEvent:{}", event);
        SupplierApplyRefundDTO dto = new SupplierApplyRefundDTO();
        dto.setSupplierRefundOrderId(event.getSupplierRefundOrderId());
        dto.setTicketRefundId(event.getTicketRefundId());
        supplerRefundService.applyRefund(dto);
    }
}
