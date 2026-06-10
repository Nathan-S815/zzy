package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordBase;

public interface MemberGmvRecordBaseService {
    
    public void addMemberGmvRecordBase(MemberGmvRecordBase memberGmvRecordBase);

    public void delMemberGmvRecordBase(Long id);

    public IPage<MemberGmvRecordBase> getMemberGmvRecordBasePage(Long pageSize, Long pageNum);
}
