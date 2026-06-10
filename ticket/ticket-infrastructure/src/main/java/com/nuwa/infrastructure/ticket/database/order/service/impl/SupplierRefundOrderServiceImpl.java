package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRefundOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.SupplierRefundOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierRefundOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供应商退款订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Slf4j
@Service
public class SupplierRefundOrderServiceImpl extends SuperServiceImpl<SupplierRefundOrderMapper, SupplierRefundOrder> implements SupplierRefundOrderService {

    @Autowired
    private SupplierRefundOrderMapper supplierRefundOrderMapper;

}
