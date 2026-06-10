package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotSupplier;
import com.nuwa.infrastructure.ticket.database.product.mapper.ScenicspotSupplierMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotSupplierService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区供应商 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class ScenicspotSupplierServiceImpl extends SuperServiceImpl<ScenicspotSupplierMapper, ScenicspotSupplier> implements ScenicspotSupplierService {

    @Autowired
    private ScenicspotSupplierMapper scenicspotSupplierMapper;

}
