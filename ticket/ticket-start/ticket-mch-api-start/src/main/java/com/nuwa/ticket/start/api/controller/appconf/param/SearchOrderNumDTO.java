package com.nuwa.ticket.start.api.controller.appconf.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchOrderNumDTO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("orderNum")
    private Integer orderNum;
}
