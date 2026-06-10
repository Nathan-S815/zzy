package com.nuwa.attract.start.api.controller.auth.param;

import javax.validation.constraints.NotBlank;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改用户信息param
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.auth.param:ModifyUserInfoParam.java,v1.0.0 2022-09-07 10:46:14
 * nanHuang Exp $
 */
@Data
@ApiModel(value = "修改用户信息param")
@EqualsAndHashCode(callSuper = true)
public class ModifyUserInfoParam extends NuwaCommand {
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @ApiModelProperty(value = "商户名称（酒店、景区、旅行社的名字）", required = true)
    @NotBlank(message = "商户名称不能为空")
    private String mchName;
    @ApiModelProperty("省份名")
    private String province;
    @ApiModelProperty("省份编号")
    private String provinceId;
    @ApiModelProperty("城市名")
    private String city;
    @ApiModelProperty("城市编码")
    private String cityId;
    @ApiModelProperty("地区名")
    private String area;
    @ApiModelProperty("地区编码")
    private String areaId;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty(value = "电话", required = true)
    @NotBlank(message = "电话不能为空")
    private String tel;
    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    private String linkName;
    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    private String linkPhone;
}
