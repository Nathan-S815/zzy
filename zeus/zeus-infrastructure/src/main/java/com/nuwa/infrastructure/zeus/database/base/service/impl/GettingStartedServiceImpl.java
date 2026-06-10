package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.mapper.GettingStartedMapper;
import com.nuwa.infrastructure.zeus.database.base.service.GettingStartedService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 新手入门 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-07-12
 */
@Slf4j
@Service
public class GettingStartedServiceImpl extends SuperServiceImpl<GettingStartedMapper, GettingStarted> implements GettingStartedService {

    @Autowired
    private GettingStartedMapper gettingStartedMapper;

}
