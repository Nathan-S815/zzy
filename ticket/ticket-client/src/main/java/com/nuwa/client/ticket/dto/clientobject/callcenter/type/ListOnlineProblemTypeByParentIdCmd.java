package com.nuwa.client.ticket.dto.clientobject.callcenter.type;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据父id获取问题类型")
public class ListOnlineProblemTypeByParentIdCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "父id")
    private Long parentId;
}
