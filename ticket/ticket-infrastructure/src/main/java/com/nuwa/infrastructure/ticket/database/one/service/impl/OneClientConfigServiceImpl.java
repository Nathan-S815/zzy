package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneClientConfig;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneClientConfigMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneClientConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通端口配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Slf4j
@Service
public class OneClientConfigServiceImpl extends SuperServiceImpl<OneClientConfigMapper, OneClientConfig> implements OneClientConfigService {

    @Autowired
    private OneClientConfigMapper oneClientConfigMapper;

}
