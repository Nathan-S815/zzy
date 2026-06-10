package com.nuwa.infrastructure.zeus.database.app.service.impl;

import com.nuwa.infrastructure.zeus.database.app.entity.AppSkuInfo;
import com.nuwa.infrastructure.zeus.database.app.mapper.AppSkuInfoMapper;
import com.nuwa.infrastructure.zeus.database.app.service.AppSkuInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * app规格 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-09
 */
@Slf4j
@Service
public class AppSkuInfoServiceImpl extends SuperServiceImpl<AppSkuInfoMapper, AppSkuInfo> implements AppSkuInfoService {

    @Autowired
    private AppSkuInfoMapper appSkuInfoMapper;

}
