package com.nuwa.infrastructure.zeus.database.mch.service.impl;

import com.nuwa.infrastructure.zeus.database.mch.entity.AppPageInfo;
import com.nuwa.infrastructure.zeus.database.mch.mapper.AppPageInfoMapper;
import com.nuwa.infrastructure.zeus.database.mch.service.AppPageInfoService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 小程序页面信息 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-04
 */
@Slf4j
@Service
public class AppPageInfoServiceImpl extends SuperServiceImpl<AppPageInfoMapper, AppPageInfo> implements AppPageInfoService {

    @Autowired
    private AppPageInfoMapper appPageInfoMapper;

}
