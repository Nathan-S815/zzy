package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.mapper.MerchantSiteConfigMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-16
 */
@Slf4j
@Service
public class MerchantSiteConfigServiceImpl extends SuperServiceImpl<MerchantSiteConfigMapper, MerchantSiteConfig> implements MerchantSiteConfigService {

    @Autowired
    private MerchantSiteConfigMapper merchantSiteConfigMapper;

}
