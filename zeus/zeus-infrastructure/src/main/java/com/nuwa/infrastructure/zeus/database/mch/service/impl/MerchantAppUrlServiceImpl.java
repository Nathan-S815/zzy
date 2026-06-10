package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppUrl;
import com.nuwa.infrastructure.zeus.database.mch.mapper.MerchantAppUrlMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppUrlService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * app跳转链接 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-18
 */
@Slf4j
@Service
public class MerchantAppUrlServiceImpl extends SuperServiceImpl<MerchantAppUrlMapper, MerchantAppUrl> implements MerchantAppUrlService {

    @Autowired
    private MerchantAppUrlMapper merchantAppUrlMapper;

}
