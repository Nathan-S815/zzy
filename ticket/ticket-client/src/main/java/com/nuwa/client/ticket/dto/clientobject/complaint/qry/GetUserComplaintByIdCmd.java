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
@ApiModel(value = "根据id获取投诉数据详情")
public class GetUserComplaintByIdCmd extends NuwaCommand {
    @ApiModelProperty(value = "主键id")
    private Long id;
}
