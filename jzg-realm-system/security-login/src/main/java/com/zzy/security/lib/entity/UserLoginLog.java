package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class UserLoginLog implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String loginAction;

    /**
     * 
     */
    private Integer loginNumber = 0;

    /**
     * 
     */
    private Date loginTime;

    /**
     * 
     */
    private String loginAddr;

    /**
     * user_login_log
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
     * @return user_name 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName 
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return login_action 
     */
    public String getLoginAction() {
        return loginAction;
    }

    /**
     * 
     * @param loginAction 
     */
    public void setLoginAction(String loginAction) {
        this.loginAction = loginAction;
    }

    /**
     * 
     * @return login_number 
     */
    public Integer getLoginNumber() {
        return loginNumber;
    }

    /**
     * 
     * @param loginNumber 
     */
    public void setLoginNumber(Integer loginNumber) {
        this.loginNumber = loginNumber;
    }

    /**
     * 
     * @return login_time 
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 
     * @param loginTime 
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 
     * @return login_addr 
     */
    public String getLoginAddr() {
        return loginAddr;
    }

    /**
     * 
     * @param loginAddr 
     */
    public void setLoginAddr(String loginAddr) {
        this.loginAddr = loginAddr;
    }
}