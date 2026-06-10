package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户解绑")
public class UnBindShipThirdUserParam extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotBlank(message = "id")
    private Long id;

}
