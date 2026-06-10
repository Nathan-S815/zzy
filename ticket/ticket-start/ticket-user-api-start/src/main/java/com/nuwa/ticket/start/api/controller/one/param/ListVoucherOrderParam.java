package com.nuwa.ticket.start.api.controller.one.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ListVoucherOrderParam {

    @ApiModelProperty("商户id")
    private Long mchId;

}
