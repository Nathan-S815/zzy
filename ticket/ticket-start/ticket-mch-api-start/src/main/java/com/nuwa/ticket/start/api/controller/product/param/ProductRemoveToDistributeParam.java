package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProductRemoveToDistributeParam 从我的分销产品中移除")
public class ProductRemoveToDistributeParam extends NuwaCommand {

    @ApiModelProperty("产品分销id(productDistributeId)")
    @NotNull(message = "最少选择一个Id")
    @Size(min = 1)
    private List<Long> productDistributeIds;
}
