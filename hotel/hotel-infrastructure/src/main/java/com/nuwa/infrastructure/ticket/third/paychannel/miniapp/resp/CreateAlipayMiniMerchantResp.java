package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "创建支付宝小程序-商户")
@Data
public class CreateAlipayMiniMerchantResp {
    private Integer mchId;
    private String name;
}
