package com.nuwa.infrastructure.zeus.database.app.service.impl;

import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.mapper.AppDependentMapper;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用依耐表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Slf4j
@Service
public class AppDependentServiceImpl extends SuperServiceImpl<AppDependentMapper, AppDependent> implements AppDependentService {

    @Autowired
    private AppDependentMapper appDependentMapper;

}
