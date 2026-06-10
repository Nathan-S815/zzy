package com.nuwa.client.ticket.dto.clientobject.complaint;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据id删除投诉")
public class DelComplaintCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id 多个逗号分隔")
    private String id;

    private Long appId;
}
