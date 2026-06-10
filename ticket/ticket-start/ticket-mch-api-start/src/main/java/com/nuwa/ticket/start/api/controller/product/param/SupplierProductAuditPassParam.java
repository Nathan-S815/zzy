package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SupplierProductAuditPassParam 分销产品审核通过")
public class SupplierProductAuditPassParam extends NuwaCommand {

    @ApiModelProperty("产品分销id")
    private Long productDistributeId;

    @ApiModelProperty("备注")
    private String remark;
}
