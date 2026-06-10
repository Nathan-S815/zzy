package com.nuwa.zeus.start.api.controller.plat.base.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * CreateMenuParam 创建菜单
 *
 * @author hy
 * @date 2021/5/26 14:03
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "创建菜单")
public class CreateMenuParam {

    @ApiModelProperty(value = "路径编码", required = true)
    @NotBlank(message = "路径编码不能为空")
    private String code;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "父级节点",allowableValues = "-1:一级菜单", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "资源路径")
   // @NotBlank(message = "资源路径不能为空")
    private String href;

    @ApiModelProperty(value = "图标", required = true)
    @NotBlank(message = "图标不能为空")
    private String icon;

    /**
     * menu dirt sys
     */
    @ApiModelProperty(value = "菜单类型", allowableValues = "menu:菜单 dirt:目录 sys:系统", required = true)
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
