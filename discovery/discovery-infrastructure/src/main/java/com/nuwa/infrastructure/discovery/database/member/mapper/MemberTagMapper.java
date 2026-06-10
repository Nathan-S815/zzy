package com.nuwa.infrastructure.discovery.database.member.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;

import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagCountVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindTagVO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 达人标签 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2022-08-04
 */
@Repository
public interface MemberTagMapper extends SuperMapper<MemberTag> {

    public IPage<MemberTagVO> getMemberTagVOPage(IPage<MemberTagVO> page);

    public List<MemberTagCountVO> getMemberTagCountVO(String mchId);

}
