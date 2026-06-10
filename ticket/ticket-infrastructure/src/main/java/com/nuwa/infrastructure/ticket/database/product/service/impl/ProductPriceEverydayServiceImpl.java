package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.mapper.ProductPriceEverydayMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ProductPriceEverydayService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 产品价格日历 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-08
 */
@Slf4j
@Service
public class ProductPriceEverydayServiceImpl extends SuperServiceImpl<ProductPriceEverydayMapper, ProductPriceEveryday> implements ProductPriceEverydayService {

    @Autowired
    private ProductPriceEverydayMapper productPriceEverydayMapper;

}
