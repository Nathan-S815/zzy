package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagVO;

import java.util.List;

public interface MemberTagService {


    public void addMemberTag(MemberTag memberTag);

    public void delMemberTag(Long id);

    public void updateMember(MemberTag memberTag);

    public MemberTag getMember(Long id);

    public IPage<MemberTagVO> getMemberTagVOPage(Long pageSize, Long pageNum);
}
