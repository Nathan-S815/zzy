package com.nuwa.ticket.start.api.controller.one.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class IdentityAuthParam {

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("用户身份证ID")
    @NotBlank(message = "用户身份证ID不能为空")
    @Pattern(message = "身份证格式不对", regexp = "(^[1-9]\\d{5}(19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)")
    private String userIdCard;

    @ApiModelProperty("用户真实姓名")
    @NotBlank(message = "用户真实姓名不能为空")
    private String userRealName;
}
