package com.nuwa.ticket.start.dispatch.controller.notify.b2b.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * B2B核销通知参数
 *
 * @author hy
 */
@Data
@ToString
public class B2bConsumerNoticeModel {

    @JsonProperty(value = "OriginOrderId")
    private String originOrderId;

    @JsonProperty(value = "OrderId")
    private String orderId;

    @JsonProperty(value = "VoucherCode")
    private List<String> voucherCode;

    @JsonProperty(value = "OrderTotal")
    private Integer orderTotal;

    @JsonProperty(value = "CheckedNumber")
    private Integer checkedNumber;
}
