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
@ApiModel(value = "创建分组-命令")
public class CreateBaseGroupCmd extends NuwaCommand {

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "上级节点", required = false)
    private Integer parentId = -1;

    @ApiModelProperty(value = "角色组类型", required = true)
    @NotNull(message = "角色组类型不能为空")
    private Integer groupType;

    /**
     * 租户id(-1:总平台)
     */
    @ApiModelProperty(value = "租户id", required = false)
    private Long tenantId = -1L;

    @ApiModelProperty(value = "描述", required = false)
    private String description;
}
