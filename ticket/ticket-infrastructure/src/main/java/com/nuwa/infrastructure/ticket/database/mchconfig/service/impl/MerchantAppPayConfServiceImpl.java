package com.nuwa.infrastructure.ticket.database.mchconfig.service.impl;

import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.mapper.MerchantAppPayConfMapper;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户App支付参数信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-20
 */
@Slf4j
@Service
public class MerchantAppPayConfServiceImpl extends SuperServiceImpl<MerchantAppPayConfMapper, MerchantAppPayConf> implements MerchantAppPayConfService {

    @Autowired
    private MerchantAppPayConfMapper merchantAppPayConfMapper;

}
