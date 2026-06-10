package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneAdConfig;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneAdConfigMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneAdConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通广告配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Slf4j
@Service
public class OneAdConfigServiceImpl extends SuperServiceImpl<OneAdConfigMapper, OneAdConfig> implements OneAdConfigService {

    @Autowired
    private OneAdConfigMapper oneAdConfigMapper;

}
