package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.CarDriver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.carpark.CarGps;

import java.util.List;

/**
 * <p>
 * 驾驶员信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface CarDriverMapper extends BaseMapper<CarDriver> {

    int batchInsert(List<CarDriver> getCarDriver);
}
