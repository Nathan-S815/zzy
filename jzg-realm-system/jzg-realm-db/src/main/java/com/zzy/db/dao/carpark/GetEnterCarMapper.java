package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetEnterCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆入场纪录 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
public interface GetEnterCarMapper extends BaseMapper<GetEnterCar> {

    List<Map<Object,Object>> getCarPlaceCount(String entertime);

    int batchInsert(List<GetEnterCar> getEnterCars);
}
