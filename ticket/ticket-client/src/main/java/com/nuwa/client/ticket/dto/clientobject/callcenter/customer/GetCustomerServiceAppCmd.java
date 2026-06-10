package com.nuwa.client.ticket.dto.clientobject.callcenter.customer;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取客服基础数据")
public class GetCustomerServiceAppCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
}
