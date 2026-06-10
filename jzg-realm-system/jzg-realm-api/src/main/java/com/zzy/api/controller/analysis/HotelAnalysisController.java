package com.zzy.api.controller.analysis;


import com.zzy.api.service.base.IBaseHotelReportService;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
@Api(value = "酒店上报数据分析接口", tags = "酒店上报数据分析接口")
public class HotelAnalysisController {

    @Autowired
    private IBaseHotelReportService iBaseHotelReportService;

    @GetMapping("/getTotalNumber")
    @ApiOperation(value = "当前接待总人数")
    public R listBaseScenic(){
        return R.ok(iBaseHotelReportService.getTotalNumber());
    }

    @GetMapping("/getHotelOccupancy")
    @ApiOperation(value = "当前酒店入住率")
    public R getHotelOccupancy(){
        return R.ok(iBaseHotelReportService.getHotelOccupancy());
    }

    @GetMapping("/getHotelOccupancyByWeek")
    @ApiOperation(value = "本周酒店入住率")
    public R getHotelOccupancyByWeek(){
        return R.ok(iBaseHotelReportService.getHotelOccupancyByWeek());
    }

    @GetMapping("/getForecastByWeek")
    @ApiOperation(value = "本周预测")
    public R getForecastByWeek(){
        return R.ok(iBaseHotelReportService.getForecastByWeek());
    }

    @GetMapping("/getHotelAnalysisByScenic")
    @ApiOperation(value = "景区周边酒店数据分析",notes = "入住率，空房数，接待人数")
    public R getHotelAnalysisByScenic(String modul){
        if ("入住率".equals(modul)){
            return R.ok(iBaseHotelReportService.getOccupancyByScenic());
        }else if ("空房数".equals(modul)){
            return R.ok(iBaseHotelReportService.getEmptyRoomByScenic());
        }else if ("接待人数".equals(modul)){
            return R.ok(iBaseHotelReportService.getTotelPeopleByScenic());
        }else {
            return R.ok("参数错误");
        }
    }


}
