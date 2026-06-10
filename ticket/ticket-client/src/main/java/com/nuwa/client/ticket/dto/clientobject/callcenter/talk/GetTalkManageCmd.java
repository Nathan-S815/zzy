package com.nuwa.client.ticket.dto.clientobject.callcenter.talk;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取会话管理数据")
public class GetTalkManageCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "AppId")
    private Long appId;
}
