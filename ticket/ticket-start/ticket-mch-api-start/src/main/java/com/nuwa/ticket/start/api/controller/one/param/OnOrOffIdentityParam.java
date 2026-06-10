package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增身份接口
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OnOrOffIdentityParam extends NuwaCommand {
    @ApiModelProperty("编码")
    private String identityCode;
}
