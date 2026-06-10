package com.nuwa.ticket.start.api.alipaydata.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataScenicSyncParam {
    @ApiModelProperty("景区小程序APPID")
    private String scenicAppId;

    @ApiModelProperty("服务商景区Id")
    private String scenicId;

    @ApiModelProperty("支付宝景区Id")
    private String alipayScenicId;
}
