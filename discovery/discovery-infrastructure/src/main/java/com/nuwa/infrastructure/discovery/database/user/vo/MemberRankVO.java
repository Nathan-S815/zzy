package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberRankVO {

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("会员等级id")
    private Integer userLevelId;

    @ApiModelProperty("会员等级id")
    private String userLevelName;
}
