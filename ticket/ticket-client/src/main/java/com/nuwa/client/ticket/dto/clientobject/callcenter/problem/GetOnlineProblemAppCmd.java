package com.nuwa.client.ticket.dto.clientobject.callcenter.problem;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据问题类别，类型，问题获取在线问题回答")
public class GetOnlineProblemAppCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "问题")
    private String problem;

}
