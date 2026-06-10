package com.zzy.api.service.base.impl;

import com.zzy.db.entity.base.WeatherInfo;
import com.zzy.db.dao.base.WeatherInfoMapper;
import com.zzy.api.service.base.IWeatherInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-20
 */
@Service
public class WeatherInfoServiceImpl extends ServiceImpl<WeatherInfoMapper, WeatherInfo> implements IWeatherInfoService {
    @Autowired
    private WeatherInfoMapper weatherInfoMapper;
    @Override
    public List<Map<String, Object>> getTempAndIndex() {
        return weatherInfoMapper.getTempAndIndex();
    }
}
