package com.nuwa.client.ticket.dto.clientobject.activity;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "C端取消活动报名")
public class OffUserCultureActivityApplyCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    private Long id;
}
