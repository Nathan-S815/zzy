package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProductVerificationConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.ScenicspotProductVerificationConfigMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductVerificationConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区产品核销规则配置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class ScenicspotProductVerificationConfigServiceImpl extends SuperServiceImpl<ScenicspotProductVerificationConfigMapper, ScenicspotProductVerificationConfig> implements ScenicspotProductVerificationConfigService {

    @Autowired
    private ScenicspotProductVerificationConfigMapper scenicspotProductVerificationConfigMapper;

}
