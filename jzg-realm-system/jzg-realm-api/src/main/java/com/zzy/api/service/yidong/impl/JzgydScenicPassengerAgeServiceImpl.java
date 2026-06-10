package com.zzy.api.service.yidong.impl;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.db.entity.yidong.JzgydScenicPassengerAge;
import com.zzy.db.dao.yidong.JzgydScenicPassengerAgeMapper;
import com.zzy.api.service.yidong.IJzgydScenicPassengerAgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县及景区客流年龄数据(日月) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Service
public class JzgydScenicPassengerAgeServiceImpl extends ServiceImpl<JzgydScenicPassengerAgeMapper, JzgydScenicPassengerAge> implements IJzgydScenicPassengerAgeService {

    @Autowired
    JzgydScenicPassengerAgeMapper jzgydScenicPassengerAgeMapper;
    @Override
    public List<Map<String, Integer>> getAgeDistribution(String startTime, String endTime) {
        List<JzgydScenicPassengerAge> ageDistributions = jzgydScenicPassengerAgeMapper.getAgeDistribution(startTime, endTime);
        Integer totelNum = 0;
        for (JzgydScenicPassengerAge ageDistribution : ageDistributions) {
            if(!ageDistribution.getTypeName().equals("未知")){
                totelNum += ageDistribution.getPeopleNum();
            }
        }
        List<Map<String, Integer>> result= new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        map.put("20岁以下",0);
        map.put("20-40岁",0);
        map.put("40-70岁",0);
        map.put("70岁以上",0);
        for (JzgydScenicPassengerAge ageDistribution : ageDistributions) {
            String typeName = ageDistribution.getTypeName();
            if ("15岁以下".equals(typeName)||"15-20".equals(typeName)){
                map.put("20岁以下",map.get("20岁以下")+ageDistribution.getPeopleNum());
            }else if ("20-25".equals(typeName)||"25-30".equals(typeName)||"30-35".equals(typeName)||"35-40".equals(typeName)){
                map.put("20-40岁",map.get("20-40岁")+ageDistribution.getPeopleNum());
            }else if ("40-45".equals(typeName)||"45-50".equals(typeName)||"50-55".equals(typeName)||"55-60".equals(typeName)||"60-65".equals(typeName)||"65-70".equals(typeName)){
                map.put("40-70岁",map.get("40-70岁")+ageDistribution.getPeopleNum());
            }else if ("70以上".equals(typeName)){
                map.put("70岁以上",map.get("70岁以上")+ageDistribution.getPeopleNum());
            }
        }
        map.put("20岁以下", Integer.parseInt(NumberMathUtil.getRate(map.get("20岁以下"),totelNum,0)));
        map.put("20-40岁", Integer.parseInt(NumberMathUtil.getRate(map.get("20-40岁"),totelNum,0)));
        map.put("40-70岁", Integer.parseInt(NumberMathUtil.getRate(map.get("40-70岁"),totelNum,0)));
        map.put("70岁以上", Integer.parseInt(NumberMathUtil.getRate(map.get("70岁以上"),totelNum,0)));
        result.add(map);
        return result;
    }
}
