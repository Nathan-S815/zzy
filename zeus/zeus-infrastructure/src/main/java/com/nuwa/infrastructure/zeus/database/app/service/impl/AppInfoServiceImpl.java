package com.nuwa.infrastructure.zeus.database.app.service.impl;

import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.mapper.AppInfoMapper;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Slf4j
@Service
public class AppInfoServiceImpl extends SuperServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

    @Autowired
    private AppInfoMapper appInfoMapper;

}
