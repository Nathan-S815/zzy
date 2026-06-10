package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgradeDetails;
import com.nuwa.infrastructure.zeus.database.base.mapper.PlatUpgradeDetailsMapper;
import com.nuwa.infrastructure.zeus.database.base.service.PlatUpgradeDetailsService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台更新日志详情 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-07-12
 */
@Slf4j
@Service
public class PlatUpgradeDetailsServiceImpl extends SuperServiceImpl<PlatUpgradeDetailsMapper, PlatUpgradeDetails> implements PlatUpgradeDetailsService {

    @Autowired
    private PlatUpgradeDetailsMapper platUpgradeDetailsMapper;

}
