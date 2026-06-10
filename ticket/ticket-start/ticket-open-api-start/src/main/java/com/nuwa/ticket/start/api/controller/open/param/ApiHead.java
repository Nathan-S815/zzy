package com.nuwa.ticket.start.api.controller.open.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApiHead {

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty("签名")
    private String sign;

    @NotBlank(message = "appId不能为空")
    @ApiModelProperty("appId")
    private String appId;

    @NotBlank(message = "timestamp不能为空")
    @ApiModelProperty("时间戳  yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}
