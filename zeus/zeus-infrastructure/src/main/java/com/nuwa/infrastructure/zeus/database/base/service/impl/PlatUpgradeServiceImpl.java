package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.database.base.mapper.PlatUpgradeMapper;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台升级日志 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-07-12
 */
@Slf4j
@Service
public class PlatUpgradeServiceImpl extends SuperServiceImpl<PlatUpgradeMapper, PlatUpgrade> implements PlatUpgradeService {

    @Autowired
    private PlatUpgradeMapper platUpgradeMapper;

}
