package com.nuwa.ticket.start.api.pubsystem.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DevelopVO {
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

    @ApiModelProperty(value = "体验版状态", allowableValues = "expVersionPackged：体验版打包成功  " +
            "expVersionPackaging：体验版打包中 " +
            "notExpVersion：非体验版")
    private String experienceStatus;

    @ApiModelProperty("体验版本URL")
    private String expQrCodeUrl;

    /* 创建版本的状态，状态说明如下：
     * 0：构建排队中；
     * 1：正在构建；
     * 2：构建成功；
     * 3：构建失败；
     * 5：构建超时；
     * 6：版本创建成功。
     */
    @ApiModelProperty(value = "构建状态", allowableValues = "init(初始)," +
            "0：构建排队中" +
            "1：正在构建" +
            "2：构建成功" +
            "3：构建失败" +
            "5：构建超时" +
            "6：版本创建成功")
    private String buildStatus;

    @ApiModelProperty("构建时间")
    private Date gmtBuild;

    @ApiModelProperty("JSON")
    private String json;
}
