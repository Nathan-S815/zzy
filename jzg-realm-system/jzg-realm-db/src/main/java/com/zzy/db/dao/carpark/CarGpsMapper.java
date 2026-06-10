package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.CarGps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.carpark.GetEnterCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆gps信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface CarGpsMapper extends BaseMapper<CarGps> {

    int batchInsert(List<CarGps> getCarGps);

    /**
     *获取今日运行景交车车牌号码
     * */
    List<Map<String,Object>> getNowDayScenicCarNo(@Param("date") String date);

    /**
     *根据车牌获取车辆的司机信息和驾驶车辆的经纬度
     */
    List<Map<String,Object>> getScenicCarDriverAndLonLat(@Param("date") String date,
                                                         @Param("time")String time,
                                                         @Param("vehicleNo")String vehicleNo);
}
