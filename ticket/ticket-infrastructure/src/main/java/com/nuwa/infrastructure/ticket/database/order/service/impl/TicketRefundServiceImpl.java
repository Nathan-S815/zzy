package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import com.nuwa.infrastructure.ticket.database.order.mapper.TicketRefundMapper;
import com.nuwa.infrastructure.ticket.database.order.service.TicketRefundService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 退款表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-23
 */
@Slf4j
@Service
public class TicketRefundServiceImpl extends SuperServiceImpl<TicketRefundMapper, TicketRefund> implements TicketRefundService {

    @Autowired
    private TicketRefundMapper ticketRefundMapper;

}
