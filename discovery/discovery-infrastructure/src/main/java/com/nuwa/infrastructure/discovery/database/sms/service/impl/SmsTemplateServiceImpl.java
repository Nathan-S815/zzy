package com.nuwa.infrastructure.discovery.database.sms.service.impl;

import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.infrastructure.discovery.database.sms.mapper.SmsTemplateMapper;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsTemplateService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户短信模板 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-30
 */
@Slf4j
@Service
public class SmsTemplateServiceImpl extends SuperServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

}
