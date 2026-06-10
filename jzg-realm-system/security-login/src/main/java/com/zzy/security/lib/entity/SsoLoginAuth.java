package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class SsoLoginAuth implements Serializable {
    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String appKey;

    /**
     * 
     */
    private String secretKey;

    /**
     * 
     */
    private Date updateTime;

    /**
     * putuo_security..sso_login_auth
     */
    private static final long serialVersionUID = 1L;

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
     * @return app_key 
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 
     * @param appKey 
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 
     * @return secret_key 
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 
     * @param secretKey 
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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