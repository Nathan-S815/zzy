package com.nuwa.zeus.start.api.controller.plat.mch.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * GetMerchantSubGroupParam 获取商户已分配角色列表
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取商户已分配角色列表")
public class GetMerchantSubGroupParam extends NuwaCommand {

    @ApiModelProperty(value = "商户id", required = true)
    @NotNull(message = "商户id不能为空")
    private Long mchId;

    @ApiModelProperty(value = "应用id", required = true)
    @NotNull(message = "应用id不能为空")
    private Integer appId;

}
