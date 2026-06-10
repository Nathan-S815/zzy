package com.nuwa.infrastructure.attract.database.invoice.service.impl;

import com.nuwa.infrastructure.attract.database.invoice.entity.Invoice;
import com.nuwa.infrastructure.attract.database.invoice.mapper.InvoiceMapper;
import com.nuwa.infrastructure.attract.database.invoice.service.InvoiceService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-11-09
 */
@Slf4j
@Service
public class InvoiceServiceImpl extends SuperServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

}
