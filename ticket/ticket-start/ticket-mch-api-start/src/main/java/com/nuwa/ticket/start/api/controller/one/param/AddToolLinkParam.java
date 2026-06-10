package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增功能链接
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddToolLinkParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("功能名称")
    private String toolName;

    @ApiModelProperty("链接名称")
    private String linkName;

    @ApiModelProperty("url路径")
    private String linkUrl;

    @ApiModelProperty("跳转方式 inner_page(内部页面),outer_page(外部页面)")
    private String jumpType;

    @ApiModelProperty("图标")
    private Long iconId;
}
