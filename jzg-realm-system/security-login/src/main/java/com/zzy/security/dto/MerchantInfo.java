package com.zzy.security.dto;


import lombok.Data;

@Data
public class MerchantInfo {

    private String merchantTypeName;
    private String merchantName;
    private Integer baseId;

    /**是否审核*/
    private Integer auditState;
    /**是否填报基础信息*/
    private boolean isFillBase;

}
