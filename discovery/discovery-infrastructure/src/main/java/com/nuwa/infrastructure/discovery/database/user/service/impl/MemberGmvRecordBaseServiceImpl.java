package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordBase;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberGmvRecordBaseMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberGmvRecordBaseService;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberGmvRecordBaseServiceImpl implements MemberGmvRecordBaseService {


    @Autowired
    private MemberGmvRecordBaseMapper memberGmvRecordBaseMapper;

    @Override
    public void addMemberGmvRecordBase(MemberGmvRecordBase memberGmvRecordBase) {
        if (memberGmvRecordBase == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberGmvRecordBase不能为空");
        }
        memberGmvRecordBaseMapper.insert(memberGmvRecordBase);
    }

    @Override
    public void delMemberGmvRecordBase(Long id) {
        if (id == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        memberGmvRecordBaseMapper.deleteById(id);
    }

    @Override
    public IPage<MemberGmvRecordBase> getMemberGmvRecordBasePage(Long pageSize, Long pageNum) {
        Page<MemberGmvRecordBase> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        QueryWrapper<MemberGmvRecordBase> queryWrapper = new QueryWrapper<>();
        IPage<MemberGmvRecordBase> memberGmvRecordBaseIPage = memberGmvRecordBaseMapper.selectPage(page, queryWrapper);
        return memberGmvRecordBaseIPage;
    }
}
