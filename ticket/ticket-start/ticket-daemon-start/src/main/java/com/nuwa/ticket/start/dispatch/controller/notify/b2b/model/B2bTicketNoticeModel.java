package com.nuwa.ticket.start.dispatch.controller.notify.b2b.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * B2B出票通知参数
 *
 * @author hy
 */
@Data
@ToString
public class B2bTicketNoticeModel {

    @JsonProperty(value = "OriginOrderId")
    private String originOrderId;

    @JsonProperty(value = "TicketStatus")
    private String ticketStatus;

    @JsonProperty(value = "OrderId")
    private String orderId;

    @JsonProperty(value = "VoucherCode")
    private VoucherCodeModel voucherCode;
}
