package com.nuwa.client.ticket.dto.clientobject.callcenter.type;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取所有问题类别和类型列表")
public class ListAllOnlineProblemTypeByListCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    private Long appId;
}
