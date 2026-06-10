package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 修改一码通端口信息
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyClientConfigParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("背景图片id")
    private String backgroundPictureId;

    @ApiModelProperty("背景颜色")
    private String backgroundColor;

    @ApiModelProperty("备注信息")
    private String remark;
}
