package com.nuwa.ticket.start.api.controller.one.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListUsableIdentityParam {
    @ApiModelProperty("身份编码 逗号隔开")
    private String codes;
}
