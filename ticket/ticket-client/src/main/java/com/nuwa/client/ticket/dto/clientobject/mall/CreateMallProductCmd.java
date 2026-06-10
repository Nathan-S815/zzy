package com.nuwa.client.ticket.dto.clientobject.mall;

import com.nuwa.client.ticket.dto.clientobject.mall.co.CreateMallProductCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "添加商品命令")
public class CreateMallProductCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private CreateMallProductCO createMallProductCO;

    private Long appId;

}
