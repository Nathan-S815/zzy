package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRefundOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class SupplierRefundRejectDTO {
    @ApiModelProperty("供应商退款订单")
    private SupplierRefundOrder supplierRefundOrder;

    private String reason;
}
