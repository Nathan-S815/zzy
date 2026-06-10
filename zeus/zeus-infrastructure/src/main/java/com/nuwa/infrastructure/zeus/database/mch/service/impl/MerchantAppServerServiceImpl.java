package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.mapper.MerchantAppServerMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户应用服务信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-06-23
 */
@Slf4j
@Service
public class MerchantAppServerServiceImpl extends SuperServiceImpl<MerchantAppServerMapper, MerchantAppServer> implements MerchantAppServerService {

    @Autowired
    private MerchantAppServerMapper merchantAppServerMapper;

}
