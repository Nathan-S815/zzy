package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralRecord;

public interface MemberIntegralRecordService {

    public void addMemberIntegral(MemberIntegralRecord memberIntegralRecord);

    public IPage<MemberIntegralRecord> getMemberIntegralRecordPage(Long pageSize, Long pageNum, Long userId);

    public void addMemberIntegralLevel(MemberIntegralLevel memberIntegralLevel);

    public void delMemberIntegralLevel(Long id);

    public void updateMemberIntegralLevel(MemberIntegralLevel memberIntegralLevel);

    public MemberIntegralLevel getMemberIntegralLevel(Long id);

    public IPage<MemberIntegralLevel> getMemberIntegralLevelPage(Long pageSize, Long pageNum);

    public void incrCount(Long id);
}
