package com.nuwa.ticket.start.dispatch.controller.notify.b2b.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VoucherCodeModel {
    @JsonProperty(value = "OrderVoucherCode")
    private String voucherCode;

    @JsonProperty(value = "OrderUserVouchers")
    private List<UserVoucherModel> OrderUserVouchers;
}
