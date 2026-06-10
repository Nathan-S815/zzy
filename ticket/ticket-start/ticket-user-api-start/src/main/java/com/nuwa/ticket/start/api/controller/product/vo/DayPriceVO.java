package com.nuwa.ticket.start.api.controller.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class DayPriceVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date day;

    @ApiModelProperty("库存模式 0:非场次 1：场次")
    private Integer stockModel;

    @ApiModelProperty("是否可选")
    private Boolean enabled;

    @ApiModelProperty("价格")
    private String price;
}
