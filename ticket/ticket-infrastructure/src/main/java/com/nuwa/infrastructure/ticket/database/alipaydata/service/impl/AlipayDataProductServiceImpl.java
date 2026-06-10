package com.nuwa.infrastructure.ticket.database.alipaydata.service.impl;

import com.nuwa.infrastructure.ticket.database.alipaydata.entity.AlipayDataProduct;
import com.nuwa.infrastructure.ticket.database.alipaydata.mapper.AlipayDataProductMapper;
import com.nuwa.infrastructure.ticket.database.alipaydata.service.AlipayDataProductService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝-景区产品 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-18
 */
@Slf4j
@Service
public class AlipayDataProductServiceImpl extends SuperServiceImpl<AlipayDataProductMapper, AlipayDataProduct> implements AlipayDataProductService {

    @Autowired
    private AlipayDataProductMapper alipayDataProductMapper;

}
