package com.zzy.db.dao.checkpoint;

import com.zzy.db.entity.carpark.GetOutCar;
import com.zzy.db.entity.checkpoint.TrafficCheckpoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-17
 */
public interface TrafficCheckpointMapper extends BaseMapper<TrafficCheckpoint> {

    int batchInsert(List<TrafficCheckpoint> trafficCheckpoint);

    List<Map<String,Object>> getVehicleOrigin(@Param("checkPointName") String checkPointName,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
