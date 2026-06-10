package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "创建微信小程序-商户")
@Data
public class CreateWeiXinMiniMerchantResp {
    private Integer mchId;
    private String name;
}
