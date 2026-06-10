package com.nuwa.client.ticket.dto.clientobject.mall.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "添加规格")
public class CreateMallStoreCO extends NuwaCO {
    @ApiModelProperty(value = "门店Id")
    private Long id;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;
}
