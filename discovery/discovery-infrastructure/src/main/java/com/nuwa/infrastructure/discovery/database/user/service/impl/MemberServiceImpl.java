package com.nuwa.infrastructure.discovery.database.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.common.exception.UserNotExistException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberIntegralLevelMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagBindService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTagService;
import com.nuwa.infrastructure.discovery.database.user.vo.*;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 达人用户表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-09
 */
@Slf4j
@Service
public class MemberServiceImpl extends SuperServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberTagBindService memberTagBindService;

    @Autowired
    private MemberIntegralLevelMapper memberIntegralLevelMapper;

    @Override
    public IPage<MemberExaminePageVO> getMemberExaminePageVO(MemberPageQry qry) {
        Page<MemberExaminePageVO> page = new Page<>(qry.getPage(), qry.getLimit());

        IPage<MemberExaminePageVO> memberExaminePageVO = memberMapper.getMemberExaminePageVO(page, qry);

        return memberExaminePageVO;
    }

    @Override
    public IPage<MemberMerchantPageVO> getMemberMerchantPageVO(MemberPageQry qry, boolean desensitized) {
        Page<MemberMerchantPageVO> page = new Page<>(qry.getPage(), qry.getLimit());
        IPage<MemberMerchantPageVO> memberMerchantPageVO = memberMapper.getMemberMerchantPageVO(page, qry);
        List<MemberMerchantPageVO> records = memberMerchantPageVO.getRecords();
        for (MemberMerchantPageVO record : records) {
            if(desensitized){
                record.setUserPhone( DesensitizedUtil.mobilePhone(record.getUserPhone()));
            }
            record.setTagList(memberTagBindService.getMemberTagByMemberId(record.getUserId()));
        }
        memberMerchantPageVO.setRecords(records);
        return memberMerchantPageVO;
    }

    @Override
    public MemberMerchantPageVO getMemberMerchantVO(Long memberId) {
        if(memberId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberId不能为空");
        }
        MemberMerchantPageVO memberMerchantVO = memberMapper.getMemberMerchantVO(memberId);
        if(memberMerchantVO == null){
            throw new UserNotExistException(ErrorEnum.USER_DOES_NOT_EXIST, "用户不存在");
        }
        memberMerchantVO.setTagList(memberTagBindService.getMemberTagByMemberId(memberId));
        return memberMerchantVO;
    }

    @Override
    public MemberVO getCurrentMember(Long memberId) {
        if(memberId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "memberId不能为空");
        }
        MemberVO currentMember = memberMapper.getCurrentMember(memberId);
        if(currentMember == null){
            throw new UserNotExistException(ErrorEnum.USER_DOES_NOT_EXIST,"用户不存在");
        }
        currentMember.setTagList(memberTagBindService.getMemberTagByMemberId(memberId));
        return currentMember;
    }

    @Override
    public MemberIntegralVO getCurrentIntegral(Long memberId) {
        if (memberId == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"memberId不能为空");
        }
        Member member = memberMapper.selectById(memberId);
        if(member == null){
            throw new UserNotExistException(ErrorEnum.USER_DOES_NOT_EXIST, "用户不存在");
        }
        QueryWrapper<MemberIntegralLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("level_integral_count");
        List<MemberIntegralLevel> memberIntegralLevels = memberIntegralLevelMapper.selectList(queryWrapper);
        MemberIntegralVO memberIntegralVO = new MemberIntegralVO();
        memberIntegralVO.setIntegral(Convert.toInt(member.getIntegral()));
        memberIntegralVO.setUserLevelId(member.getUserLevelId());

        for (int i = 0; i < memberIntegralLevels.size(); i++) {
            if (Convert.toInt(member.getIntegral()) >= memberIntegralLevels.get(i).getLevelIntegralCount()) {
                if(i != 0){
                    memberIntegralVO.setNextLevelIntegral(memberIntegralLevels.get(i - 1).getLevelIntegralCount());
                    memberIntegralVO.setNextLevel(memberIntegralLevels.get(i - 1).getLevel());
                }
                break;
            }
        }

        Map<Integer, String> levelMap = getLevelMap();
        memberIntegralVO.setUserLevelName(levelMap.get(memberIntegralVO.getUserLevelId()));
        memberIntegralVO.setNextLevelName(levelMap.get(memberIntegralVO.getNextLevel()));
        return memberIntegralVO;
    }

    @Override
    public List<MemberVO> getBannerMember() {
        List<MemberVO> bannerMember = memberMapper.getBannerMember();
        return bannerMember;
    }

    @Override
    public MemberVO getMemberVOByUserId(Long userId) {
        if (userId == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"userId不能为空");
        }
        MemberVO memberVOByUserId = memberMapper.getMemberVOByUserId(userId);
        return memberVOByUserId;
    }

    @Override
    public List<MemberRankVO> getMemberRankVO() {
        List<MemberRankVO> memberRankVO = memberMapper.getMemberRankVO();
        return memberRankVO;
    }

    private Map<Integer, String> getLevelMap(){
        Map<Integer, String> levelMap = new HashMap<>();
        List<MemberIntegralLevel> memberIntegralLevels = memberIntegralLevelMapper.selectList(new QueryWrapper<>());
        levelMap = memberIntegralLevels.stream().collect(Collectors.toMap(MemberIntegralLevel :: getLevel, MemberIntegralLevel::getLevelName,(key1, key2) -> key2));
        return levelMap;
    }
}
