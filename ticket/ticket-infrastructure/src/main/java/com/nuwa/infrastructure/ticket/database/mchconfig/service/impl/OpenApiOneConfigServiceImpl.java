package com.nuwa.infrastructure.ticket.database.mchconfig.service.impl;

import com.nuwa.infrastructure.ticket.database.mchconfig.entity.OpenApiOneConfig;
import com.nuwa.infrastructure.ticket.database.mchconfig.mapper.OpenApiOneConfigMapper;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.OpenApiOneConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通开放API 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-09-30
 */
@Slf4j
@Service
public class OpenApiOneConfigServiceImpl extends SuperServiceImpl<OpenApiOneConfigMapper, OpenApiOneConfig> implements OpenApiOneConfigService {

    @Autowired
    private OpenApiOneConfigMapper openApiOneConfigMapper;

}
