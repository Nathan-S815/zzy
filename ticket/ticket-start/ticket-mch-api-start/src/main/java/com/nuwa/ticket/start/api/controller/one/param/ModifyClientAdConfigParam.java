package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 新增一码通广告
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyClientAdConfigParam extends NuwaCommand {

    @ApiModelProperty("广告id")
    private Long adId;

    @ApiModelProperty("广告名称")
    @NotBlank(message = "广告名称不能为空")
    private String title;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("调整链接")
    private String link;
}
