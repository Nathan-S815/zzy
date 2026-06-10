package com.nuwa.zeus.start.api.controller.plat.base.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CreateMenuParam 修改菜单
 *
 * @author hy
 * @date 2021/5/26 14:03
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "修改菜单")
public class ModifyMenuParam {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "路径编码", required = true)
    @NotBlank(message = "路径编码不能为空")
    private String code;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "父级节点", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "资源路径", required = true)
    @NotBlank(message = "资源路径不能为空")
    private String href;

    @ApiModelProperty(value = "图标", required = true)
    @NotBlank(message = "图标不能为空")
    private String icon;

    /**
     * menu dirt sys
     */
    @ApiModelProperty(value = "菜单类型", example = "menu:菜单 dirt:目录 sys:子系统", required = true)
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    /**
     * 排序
     */
    private Integer orderNum = 0;

    /**
     * 描述
     */
    private String description;
}
