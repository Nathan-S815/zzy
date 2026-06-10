package com.nuwa.infrastructure.discovery.database.user.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberTagMapper;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagVO;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagService;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MemberTagServiceImpl implements MemberTagService{

    @Autowired
    private MemberTagMapper memberTagMapper;


    @Override
    public void addMemberTag(MemberTag memberTag) {
        if(memberTag == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED,"memberTag 不能为空");
        }
        memberTagMapper.insert(memberTag);
    }

    @Override
    public void delMemberTag(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED,"id不能为空");
        }

        MemberTag memberTag = new MemberTag();
        memberTag.setId(id);
        memberTag.setDeleteFlag(1);
        memberTagMapper.updateById(memberTag);
    }

    @Override
    public void updateMember(MemberTag memberTag) {
        if(memberTag == null || memberTag.getId() == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberTag 或 id不能为空");
        }
        memberTagMapper.updateById(memberTag);
    }


    @Override
    public MemberTag getMember(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        MemberTag memberTag = memberTagMapper.selectById(id);
        return memberTag;
    }

    @Override
    public IPage<MemberTagVO> getMemberTagVOPage(Long pageSize, Long pageNum) {
        Page<MemberTagVO> page = new Page<>();
        if(pageSize != null){
            page.setSize(pageSize);
        }
        if(pageNum != null){
            page.setCurrent(pageNum);
        }
        IPage<MemberTagVO> memberTagVOPage = memberTagMapper.getMemberTagVOPage(page);
        return memberTagVOPage;
    }
}
