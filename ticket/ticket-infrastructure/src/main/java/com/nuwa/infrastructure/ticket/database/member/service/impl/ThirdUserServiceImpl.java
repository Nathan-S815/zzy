package com.nuwa.infrastructure.ticket.database.member.service.impl;

import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
import com.nuwa.infrastructure.ticket.database.member.mapper.ThirdUserMapper;
import com.nuwa.infrastructure.ticket.database.member.service.ThirdUserService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 三方账户表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-13
 */
@Slf4j
@Service
public class ThirdUserServiceImpl extends SuperServiceImpl<ThirdUserMapper, ThirdUser> implements ThirdUserService {

    @Autowired
    private ThirdUserMapper thirdUserMapper;

}
