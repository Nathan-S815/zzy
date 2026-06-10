package com.nuwa.zeus.start.api.controller.plat.app.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * ModifyMenuOrderNumParam 修改菜单权重
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改菜单权重")
public class ModifyMenuOrderNumParam extends NuwaCommand {
    @ApiModelProperty(value = "菜单id", required = true)
    @NotNull(message = "id")
    private Long id;

    @ApiModelProperty(value = "排序字段", required = true)
    private Integer orderNum;

}
