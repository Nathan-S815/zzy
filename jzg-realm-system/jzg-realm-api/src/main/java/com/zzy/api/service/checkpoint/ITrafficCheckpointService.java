package com.zzy.api.service.checkpoint;

import com.zzy.db.entity.checkpoint.TrafficCheckpoint;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-06-17
 */
public interface ITrafficCheckpointService extends IService<TrafficCheckpoint> {

    public List<Map<String,Object>> getVehicleOrigin(String checkPointName,String startTime,String endTime);
}
