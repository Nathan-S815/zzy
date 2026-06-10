package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class RoleInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 功能名称
     */
    private String roleName;

    private String roleCode;

    /**
     * 是否启用 0-启用，1-未启用
     */
    private Integer isEnable;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * pt_role_info
     */
    private static final long serialVersionUID = 1L;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 功能名称
     * @return role_name 功能名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 功能名称
     * @param roleName 功能名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 是否启用 0-启用，1-未启用
     * @return is_enable 是否启用 0-启用，1-未启用
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * 是否启用 0-启用，1-未启用
     * @param isEnable 是否启用 0-启用，1-未启用
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 添加时间
     * @return create_time 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 添加时间
     * @param createTime 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}