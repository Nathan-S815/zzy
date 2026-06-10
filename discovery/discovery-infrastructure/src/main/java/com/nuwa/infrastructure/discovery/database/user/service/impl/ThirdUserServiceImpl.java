package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.nuwa.infrastructure.discovery.database.user.entity.ThirdUser;
import com.nuwa.infrastructure.discovery.database.user.mapper.ThirdUserMapper;
import com.nuwa.infrastructure.discovery.database.user.service.ThirdUserService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 达人三方账户表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
@Slf4j
@Service
public class ThirdUserServiceImpl extends SuperServiceImpl<ThirdUserMapper, ThirdUser> implements ThirdUserService {

    @Autowired
    private ThirdUserMapper thirdUserMapper;

}
