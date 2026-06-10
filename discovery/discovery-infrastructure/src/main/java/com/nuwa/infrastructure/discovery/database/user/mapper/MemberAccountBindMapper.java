package com.nuwa.infrastructure.discovery.database.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.vo.MemBerFansCountVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberDetailVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberGmvDetailVO;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;

import com.nuwa.infrastructure.discovery.database.user.vo.MemberVideoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 达人账号绑定表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-04-12
 */
@Repository
public interface MemberAccountBindMapper extends SuperMapper<MemberAccountBind> {


    IPage<MemberDetailVO> getMemberDetailList(IPage<MemberDetailVO> page,
                                              @Param("opType") String opType,
                                              @Param("mchId") String mchId,
                                              @Param("userNick") String userNick);

    IPage<MemBerFansCountVO> getMemberFansCountList(IPage<MemBerFansCountVO> page, @Param("mchId") String mchId);

    IPage<MemberGmvDetailVO> getMemberGmvDetailList(IPage<MemberGmvDetailVO> page, @Param("mchId") String mchId);

    IPage<MemberVideoVO> getMemberVideoDetailList(IPage<MemberVideoVO> page,    @Param("mchId") String mchId);
}
