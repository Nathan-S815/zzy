package com.nuwa.ticket.start.api.controller.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class DaySessionVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("是否可选")
    private Boolean enabled;

    @ApiModelProperty("场次名称")
    private String title;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("结束时间")
    private String end;

}
