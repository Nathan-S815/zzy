package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.OrderVoucher;
import com.nuwa.infrastructure.ticket.database.order.mapper.OrderVoucherMapper;
import com.nuwa.infrastructure.ticket.database.order.service.OrderVoucherService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单核销凭证表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Slf4j
@Service
public class OrderVoucherServiceImpl extends SuperServiceImpl<OrderVoucherMapper, OrderVoucher> implements OrderVoucherService {

    @Autowired
    private OrderVoucherMapper orderVoucherMapper;

}
