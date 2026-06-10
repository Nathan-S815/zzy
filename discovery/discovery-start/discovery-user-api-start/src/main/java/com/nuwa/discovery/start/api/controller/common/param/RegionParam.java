package com.nuwa.discovery.start.api.controller.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegionParam {

    @ApiModelProperty(value = "父级区域ID，查询level为2,3时必填,level为1时无效(level=2,areaCode传的是省code)")
    private String areaCode;

    @NotNull(message = "level不能为空")
    @NotEmpty(message = "level不能为空")
    @ApiModelProperty(value = "等级1省，2市，3区")
    private String level;
}
