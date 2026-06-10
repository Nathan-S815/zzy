package com.nuwa.ticket.start.dispatch.controller.notify.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class OrderNotifyHeader {
    @JsonProperty(value = "PartnerId")
    private String partnerId;

    /**
     *  order_refund_notify,
     *  order_verify_notify,
     *  order_ticket_notify
     */
    @JsonProperty(value = "NotifyServiceName")
    private String notifyServiceName;
}
