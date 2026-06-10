package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTagBind;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindMemberVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindTagVO;

import java.util.List;

public interface MemberTagBindService {

    public void addMemberTabBind(List<MemberTagBind> memberTagBindList);

    public void delMemberTagBind(List<Long> ids);

    public IPage<MemberTagBindTagVO> getMemberTagBindTagVO(Long pageSize, Long pageNum, Long memberId);

    public IPage<MemberTagBindMemberVO> getMemberTagBindMemberVO(Long pageSize, Long pageNum, Long memberTagId);

    public List<MemberTag> getMemberTagByMemberId(Long memberId);
}
