package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.mapper.MerchantMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商户信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Slf4j
@Service
public class MerchantServiceImpl extends SuperServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

}
