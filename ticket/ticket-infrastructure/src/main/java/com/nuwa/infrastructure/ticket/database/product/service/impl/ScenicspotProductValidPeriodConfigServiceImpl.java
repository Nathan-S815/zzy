package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProductValidPeriodConfig;
import com.nuwa.infrastructure.ticket.database.product.mapper.ScenicspotProductValidPeriodConfigMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductValidPeriodConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 景区产品门票有效期规则设置 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class ScenicspotProductValidPeriodConfigServiceImpl extends SuperServiceImpl<ScenicspotProductValidPeriodConfigMapper, ScenicspotProductValidPeriodConfig> implements ScenicspotProductValidPeriodConfigService {

    @Autowired
    private ScenicspotProductValidPeriodConfigMapper scenicspotProductValidPeriodConfigMapper;

}
