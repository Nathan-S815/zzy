package com.zzy.api.service.carpark.impl;

import com.zzy.db.entity.carpark.CarGps;
import com.zzy.db.dao.carpark.CarGpsMapper;
import com.zzy.api.service.carpark.ICarGpsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆gps信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
@Service
public class CarGpsServiceImpl extends ServiceImpl<CarGpsMapper, CarGps> implements ICarGpsService {
    @Autowired
    private CarGpsMapper carGpsMapper;
    @Override
    public List<Map<String,Object>> getNowDayScenicCarNo(String date) {
        return carGpsMapper.getNowDayScenicCarNo(date);
    }

    @Override
    public List<Map<String, Object>> getScenicCarDriverAndLonLat(String date, String time, String vehicleNo) {
        return carGpsMapper.getScenicCarDriverAndLonLat(date,time,vehicleNo);
    }
}
