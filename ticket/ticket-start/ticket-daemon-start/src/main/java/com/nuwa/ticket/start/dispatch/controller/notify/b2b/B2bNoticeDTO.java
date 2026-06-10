package com.nuwa.ticket.start.dispatch.controller.notify.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class B2bNoticeDTO {

    @JsonProperty(value = "OrderNotifyHeader")
    private OrderNotifyHeader orderNotifyHeader;

    @JsonProperty(value = "OrderNotifyBody")
    private String orderNotifyBody;
}
