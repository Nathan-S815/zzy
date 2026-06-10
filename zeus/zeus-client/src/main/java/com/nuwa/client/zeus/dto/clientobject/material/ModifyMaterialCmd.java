package com.nuwa.client.zeus.dto.clientobject.material;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

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
@ApiModel(value = "修改资源分类命令")
public class ModifyMaterialCmd extends NuwaCommand {

    @ApiModelProperty(value = "资源ID,多个逗号分隔", required = true)
    @NotBlank(message = "ID不能为空")
    private String ids;

    @ApiModelProperty(value = "分类ID，不传移除")
    private Long typeId;

}
