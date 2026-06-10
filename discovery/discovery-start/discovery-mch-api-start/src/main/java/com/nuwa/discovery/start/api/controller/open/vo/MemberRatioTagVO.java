package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "达人标签对象")
public class MemberRatioTagVO {

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("占比")
    private Double ratio;
}
