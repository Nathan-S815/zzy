package com.nuwa.infrastructure.zeus.database.base.service.impl;

import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.mapper.BaseGroupMemberMapper;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
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
public class BaseGroupMemberServiceImpl extends SuperServiceImpl<BaseGroupMemberMapper, BaseGroupMember> implements BaseGroupMemberService {

    @Autowired
    private BaseGroupMemberMapper baseGroupMemberMapper;

}
