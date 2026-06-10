package com.zzy.api.service.portrait.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.portrait.ITouristPortraitScenicDayService;
import com.zzy.db.dao.portrait.TouristPortraitScenicDayMapper;
import com.zzy.db.entity.portrait.TouristPortraitScenicDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区旅客画像-游客性别、年龄统计(日) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
@Service
public class TouristPortraitScenicDayServiceImpl extends ServiceImpl<TouristPortraitScenicDayMapper, TouristPortraitScenicDay> implements ITouristPortraitScenicDayService {
    @Autowired
    private TouristPortraitScenicDayMapper touristPortraitScenicDayMapper;
    @Override
    public List<Map<String, Object>> getTouristPortraitSexCount(Long accTime) {
        return touristPortraitScenicDayMapper.getTouristPortraitSexCount(accTime);
    }

    @Override
    public List<Map<String, Object>> getAgeDistribution(Long accTime,String sceneId) {
        return touristPortraitScenicDayMapper.getAgeDistribution(accTime,sceneId);
    }
}
