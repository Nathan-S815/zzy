package com.nuwa.infrastructure.discovery.database.member.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTagBind;

import com.nuwa.infrastructure.discovery.database.user.vo.MemberExaminePageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindMemberVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindTagVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 达人标签绑定表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-08-17
 */
@Repository
public interface MemberTagBindMapper extends SuperMapper<MemberTagBind> {

    public IPage<MemberTagBindTagVO> getMemberTagBindTagVO(IPage<MemberTagBindTagVO> page, @Param("memberId") Long memberId);

    public IPage<MemberTagBindMemberVO> getMemberTagBindMemberVO(IPage<MemberTagBindMemberVO> page, @Param("tagId") Long tagId);

    public void addMemberTagBindBatch(List<MemberTagBind> memberTagBindList);

    public List<MemberTag> getMemberTagByMemberId(Long memberId);
}
