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
@ApiModel(value = "ProductOffLineParam 产品下架")
public class ProductOffLineParam extends NuwaCommand {

    @NotNull(message = "产品Id不能为空")
    private Long productId;
}
