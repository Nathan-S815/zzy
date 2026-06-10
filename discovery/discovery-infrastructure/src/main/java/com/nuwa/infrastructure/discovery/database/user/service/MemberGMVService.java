package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;

import java.util.HashMap;
import java.util.List;

public interface MemberGMVService {

    public void addMemberGmvRecordBatch(List<MemberGmvRecordDouyin> memberGmvRecordDouyinList);

    public void delMemberGMVRecord(Long id);

    public IPage<MemberGmvRecordDouyin> getMemberGmvRecordPage(Long pageSize, Long pageNum, HashMap<String, Object> param);

    public void delMemberGMVRecordByBaseId(Long baseId);
}
