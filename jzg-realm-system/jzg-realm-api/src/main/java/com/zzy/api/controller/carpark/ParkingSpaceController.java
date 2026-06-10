package com.zzy.api.controller.carpark;

import com.zzy.api.annotations.SelectLog;
import com.zzy.api.service.carpark.IGetRemainingSpaceHService;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Api(tags = "停车位接口")
@RestController
@RequestMapping("/parkSpace")
public class ParkingSpaceController {

    @Autowired
    private IGetRemainingSpaceHService iGetRemainingSpaceHService;

    @SelectLog(operContent = "景区总车位情况")
    @ApiOperation(value = "景区总车位情况")
    @GetMapping("/getAllScenicPark")
    public R getAllDepartments(){
        return R.ok(iGetRemainingSpaceHService.getAllScenicPark());
    }

    @SelectLog(operContent = "景区总车位情况(大屏)")
    @ApiOperation(value = "景区总车位情况(大屏)")
    @GetMapping("/public/getAllScenicParkScreen")
    public R getAllScenicParkScreen(){
        return R.ok(iGetRemainingSpaceHService.getAllScenicParkScreen());
    }

    @SelectLog(operContent = "景区各停车场车位情况")
    @ApiOperation(value = "景区各停车场车位情况",notes = "九寨沟景区、九寨沟县、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)、古藏寨")
    @GetMapping("/getParkByScenic")
    public R getParkByScenic(String scenicName){
        if (scenicName == null){
            return R.error("参数不能为空");
        }
        if (!("九寨沟景区".equals(scenicName)||
                "九寨沟县".equals(scenicName)||
                "嫩恩桑措(神仙池)".equals(scenicName)||
                "甲勿海".equals(scenicName)||
                "大熊猫".equals(scenicName)||
                "爱情海(甘海子)".equals(scenicName)||
                "古藏寨".equals(scenicName)
        )){
            return R.error("参数错误");
        }
        return R.ok(iGetRemainingSpaceHService.getParkByScenic(scenicName));
    }
}
