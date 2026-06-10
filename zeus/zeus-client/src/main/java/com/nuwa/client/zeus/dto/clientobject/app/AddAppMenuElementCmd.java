package com.nuwa.client.zeus.dto.clientobject.app;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AddAppMenuElementCmd 添加应用菜单权限
 *
 * @author hy
 * @date 2021/6/2 13:30
 * @since 1.0.0
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用菜单添加权限点-命令")
public class AddAppMenuElementCmd extends NuwaCommand {

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appId不能为空")
    private Integer appId;

    @ApiModelProperty(value = "路径编码", required = true)
    @NotBlank(message = "资源编码不能为空")
    private String code;

    @ApiModelProperty(value = "资源类型", example = "button,uri", required = true)
    @NotBlank(message = "资源类型不能为空")
    private String type;

    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank(message = "资源名称不能为空")
    private String name;

    @ApiModelProperty(value = "资源路径", required = true)
    @NotBlank(message = "资源路径不能为空")
    private String uri;

    @ApiModelProperty(value = "资源关联菜单", required = true)
    @NotBlank(message = "资源关联菜单不能为空")
    private String menuId;

    @ApiModelProperty(value = "资源请求类型", example = "GET,POST", required = true)
    @NotBlank(message = "资源请求类型不能为空")
    private String method;

    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空")
    private String description;
}
