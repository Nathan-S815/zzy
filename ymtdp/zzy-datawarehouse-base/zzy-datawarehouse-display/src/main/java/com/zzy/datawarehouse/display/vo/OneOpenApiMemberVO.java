package com.zzy.datawarehouse.display.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/28
 * @Description: TODO
 */
@Data
@ApiModel(value = "景区一码通商家数据情况")
public class OneOpenApiMemberVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家数量")
    private Integer merchantsNum;

    @ApiModelProperty(value = "商家覆盖率")
    private String merchantsCoverage;

    @ApiModelProperty(value = "景区入园人数")
    private Integer visitorNum;

    @ApiModelProperty(value = "景区一码通使用人数")
    private Integer visitorOneOpenNum;

    @ApiModelProperty(value = "景区一码通使用率")
    private String oneOpenUsageRate;
}
