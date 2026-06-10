package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.mapper.BaseUserMapper;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-25
 */
@Slf4j
@Service
public class BaseUserServiceImpl extends SuperServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

}
