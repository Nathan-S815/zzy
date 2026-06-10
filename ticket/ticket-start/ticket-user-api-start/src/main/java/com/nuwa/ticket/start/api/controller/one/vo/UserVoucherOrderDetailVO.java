package com.nuwa.ticket.start.api.controller.one.vo;

import com.nuwa.infrastructure.ticket.database.order.entity.OrderVoucher;
import lombok.Data;

import java.util.List;

@Data
public class UserVoucherOrderDetailVO {
    private UserVoucherOrderPoiListVO voucherOrderVO;
    private List<OrderVoucher> orderVoucherList;
}
