package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.dto.MemberAuthenticationDTO;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberAccountBindRecordVO;

public interface MemberAccountBindRecordService {

    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByBindId(Long memberAccountBindId, Integer status);

    public MemberAccountBindRecordVO getMemberAccountBindRecordVO(Long id);

    public IPage<MemberAccountBindRecord> getMemberAccountBindRecordPage(Long pageSize, Long pageNum, Long userId, Integer status);

    public void authentication(MemberAuthenticationDTO memberAuthenticationDTO);

    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByUserId(Long userId, Integer status);

}
