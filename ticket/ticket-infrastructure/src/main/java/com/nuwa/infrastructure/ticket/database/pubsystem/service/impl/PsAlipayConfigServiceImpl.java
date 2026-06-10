package com.nuwa.infrastructure.ticket.database.pubsystem.service.impl;

import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.mapper.PsAlipayConfigMapper;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝模板参数配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-06
 */
@Slf4j
@Service
public class PsAlipayConfigServiceImpl extends SuperServiceImpl<PsAlipayConfigMapper, PsAlipayConfig> implements PsAlipayConfigService {

    @Autowired
    private PsAlipayConfigMapper psAlipayConfigMapper;

}
