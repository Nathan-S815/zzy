package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 供应商支付成功
 *
 * @author hy
 */
@Data
public class SupplierPaySuccessDTO {
    @ApiModelProperty("供应商支付订单")
    private SupplierPaymentOrder supplierPaymentOrder;

    private String voucherCode;

    @ApiModelProperty("支付时间")
    private Date timePaid;

    private Integer times;
}
