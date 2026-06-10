package com.nuwa.ticket.start.api.controller.one.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SendRegisterSmsCodeParam {
    private Long clientId;

    @NotBlank(message = "手机号不能为空")
    private String mobile;
}
