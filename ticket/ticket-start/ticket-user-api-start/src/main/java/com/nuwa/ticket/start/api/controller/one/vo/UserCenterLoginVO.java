package com.nuwa.ticket.start.api.controller.one.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCenterLoginVO {
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("是否实名认证 yes|no")
    private String identityAuth;
}
