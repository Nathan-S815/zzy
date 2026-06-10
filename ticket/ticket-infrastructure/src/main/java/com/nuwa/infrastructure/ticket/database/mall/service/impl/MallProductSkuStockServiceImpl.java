package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallProductSkuStockMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品规格表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallProductSkuStockServiceImpl extends SuperServiceImpl<MallProductSkuStockMapper, MallProductSkuStock> implements MallProductSkuStockService {

    @Autowired
    private MallProductSkuStockMapper mallProductSkuStockMapper;

}
