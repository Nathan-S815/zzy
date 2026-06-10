package com.nuwa.attract.start.api.controller.travel.param;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户信息
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.travel.param:TravelTeamCustomerParam.java,v1.0.0 2022-09-15 09:30:08
 * nanHuang Exp $
 */

@Data
public class TravelTeamCustomerParam {
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
    @ApiModelProperty(value = "身份证", required = true)
    private String idcard;
}
