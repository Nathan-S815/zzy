package com.nuwa.ticket.start.api.controller.one.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModifyIdentityAuthParam {

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;
}
