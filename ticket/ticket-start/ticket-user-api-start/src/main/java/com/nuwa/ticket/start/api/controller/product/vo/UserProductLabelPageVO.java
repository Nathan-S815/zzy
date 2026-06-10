package com.nuwa.ticket.start.api.controller.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hy
 */
@Data
public class UserProductLabelPageVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("最小价格")
    private BigDecimal price;

    private List<String> labels;

    @ApiModelProperty("已售数量")
    private Integer sellNumber;
}
