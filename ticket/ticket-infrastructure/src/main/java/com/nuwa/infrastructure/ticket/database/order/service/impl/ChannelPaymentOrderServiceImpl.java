package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.ChannelPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.ChannelPaymentOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.ChannelPaymentOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 渠道支付订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-20
 */
@Slf4j
@Service
public class ChannelPaymentOrderServiceImpl extends SuperServiceImpl<ChannelPaymentOrderMapper, ChannelPaymentOrder> implements ChannelPaymentOrderService {

    @Autowired
    private ChannelPaymentOrderMapper channelPaymentOrderMapper;

}
