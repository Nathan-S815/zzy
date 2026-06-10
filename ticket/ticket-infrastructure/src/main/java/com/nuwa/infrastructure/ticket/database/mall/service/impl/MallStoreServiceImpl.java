package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallStore;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallStoreMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 门店信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallStoreServiceImpl extends SuperServiceImpl<MallStoreMapper, MallStore> implements MallStoreService {

    @Autowired
    private MallStoreMapper mallStoreMapper;

}
