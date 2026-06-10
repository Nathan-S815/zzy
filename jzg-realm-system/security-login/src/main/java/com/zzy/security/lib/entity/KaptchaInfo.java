package com.zzy.security.lib.entity;

import java.io.Serializable;
import java.util.Date;

public class KaptchaInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String capText;

    /**
     * 
     */
    private String capToken;

    /**
     * 
     */
    private Date createTime;

    /**
     * security_login..kaptcha_info
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
     * @return cap_text 
     */
    public String getCapText() {
        return capText;
    }

    /**
     * 
     * @param capText 
     */
    public void setCapText(String capText) {
        this.capText = capText;
    }

    /**
     * 
     * @return cap_token 
     */
    public String getCapToken() {
        return capToken;
    }

    /**
     * 
     * @param capToken 
     */
    public void setCapToken(String capToken) {
        this.capToken = capToken;
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