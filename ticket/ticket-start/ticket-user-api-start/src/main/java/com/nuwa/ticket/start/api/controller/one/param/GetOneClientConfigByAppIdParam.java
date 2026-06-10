package com.nuwa.ticket.start.api.controller.one.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetOneClientConfigByAppIdParam {
    @NotBlank(message = "appId不能为空")
    private String appId;
}
