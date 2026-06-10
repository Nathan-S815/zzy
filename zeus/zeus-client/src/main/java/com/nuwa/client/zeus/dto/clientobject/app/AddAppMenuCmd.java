package com.nuwa.client.zeus.dto.clientobject.app;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * AppAddMenuCmd 添加应用菜单
 *
 * @author hy
 * @date 2021/5/31 13:33
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用添加菜单-命令")
public class AddAppMenuCmd extends NuwaCommand {

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "路径编码", required = true)
    @NotBlank(message = "路径编码不能为空")
    private String code;

    @ApiModelProperty(value = "父级节点", allowableValues = "-1:一级菜单", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "所属appId", required = true)
    @NotNull(message = "所属appId不能为空")
    private Integer appId;

    @ApiModelProperty(value = "资源路径", required = true)
    @NotBlank(message = "资源路径不能为空")
    private String href;

    @ApiModelProperty(value = "图标", required = true)
    @NotBlank(message = "图标不能为空")
    private String icon;

    @ApiModelProperty(value = "菜单类型", allowableValues = "menu:菜单 dirt:目录", required = true)
    @NotBlank(message = "菜单类型不能为空")
    private String type;


    @ApiModelProperty(value = "排序", required = true)
    private Integer orderNum = 0;

    @ApiModelProperty(value = "描述")
    private String description;
}
