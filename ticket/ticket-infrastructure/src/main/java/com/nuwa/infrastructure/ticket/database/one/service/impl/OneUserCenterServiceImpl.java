package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneUserCenter;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneUserCenterMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneUserCenterService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通用户中心 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Slf4j
@Service
public class OneUserCenterServiceImpl extends SuperServiceImpl<OneUserCenterMapper, OneUserCenter> implements OneUserCenterService {

    @Autowired
    private OneUserCenterMapper oneUserCenterMapper;

}
