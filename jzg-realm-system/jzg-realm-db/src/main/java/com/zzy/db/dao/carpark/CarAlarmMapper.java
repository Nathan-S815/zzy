package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.CarAlarm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.carpark.CarDriver;

import java.util.List;

/**
 * <p>
 * 车辆报警信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface CarAlarmMapper extends BaseMapper<CarAlarm> {

    int batchInsert(List<CarAlarm> getCarAlarm);
}
