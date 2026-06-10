package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.database.member.vo.MemBerFansCountVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberDetailVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberGmvDetailVO;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberVideoVO;

/**
 * 达人账号绑定表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
public interface MemberAccountBindService extends SuperService<MemberAccountBind> {

    IPage<MemberDetailVO> getMemberDetailList(Long pageSize, Long pageNum, String opType,String mchId,String userNick);

    IPage<MemBerFansCountVO> getMemberFansCountList(Long pageSize, Long pageNum,String mchId);

    IPage<MemberGmvDetailVO> getMemberGmvDetailList(Long pageSize, Long pageNum,String mchId);

    IPage<MemberVideoVO> getMemberVideoDetailList(Long pageSize, Long pageNum, String mchId);
}
