package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

import java.util.List;

/**
 * 出票成功处理
 *
 * @author hy
 */
@Data
public class B2bSupplierTicketNotifyParam {
    /**
     * 供应商平台订单号
     */
    private String paymentNo;

    private List<VoucherDTO> voucherItems;

    private String ticketStatus;

    private String requestStr;

    private String responseStr;
}
