package com.nuwa.ticket.start.api.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductRefundRuleConfigDTO 产品退款规则")
public class ProductRefundRuleConfigDTO {
    @ApiModelProperty("退款模式 0:随心退 1：不可退款")
    private Integer refundMode;

    @ApiModelProperty("退款补充说明")
    private String instruction;
}
