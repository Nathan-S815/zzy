package com.nuwa.ticket.start.api.biz.ret;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class PlaceOrderRet {
    public Long orderId;
    private SupplierPaymentOrder supplierPaymentOrder;
}
