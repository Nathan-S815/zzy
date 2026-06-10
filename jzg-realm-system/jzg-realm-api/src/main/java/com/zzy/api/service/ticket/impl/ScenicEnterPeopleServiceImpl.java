package com.zzy.api.service.ticket.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.service.ticket.IScenicEnterPeopleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.db.dao.base.BaseScenicMapper;
import com.zzy.db.dao.ticket.ScenicEnterPeopleMapper;
import com.zzy.db.entity.base.BaseScenic;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区当日入园人数表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
@Service
public class ScenicEnterPeopleServiceImpl extends ServiceImpl<ScenicEnterPeopleMapper, ScenicEnterPeople> implements IScenicEnterPeopleService {

    @Autowired
    ScenicEnterPeopleMapper scenicEnterPeopleMapper;

    @Autowired
    BaseScenicMapper baseScenicMapper;

    @Override
    public List<Map<String, Object>> getScenicInformation() {
        List<ScenicEnterPeople> ScenicEnterPeoples = scenicEnterPeopleMapper.getLatestScenicEnterPeople();
        List result = new ArrayList();
        for (ScenicEnterPeople scenicEnterPeople : ScenicEnterPeoples) {
            Map<String, Object> map = new HashMap<>();
            map.put("scenic", scenicEnterPeople.getName());
            BaseScenic baseScenic = baseScenicMapper.selectByScenicName(scenicEnterPeople.getName());
            Integer warning = baseScenic.getWarning();
            map.put("rate", warning);
            String rate = NumberMathUtil.getRateUp(scenicEnterPeople.getEnterNumber(), scenicEnterPeople.getEnterNumber() + scenicEnterPeople.getRestNumber(), 0);
            if (Integer.parseInt(rate)<warning){
                map.put("warning", false);
            }else {
                map.put("warning", true);
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public int getAllTouristNumber() {
        List<ScenicEnterPeople> ScenicEnterPeoples = scenicEnterPeopleMapper.getLatestScenicEnterPeople();
        int num = 0;
        for (ScenicEnterPeople scenicEnterPeople : ScenicEnterPeoples) {
            num += scenicEnterPeople.getEnterNumber();
        }
        return num;
    }

    @Override
    public int getAllTouristNumberByTime(String startTime, String endTime) {
        try {
            return scenicEnterPeopleMapper.getAllTouristNumberByTime(startTime, endTime);
        }catch (Exception e){
            return 0;
        }
    }
}
