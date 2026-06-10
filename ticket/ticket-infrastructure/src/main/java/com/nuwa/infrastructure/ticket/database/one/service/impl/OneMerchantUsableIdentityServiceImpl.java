package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneMerchantUsableIdentityMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantUsableIdentityService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通商户端可用身份认 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Slf4j
@Service
public class OneMerchantUsableIdentityServiceImpl extends SuperServiceImpl<OneMerchantUsableIdentityMapper, OneMerchantUsableIdentity> implements OneMerchantUsableIdentityService {

    @Autowired
    private OneMerchantUsableIdentityMapper oneMerchantUsableIdentityMapper;

}
