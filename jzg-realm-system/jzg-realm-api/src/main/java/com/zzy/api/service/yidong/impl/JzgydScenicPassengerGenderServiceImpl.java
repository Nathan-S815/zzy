package com.zzy.api.service.yidong.impl;

import com.zzy.core.utils.NumberMathUtil;
import com.zzy.db.entity.yidong.JzgydScenicPassengerGender;
import com.zzy.db.dao.yidong.JzgydScenicPassengerGenderMapper;
import com.zzy.api.service.yidong.IJzgydScenicPassengerGenderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县及景区客流性别数据(日月) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Service
public class JzgydScenicPassengerGenderServiceImpl extends ServiceImpl<JzgydScenicPassengerGenderMapper, JzgydScenicPassengerGender> implements IJzgydScenicPassengerGenderService {

    @Autowired
    JzgydScenicPassengerGenderMapper jzgydScenicPassengerGenderMapper;
    @Override
    public List<Map<String, Integer>> getTouristPortraitSexCount(String startTime,String endTime) {
        List<JzgydScenicPassengerGender> touristPortraitSexCounts = jzgydScenicPassengerGenderMapper.getTouristPortraitSexCount(startTime, endTime);
        List<Map<String, Integer>> result = new ArrayList<>();
        int man =0;
        int woman =0;
        for (JzgydScenicPassengerGender touristPortraitSexCount : touristPortraitSexCounts) {
            String sex = touristPortraitSexCount.getTypeName();
            if ("男".equals(sex)){
                man+=touristPortraitSexCount.getPeopleNum();
            }else if ("女".equals(sex)){
                woman+=touristPortraitSexCount.getPeopleNum();
            }
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("男生人数", Integer.parseInt(NumberMathUtil.getRate(man,man+woman,0)));
        map.put("女生人数",100-Integer.parseInt(NumberMathUtil.getRate(man,man+woman,0)));
        result.add(map);
        return result;
    }
}
