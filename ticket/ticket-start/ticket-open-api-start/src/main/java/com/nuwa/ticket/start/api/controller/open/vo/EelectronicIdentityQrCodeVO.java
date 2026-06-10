package com.nuwa.ticket.start.api.controller.open.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EelectronicIdentityQrCodeVO {
    @ApiModelProperty("二维码")
    private String qrCode;

    @ApiModelProperty("最后更新时间")
    private Date lastUpdatetime;

    @ApiModelProperty("身份证姓名")
    private String idName;

    @ApiModelProperty("身份证号码")
    private String idCardNo;
}
