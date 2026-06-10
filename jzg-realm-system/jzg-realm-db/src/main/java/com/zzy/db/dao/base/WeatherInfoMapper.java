package com.zzy.db.dao.base;

import com.zzy.db.entity.base.WeatherInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-20
 */
public interface WeatherInfoMapper extends BaseMapper<WeatherInfo> {

    List<Map<String,Object>> getTempAndIndex();

}
