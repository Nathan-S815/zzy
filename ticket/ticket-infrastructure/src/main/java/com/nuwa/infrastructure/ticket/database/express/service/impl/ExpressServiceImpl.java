package com.nuwa.infrastructure.ticket.database.express.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.express.entity.Express;
import com.nuwa.infrastructure.ticket.database.express.mapper.ExpressMapper;
import com.nuwa.infrastructure.ticket.database.express.service.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 物流信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-18
 */
@Slf4j
@Service
public class ExpressServiceImpl extends SuperServiceImpl<ExpressMapper, Express> implements ExpressService {

    @Autowired
    private ExpressMapper expressMapper;

}
