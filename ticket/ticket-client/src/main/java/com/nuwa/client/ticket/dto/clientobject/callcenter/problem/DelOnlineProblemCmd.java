package com.nuwa.client.ticket.dto.clientobject.callcenter.problem;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "删除在线问题")
public class DelOnlineProblemCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ids")
    private List<Long> ids;
}
