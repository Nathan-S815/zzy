package com.nuwa.infrastructure.ticket.database.pubsystem.service.impl;

import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.mapper.PsAppInfoMapper;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝-小程序应用 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-06
 */
@Slf4j
@Service
public class PsAppInfoServiceImpl extends SuperServiceImpl<PsAppInfoMapper, PsAppInfo> implements PsAppInfoService {

    @Autowired
    private PsAppInfoMapper psAppInfoMapper;

}
