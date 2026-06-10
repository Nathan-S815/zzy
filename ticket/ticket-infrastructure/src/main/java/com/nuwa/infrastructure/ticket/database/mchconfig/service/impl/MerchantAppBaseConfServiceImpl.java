package com.nuwa.infrastructure.ticket.database.mchconfig.service.impl;

import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.mapper.MerchantAppBaseConfMapper;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户App基本信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-24
 */
@Slf4j
@Service
public class MerchantAppBaseConfServiceImpl extends SuperServiceImpl<MerchantAppBaseConfMapper, MerchantAppBaseConf> implements MerchantAppBaseConfService {

    @Autowired
    private MerchantAppBaseConfMapper merchantAppBaseConfMapper;

}
