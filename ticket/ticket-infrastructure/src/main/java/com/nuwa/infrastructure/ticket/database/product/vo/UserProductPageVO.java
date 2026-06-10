package com.nuwa.infrastructure.ticket.database.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hy
 */
@Data
public class UserProductPageVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("最小价格")
    private BigDecimal price;

    private Integer refundMode;

    private Integer entranceMode;

    @ApiModelProperty("已售数量")
    private Integer sellNumber;
}
