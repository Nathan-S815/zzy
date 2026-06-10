package com.nuwa.ticket.start.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class DateTimeDTO {

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "HH:mm")
    private Date start;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "HH:mm")
    private Date end;
}
