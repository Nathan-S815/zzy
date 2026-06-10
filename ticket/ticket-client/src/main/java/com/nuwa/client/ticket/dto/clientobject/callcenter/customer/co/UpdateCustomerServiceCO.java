package com.nuwa.client.ticket.dto.clientobject.callcenter.customer.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改客服基础数据")
public class UpdateCustomerServiceCO extends NuwaCO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "客服名称")
    private String name;

    @ApiModelProperty(value = "客服头像")
    private String pic;

    @ApiModelProperty(value = "备注")
    private String remark;
}
