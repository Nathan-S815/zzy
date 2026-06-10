package com.nuwa.ticket.start.api.controller.diy.param;

import cn.hutool.json.JSONArray;
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
public class SaveTemplateParam {

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appId不能为空")
    private Long appId;

    @ApiModelProperty(value = "类型 DIYVIEW_INDEX:主页 DIYVIEW_USER_CENTER:个人中心 DIYVIEW_BOTTOM_BAR:底部导航", required = true)
    @NotNull(message = "type不能为空")
    private String type;

    @ApiModelProperty(value = "value", required = true)
    @NotNull(message = "json数据")
    private JSONObject value;
}
