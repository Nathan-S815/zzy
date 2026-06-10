package com.nuwa.ticket.start.api.controller.user.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class BindMobileParam {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1\\d{10}", message = "手机号格式错误")
    private String mobile;

    @NotBlank(message = "验证码")
    private String code;
}
