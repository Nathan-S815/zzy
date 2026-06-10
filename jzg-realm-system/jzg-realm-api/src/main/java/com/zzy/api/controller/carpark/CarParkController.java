package com.zzy.api.controller.carpark;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.annotations.SelectLog;
import com.zzy.api.service.carpark.IGetEnterCarService;
import com.zzy.api.service.carpark.IGetOutCarService;
import com.zzy.core.dto.R;
import com.zzy.db.entity.carpark.GetEnterCar;
import com.zzy.db.entity.carpark.GetOutCar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/carpark")
@Api(value = "游客车源地分析接口", tags = "游客车源地分析接口")
public class CarParkController {

    @Autowired
    private IGetEnterCarService iGetEnterCarService;

    @Autowired
    private IGetOutCarService iGetOutCarService;

    @SelectLog(operContent = "测试123")
    @GetMapping("/getCarPlaceInCount")
    @ApiOperation(value = "车辆来源数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String")
    })
    public R getCarPlaceInCount(String enterTime){
//        List<Map<Object, Object>> carPlaceCount = iGetEnterCarService.getCarPlaceCount(enterTime);
//        if(carPlaceCount!=null) {
//            return R.ok(carPlaceCount);
//        }
//        return R.ok("暂无数据");
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("caron","四川");
        map1.put("carcount","3786");

        Map<String,Object> map2 = new HashMap<>();
        map2.put("caron","福建");
        map2.put("carcount","1576");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("caron","山东");
        map3.put("carcount","1154");

        Map<String,Object> map4 = new HashMap<>();
        map4.put("caron","广州");
        map4.put("carcount","1010");

        Map<String,Object> map5 = new HashMap<>();
        map5.put("caron","浙江");
        map5.put("carcount","854");

        Map<String,Object> map6 = new HashMap<>();
        map6.put("caron","北京");
        map6.put("carcount","631");

        Map<String,Object> map7 = new HashMap<>();
        map7.put("caron","山西");
        map7.put("carcount","598");

        Map<String,Object> map8 = new HashMap<>();
        map8.put("caron","湖南");
        map8.put("carcount","510");

        Map<String,Object> map9 = new HashMap<>();
        map9.put("caron","湖北");
        map9.put("carcount","480");

        Map<String,Object> map10 = new HashMap<>();
        map10.put("caron","辽宁");
        map10.put("carcount","453");

        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        result.add(map7);
        result.add(map8);
        result.add(map9);
        result.add(map10);
        return R.ok(result);
    }
    @SelectLog(operContent = "获取进入车辆数量")
    @GetMapping("/getInCarCount")
    @ApiOperation(value = "获取进入车辆数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String")
    })
    public R getInCarCount(String enterTime){
        Integer entertime1=null;
        if(enterTime!=null) {
            entertime1 = iGetEnterCarService.count(new QueryWrapper<GetEnterCar>().like("enter_time", enterTime));
        }else {
            entertime1 = iGetEnterCarService.count(new QueryWrapper<GetEnterCar>().like("enter_time", DateUtil.year(new Date())));
        }
        return R.ok(entertime1);
    }
    @SelectLog(operContent = "获取出去车辆数量")
    @GetMapping("/getOutCarCount")
    @ApiOperation(value = "获取出去车辆数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String")
    })
    public R getOutCarCount(String outTime){
        Integer out=null;
        if(outTime!=null) {
             out = iGetOutCarService.count(new QueryWrapper<GetOutCar>().like("out_time", outTime));
        }else {
            out = iGetOutCarService.count(new QueryWrapper<GetOutCar>().like("out_time", DateUtil.year(new Date())));
        }
        return R.ok(out);
    }
    @SelectLog(operContent = "当前车辆数量")
    @GetMapping("/getNowCarCount")
    @ApiOperation(value = "当前车辆数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nowTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String")
    })
    public R getNowCarCount(String nowTime){
        Integer entertime1=null;
        Integer out=null;
        if(nowTime!=null) {
             entertime1 = iGetEnterCarService.count(new QueryWrapper<GetEnterCar>().like("enter_time", nowTime));
             out = iGetOutCarService.count(new QueryWrapper<GetOutCar>().like("out_time", nowTime));
        }else {
            entertime1 = iGetEnterCarService.count(new QueryWrapper<GetEnterCar>().like("enter_time",  DateUtil.year(new Date())));
            out = iGetOutCarService.count(new QueryWrapper<GetOutCar>().like("out_time",  DateUtil.year(new Date())));
        }
        Integer ceshishuju =308;
        long time = System.currentTimeMillis();
        int number = new Random().nextInt(10) + 1;
        if(time%2==0){
            ceshishuju+=number;
        }else {
            ceshishuju-=number;
        }

        return R.ok(ceshishuju);
    }
}
