package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.MerchantProductDistribute;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductDistributeMapper;
import com.nuwa.infrastructure.ticket.database.product.service.MerchantProductDistributeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商家产品分销表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-02-15
 */
@Slf4j
@Service
public class MerchantProductDistributeServiceImpl extends SuperServiceImpl<MerchantProductDistributeMapper, MerchantProductDistribute> implements MerchantProductDistributeService {

    @Autowired
    private MerchantProductDistributeMapper merchantProductDistributeMapper;

}
