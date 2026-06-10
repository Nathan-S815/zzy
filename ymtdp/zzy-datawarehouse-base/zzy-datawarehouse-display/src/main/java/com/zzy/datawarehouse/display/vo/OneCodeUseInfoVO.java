package com.zzy.datawarehouse.display.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/30
 * @Description: TODO
 */
@Data
@ApiModel(value = "一码通使用数据")
public class OneCodeUseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "使用人数")
    private Integer userCount;

    @ApiModelProperty(value = "入园人数")
    private Integer visitorNum;

    @ApiModelProperty(value = "使用率")
    private String utilizationRate;
}
