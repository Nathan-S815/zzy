package com.nuwa.ticket.start.api.pubsystem.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AuditVO {
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

    @ApiModelProperty(value = "审核状态", allowableValues = "wait_audit(审核中),passed(审核成功),rejected(审核失败)")
    private String auditStatus;

    @ApiModelProperty("审核时间")
    private Date gmtAudit;

    @ApiModelProperty("审核失败原因")
    private String auditRejectReason;

    @ApiModelProperty("JSON")
    private String json;
}
