package com.nuwa.ticket.start.api.controller.promote.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hy
 */
@Data
public class UserPromoteTotalPageVO {

    @ApiModelProperty("景区名id")
    private String scenicId;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("展示主图")
    @JsonSerialize(using = MaterialJson.class)
    private String mainPicture;

    @ApiModelProperty("订单数量")
    private Integer orderCount;

    @ApiModelProperty("订单总金额")
    private BigDecimal totalAmount;
}
