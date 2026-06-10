package com.zzy.security.lib.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    private String headIcon;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0-未删除（默认）1-已删除
     */
    private Integer isDelete;

    /**
     * 是否可用(1:是,0:否)
     */
    private Integer isEnable;

    /**
     * 1:审核通过,2:未提交,3:已提交待审核
     */
    private Integer checkState;

    private String qq;

    private String email;


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    /**
     * pt_user_info
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
     * 用户名
     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 密码
     * @return pass_word 密码
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 密码(需要使用{@link com.zzy.core.utils.AuthUtil.getSaltedPwd｝的返回值填入)
     * @param passWord 密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 0-未删除（默认）1-已删除
     * @return is_delete 0-未删除（默认）1-已删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 0-未删除（默认）1-已删除
     * @param isDelete 0-未删除（默认）1-已删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 是否可用(1:是,0:否)
     * @return is_enable 是否可用(1:是,0:否)
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * 是否可用(1:是,0:否)
     * @param isEnable 是否可用(1:是,0:否)
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}