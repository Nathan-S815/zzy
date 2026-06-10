package com.zzy.api.service.carpark.impl;

import com.zzy.db.entity.carpark.CarInfo;
import com.zzy.db.dao.carpark.CarInfoMapper;
import com.zzy.api.service.carpark.ICarInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆静态信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Service
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements ICarInfoService {

}
