package com.zzy.security.lib.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;



public class SysMenu implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 上级菜单
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 链接
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型  1：菜单 2：按钮
     */
    private Integer type;

    /**
     * 按钮权限(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 状态 0.已停用 1.正常
     */
    private Integer isEnable;

    /**
     * 是否删除（0.已删除 1.正常）
     */
    private Integer isDel;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * sys_menu
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 上级菜单
     * @return parent_id 上级菜单
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 上级菜单
     * @param parentId 上级菜单
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 菜单名称
     * @return name 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 菜单名称
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 链接
     * @return url 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 链接
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 菜单图标
     * @return icon 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 菜单图标
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 菜单类型  1：菜单 2：按钮
     * @return type 菜单类型  1：菜单 2：按钮
     */
    public Integer getType() {
        return type;
    }

    /**
     * 菜单类型  1：菜单 2：按钮
     * @param type 菜单类型  1：菜单 2：按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 按钮权限(多个用逗号分隔，如：user:list,user:create)
     * @return perms 按钮权限(多个用逗号分隔，如：user:list,user:create)
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 按钮权限(多个用逗号分隔，如：user:list,user:create)
     * @param perms 按钮权限(多个用逗号分隔，如：user:list,user:create)
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * 排序
     * @return sequence 排序
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * 排序
     * @param sequence 排序
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 状态 0.已停用 1.正常
     * @return is_enable 状态 0.已停用 1.正常
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * 状态 0.已停用 1.正常
     * @param isEnable 状态 0.已停用 1.正常
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 是否删除（0.已删除 1.正常）
     * @return is_del 是否删除（0.已删除 1.正常）
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否删除（0.已删除 1.正常）
     * @param isDel 是否删除（0.已删除 1.正常）
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}