package com.nuwa.ticket.start.api.controller.one.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class ParseEelectronicIdentityQrCodeParam {
    @NotBlank(message = "身份码编码")
    private String qrCode;

    @NotNull
    private Long rightsId;
}
