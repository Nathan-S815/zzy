package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;

import com.nuwa.infrastructure.discovery.database.user.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 达人用户表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-11-09
 */
@Repository
public interface MemberMapper extends SuperMapper<Member> {

    public IPage<MemberExaminePageVO> getMemberExaminePageVO(IPage<MemberExaminePageVO> page, @Param("params") MemberPageQry qry);

    public IPage<MemberMerchantPageVO> getMemberMerchantPageVO(IPage<MemberMerchantPageVO> page, @Param("params") MemberPageQry qry);

    public MemberMerchantPageVO getMemberMerchantVO(Long memberId);

    public MemberVO getCurrentMember(Long memberId);

    public List<MemberVO> getBannerMember();

    public MemberVO getMemberVOByUserId(Long userId);

    public List<MemberRankVO> getMemberRankVO();

    public List<MemberRankDataVO> getMemberRankDataVOByMchId(String MchId);

    public Integer sexCount (@Param("mchId") String mchId, @Param("sex") Integer sex);

    public Long getTotalGMV(String MchId);

    public Long getTotalFans(String MchId);

    public Long getTotalVideo(String MchId);

    public List<GreateVideoVO> selectGreateList(@Param("mchId") String mchId);
}
