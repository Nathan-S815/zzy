package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MerchantSubUserAllotGroupParam 商户子用户分配角色
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户子用户分配角色")
public class MerchantSubUserAllotGroupParam extends NuwaCommand {

    @ApiModelProperty(value = "子用户id", required = true)
    @NotNull(message = "子用户id不能为空")
    private Integer userId;

    @ApiModelProperty(value = "应用id", required = true)
    @NotNull(message = "应用id不能为空")
    private Integer appId;

    @ApiModelProperty(value = "分配的角色id", required = true)
    private List<Integer> groupList;

}
