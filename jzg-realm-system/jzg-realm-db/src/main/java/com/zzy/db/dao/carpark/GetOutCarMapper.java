package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetOutCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 车辆出场纪录 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
public interface GetOutCarMapper extends BaseMapper<GetOutCar> {

    int batchInsert(List<GetOutCar> getOutCars);

    GetOutCar selectOneByInfo(GetOutCar gr);
}
