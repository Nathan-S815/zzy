package com.nuwa.client.ticket.dto.clientobject.callcenter.center;

import com.nuwa.client.ticket.dto.clientobject.callcenter.center.co.CreateCallCenterCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增呼叫中心数据")
public class CreateCallCenterCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    private CreateCallCenterCO createCallCenterCO;
    private Long appId;
}
