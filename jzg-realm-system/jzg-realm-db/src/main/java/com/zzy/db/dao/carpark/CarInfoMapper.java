package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.CarGps;
import com.zzy.db.entity.carpark.CarInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 车辆静态信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface CarInfoMapper extends BaseMapper<CarInfo> {

    int batchInsert(List<CarInfo> getCarInfo);
}
