package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.database.member.vo.MemBerFansCountVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberDetailVO;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberGmvDetailVO;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberAccountBindMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 达人账号绑定表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
@Slf4j
@Service
public class MemberAccountBindServiceImpl extends SuperServiceImpl<MemberAccountBindMapper, MemberAccountBind> implements MemberAccountBindService {

    @Autowired
    private MemberAccountBindMapper memberAccountBindMapper;

    @Override
    public IPage<MemberDetailVO> getMemberDetailList(Long pageSize, Long pageNum, String opType,String mchId,String userNick) {
        Page<MemberDetailVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberDetailVO> record = memberAccountBindMapper.getMemberDetailList(page,opType,mchId,userNick);
        return record;
    }

    @Override
    public IPage<MemBerFansCountVO> getMemberFansCountList(Long pageSize, Long pageNum,String mchId) {
        Page<MemBerFansCountVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemBerFansCountVO> record = memberAccountBindMapper.getMemberFansCountList(page,mchId);
        return record;
    }

    @Override
    public IPage<MemberGmvDetailVO> getMemberGmvDetailList(Long pageSize, Long pageNum,String mchId) {
        Page<MemberGmvDetailVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberGmvDetailVO> record = memberAccountBindMapper.getMemberGmvDetailList(page,mchId);
        for (MemberGmvDetailVO memberGmvDetailVO : record.getRecords()) {
            memberGmvDetailVO.setGmv(memberGmvDetailVO.getGmv().divide(new BigDecimal(100)));
        }
        return record;
    }

    @Override
    public IPage<MemberVideoVO> getMemberVideoDetailList(Long pageSize, Long pageNum, String mchId) {
        Page<MemberVideoVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberVideoVO> record = memberAccountBindMapper.getMemberVideoDetailList(page,mchId);

        return record;
    }
}
