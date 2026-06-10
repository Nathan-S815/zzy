package com.zzy.api.controller.base;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.annotations.AddLog;
import com.zzy.api.service.base.*;
import com.zzy.api.service.excel.ExcelService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.*;
import com.zzy.db.entity.reportbase.ReportBaseEntertainment;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import io.minio.messages.ObjectLockConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/baseReportQuery")
@Api(value = "每日上报数据查询接口", tags = "每日上报数据查询接口")
public class BaseReportQueryController {
    @Autowired
    private IBaseHotelReportService iBaseHotelReportService;

    @Autowired
    private IBaseScenicReportService iBaseScenicReportService;

    @Autowired
    private IBaseTravelReportService iBaseTravelReportService;

    @Autowired
    private IBaseTravelLineReportService iBaseTravelLineReportService;

    @Autowired
    private IBaseRestaurantReportService iBaseRestaurantReportService;

    @Autowired
    private IBaseShoppingReportService iBaseShoppingReportService;

    @Autowired
    private IBaseRecreationReportService iBaseRecreationReportService;

    @Autowired
    private IBaseTrafficReportService iBaseTrafficReportService;

    @Autowired
    private IBaseGuideReportService iBaseGuideReportService;

    @Autowired
    private IBaseNoticeService iBaseNoticeService;


    @GetMapping("/listBaseNotice")
    @ApiOperation(value = "获取公告列表")
    public R listBaseNotice(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseNoticeService.selectPageListBaseNotice(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseNoticeById")
    @ApiOperation(value = "获取公告内容")
    public R getBaseNoticeById(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseNoticeService.getById(id));
    }

    @GetMapping("/listBaseGuideReport")
    @ApiOperation(value = "获取导游数据上报列表(管理员)")
    public R listBaseGuideReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseGuideReportService.selectPageListBaseGuideReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/listBaseScenicReport")
    @ApiOperation(value = "获取景区数据上报列表(管理员)")
    public R listBaseScenicReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseScenicReportService.selectPageListBaseScenicReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseGuideReportByScenicIdADMIN")
    @ApiOperation(value = "根据导游id获取导游上报数据(管理员)")
    public R getBaseGuideReportByScenicId(Integer pagNumber, Integer pageSize, Integer guideId, HttpServletRequest request) {
        if (guideId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        PageInfo<BaseGuideReport> baseGuideReportByGuideId = iBaseGuideReportService.getBaseGuideReportByGuideId(pagNumber, pageSize, guideId);
//        List<BaseGuideReport> list = iBaseGuideReportService.list(new QueryWrapper<BaseGuideReport>().eq("guide_id", guideId).orderByDesc("report_time"));
//        PageHelper.startPage(pagNumber, pageSize);
//        PageInfo<BaseGuideReport> baseGuideReportPageInfo = new PageInfo<>(list);
        return R.ok(baseGuideReportByGuideId);
    }

    @GetMapping("/getBaseScenicReportByScenicIdADMIN")
    @ApiOperation(value = "根据景区id获取景区上报数据(管理员)")
    public R getBaseScenicReportByScenicId(Integer pagNumber, Integer pageSize, Integer scenicId, HttpServletRequest request) {
        if (scenicId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        Integer userId = iBaseScenicReportService.getById(scenicId).getUserId();
        return R.ok(iBaseScenicReportService.getBaseScenicReportByScenicId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseScenicReportByScenicId")
    @ApiOperation(value = "获取本景区上报数据")
    public R getBaseScenicReportByScenicId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseScenicReportService.getBaseScenicReportByScenicId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseScenicReportByReportId")
    @ApiOperation(value = "获取景区上报数据内容")
    public R getBaseScenicReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseScenicReportService.getById(id));
    }

    @GetMapping("/listBaseHotelReport")
    @ApiOperation(value = "获取酒店数据上报列表(管理员)")
    public R listBaseHotelReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseHotelReportService.selectPageListBaseHotelReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseHotelReportByHotelIdADMIN")
    @ApiOperation(value = "根据酒店id获取酒店上报数据(管理员)")
    public R getBaseHotelReportByHotelIdADMIN(Integer pagNumber, Integer pageSize, Integer hotelId, HttpServletRequest request) {
        if (hotelId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseHotelReportService.getBaseHotelReportByHotelId(pagNumber, pageSize, hotelId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "住宿管理")
    @GetMapping("/getBaseHotelReportByHotelId")
    @ApiOperation(value = "获取本酒店上报数据")
    public R getBaseHotelReportByHotelId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseHotelReportService.getBaseHotelReportByHotelId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseHotelReportByReportId")
    @ApiOperation(value = "获取酒店上报数据内容")
    public R getBaseHotelReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseHotelReportService.getById(id));
    }

    @GetMapping("/listBaseTravelReport")
    @ApiOperation(value = "获取旅行社数据上报列表(管理员)")
    public R listBaseTravelReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseTravelReportService.selectPageListBaseTravelReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseTravelReportByTravelIdADMIN")
    @ApiOperation(value = "根据旅行社id获取旅行社上报数据(管理员)")
    public R getBaseTravelReportByTravelIdADMIN(Integer pagNumber, Integer pageSize, Integer travelId, HttpServletRequest request) {
        if (travelId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseTravelLineReportService.getBaseTravelReportByTravelId(pagNumber, pageSize, travelId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "旅行社管理")
    @GetMapping("/getBaseTravelReportByTravelId")
    @ApiOperation(value = "获取本旅行社上报数据")
    public R getBaseTravelReportByTravelId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseTravelLineReportService.getBaseTravelReportByTravelId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseTravelReportByReportId")
    @ApiOperation(value = "获取旅行社上报数据内容")
    public R getBaseTravelReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseTravelLineReportService.getById(id));
    }

    @GetMapping("/listBaseRestaurantReport")
    @ApiOperation(value = "获取餐饮数据上报列表(管理员)")
    public R listBaseRestaurantReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseRestaurantReportService.selectPageListBaseRestaurantReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseRestaurantReportByRestaurantIdADMIN")
    @ApiOperation(value = "根据餐饮id获取餐饮上报数据(管理员)")
    public R getBaseRestaurantReportByRestaurantIdADMIN(Integer pagNumber, Integer pageSize, Integer restaurantId, HttpServletRequest request) {
        if (restaurantId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseRestaurantReportService.getBaseRestaurantReportByRestaurantId(pagNumber, pageSize, restaurantId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "餐饮管理")
    @GetMapping("/getBaseRestaurantReportByRestaurantId")
    @ApiOperation(value = "获取本餐饮上报数据")
    public R getBaseRestaurantReportByRestaurantId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseRestaurantReportService.getBaseRestaurantReportByRestaurantId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseRestaurantReportByReportId")
    @ApiOperation(value = "获取餐饮上报数据内容")
    public R getBaseRestaurantReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseRestaurantReportService.getById(id));
    }

    @GetMapping("/listBaseShoppingReport")
    @ApiOperation(value = "获取购物数据上报列表(管理员)")
    public R listBaseShoppingReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseShoppingReportService.selectPageListBaseShoppingReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseShoppingReportByShoppingIdADMIN")
    @ApiOperation(value = "根据购物id获取购物上报数据(管理员)")
    public R getBaseShoppingReportByShoppingIdADMIN(Integer pagNumber, Integer pageSize, Integer shoppingId, HttpServletRequest request) {
        if (shoppingId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseShoppingReportService.getBaseShoppingReportByShoppingId(pagNumber, pageSize, shoppingId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "购物管理")
    @GetMapping("/getBaseShoppingReportByShoppingId")
    @ApiOperation(value = "获取本购物上报数据")
    public R getBaseShoppingReportByShoppingId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseShoppingReportService.getBaseShoppingReportByShoppingId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseShoppingReportByReportId")
    @ApiOperation(value = "获取购物上报数据内容")
    public R getBaseShoppingReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseShoppingReportService.getById(id));
    }

    @GetMapping("/listBaseRecreationReport")
    @ApiOperation(value = "获取娱乐数据上报列表(管理员)")
    public R listBaseRecreationReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseRecreationReportService.selectPageListBaseRecreationReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseRecreationReportByRecreationIdADMIN")
    @ApiOperation(value = "根据娱乐id获取娱乐上报数据(管理员)")
    public R getBaseRecreationReportByRecreationIdADMIN(Integer pagNumber, Integer pageSize, Integer recreationId, HttpServletRequest request) {
        if (recreationId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseRecreationReportService.getBaseRecreationReportByRecreationId(pagNumber, pageSize, recreationId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "娱乐管理")
    @GetMapping("/getBaseRecreationReportByRecreationId")
    @ApiOperation(value = "获取本娱乐上报数据")
    public R getBaseRecreationReportByRecreationId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseRecreationReportService.getBaseRecreationReportByRecreationId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseRecreationReportByReportId")
    @ApiOperation(value = "获取娱乐上报数据内容")
    public R getBaseRecreationReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseRecreationReportService.getById(id));
    }

    @GetMapping("/listBaseTrafficReport")
    @ApiOperation(value = "获取交通数据上报列表(管理员)")
    public R listBaseTrafficReport(Integer pagNumber, Integer pageSize, String keyword, HttpServletRequest request) {
        //CustomUser cu = JwtUtil.getFromJwt(request);
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (keyword == null) {
            keyword = "";
        }
        return R.ok(iBaseTrafficReportService.selectPageListBaseTrafficReport(pagNumber, pageSize, keyword));
    }

    @GetMapping("/getBaseTrafficReportByTrafficIdADMIN")
    @ApiOperation(value = "根据交通id获取交通上报数据(管理员)")
    public R getBaseTrafficReportByTrafficIdADMIN(Integer pagNumber, Integer pageSize, Integer trafficId, HttpServletRequest request) {
        if (trafficId == null) {
            return R.ok("参数错误");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        String startTime = "2000-01-01";
        String endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        return R.ok(iBaseTrafficReportService.getBaseTrafficReportByTrafficId(pagNumber, pageSize, trafficId, startTime, endTime));
    }

    @RequiredPermission(hasRole = "交通管理")
    @GetMapping("/getBaseTrafficReportByTrafficId")
    @ApiOperation(value = "获取本交通上报数据")
    public R getBaseTrafficReportByTrafficId(Integer pagNumber, Integer pageSize, HttpServletRequest request, String startTime, String endTime) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        Integer userId = cu.getUserId();
        if (userId == null) {
            return R.ok("获取不到userid");
        }
        if (pagNumber == null || pageSize == null) {
            pagNumber = 1;
            pageSize = 10;
        }
        if (startTime == null) {
            startTime = "2000-01-01";
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(DateUtil.tomorrow());
        }
        return R.ok(iBaseTrafficReportService.getBaseTrafficReportByTrafficId(pagNumber, pageSize, userId, startTime, endTime));
    }

    @GetMapping("/getBaseTrafficReportByReportId")
    @ApiOperation(value = "获取交通上报数据内容")
    public R getBaseTrafficReportByReportId(String id) {
        if (id == null) {
            return R.ok("获取不到id");
        }
        return R.ok(iBaseTrafficReportService.getById(id));
    }

    @ApiOperation(value = "导出Excel")
    @GetMapping("/public/getExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商户名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "商户id", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "导出类型 1.景区  2.住宿  3.旅行社  4.餐饮  5.交通  6.购物  7.娱乐", required = false, paramType = "query", dataType = "String")
    })
    public String getExcel(String name, int[] id, int type, HttpServletResponse response) throws IOException {
        try {
            if (id.length == 0 || name == null) {
                return "参数错误";
            }
            ExcelWriter writer = null;
            OutputStream outputStream = response.getOutputStream();
            String fileName = name + "每日上报数据.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);
            if (type == 1) {
//                PageInfo<Map<String, Object>> result = iBaseScenicReportService.getBaseScenicReportByScenicId(1, 100000, id, "2000-01-01", "2099-12-31");
                List<BaseScenicReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseScenicReport baseScenicReport = iBaseScenicReportService.getById(i);
                    result.add(baseScenicReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.ScenicReport.class);
                sheet.setSheetName("目录");
                List<ExcelService.ScenicReport> catagoryList = new ArrayList<>();
                for (BaseScenicReport baseScenicReport : result) {
                    ExcelService.ScenicReport scenicReport = new ExcelService.ScenicReport();
                    scenicReport.setScenicName(name);
                    scenicReport.setReportTime(DateUtil.format(baseScenicReport.getReportTime(),"yyyy-MM-dd"));
                    scenicReport.setIndividualPeopleNum(baseScenicReport.getAloneNum());
                    scenicReport.setRestaurantPeopleNum(baseScenicReport.getFoodServiceNum());
                    scenicReport.setAllPeopleNum(baseScenicReport.getInPeople());
                    catagoryList.add(scenicReport);
                }
                //输出
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 2) {
//                List<Map<String, Object>> result = (List<Map<String, Object>>) iBaseHotelReportService.getBaseHotelReportByHotelId(1, 100000, id, "2000-01-01", "2099-12-31").get("data");
                List<BaseHotelReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseHotelReport baseHotelReport = iBaseHotelReportService.getById(i);
                    result.add(baseHotelReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.HotelReport.class);
                sheet.setSheetName("目录");
                List<ExcelService.HotelReport> catagoryList = new ArrayList<>();
                for (BaseHotelReport baseHotelReport : result) {
                    ExcelService.HotelReport hotelReport = new ExcelService.HotelReport();
                    hotelReport.setHotelName(name);
                    hotelReport.setReportTime(DateUtil.format(baseHotelReport.getReportTime(),"yyyy-MM-dd"));
                    hotelReport.setAllPeopleNum(baseHotelReport.getInPeople());
                    hotelReport.setIncome(baseHotelReport.getIncome());
                    hotelReport.setEmptyRoom(baseHotelReport.getSurplusRoomNum());
                    catagoryList.add(hotelReport);
                }
                //输出
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 3) {
//                List<Map<String, Object>> result = (List<Map<String, Object>>) iBaseTravelLineReportService.getBaseTravelReportByTravelId(1, 100000, id, "2000-01-01", "2099-12-31").get("list");
                List<BaseTravelLineReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseTravelLineReport baseTravelLineReport = iBaseTravelLineReportService.getById(i);
                    result.add(baseTravelLineReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.TravelReport.class);
                sheet.setSheetName("目录");
                List<ExcelService.TravelReport> catagoryList = new ArrayList<>();
                for (BaseTravelLineReport baseTravelLineReport : result) {
                    ExcelService.TravelReport travelReport = new ExcelService.TravelReport();
                    travelReport.setTravelName(name);
                    travelReport.setReportTime(DateUtil.format(baseTravelLineReport.getReportTime(),"yyyy-MM-dd"));
                    travelReport.setLine(baseTravelLineReport.getTravelLine());
                    travelReport.setPeopleNum(baseTravelLineReport.getPeopleNum().toString());
                    catagoryList.add(travelReport);
                }
                //输出
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 4) {
//                PageInfo<Map<String, Object>> result = iBaseRestaurantReportService.getBaseRestaurantReportByRestaurantId(1, 100000, id, "2000-01-01", "2099-12-31");
                List<BaseRestaurantReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseRestaurantReport baseRestaurantReport = iBaseRestaurantReportService.getById(i);
                    result.add(baseRestaurantReport);
                }
                List<ExcelService.OtherReport> catagoryList = new ArrayList<>();
                for (BaseRestaurantReport baseRestaurantReport : result) {
                    ExcelService.OtherReport otherReport = new ExcelService.OtherReport();
                    otherReport.setName(name);
                    otherReport.setReportTime(DateUtil.format(baseRestaurantReport.getReportTime(),"yyyy-MM-dd"));
                    otherReport.setIncome(baseRestaurantReport.getIncome());
                    catagoryList.add(otherReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.OtherReport.class);
                sheet.setSheetName("目录");
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 5) {
//                PageInfo<Map<String, Object>> result = iBaseTrafficReportService.getBaseTrafficReportByTrafficId(1, 100000, id, "2000-01-01", "2099-12-31");
                List<BaseTrafficReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseTrafficReport baseTrafficReport = iBaseTrafficReportService.getById(i);
                    result.add(baseTrafficReport);
                }
                List<ExcelService.OtherReport> catagoryList = new ArrayList<>();
                for (BaseTrafficReport baseTrafficReport : result) {
                    ExcelService.OtherReport otherReport = new ExcelService.OtherReport();
                    otherReport.setName(name);
                    otherReport.setReportTime(DateUtil.format(baseTrafficReport.getReportTime(),"yyyy-MM-dd"));
                    otherReport.setIncome(baseTrafficReport.getIncome());
                    catagoryList.add(otherReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.OtherReport.class);
                sheet.setSheetName("目录");
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 6) {
//                PageInfo<Map<String, Object>> result = iBaseShoppingReportService.getBaseShoppingReportByShoppingId(1, 100000, id, "2000-01-01", "2099-12-31");
                List<BaseShoppingReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseShoppingReport baseShoppingReport = iBaseShoppingReportService.getById(i);
                    result.add(baseShoppingReport);
                }
                List<ExcelService.OtherReport> catagoryList = new ArrayList<>();
                for (BaseShoppingReport baseShoppingReport : result) {
                    ExcelService.OtherReport otherReport = new ExcelService.OtherReport();
                    otherReport.setName(name);
                    otherReport.setReportTime(DateUtil.format(baseShoppingReport.getReportTime(),"yyyy-MM-dd"));
                    otherReport.setIncome(baseShoppingReport.getIncome());
                    catagoryList.add(otherReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.OtherReport.class);
                sheet.setSheetName("目录");
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else if (type == 7) {
//                PageInfo<Map<String, Object>> result = iBaseRecreationReportService.getBaseRecreationReportByRecreationId(1, 100000, id, "2000-01-01", "2099-12-31");
                List<BaseRecreationReport> result = new ArrayList<>();
                for (int i : id) {
                    BaseRecreationReport baseRecreationReport = iBaseRecreationReportService.getById(i);
                    result.add(baseRecreationReport);
                }
                List<ExcelService.OtherReport> catagoryList = new ArrayList<>();
                for (BaseRecreationReport baseRecreationReport : result) {
                    ExcelService.OtherReport otherReport = new ExcelService.OtherReport();
                    otherReport.setName(name);
                    otherReport.setReportTime(DateUtil.format(baseRecreationReport.getReportTime(),"yyyy-MM-dd"));
                    otherReport.setIncome(baseRecreationReport.getIncome());
                    catagoryList.add(otherReport);
                }
                Sheet sheet = new Sheet(1, 0, ExcelService.OtherReport.class);
                sheet.setSheetName("目录");
                writer.write(catagoryList, sheet);
                writer.finish();
                outputStream.flush();
            } else {
                return "参数错误";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "导出成功";
    }

//    private List<ExcelService.OtherReport> setOtherExcel(PageInfo<Map<String, Object>> result, String name) {
//        List<ExcelService.OtherReport> catagoryList = new ArrayList<>();
//        for (Map<String, Object> map : result.getList()) {
//            ExcelService.OtherReport otherReport = new ExcelService.OtherReport();
//            otherReport.setName(name);
//            otherReport.setReportTime(map.get("report_time").toString().substring(0, 10));
//            otherReport.setIncome(map.get("income").toString());
//            catagoryList.add(otherReport);
//        }
//        return catagoryList;
//    }
    @ApiOperation(value = "test")
    @GetMapping("/test")
    public String test(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {

            String url = "http://172.20.18.3:7777/loginCenter/sysLog/insert";
            Map<String ,Object> param = new HashMap<>();
            param.put("operUser","userId");
            param.put("logType",2);
            param.put("operType",5);
            param.put("logPlatform",2);
            param.put("operContent","测试");
            param.put("remoteIp","192.168.1.1");
            String body = HttpUtil.createPost(url).form("operUser","userId").body(JSON.toJSONString(param)).setConnectionTimeout(3000).execute().body();
            return body;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
