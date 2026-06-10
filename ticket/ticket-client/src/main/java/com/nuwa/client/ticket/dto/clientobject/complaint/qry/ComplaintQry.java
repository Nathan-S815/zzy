package com.nuwa.client.ticket.dto.clientobject.complaint.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "投诉查询")
public class ComplaintQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    private Long appId;
}
