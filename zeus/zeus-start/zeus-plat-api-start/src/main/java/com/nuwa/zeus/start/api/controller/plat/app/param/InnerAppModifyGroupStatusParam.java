package com.nuwa.zeus.start.api.controller.plat.app.param;

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
 * InnerAppModifyGroupStatusParam 修改角色状态
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改角色状态")
public class InnerAppModifyGroupStatusParam extends NuwaCommand {
    @ApiModelProperty(value = "ids", required = true)
    @NotNull(message = "ids")
    private List<Long> ids;

    @ApiModelProperty(value = "状态", required = true)
    @NotBlank(message = "状态 on | off")
    private String status;

}
