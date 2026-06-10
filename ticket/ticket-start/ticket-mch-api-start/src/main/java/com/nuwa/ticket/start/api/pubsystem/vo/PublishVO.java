package com.nuwa.ticket.start.api.pubsystem.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PublishVO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("应用APPID")
    private String appId;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("模板版本号")
    private String templateVersion;

    @ApiModelProperty("版本号")
    private String appVersion;

    @ApiModelProperty("上架状态 online:上架  offline:下架")
    private String publishStatus;

    @ApiModelProperty("上架时间")
    private Date gmtPublish;

    @ApiModelProperty("JSON")
    private String json;
}
