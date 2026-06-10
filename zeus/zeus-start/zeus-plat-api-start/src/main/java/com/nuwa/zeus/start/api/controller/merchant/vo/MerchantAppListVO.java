package com.nuwa.zeus.start.api.controller.merchant.vo;

import lombok.Data;

@Data
public class MerchantAppListVO {
    private Long id;

    private String appName;

    private Long appId;

    private String provider;

    private Integer appType;

    private Integer orderNum;

    private Integer ssh;
}
