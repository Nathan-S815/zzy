package com.nuwa.infrastructure.ticket.database.gateway.service.impl;

import com.nuwa.infrastructure.ticket.database.gateway.entity.MerchantPayGatewayConfig;
import com.nuwa.infrastructure.ticket.database.gateway.mapper.MerchantPayGatewayConfigMapper;
import com.nuwa.infrastructure.ticket.database.gateway.service.MerchantPayGatewayConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付中心配置参数 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-04-25
 */
@Slf4j
@Service
public class MerchantPayGatewayConfigServiceImpl extends SuperServiceImpl<MerchantPayGatewayConfigMapper, MerchantPayGatewayConfig> implements MerchantPayGatewayConfigService {

    @Autowired
    private MerchantPayGatewayConfigMapper merchantPayGatewayConfigMapper;

}
