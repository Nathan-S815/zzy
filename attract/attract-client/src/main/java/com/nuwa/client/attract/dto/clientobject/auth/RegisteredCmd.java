package com.nuwa.client.attract.dto.clientobject.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注册参数
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.auth.param:RegisteredParam.java,v1.0.0 2022-09-06 10:37:51 nanHuang
 * Exp $
 */
@Data
@ApiModel(value = "用户注册指令")
@EqualsAndHashCode(callSuper = true)
public class RegisteredCmd extends NuwaCommand {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String  username;
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String  password;
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    private String  email;
    @ApiModelProperty(value = "商户名称（酒店、景区、旅行社的名字）", required = true)
    @NotBlank(message = "商户名称不能为空")
    private String  mchName;
    @ApiModelProperty(value = "省份名", required = true)
    @NotBlank(message = "省份名不能为空")
    private String  province;
    @ApiModelProperty(value = "省份编号", required = true)
    @NotBlank(message = "省份编号不能为空")
    private String  provinceId;
    @ApiModelProperty(value = "城市名", required = true)
    @NotBlank(message = "城市名不能为空")
    private String  city;
    @ApiModelProperty(value = "市id", required = true)
    @NotBlank(message = "市id不能为空")
    private String  cityId;
    @ApiModelProperty(value = "地区名", required = true)
    @NotBlank(message = "地区名不能为空")
    private String  area;
    @ApiModelProperty(value = "区id", required = true)
    @NotBlank(message = "区id不能为空")
    private String  areaId;
    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    private String  address;
    @ApiModelProperty(value = "电话", required = true)
    @NotBlank(message = "电话不能为空")
    private String  tel;
    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    private String  linkName;
    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    private String  linkPhone;
    @ApiModelProperty(value = "营业执照图片id", required = true)
    @NotBlank(message = "营业执照图片id不能为空")
    private String  licenseImageId;
    @ApiModelProperty("统一社会信用代码")
    private String  socialCreditCode;
    @ApiModelProperty(value = "账号类型 0-文旅局 1-景区 2-酒店 3-旅行社", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;
    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String  code;
    @ApiModelProperty(value = "验证码uuid", required = true)
    @NotBlank(message = "验证码uuid不能为空")
    private String  uuid;
}
