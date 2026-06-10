package com.nuwa.infrastructure.discovery.database.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.dto.MemberAuthenticationDTO;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberAccountBindRecord;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralRecord;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTagBind;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberAccountBindRecordMapper;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberAccountBindRecordVO;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberAccountBindMapper;
import com.nuwa.infrastructure.discovery.database.user.service.*;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MemberAccountBindRecordServiceImpl implements MemberAccountBindRecordService {

    @Autowired
    private MemberAccountBindRecordMapper memberAccountBindRecordMapper;

    @Autowired
    private MemberTagBindService memberTagBindService;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @Autowired
    private MemberIntegralRecordService memberIntegralRecordService;



    @Override
    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByBindId(Long memberAccountBindId, Integer status) {
        if(memberAccountBindId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberAccountBindId不能为空");
        }
        //默认查询待审核记录
        if(status == null){
            status = 0;
        }
        MemberAccountBindRecordVO memberAccountBindRecordVOByBindId = memberAccountBindRecordMapper.getMemberAccountBindRecordVOByBindId(memberAccountBindId, status);
        return memberAccountBindRecordVOByBindId;
    }

    @Override
    public MemberAccountBindRecordVO getMemberAccountBindRecordVO(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        MemberAccountBindRecordVO memberAccountBindRecordVO = memberAccountBindRecordMapper.getMemberAccountBindRecordVO(id);
        return memberAccountBindRecordVO;
    }

    @Override
    public IPage<MemberAccountBindRecord> getMemberAccountBindRecordPage(Long pageSize, Long pageNum, Long userId, Integer status) {
        if(userId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "userId不能为空");
        }
        Page<MemberAccountBindRecord> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        QueryWrapper<MemberAccountBindRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if(status != null){
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("update_time");
        IPage<MemberAccountBindRecord> memberAccountBindRecordIPage = memberAccountBindRecordMapper.selectPage(page, queryWrapper);
        return memberAccountBindRecordIPage;
    }

    @Override
    public void authentication(MemberAuthenticationDTO memberAuthenticationDTO) {
        if(memberAuthenticationDTO.getId() == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        if(memberAuthenticationDTO.getMemberAccountBindId() == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberAccountBindId不能为空");
        }
        //初始化更新记录对象
        MemberAccountBindRecord memberAccountBindRecord = new MemberAccountBindRecord();
        memberAccountBindRecord.setId(memberAuthenticationDTO.getId());

        //初始化绑定主题对象
        MemberAccountBind memberAccountBind = new MemberAccountBind();
        memberAccountBind.setId(memberAuthenticationDTO.getMemberAccountBindId());
        if(memberAuthenticationDTO.getStatus() == 1){
            List<Long> tagIds = memberAuthenticationDTO.getTagIds();
            if (CollectionUtils.isEmpty(tagIds)) {
                throw new ParamException(ErrorEnum.PARAM_FAILED, "标签id不能为空");
            }
            if (memberAuthenticationDTO.getUserId() == null) {
                throw new ParamException(ErrorEnum.PARAM_FAILED, "userId不能为空");
            }
            //更新标签绑定关系
            memberAccountBindRecord.setStatus(memberAuthenticationDTO.getStatus());
            List<MemberTagBind> memberTagBindList = new ArrayList<>();
            for (Long tagId : tagIds) {
                MemberTagBind memberTagBind = new MemberTagBind();
                memberTagBind.setMemberId(memberAuthenticationDTO.getUserId());
                memberTagBind.setMemberTagId(tagId);
                memberTagBindList.add(memberTagBind);
            }
            memberTagBindService.addMemberTabBind(memberTagBindList);
            //更新历史记录状态
            memberAccountBindRecord.setStatus(memberAuthenticationDTO.getStatus());
            memberAccountBindRecordMapper.updateById(memberAccountBindRecord);
            //取出最新成功的记录
            MemberAccountBindRecord memberAccountBindRecord1 = memberAccountBindRecordMapper.selectById(memberAccountBindRecord.getId());
            //更新绑定主体
            BeanUtil.copyProperties(memberAccountBindRecord1 , memberAccountBind);
            memberAccountBind.setStatus(memberAuthenticationDTO.getStatus());
            //将绑定主体认证状态设置为已认证
            memberAccountBind.setRecertificationStatus(1);
//            memberAccountBind.setInviteCode(memberAccountBindRecord1.getInviteCode());
//            memberAccountBind.setAccountId(memberAccountBindRecord1.getAccountId());
//            memberAccountBind.setFansCount(memberAccountBindRecord1.getFansCount());
//            memberAccountBind.setNick(memberAccountBindRecord1.getNick());
//            memberAccountBind.setLevel(memberAccountBindRecord1.getLevel());
//            memberAccountBind.setRegion(memberAccountBindRecord1.getRegion());
//            memberAccountBind.setSex(memberAccountBindRecord1.getSex());
//            memberAccountBind.setContent(memberAccountBindRecord1.getContent());
//            memberAccountBind.setBirthday(memberAccountBindRecord1.getBirthday());
//            memberAccountBind.setThirdPartyTag(memberAccountBindRecord1.getThirdPartyTag());
//            memberAccountBind.updateById();
            //这条千万不能去掉！！！！
            memberAccountBind.setId(memberAuthenticationDTO.getMemberAccountBindId());
            memberAccountBind.updateById();

            addMemberIntegral(Convert.toInt(memberAuthenticationDTO.getUserId())
                    , 200, "通过认证", 2, 1);

            addMemberIntegral(Convert.toInt(memberAuthenticationDTO.getUserId())
                    , 1000, "粉丝数50w以下", 3, 1);

            if (memberAccountBind.getFansCount() >= 500000) {
                addMemberIntegral(Convert.toInt(memberAuthenticationDTO.getUserId())
                        , 4000, "粉丝数50w以上", 4, 1);
            }
        }else if(memberAuthenticationDTO.getStatus() == 2){
            if (StringUtils.isEmpty(memberAuthenticationDTO.getReason())) {
                throw new ParamException(ErrorEnum.PARAM_FAILED, "审核不通过原因不能为空");
            }
            //更新历史记录状态
            memberAccountBindRecord.setRefuseReason(memberAuthenticationDTO.getReason());
            memberAccountBindRecord.setRefusePictures(memberAuthenticationDTO.getPicture());
            memberAccountBindRecord.setStatus(memberAuthenticationDTO.getStatus());
            memberAccountBindRecordMapper.updateById(memberAccountBindRecord);
//            //更新绑定主体状态
//            memberAccountBind.setStatus(memberAuthenticationDTO.getStatus());
            //将绑定主体认证状态设置为认证失败
            memberAccountBind.setRecertificationStatus(3);
            memberAccountBind.updateById();
        }
    }

    @Override
    public MemberAccountBindRecordVO getMemberAccountBindRecordVOByUserId(Long userId, Integer status) {
        if(userId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "userId不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("userId", userId);
        MemberAccountBindRecordVO memberAccountBindRecordVO = memberAccountBindRecordMapper.getMemberAccountBindRecordVOByParams(params);
        return memberAccountBindRecordVO;
    }

    private void addMemberIntegral(Integer userId, Integer integralCount, String EventContent, Integer EventId, Integer EventType){
        MemberIntegralRecord memberIntegralRecord = new MemberIntegralRecord();
        memberIntegralRecord.setUserId(userId);
        memberIntegralRecord.setIntegralCount(integralCount);
        memberIntegralRecord.setEventContent(EventContent);
        memberIntegralRecord.setEventId(EventId);
        memberIntegralRecord.setEventType(EventType);
        memberIntegralRecordService.addMemberIntegral(memberIntegralRecord);
    }
}
