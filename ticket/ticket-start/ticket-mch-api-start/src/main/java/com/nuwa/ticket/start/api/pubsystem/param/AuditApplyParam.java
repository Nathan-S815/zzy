package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AuditApplyParam 小程序提交审核")
public class AuditApplyParam extends NuwaCommand {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "versionDesc", allowableValues = "小程序版本描述，30-500个字符。")
    @Length(min = 30,max = 500,message = "小程序版本描述，30-500个字符")
    private String versionDesc;

    @ApiModelProperty(value = "appDesc", allowableValues = "小程序应用描述，长度限制 20~200 个字")
    @Length(min = 20,max = 200,message = "小程序应用描述，长度限制 20~200 个字")
    private String appDesc;

    @ApiModelProperty(value = "servicePhone", allowableValues = "客服电话")
    private String servicePhone;

    @ApiModelProperty(value = "appName", allowableValues = "小程序应用名称，长度限制 3~20 个字符，仅支持包含中文、数字、英文及下划线")
    @Length(min = 3,max = 20,message = "小程序应用名称，长度限制 3~20 个字符，仅支持包含中文、数字、英文及下划线")
    private String appName;

    @ApiModelProperty(value = "appSlogan", allowableValues = "小程序应用简介，一句话描述小程序功能，长度限制 10~32个字符")
    @Length(min = 10,max = 32,message = "小程序应用简介，一句话描述小程序功能，长度限制 10~32个字符")
    private String appSlogan;
}
