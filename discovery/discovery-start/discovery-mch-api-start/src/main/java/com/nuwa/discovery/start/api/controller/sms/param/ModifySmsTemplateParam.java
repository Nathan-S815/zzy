package com.nuwa.discovery.start.api.controller.sms.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ModifySmsTemplateParam 修改短信模板参数")
public class ModifySmsTemplateParam {
    @ApiModelProperty("模板内容")
    @NotBlank(message = "模板内容不能为空")
    private String content;

    @ApiModelProperty("模板标题")
    @NotBlank(message = "模板标题不能为空")
    private String title;

    @ApiModelProperty("业务类型")
    @NotBlank(message = "业务类型不能为空")
    private String bizCode;
}
