package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.DouyinSettleOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.DouyinSettleOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.DouyinSettleOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抖音结算订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-01-12
 */
@Slf4j
@Service
public class DouyinSettleOrderServiceImpl extends SuperServiceImpl<DouyinSettleOrderMapper, DouyinSettleOrder> implements DouyinSettleOrderService {

    @Autowired
    private DouyinSettleOrderMapper douyinSettleOrderMapper;

}
