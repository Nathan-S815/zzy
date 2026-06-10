package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrderLog;
import com.nuwa.infrastructure.ticket.database.order.mapper.TicketOrderLogMapper;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderLogService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单日志 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-24
 */
@Slf4j
@Service
public class TicketOrderLogServiceImpl extends SuperServiceImpl<TicketOrderLogMapper, TicketOrderLog> implements TicketOrderLogService {

    @Autowired
    private TicketOrderLogMapper ticketOrderLogMapper;

}
