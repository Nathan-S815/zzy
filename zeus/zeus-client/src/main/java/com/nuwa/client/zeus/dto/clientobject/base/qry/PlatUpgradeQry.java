package com.nuwa.client.zeus.dto.clientobject.base.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * GetCurrentUserAuthInfoQry
 *
 * @author hy
 * @date 2021/5/25 15:32
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询版本")
public class PlatUpgradeQry extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

}
