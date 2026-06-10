package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class RolePermissionKey implements Serializable {
    /**
     * 
     */
    private Integer roleId;

    /**
     * 
     */
    private Integer permissionId;

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * putuo_security.._role_permission
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return role_id 
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 
     * @param roleId 
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 
     * @return permission_id 
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 
     * @param permissionId 
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}