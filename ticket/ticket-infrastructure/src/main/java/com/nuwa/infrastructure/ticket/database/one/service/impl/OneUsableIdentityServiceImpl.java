package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneUsableIdentityMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通可用身份认证配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Slf4j
@Service
public class OneUsableIdentityServiceImpl extends SuperServiceImpl<OneUsableIdentityMapper, OneUsableIdentity> implements OneUsableIdentityService {

    @Autowired
    private OneUsableIdentityMapper oneUsableIdentityMapper;

}
