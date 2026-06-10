package com.nuwa.ticket.start.api.controller.one.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParseEelectronicIdentityQrCodeParam {
    @NotBlank(message = "secretkey")
    private String secretkey;

    @NotBlank(message = "身份码编码")
    private String qrCode;
}
