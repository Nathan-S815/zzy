package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CheckUserAccountAuthStatusParam 检测用户平台账户绑定情况")
public class CheckUserAccountAuthStatusParam {
    @ApiModelProperty("认证平台编码 douyin")
    private String platCode;
}
