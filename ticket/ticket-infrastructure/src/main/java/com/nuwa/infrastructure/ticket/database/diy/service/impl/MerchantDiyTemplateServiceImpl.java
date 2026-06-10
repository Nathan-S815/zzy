package com.nuwa.infrastructure.ticket.database.diy.service.impl;

import com.nuwa.infrastructure.ticket.database.diy.entity.MerchantDiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.mapper.MerchantDiyTemplateMapper;
import com.nuwa.infrastructure.ticket.database.diy.service.MerchantDiyTemplateService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户渠道装修 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-03-11
 */
@Slf4j
@Service
public class MerchantDiyTemplateServiceImpl extends SuperServiceImpl<MerchantDiyTemplateMapper, MerchantDiyTemplate> implements MerchantDiyTemplateService {

    @Autowired
    private MerchantDiyTemplateMapper merchantDiyTemplateMapper;

}
