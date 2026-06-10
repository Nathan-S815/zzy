package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ProductDayTime;
import com.nuwa.infrastructure.ticket.database.product.mapper.ProductDayTimeMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ProductDayTimeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 产品场次表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-04-06
 */
@Slf4j
@Service
public class ProductDayTimeServiceImpl extends SuperServiceImpl<ProductDayTimeMapper, ProductDayTime> implements ProductDayTimeService {

    @Autowired
    private ProductDayTimeMapper productDayTimeMapper;

}
