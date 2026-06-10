package com.nuwa.infrastructure.ticket.database.mall.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.mapper.MallTradeMapper;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Slf4j
@Service
public class MallTradeServiceImpl extends SuperServiceImpl<MallTradeMapper, MallTrade> implements MallTradeService {

    @Autowired
    private MallTradeMapper mallTradeMapper;

}
