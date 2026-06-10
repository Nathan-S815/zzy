package com.nuwa.client.ticket.dto.clientobject.mall.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "发货")
public class SendTradeCO extends NuwaCO {

    @ApiModelProperty(value = "物流公司")
    private Integer expressCompany;

    @ApiModelProperty(value = "物流编号")
    private String expressNo;

    @ApiModelProperty(value = "备注")
    private String remarks;
}


