package com.nuwa.infrastructure.discovery.database.user.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberPageBase {


    private Long userId;

    @ApiModelProperty("用户编号")
    private Long userNumber;

    @ApiModelProperty("用户手机号")
    private String userPhone;

    @ApiModelProperty("用户微信昵称")
    private String userNike;

    @ApiModelProperty("绑定主体id")
    private Long memberAccountBindId;
}
