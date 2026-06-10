package com.nuwa.infrastructure.ticket.database.order.service.impl;

import com.nuwa.infrastructure.ticket.database.order.entity.SupplierRequestLog;
import com.nuwa.infrastructure.ticket.database.order.mapper.SupplierRequestLogMapper;
import com.nuwa.infrastructure.ticket.database.order.service.SupplierRequestLogService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供应商请求日志 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-21
 */
@Slf4j
@Service
public class SupplierRequestLogServiceImpl extends SuperServiceImpl<SupplierRequestLogMapper, SupplierRequestLog> implements SupplierRequestLogService {

    @Autowired
    private SupplierRequestLogMapper supplierRequestLogMapper;

}
