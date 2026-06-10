package com.nuwa.ticket.start.api.controller.diy.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 应用发布
 *
 * @author hy
 */
@Data
@ApiModel(value = "AppPublishParam 应用发布")
public class AppPublishParam {

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appId不能为空")
    private Long appId;
}
