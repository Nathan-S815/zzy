package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.LoginLog;
import com.nuwa.infrastructure.zeus.database.base.mapper.LoginLogMapper;
import com.nuwa.infrastructure.zeus.database.base.service.LoginLogService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录日志 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-10
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends SuperServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

}
