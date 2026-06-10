package com.nuwa.ticket.start.api.controller.product.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class ListMonthPriceParam {

    @JsonFormat(pattern = "yyyyMM")
    @ApiModelProperty("月份(yyyyMM)")
    private Integer month;

    @JsonFormat(pattern = "产品编号")
    @ApiModelProperty("产品编号")
    private Integer scenicProductId;


}
