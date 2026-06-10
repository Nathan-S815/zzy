package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "创建-商户")
@Data
public class CreateMerchantResp {
    private Integer mchId;
    private String name;
}
