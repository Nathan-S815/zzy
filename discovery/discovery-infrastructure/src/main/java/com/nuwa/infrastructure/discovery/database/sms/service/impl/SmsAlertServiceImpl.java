package com.nuwa.infrastructure.discovery.database.sms.service.impl;

import com.nuwa.infrastructure.discovery.database.sms.entity.SmsAlert;
import com.nuwa.infrastructure.discovery.database.sms.mapper.SmsAlertMapper;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsAlertService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信提醒表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-30
 */
@Slf4j
@Service
public class SmsAlertServiceImpl extends SuperServiceImpl<SmsAlertMapper, SmsAlert> implements SmsAlertService {

    @Autowired
    private SmsAlertMapper smsAlertMapper;

}
