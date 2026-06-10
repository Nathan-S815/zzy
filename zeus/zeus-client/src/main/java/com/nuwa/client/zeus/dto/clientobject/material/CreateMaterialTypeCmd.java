package com.nuwa.client.zeus.dto.clientobject.material;

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
@ApiModel(value = "创建资源分类命令")
public class CreateMaterialTypeCmd extends NuwaCommand {

    @ApiModelProperty(value = "分类名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String name;

}
