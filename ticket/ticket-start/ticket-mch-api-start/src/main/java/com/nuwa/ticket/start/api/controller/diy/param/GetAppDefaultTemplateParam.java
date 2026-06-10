package com.nuwa.ticket.start.api.controller.diy.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取商户应用的默认模板
 *
 * @author hy
 */
@Data
@ApiModel(value = "GetAppDefaultTemplateParam 获取商户应用的默认模板")
public class GetAppDefaultTemplateParam {

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appId不能为空")
    private Long appId;

    @ApiModelProperty("类型 DIYVIEW_INDEX:主页 DIYVIEW_USER_CENTER:个人中心 DIYVIEW_BOTTOM_BAR:底部导航")
    private String type;
}
