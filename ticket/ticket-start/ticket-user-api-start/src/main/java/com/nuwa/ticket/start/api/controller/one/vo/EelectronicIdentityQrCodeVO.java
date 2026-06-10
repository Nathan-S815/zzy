package com.nuwa.ticket.start.api.controller.one.vo;

import lombok.Data;

import java.util.Date;

@Data
public class EelectronicIdentityQrCodeVO {
    private Integer memberId;
    private String qrCode;
    private Date lastUpdatetime;
    private String idName;
    private String idCardNo;
    private String realIdCardNo;
    private Boolean oldPeople;
    private String appId;
    private String mobile;
}
