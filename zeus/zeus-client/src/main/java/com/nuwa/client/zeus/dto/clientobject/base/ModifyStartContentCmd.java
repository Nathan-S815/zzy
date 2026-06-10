package com.nuwa.client.zeus.dto.clientobject.base;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CreateBaseGroupCmd 创建分组
 *
 * @author hy
 * @date 2021/5/25 17:13
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改新手入门正文-命令")
public class ModifyStartContentCmd extends NuwaCommand {

    @ApiModelProperty(value = "标签ID", required = true)
    @NotNull(message = "标签ID不能为空")
    private Long id;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "正文内容", required = true)
    @NotBlank(message = "正文内容不能为空")
    private String contentDetail;

}
