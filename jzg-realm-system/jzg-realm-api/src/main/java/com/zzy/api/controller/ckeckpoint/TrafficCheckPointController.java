package com.zzy.api.controller.ckeckpoint;

import cn.hutool.core.date.DateUtil;
import com.zzy.api.annotations.SelectLog;
import com.zzy.api.service.checkpoint.ITrafficCheckpointService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.checkpoint.TrafficCheckpointMapper;
import com.zzy.db.entity.checkpoint.TrafficCheckpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trafficCheckPoint")
@Api(value = "交通卡口接口", tags = "交通卡口接口")
public class TrafficCheckPointController {
    @Autowired
    private TrafficCheckpointMapper trafficCheckpointMapper;

    @Autowired
    private ITrafficCheckpointService iTrafficCheckpointService;
    @SelectLog(operContent = "卡口数据采集")
    @ApiOperation(value = "卡口数据采集")
    @PostMapping("/public/getInformation")
    public R getInformation(@RequestBody List<TrafficCheckpoint> trafficCheckpointList) {
        for (TrafficCheckpoint trafficCheckpoint : trafficCheckpointList) {
            trafficCheckpoint.setIsDelete(0);
            trafficCheckpoint.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }
        int i = trafficCheckpointMapper.batchInsert(trafficCheckpointList);
        if (i>0){
            return R.ok("保存成功");
        }else {
            return R.ok("保存失败");
        }
    }
    @SelectLog(operContent = "本月游客车源地分析")
    @ApiOperation(value = "本月游客车源地分析")
    @PostMapping("/public/vehicleOrigin")
    public R vehicleOrigin(String checkPointName,String startTime,String endTime) {
        if (startTime == null){
            startTime = TimeDateUtil.getFirstOfMonth(TimeDateUtil.getFormatDate(new Date()));
        }
        if (endTime == null){
            endTime = TimeDateUtil.getFormatDate(DateUtil.endOfMonth(new Date()));
        }
        return R.ok(iTrafficCheckpointService.getVehicleOrigin(checkPointName,startTime,endTime));
    }

}
