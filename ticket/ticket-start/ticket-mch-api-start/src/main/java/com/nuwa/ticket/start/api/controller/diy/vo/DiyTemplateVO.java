package com.nuwa.ticket.start.api.controller.diy.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 默认模板VO
 *
 * @author hy
 */
@Data
public class DiyTemplateVO {
    private Integer id;

    @ApiModelProperty("模板名称")
    private String title;

    @ApiModelProperty("模板标识")
    private String mark;

    @ApiModelProperty("类型 DIYVIEW_INDEX:主页 DIYVIEW_USER_CENTER:个人中心 DIYVIEW_BOTTOM_BAR:底部导航")
    private String type;

    @ApiModelProperty("图片")
    @JsonSerialize(using = MaterialJson.class)
    private Long image;

    @ApiModelProperty("是否默认")
    private Boolean defaultFlag;
}
