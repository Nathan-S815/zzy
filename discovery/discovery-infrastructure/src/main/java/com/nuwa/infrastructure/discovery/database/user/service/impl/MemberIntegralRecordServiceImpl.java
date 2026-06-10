package com.nuwa.infrastructure.discovery.database.user.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.UserNotExistException;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralLevel;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberIntegralRecord;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberIntegralLevelMapper;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberIntegralRecordMapper;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.service.MemberIntegralRecordService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberIntegralRecordServiceImpl implements MemberIntegralRecordService {

    @Autowired
    private MemberIntegralRecordMapper memberIntegralRecordMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberIntegralLevelMapper memberIntegralLevelMapper;


    @Override
    public void addMemberIntegral(MemberIntegralRecord memberIntegralRecord) {
        checkParam(memberIntegralRecord);
        Member member = memberService.getById(memberIntegralRecord.getUserId());
        if(member == null){
           throw new UserNotExistException(ErrorEnum.USER_DOES_NOT_EXIST, "用户不存在");
        }
        QueryWrapper<MemberIntegralRecord> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", memberIntegralRecord.getUserId())
                .eq("event_id", memberIntegralRecord.getEventId());
        MemberIntegralRecord memberIntegralRecord1 = memberIntegralRecordMapper.selectOne(queryWrapper1);
        //如果记录不为空 则说明该事件导致的成长值变动已发生 直接返回即可
        if(memberIntegralRecord1 != null){
            return;
        }
        //更新达人积分
        member.setIntegral(member.getIntegral() + memberIntegralRecord.getIntegralCount());
        //查询达人等级对应积分
        QueryWrapper<MemberIntegralLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("level_integral_count");
        List<MemberIntegralLevel> memberIntegralLevels = memberIntegralLevelMapper.selectList(queryWrapper);
        //更新达人等级
        for (MemberIntegralLevel memberIntegralLevel : memberIntegralLevels) {
            if (Convert.toInt(member.getIntegral()) >= memberIntegralLevel.getLevelIntegralCount()) {
                //判断等级是否变动 如果变动 则进行更新
                if(!member.getUserLevelId().equals(memberIntegralLevel.getLevel())){
                    memberIntegralLevelMapper.decrCount(Convert.toLong(member.getUserLevelId()));
                    member.setUserLevelId(memberIntegralLevel.getLevel());
                    memberIntegralLevelMapper.incrCount(memberIntegralLevel.getId());
                }
                break;
            }
        }
        //执行更新
        memberService.updateById(member);
        //新增积分记录
        memberIntegralRecord.setIntegralSnapshot(Convert.toInt(member.getIntegral()));
        memberIntegralRecordMapper.insert(memberIntegralRecord);
    }

    @Override
    public IPage<MemberIntegralRecord> getMemberIntegralRecordPage(Long pageSize, Long pageNum, Long userId) {
        Page<MemberIntegralRecord> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        QueryWrapper<MemberIntegralRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        IPage<MemberIntegralRecord> memberIntegralRecordIPage = memberIntegralRecordMapper.selectPage(page, queryWrapper);
        return memberIntegralRecordIPage;
    }

    @Override
    public void addMemberIntegralLevel(MemberIntegralLevel memberIntegralLevel) {
        if(memberIntegralLevel == null
                || memberIntegralLevel.getLevel() == null
                || memberIntegralLevel.getLevelIntegralCount() == null){
            throw  new ParamException(ErrorEnum.PARAM_FAILED, "请检查输入的参数");
        }
        memberIntegralLevelMapper.insert(memberIntegralLevel);
    }

    @Override
    public void delMemberIntegralLevel(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        memberIntegralLevelMapper.deleteById(id);
    }

    @Override
    public void updateMemberIntegralLevel(MemberIntegralLevel memberIntegralLevel) {
        if(memberIntegralLevel == null || memberIntegralLevel.getId() == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        memberIntegralLevelMapper.updateById(memberIntegralLevel);
        reCalculationMemberIntegralLevel();
    }

    @Override
    public MemberIntegralLevel getMemberIntegralLevel(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        MemberIntegralLevel memberIntegralLevel = memberIntegralLevelMapper.selectById(id);
        return memberIntegralLevel;
    }

    @Override
    public IPage<MemberIntegralLevel> getMemberIntegralLevelPage(Long pageSize, Long pageNum) {
        Page<MemberIntegralLevel> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<MemberIntegralLevel> memberIntegralLevelIPage = memberIntegralLevelMapper.selectPage(page, new QueryWrapper<>());
        return memberIntegralLevelIPage;
    }

    @Override
    public void incrCount(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        memberIntegralLevelMapper.incrCount(id);
    }


    /**
     * 用户等级全表更新
     */
    private void reCalculationMemberIntegralLevel(){
        QueryWrapper<MemberIntegralLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("level_integral_count");
        List<MemberIntegralLevel> memberIntegralLevels = memberIntegralLevelMapper.selectList(queryWrapper);
        Integer max = Integer.MAX_VALUE;
        Integer min = Integer.MIN_VALUE;
        for (MemberIntegralLevel memberIntegralLevel : memberIntegralLevels) {
            min = memberIntegralLevel.getLevelIntegralCount();
            UpdateWrapper<Member> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("user_level_id", memberIntegralLevel.getLevel());
            updateWrapper.lt("integral",max).ge("integral",min);
            int count = memberService.count(updateWrapper);
            memberService.update(updateWrapper);
            memberIntegralLevel.setCount(count);
            memberIntegralLevelMapper.updateById(memberIntegralLevel);
            max = min;
        }
        //todo
    }

    private void checkParam(MemberIntegralRecord memberIntegralRecord){
        if (memberIntegralRecord == null || memberIntegralRecord.getIntegralCount() == null
                || memberIntegralRecord.getEventContent() == null || memberIntegralRecord.getUserId() == null
                || memberIntegralRecord.getEventId() == null || memberIntegralRecord.getEventType() == null) {
            throw new ParamException(ErrorEnum.PARAM_FAILED,"请检查传入参数是否为空");
        }
    }
}
