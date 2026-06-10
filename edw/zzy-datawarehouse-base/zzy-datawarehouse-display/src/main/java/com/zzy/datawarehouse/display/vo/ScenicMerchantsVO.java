package com.zzy.datawarehouse.display.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/29
 * @Description: TODO
 */
@Data
@ApiModel(value = "ScenicMerchantsVO")
public class ScenicMerchantsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "累计入驻")
    private Integer merchantEnterNum;

    @ApiModelProperty(value = "商家总数")
    private Integer merchantNum;

    @ApiModelProperty(value = "入驻率")
    private String enterRate;
}
