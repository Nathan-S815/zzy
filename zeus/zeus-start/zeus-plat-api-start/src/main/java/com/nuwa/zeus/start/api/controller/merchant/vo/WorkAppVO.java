package com.nuwa.zeus.start.api.controller.merchant.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WorkAppVO {

    @ApiModelProperty("应用id")
    private Long appId;

    @ApiModelProperty("应用名称")
    private String appName;

    @JsonSerialize(using = MaterialJson.class)
    private Long logo;

    @ApiModelProperty("应用简介")
    private String appIntroEditor;

    @ApiModelProperty("应用提供商")
    private String provider;

    @ApiModelProperty("应用类型")
    private Integer appType;

    private List<InnerAppGroupVO> adminList;
}
