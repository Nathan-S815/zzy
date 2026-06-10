package com.nuwa.zeus.start.api.controller.plat.app.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * GetAppUpdateLogByIdParam
 *
 * @author hy
 * @date 2021/5/25 15:32
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据id获取版本")
public class GetAppUpdateLogByIdParam extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

}
