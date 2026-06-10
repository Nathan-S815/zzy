package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberIntegralVO {

    @ApiModelProperty("当前积分")
    private Integer integral;

    @ApiModelProperty("当前等级")
    private Integer userLevelId;

    @ApiModelProperty("当前等级")
    private String userLevelName;

    @ApiModelProperty("下一等级")
    private Integer nextLevel;

    @ApiModelProperty("下一等级")
    private String nextLevelName;

    @ApiModelProperty("下一等级所需积分总和")
    private Integer nextLevelIntegral;
}
