package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "修改支付宝小程序-商户")
@Data
public class ModifyAlipayMiniMerchantResp {
    private Integer mchId;
    private String name;
}
