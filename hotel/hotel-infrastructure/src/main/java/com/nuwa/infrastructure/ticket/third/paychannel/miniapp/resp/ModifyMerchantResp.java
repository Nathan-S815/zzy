package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "修改-商户")
@Data
public class ModifyMerchantResp {
    private Integer mchId;
    private String name;
}
