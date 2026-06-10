package com.zzy.api.controller.carpark;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.zzy.api.annotations.SelectLog;
import com.zzy.api.service.carpark.ICarGpsService;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scenicCar/public")
@Api(value = "大屏景交车数据接口", tags = "大屏景交车数据接口")
public class ScenicCarController {

   @Autowired
   private ICarGpsService iCarGpsService;

    @SelectLog(operContent = "获取今日运行景交车车牌号码")
    @GetMapping("/getNowDayScenicCarNo")
    @ApiOperation(value = "获取今日运行景交车车牌号码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="date",value="日期（eg:2020-06-04）", required=true,paramType = "query",  dataType="string")
    })
    public R getNowDayScenicCarNo(String date){
        List<Map<String, Object>> nowDayScenicCarNo = iCarGpsService.getNowDayScenicCarNo(date);
        if(nowDayScenicCarNo.size()>0){
            return R.ok(nowDayScenicCarNo);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getScenicCarDriverAndLonLat")
    @ApiOperation(value = "根据车牌获取车辆的司机信息和驾驶车辆的经纬度")
    @ApiImplicitParams({
            @ApiImplicitParam(name="date",value="日期（eg:2020-06-04）", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="time",value="当前时间（eg:12:01:51）", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="vehicleNo",value="车牌号（eg:川UA0026）", required=false,paramType = "query",  dataType="string")
    })
    public R getScenicCarDriverAndLonLat(String date, String time, String vehicleNo){
        List<Map<String, Object>> scenicCarDriverAndLonLat = iCarGpsService.getScenicCarDriverAndLonLat(date, time, vehicleNo);
        if(scenicCarDriverAndLonLat.size()>0){
            return R.ok(scenicCarDriverAndLonLat);
        }
        return R.ok("暂无数据");
    }

}
