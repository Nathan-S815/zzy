package com.nuwa.infrastructure.discovery.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.user.vo.*;

import java.util.List;

/**
 * 达人用户表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-11-09
 */
public interface MemberService extends SuperService<Member> {

    public IPage<MemberExaminePageVO> getMemberExaminePageVO(MemberPageQry qry);

    public IPage<MemberMerchantPageVO> getMemberMerchantPageVO(MemberPageQry qry, boolean desensitized);

    public MemberMerchantPageVO getMemberMerchantVO(Long memberId);

    public MemberVO getCurrentMember(Long memberId);

    public MemberIntegralVO getCurrentIntegral(Long memberId);

    public List<MemberVO> getBannerMember();

    public MemberVO getMemberVOByUserId(Long userId);

    public List<MemberRankVO> getMemberRankVO();
}
