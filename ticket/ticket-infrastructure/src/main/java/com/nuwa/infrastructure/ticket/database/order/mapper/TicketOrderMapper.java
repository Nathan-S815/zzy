package com.nuwa.infrastructure.ticket.database.order.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;

import org.springframework.stereotype.Repository;


/**
 * 订单表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-04-15
 */
@Repository
public interface TicketOrderMapper extends SuperMapper<TicketOrder> {


}
