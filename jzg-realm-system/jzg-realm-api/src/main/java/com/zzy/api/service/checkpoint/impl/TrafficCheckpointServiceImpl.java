package com.zzy.api.service.checkpoint.impl;

import com.zzy.db.entity.checkpoint.TrafficCheckpoint;
import com.zzy.db.dao.checkpoint.TrafficCheckpointMapper;
import com.zzy.api.service.checkpoint.ITrafficCheckpointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-17
 */
@Service
public class TrafficCheckpointServiceImpl extends ServiceImpl<TrafficCheckpointMapper, TrafficCheckpoint> implements ITrafficCheckpointService {

    @Autowired
    private TrafficCheckpointMapper trafficCheckpointMapper;

    @Override
    public List<Map<String, Object>> getVehicleOrigin(String checkPointName, String startTime, String endTime) {
        return trafficCheckpointMapper.getVehicleOrigin(checkPointName,startTime,endTime);
    }
}
