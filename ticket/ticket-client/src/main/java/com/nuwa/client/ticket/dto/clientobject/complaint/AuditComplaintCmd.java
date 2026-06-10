package com.nuwa.client.ticket.dto.clientobject.complaint;

import com.nuwa.client.ticket.dto.clientobject.complaint.co.AuditComplaintCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "投诉处理")
public class AuditComplaintCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    private AuditComplaintCO auditComplaintCO;

    @ApiModelProperty(value = "主键id")
    private Long id;

    private Long appId;
}
