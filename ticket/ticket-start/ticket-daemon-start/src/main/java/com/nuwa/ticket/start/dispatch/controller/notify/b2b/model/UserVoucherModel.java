package com.nuwa.ticket.start.dispatch.controller.notify.b2b.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVoucherModel {
    @JsonProperty(value = "IDCardNo")
    private String iDCardNo;

    @JsonProperty(value = "VoucherCode")
    private String voucherCode;

    @JsonProperty(value = "Mobile")
    private String mobile;

    @JsonProperty(value = "Name")
    private String name;
}
