package com.nuwa.client.zeus.api.merchant.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClientAppVO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("版本名称")
    private String versionName;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("应用类型 1:独立应用 2:功能应用")
    private Integer appType;
}
