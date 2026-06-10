package com.nuwa.ticket.start.api.controller.one.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EelectronicIdentityQrCodeVO {
    private Long id;
    private String clientName;
    private Integer memberId;
    private String qrCode;
    private String idName;
    private String idCardNo;
    private String appId;
    private String mobile;
    private Date lastUpdatetime;

    private List<IdentityRightsVO> identityRightsList;

}
