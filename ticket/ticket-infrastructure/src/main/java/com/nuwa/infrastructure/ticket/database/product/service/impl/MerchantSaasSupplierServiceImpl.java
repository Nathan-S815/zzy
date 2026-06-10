package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSaasSupplier;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantSaasSupplierMapper;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSaasSupplierService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户saas平台供应商信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-02-15
 */
@Slf4j
@Service
public class MerchantSaasSupplierServiceImpl extends SuperServiceImpl<MerchantSaasSupplierMapper, MerchantSaasSupplier> implements MerchantSaasSupplierService {

    @Autowired
    private MerchantSaasSupplierMapper merchantSaasSupplierMapper;

}
