package com.zzy.api.service.portrait.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.portrait.ITouristStayScenicMonthService;
import com.zzy.db.dao.portrait.TouristStayScenicMonthMapper;
import com.zzy.db.entity.portrait.TouristStayScenicMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区用户逗留时长分析(月) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-27
 */
@Service
public class TouristStayScenicMonthServiceImpl extends ServiceImpl<TouristStayScenicMonthMapper, TouristStayScenicMonth> implements ITouristStayScenicMonthService {

    @Autowired
    private TouristStayScenicMonthMapper touristStayScenicMonthMapper;

    @Override
    public List<Map<String, Object>> getTouristStayOutProvince(Long accTime,String sceneId) {
        return touristStayScenicMonthMapper.getTouristStayOutProvince(accTime,sceneId);
    }

    @Override
    public List<Map<String, Object>> getTouristStayInProvince(Long accTime,String sceneId) {
        return touristStayScenicMonthMapper.getTouristStayInProvince(accTime,sceneId);
    }

    @Override
    public List<Map<String, Object>> getTouristStayInProvinceByScenic(Long accTime) {
        return touristStayScenicMonthMapper.getTouristStayInProvinceByScenic(accTime);
    }

    @Override
    public List<Map<String, Object>> getTouristStayOutProvinceByScenic(Long accTime) {
        return touristStayScenicMonthMapper.getTouristStayOutProvinceByScenic(accTime);
    }
}
