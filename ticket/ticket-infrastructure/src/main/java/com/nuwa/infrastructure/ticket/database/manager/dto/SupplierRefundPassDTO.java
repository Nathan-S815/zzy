package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRefundOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 供应商退款审核通过
 *
 * @author hy
 */
@Data
public class SupplierRefundPassDTO {
    @ApiModelProperty("供应商退款订单")
    private SupplierRefundOrder supplierRefundOrder;

    @ApiModelProperty("退款时间")
    private Date timeRefund;
}
