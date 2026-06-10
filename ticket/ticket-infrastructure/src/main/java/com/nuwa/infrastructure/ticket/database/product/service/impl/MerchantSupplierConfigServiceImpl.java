package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantSupplierConfigMapper;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantSupplierConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户供应商配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class MerchantSupplierConfigServiceImpl extends SuperServiceImpl<MerchantSupplierConfigMapper, MerchantSupplierConfig> implements MerchantSupplierConfigService {

    @Autowired
    private MerchantSupplierConfigMapper merchantSupplierConfigMapper;

}
