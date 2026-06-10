package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 供应商出票通知
 *
 * @author hy
 */
@Data
public class SupplierTicketSuccessDTO {
    @ApiModelProperty("供应商支付订单")
    private SupplierPaymentOrder supplierPaymentOrder;

    private List<VoucherItem> voucherItems;

    @Data
    public static class VoucherItem {
        private String voucherCode;
        private String iDCardNo;
        private String mobile;
        private String name;
    }
}
