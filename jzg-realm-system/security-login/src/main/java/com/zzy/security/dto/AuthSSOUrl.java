package com.zzy.security.dto;

public class AuthSSOUrl {
    //  http://<PUBLIC_HOST>:8081/#/wordMouth
    private String appkey;
    private String timestamp;
    private String sign;
    private String username;
    private String service;


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
