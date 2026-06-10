package com.zzy.api.controller.portrait;

import cn.hutool.core.date.DateUtil;
import com.zzy.api.service.base.IBaseHotelReportService;
import com.zzy.api.service.portrait.ITouristStayScenicMonthService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stay")
@Api(value = "游客逗留时长相关接口", tags = "游客逗留时长相关接口")
public class TouristStayScenicMonthController {
    @Autowired
    private IBaseHotelReportService iBaseHotelReportService;

    @GetMapping("/getTouristStay")
    @ApiOperation(value = "获取逗留时长")
    public R getTouristStay(){
        String startTime = TimeDateUtil.getDateBefore(30);
        String endTime = TimeDateUtil.getFormatDate(new Date());
        return R.ok(iBaseHotelReportService.getTouristStay(startTime,endTime));
    }

    @GetMapping("/getTouristStayByScenic")
    @ApiOperation(value = "根据景区获取逗留时长")
    public R getTouristStayByScenic(){
        String startTime = TimeDateUtil.getDateBefore(30);
        String endTime = TimeDateUtil.getFormatDate(new Date());
        return R.ok(iBaseHotelReportService.getTouristStayByScenic(startTime,endTime));
    }
//    @Autowired
//    private ITouristStayScenicMonthService iTouristStayScenicMonthService;
//
//    @GetMapping("/getTouristStayOutProvince")
//    @ApiOperation(value = "获取省外逗留时长")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-05)", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "sceneId", value = "景区编码", required = true, paramType = "query", dataType = "String")
//    })
//    public R getTouristStayOutProvince(String accTime,String sceneId){
//        if(accTime!=null) {
//            String replace = accTime.replace("-", "");
//             return R.ok(iTouristStayScenicMonthService.getTouristStayOutProvince(Long.valueOf(replace), sceneId));
//        }
//
//        return R.ok(iTouristStayScenicMonthService.getTouristStayOutProvince(null, null));
//    }
//    @GetMapping("/getTouristStayInProvince")
//    @ApiOperation(value = "获取省内逗留时长")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-05)", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "sceneId", value = "景区编码", required = true, paramType = "query", dataType = "String")
//    })
//    public R getTouristStayInProvince(String accTime,String sceneId){
//        if(accTime!=null) {
//            String replace = accTime.replace("-", "");
//            return R.ok(iTouristStayScenicMonthService.getTouristStayInProvince(Long.valueOf(replace), sceneId));
//        }
//        return R.ok(iTouristStayScenicMonthService.getTouristStayInProvince(null, null));
//    }
//
//    @GetMapping("/getTouristStayInProvinceByScenic")
//    @ApiOperation(value = "获取各景区省内人数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-05)", required = true, paramType = "query", dataType = "String")
//    })
//    public R getTouristStayInProvinceByScenic(String accTime){
//        if(accTime!=null) {
//            String replace = accTime.replace("-", "");
//            return R.ok(iTouristStayScenicMonthService.getTouristStayInProvinceByScenic(Long.valueOf(replace)));
//
//        }
//        return R.ok(iTouristStayScenicMonthService.getTouristStayInProvinceByScenic(null));
//    }
//
//    @GetMapping("/getTouristStayOutProvinceByScenic")
//    @ApiOperation(value = "获取各景区省外人数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-05)", required = true, paramType = "query", dataType = "String")
//    })
//    public R getTouristStayOutProvinceByScenic(String accTime){
//        if(accTime!=null) {
//            String replace = accTime.replace("-", "");
//            return R.ok(iTouristStayScenicMonthService.getTouristStayOutProvinceByScenic(Long.valueOf(replace)));
//        }
//        return R.ok(iTouristStayScenicMonthService.getTouristStayOutProvinceByScenic(null));
//    }
}
