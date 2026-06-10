package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.*;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "ProductAppendToDistributeParam 添加到我的分销")
public class ProductAppendToDistributeParam extends NuwaCommand {
    @NotNull(message = "最少选择一个产品")
    @Size(min = 1)
    private List<Long> productIds;
}
