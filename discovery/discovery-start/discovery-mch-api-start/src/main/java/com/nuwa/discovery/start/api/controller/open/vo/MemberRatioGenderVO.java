package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "达人性别对象")
public class MemberRatioGenderVO {

    @ApiModelProperty("男性比例")
    private Double male;

    @ApiModelProperty("女性比例")
    private Double Female;
}
