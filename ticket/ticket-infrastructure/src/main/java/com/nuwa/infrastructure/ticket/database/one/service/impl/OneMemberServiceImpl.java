package com.nuwa.infrastructure.ticket.database.one.service.impl;

import com.nuwa.infrastructure.ticket.database.one.entity.OneMember;
import com.nuwa.infrastructure.ticket.database.one.mapper.OneMemberMapper;
import com.nuwa.infrastructure.ticket.database.one.service.OneMemberService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码通会员 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-10-27
 */
@Slf4j
@Service
public class OneMemberServiceImpl extends SuperServiceImpl<OneMemberMapper, OneMember> implements OneMemberService {

    @Autowired
    private OneMemberMapper oneMemberMapper;

}
