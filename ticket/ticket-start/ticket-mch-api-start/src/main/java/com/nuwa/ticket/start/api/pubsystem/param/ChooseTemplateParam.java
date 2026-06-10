package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ChooseTemplateParam 小程序修改模板")
public class ChooseTemplateParam extends NuwaCommand {
    @ApiModelProperty("templateId")
    @NotBlank(message = "templateId不能为空")
    private String templateId;
}
