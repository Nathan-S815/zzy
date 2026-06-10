package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.ServiceOpenApiConfig;
import com.nuwa.infrastructure.ticket.database.one.mapper.ServiceOpenApiConfigMapper;
import com.nuwa.infrastructure.ticket.database.one.service.ServiceOpenApiConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通服务商开放API 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-28
 */
@Slf4j
@Service
public class ServiceOpenApiConfigServiceImpl extends SuperServiceImpl<ServiceOpenApiConfigMapper, ServiceOpenApiConfig> implements ServiceOpenApiConfigService {

    @Autowired
    private ServiceOpenApiConfigMapper serviceOpenApiConfigMapper;

}
