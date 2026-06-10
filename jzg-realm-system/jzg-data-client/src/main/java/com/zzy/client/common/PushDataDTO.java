package com.zzy.client.common;


import lombok.Data;

@Data
public class PushDataDTO {

    /**服务代码*/
    private String serviceCode;

    /**推送的数据 json格式*/
    private String data;

    /**0 增加 1更新 2删除*/
    private String action;

    /**签名*/
    private String sign;

    /**时间戳*/
    private String timestamp;

    /**回调函数*/
    private String callback;

    /**主键字段（去重、删除需要判断）*/
    private String keys;

}
