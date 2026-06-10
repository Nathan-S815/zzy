package com.nuwa.infrastructure.ticket.database.member.service.impl;

import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.mapper.MemberMapper;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-13
 */
@Slf4j
@Service
public class MemberServiceImpl extends SuperServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

}
