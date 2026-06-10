package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.mapper.MerchantAppMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户应用 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-03
 */
@Slf4j
@Service
public class MerchantAppServiceImpl extends SuperServiceImpl<MerchantAppMapper, MerchantApp> implements MerchantAppService {

    @Autowired
    private MerchantAppMapper merchantAppMapper;

}
