package com.nuwa.client.ticket.dto.clientobject.mall;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "上架商品命令")
public class OnMallProductCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "多个Id逗号分隔")
    private String ids;

    private Long appId;

}
