package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class UserRole implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 功能
     */
    private Integer roleId;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * pt_user_role
     */
    private static final long serialVersionUID = 1L;

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
     * 用户id
     * @return user_id 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 功能
     * @return role_id 功能
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 功能
     * @param roleId 功能
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 创建时间
     * @return creation_time 创建时间
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 创建时间
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public static UserRole build(Integer userId, Integer roleId){
        UserRole ur = new UserRole();
        ur.setRoleId(roleId);
        ur.setUserId(userId);
        ur.setCreationTime(new Date());
        return ur;
    }
}