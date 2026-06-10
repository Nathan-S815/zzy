package com.nuwa.client.ticket.dto.clientobject.callcenter.talk;

import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.co.UpdateTalkManageCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改会话数据管理")
public class UpdateTalkManageCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private UpdateTalkManageCO updateTalkManageCO;

    @ApiModelProperty(value = "AppId")
    private Long appId;
}
