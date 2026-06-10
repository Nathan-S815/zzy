package com.nuwa.infrastructure.zeus.database.app.service.impl;

import com.nuwa.infrastructure.zeus.database.app.entity.AppUpdateLog;
import com.nuwa.infrastructure.zeus.database.app.mapper.AppUpdateLogMapper;
import com.nuwa.infrastructure.zeus.database.app.service.AppUpdateLogService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * app升级日志 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-06-27
 */
@Slf4j
@Service
public class AppUpdateLogServiceImpl extends SuperServiceImpl<AppUpdateLogMapper, AppUpdateLog> implements AppUpdateLogService {

    @Autowired
    private AppUpdateLogMapper appUpdateLogMapper;

}
