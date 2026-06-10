package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DistributeUpdateWeightProductParam 分销商修改产品权重")
public class DistributeUpdateWeightProductParam extends NuwaCommand {

    @NotNull(message = "产品Id不能为空")
    private Long id;

    @NotNull(message = "产品权重不能为空")
    private Integer weight;

    @Override
    public String toJson() {
        return super.toJson();
    }
}
