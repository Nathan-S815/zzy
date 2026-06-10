package com.zzy.api.controller.consumption;

import com.zzy.api.annotations.SelectLog;
import com.zzy.api.service.base.*;
import com.zzy.api.service.consumption.ConsumptionService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.security.annotations.RequiredPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

@RestController
@RequestMapping("/consumption")
@Api(value = "游客综合消费数据分析", tags = "游客综合消费数据分析")
public class ConsumptionController {

    @Autowired
    private ConsumptionService consumptionService;
    @SelectLog(operContent = "游客综合消费数据分析")
    @GetMapping("/getConsumption")
    @ApiOperation(value = "游客综合消费数据分析")
    public R getConsumption(String startTime, String endTime) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (endTime == null){
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null){
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        return R.ok(consumptionService.getConsumption(startTime, endTime));
    }
    @SelectLog(operContent = "人均消费数据")
    @GetMapping("/getConsumptionPerPerson")
    @ApiOperation(value = "人均消费数据")
    public R getConsumptionPerPerson(String startTime, String endTime) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (endTime == null){
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null){
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        return R.ok(consumptionService.getConsumptionPerPerson(startTime, endTime));
    }
    @SelectLog(operContent = "消费数据预测")
    @GetMapping("/getConsumptionForecast")
    @ApiOperation(value = "消费数据预测")
    public R getConsumptionForecast() {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        return R.ok(consumptionService.getConsumptionForecast());
    }


}
