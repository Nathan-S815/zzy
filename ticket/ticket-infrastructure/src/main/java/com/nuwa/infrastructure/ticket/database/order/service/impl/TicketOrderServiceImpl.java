package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.TicketOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-23
 */
@Slf4j
@Service
public class TicketOrderServiceImpl extends SuperServiceImpl<TicketOrderMapper, TicketOrder> implements TicketOrderService {

    @Autowired
    private TicketOrderMapper ticketOrderMapper;

}
