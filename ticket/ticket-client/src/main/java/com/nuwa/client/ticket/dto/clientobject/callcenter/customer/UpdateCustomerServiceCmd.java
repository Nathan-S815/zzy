package com.nuwa.client.ticket.dto.clientobject.callcenter.customer;

import com.nuwa.client.ticket.dto.clientobject.callcenter.customer.co.UpdateCustomerServiceCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改客服基础数据")
public class UpdateCustomerServiceCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private UpdateCustomerServiceCO updateCustomerServiceCO;

    @ApiModelProperty(value = "AppId")
    private Long appId;

}
