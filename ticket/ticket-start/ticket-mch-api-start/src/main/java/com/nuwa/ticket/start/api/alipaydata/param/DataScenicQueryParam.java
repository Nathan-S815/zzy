package com.nuwa.ticket.start.api.alipaydata.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataScenicQueryParam {
    @ApiModelProperty("景区小程序APPID")
    private String scenicAppId;
}
