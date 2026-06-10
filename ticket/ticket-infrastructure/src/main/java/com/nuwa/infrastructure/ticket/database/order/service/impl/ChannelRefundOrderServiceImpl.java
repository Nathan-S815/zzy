package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.ChannelRefundOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.ChannelRefundOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 渠道退款订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-20
 */
@Slf4j
@Service
public class ChannelRefundOrderServiceImpl extends SuperServiceImpl<ChannelRefundOrderMapper, ChannelRefundOrder> implements ChannelRefundOrderService {

    @Autowired
    private ChannelRefundOrderMapper channelRefundOrderMapper;

}
