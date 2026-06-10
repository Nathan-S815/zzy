package com.nuwa.client.ticket.api.order.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoucherDTO {
    private String iDCardNo;

    private String voucherCode;

    private String mobile;

    private String name;
}
