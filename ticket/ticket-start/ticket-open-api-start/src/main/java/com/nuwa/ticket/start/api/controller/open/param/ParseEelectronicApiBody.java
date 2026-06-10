package com.nuwa.ticket.start.api.controller.open.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParseEelectronicApiBody extends ApiBody {
    @NotBlank(message = "身份码编码")
    @ApiModelProperty("身份码编码")
    private String qrCode;

   // @NotBlank(message = "身份码编码")
    @ApiModelProperty("景区编码")
    private String scenicspotId;
}
