package com.nuwa.client.zeus.dto.clientobject.app;

import com.nuwa.client.zeus.dto.clientobject.app.co.AppSkuInfoCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * CreateAppCmd 创建应用
 *
 * @author hy
 * @date 2021/5/31 13:33
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建应用-命令")
public class CreateAppCmd extends NuwaCommand {

    @ApiModelProperty(value = "应用名称", required = true)
    @NotBlank(message = "应用名称不能为空")
    private String appName;

    @ApiModelProperty(value = "logo", required = true)
    @NotNull(message = "logo不能为空")
    private Long logo;

    @ApiModelProperty(value = "应用类型", example = "1:普通系统应用 2:功能应用 3:核心系统应用 ", required = true)
    @NotNull(message = "应用类型不能为空")
    private Integer appType;

    @ApiModelProperty(value = "应用提供方", example = "outer |  inner")
    private String provider;

    @ApiModelProperty("免密登录(0:不支持 1:支持)")
    private Integer ssh;

    @ApiModelProperty("私有化部署(0:不支持 1:支持)")
    private Integer privatization;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "应用简介", required = true)
    @NotBlank(message = "应用简介不能为空")
    private String appIntroEditor;

    @ApiModelProperty(value = "依赖的应用id", required = true)
    private List<Integer> dependentAppId;

    @ApiModelProperty(value = "规格", required = true)
    @NotNull(message = "规格不能为空")
    private List<AppSkuInfoCO> sku;

    @ApiModelProperty(value = "应用详情", required = true)
    @NotBlank(message = "应用详情不能为空")
    private String appDetailEditor;

    @ApiModelProperty(value = "应用教程", required = true)
    @NotBlank(message = "应用教程不能为空")
    private String appCourseEditor;

    @ApiModelProperty(value = "服务商名称", required = true)
    @NotBlank(message = "服务商名称不能为空")
    private String serviceName;

    @ApiModelProperty(value = "咨询电话", required = true)
    @NotBlank(message = "咨询电话不能为空")
    private String hotline;

    @ApiModelProperty(value = "技术咨询电话", required = true)
    @NotBlank(message = "技术咨询电话不能为空")
    private String technologyHotline;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "应用状态 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty(value = "应用链接")
    private String manageUrl;
}
