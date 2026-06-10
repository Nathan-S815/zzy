package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.nuwa.infrastructure.discovery.database.user.entity.SmsCode;
import com.nuwa.infrastructure.discovery.database.user.mapper.SmsCodeMapper;
import com.nuwa.infrastructure.discovery.database.user.service.SmsCodeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信验证码表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-13
 */
@Slf4j
@Service
public class SmsCodeServiceImpl extends SuperServiceImpl<SmsCodeMapper, SmsCode> implements SmsCodeService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

}
