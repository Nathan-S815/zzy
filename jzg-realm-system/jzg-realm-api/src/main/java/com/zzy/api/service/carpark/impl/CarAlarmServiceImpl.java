package com.zzy.api.service.carpark.impl;

import com.zzy.db.entity.carpark.CarAlarm;
import com.zzy.db.dao.carpark.CarAlarmMapper;
import com.zzy.api.service.carpark.ICarAlarmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆报警信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Service
public class CarAlarmServiceImpl extends ServiceImpl<CarAlarmMapper, CarAlarm> implements ICarAlarmService {

}
