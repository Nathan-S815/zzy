package com.zzy.api.service.carpark;

import com.zzy.db.entity.carpark.CarGps;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆gps信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface ICarGpsService extends IService<CarGps> {

    List<Map<String,Object>> getNowDayScenicCarNo(String date);

    List<Map<String,Object>> getScenicCarDriverAndLonLat(String date,
                                                         String time,
                                                         String vehicleNo);

}
