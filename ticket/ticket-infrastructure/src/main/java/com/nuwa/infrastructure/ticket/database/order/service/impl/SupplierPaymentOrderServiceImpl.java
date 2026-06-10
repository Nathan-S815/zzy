package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.mapper.SupplierPaymentOrderMapper;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierPaymentOrderService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供应商支付订单表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-15
 */
@Slf4j
@Service
public class SupplierPaymentOrderServiceImpl extends SuperServiceImpl<SupplierPaymentOrderMapper, SupplierPaymentOrder> implements SupplierPaymentOrderService {

    @Autowired
    private SupplierPaymentOrderMapper supplierPaymentOrderMapper;

}
