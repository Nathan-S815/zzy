package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.discovery.common.exception.ParamException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberGmvRecordBaseMapper;
import com.nuwa.infrastructure.discovery.database.member.mapper.MemberGmvRecordDouyinMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberGMVService;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MemberGMVServiceImpl implements MemberGMVService {

    @Autowired
    private MemberGmvRecordDouyinMapper memberGmvRecordDouyinMapper;


    @Override
    public void addMemberGmvRecordBatch(List<MemberGmvRecordDouyin> memberGmvRecordDouyinList) {
        if (CollectionUtils.isEmpty(memberGmvRecordDouyinList)) {
            return;
        }
        memberGmvRecordDouyinMapper.insertMemberGmvRecordDouyinBatch(memberGmvRecordDouyinList);
        //更新达人表中的gmv
        Map<Integer, Integer> useridAndPriceMap = getUseridAndPriceMap(memberGmvRecordDouyinList);
        Set<Integer> userIdSet = useridAndPriceMap.keySet();
        for (Integer userId : userIdSet) {
            Integer gmv = useridAndPriceMap.get(userId);
            memberGmvRecordDouyinMapper.incrMemberGMV(userId, gmv);
        }
    }

    @Override
    public void delMemberGMVRecord(Long id) {
        if(id == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "id不能为空");
        }
        memberGmvRecordDouyinMapper.deleteById(id);
    }

    @Override
    public IPage<MemberGmvRecordDouyin> getMemberGmvRecordPage(Long pageSize, Long pageNum, HashMap<String, Object> param) {
        Page<MemberGmvRecordDouyin> page = new Page<>();
        if(pageSize != null && pageNum != null){
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        QueryWrapper<MemberGmvRecordDouyin> queryWrapper = new QueryWrapper<>();
        if(param.get("baseId") != null){
            queryWrapper.eq("base_id", param.get("baseId"));
        }
        if(param.get("userId") != null){
            queryWrapper.eq("user_id", param.get("userId"));
        }
        IPage<MemberGmvRecordDouyin> memberGmvRecordDouyinIPage = memberGmvRecordDouyinMapper.selectPage(page, queryWrapper);
        return memberGmvRecordDouyinIPage;
    }

    @Override
    public void delMemberGMVRecordByBaseId(Long baseId) {
        if(baseId == null){
            throw new ParamException(ErrorEnum.PARAM_FAILED, "baseId不能为空");
        }

        QueryWrapper<MemberGmvRecordDouyin> wrapper = new QueryWrapper<>();
        wrapper.eq("base_id", baseId);
        List<MemberGmvRecordDouyin> memberGmvRecordDouyinList = memberGmvRecordDouyinMapper.selectList(wrapper);
        Map<Integer, Integer> useridAndPriceMap = getUseridAndPriceMap(memberGmvRecordDouyinList);
        Set<Integer> userIdSet = useridAndPriceMap.keySet();
        for (Integer userId : userIdSet) {
            Integer gmv = useridAndPriceMap.get(userId);
            memberGmvRecordDouyinMapper.decrMemberGMV(userId, gmv);
        }
        memberGmvRecordDouyinMapper.delete(wrapper);
    }

    private Map<Integer, Integer> getUseridAndPriceMap(List<MemberGmvRecordDouyin> memberGmvRecordDouyinList){
        Map<Integer, Integer> useridAndPriceMap = new HashMap<>();
        for (MemberGmvRecordDouyin memberGmvRecordDouyin : memberGmvRecordDouyinList) {
            useridAndPriceMap.put(memberGmvRecordDouyin.getUserId()
                    ,useridAndPriceMap.getOrDefault(memberGmvRecordDouyin.getUserId(),0) + memberGmvRecordDouyin.getPrice());
        }
        return useridAndPriceMap;
    }
}
