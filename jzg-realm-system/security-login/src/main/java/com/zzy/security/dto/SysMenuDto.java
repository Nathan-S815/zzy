package com.zzy.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class SysMenuDto {

    @ApiModelProperty(hidden = true)
    private Integer menuId;

    /**
     * 上级菜单
     */
    @ApiModelProperty(value = "父级菜单Id,不填则为一级菜单", required = false)
    private Integer parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    /**
     * 链接
     */
    @ApiModelProperty(value = "菜单链接/路由地址(eg: user/list.html)", required = true)
    private String url;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单css图标(eg: fa-users,fa-cog)", required = false)
    private String icon;

    /**
     * 菜单类型 1：菜单 2：按钮
     */
    @ApiModelProperty(value = "菜单类型 1：菜单 2：按钮", required = true)
    private Integer type;

    /**
     * 按钮权限(多个用逗号分隔，如：user:list,user:create)
     */
    @ApiModelProperty(value = "权限控制(多个用逗号分隔，如：user:list,user:create,root:update,root:delete",notes = "root:update为root角色才具有更新操作",required = false)
    private String perms;


    /**
     * 状态 0.已停用 1.正常
     */
    @ApiModelProperty(value = "状态 0.已停用 1.正常(默认1)", required = false)
    private Integer isEnable;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序eg: 二级列表的多个列表数字大的排在前面(不填则默认为1)", required = false)
    private Integer sequence;


    @ApiModelProperty(hidden = true)
    private String parentName;


    @ApiModelProperty(hidden = true)
    private List<SysMenuDto> child;


}
