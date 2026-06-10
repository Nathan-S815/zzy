package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import lombok.Data;

import java.util.Date;

/**
 * 供应商核销成功处理
 *
 * @author hy
 */
@Data
public class SupplierConsumerSuccessDTO {
    private Integer totalCount;

    private Integer checkCount;

    private SupplierPaymentOrder supplierPaymentOrder;

    private Date timeConsumer;
}
