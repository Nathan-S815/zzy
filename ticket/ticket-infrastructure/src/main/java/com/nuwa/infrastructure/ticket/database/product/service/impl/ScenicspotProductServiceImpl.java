package com.nuwa.infrastructure.ticket.database.product.service.impl;

import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.infrastructure.ticket.database.product.mapper.ScenicspotProductMapper;
import com.nuwa.infrastructure.ticket.database.product.service.ScenicspotProductService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Slf4j
@Service
public class ScenicspotProductServiceImpl extends SuperServiceImpl<ScenicspotProductMapper, ScenicspotProduct> implements ScenicspotProductService {

    @Autowired
    private ScenicspotProductMapper scenicspotProductMapper;

}
