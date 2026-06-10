package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ModifyAuthTokenParam 小程序修改authToken")
public class ModifyAuthTokenParam extends NuwaCommand {
    @ApiModelProperty("appAuthToken")
    @NotBlank(message = "appAuthToken不能为空")
    private String appAuthToken;
}
