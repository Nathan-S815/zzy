package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTagBind;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberTagBindMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagBindService;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindMemberVO;
import com.nuwa.infrastructure.discovery.database.user.vo.MemberTagBindTagVO;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MemberTagBindServiceImpl implements MemberTagBindService {

    @Autowired
    private MemberTagBindMapper memberTagBindMapper;


    @Override
    public void addMemberTabBind(List<MemberTagBind> memberTagBindList) {
        if (CollectionUtils.isEmpty(memberTagBindList)) {
            return;
        }
        Set<Long> memberIds = new HashSet();
        for (MemberTagBind memberTagBind : memberTagBindList) {
            if(memberTagBind.getMemberTagId() == null || memberTagBind.getMemberId() == null){
                throw new ParamException(ErrorEnum.PARAM_FAILED, "MemberTagId 或 MemberId 不能为空");
            }
            memberIds.add(memberTagBind.getMemberId());
        }

        //删除原有绑定关系
        QueryWrapper<MemberTagBind> wrapper = new QueryWrapper<>();
        wrapper.in("member_id", memberIds);
        memberTagBindMapper.delete(wrapper);

        //新增绑定关系
        memberTagBindMapper.addMemberTagBindBatch(memberTagBindList);

    }

    @Override
    public void delMemberTagBind(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        memberTagBindMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<MemberTagBindTagVO> getMemberTagBindTagVO(Long pageSize, Long pageNum, Long memberId) {
        if(memberId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberId不能为空");
        }
        Page<MemberTagBindTagVO> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberTagBindTagVO> memberTagBindTagVO = memberTagBindMapper.getMemberTagBindTagVO(page, memberId);

        List<MemberTagBindTagVO> records = memberTagBindTagVO.getRecords();
        for (MemberTagBindTagVO record : records) {
            //判断标签是否和该用户绑定
            if (record.getMemberId() != null) {
                record.setIsCheck(1);
            }else{
                record.setIsCheck(0);
            }
        }
        return memberTagBindTagVO;
    }

    @Override
    public IPage<MemberTagBindMemberVO> getMemberTagBindMemberVO(Long pageSize, Long pageNum, Long memberTagId) {
        if(memberTagId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberTagId不能为空");
        }
        Page<MemberTagBindMemberVO> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberTagBindMemberVO> memberTagBindMemberVO = memberTagBindMapper.getMemberTagBindMemberVO(page, memberTagId);
        return memberTagBindMemberVO;
    }

    @Override
    public List<MemberTag> getMemberTagByMemberId(Long memberId) {
        if(memberId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED,"memberId不能为空");
        }
        List<MemberTag> memberTagByMemberId = memberTagBindMapper.getMemberTagByMemberId(memberId);
        return memberTagByMemberId;
    }

}
