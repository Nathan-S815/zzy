package com.nuwa.discovery.start.api.controller.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberUpdateParam {

    @ApiModelProperty(value = "code", required = true)
    private String userNike;

    @ApiModelProperty(value = "appId", required = true)
    private String userImg;
}
