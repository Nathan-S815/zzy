package com.nuwa.ticket.start.api.controller.one.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendLoginSmsCodeParam {
    @NotBlank(message = "一码通应用id")
    private Long clientId;

    @NotBlank(message = "手机号不能为空")
    private String mobile;
}
