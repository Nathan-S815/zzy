package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class PermissionInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String permissionName;

    /**
     * 
     */
    private String permissionCode;

    /**
     * 
     */
    private String permissionUrl;

    /**
     * 
     */
    private Integer isEnable;

    /**
     * 
     */
    private Date createTime;

    /**
     * pt_permission_info
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
     * 
     * @return permission_name 
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * 
     * @param permissionName 
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * 
     * @return permission_code 
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 
     * @param permissionCode 
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * 
     * @return permission_url 
     */
    public String getPermissionUrl() {
        return permissionUrl;
    }

    /**
     * 
     * @param permissionUrl 
     */
    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    /**
     * 
     * @return is_enable 
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * 
     * @param isEnable 
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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
}