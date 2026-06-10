package com.nuwa.ticket.start.api.controller.diy.param;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 保存装修数据
 *
 * @author hy
 */
@Data
@ApiModel(value = "SaveTemplateParam 保存装修数据")
public class PublishTemplateParam {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
