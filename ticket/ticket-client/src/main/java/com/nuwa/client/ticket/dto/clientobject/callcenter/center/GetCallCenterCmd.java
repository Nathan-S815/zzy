package com.nuwa.client.ticket.dto.clientobject.callcenter.center;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据id获取呼叫中心数据")
public class GetCallCenterCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id")
    private Long id;
}
