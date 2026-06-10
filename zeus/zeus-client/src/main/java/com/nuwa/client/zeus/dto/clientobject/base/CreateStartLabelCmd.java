package com.nuwa.client.zeus.dto.clientobject.base;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
@ApiModel(value = "创建新手入门标签-命令")
public class CreateStartLabelCmd extends NuwaCommand {

    @ApiModelProperty(value = "标签名称", required = true)
    @NotBlank(message = "标签名称不能为空")
    private String label;

    @ApiModelProperty(value = "父ID,顶级传-1", required = true)
    @NotNull(message = "父ID不能为空")
    private Long pid;

}
