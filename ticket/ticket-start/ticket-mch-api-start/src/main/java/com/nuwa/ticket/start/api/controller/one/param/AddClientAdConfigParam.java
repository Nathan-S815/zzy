package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增一码通广告
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddClientAdConfigParam extends NuwaCommand {

    @ApiModelProperty("一码通客户端id")
    private Long oneClientId;

    @ApiModelProperty("广告名称")
    @NotBlank(message = "广告名称不能为空")
    private String title;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("调整链接")
    private String link;
}
