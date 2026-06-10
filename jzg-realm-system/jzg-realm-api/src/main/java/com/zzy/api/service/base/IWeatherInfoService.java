package com.zzy.api.service.base;

import com.zzy.db.entity.base.WeatherInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-20
 */
public interface IWeatherInfoService extends IService<WeatherInfo> {

    List<Map<String,Object>> getTempAndIndex();

}
