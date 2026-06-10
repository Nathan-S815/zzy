package com.zzy.api.controller.analysis;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zzy.api.service.base.IBaseTravelLineReportService;
import com.zzy.api.service.base.IBaseTravelReportService;
import com.zzy.api.service.excel.ExcelService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.BaseTravelLineReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/travel")
@Api(value = "旅行社上报数据分析接口", tags = "旅行社上报数据分析接口")
public class TravelAnalylisController {

    @Autowired
    private IBaseTravelReportService iBaseTravelReportService;

    @Autowired
    private IBaseTravelLineReportService iBaseTravelLineReportService;

    @GetMapping("/getTravelTotalReception")
    @ApiOperation(value = "旅行社接待总量接口")
    public R getTravelTotalReception(String year) {
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        Integer travelTotalReception = iBaseTravelLineReportService.getTravelForecastWeek(startTime, endTime);
        if (travelTotalReception != null) {
            return R.ok(travelTotalReception);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getTravelTotalReceptionNew")
    @ApiOperation(value = "旅行社接待总量接口")
    public R getTravelTotalReceptionNew() {
        String result = HttpUtil.post("https://m.abatour.com/chartAbaTour/dataAnalysis/YKTSB_4.html", new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray series = jsonObject.getJSONArray("series");
        JSONArray datalist = series.getJSONObject(0).getJSONArray("datalist");
        JSONArray data = datalist.getJSONObject(0).getJSONArray("data");
        Map<String, Object> map;
        for (Object datum : data) {
            map = (Map<String,Object>) datum;
            String name = Convert.toStr(map.get("name"));
            if("团队".equals(name)){
                return R.ok(map.get("value"));
            }
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getScenicTravelReceptionCount")
//    @ApiOperation(value = "各景区旅行社接待人数")
//    public R getScenicTravelReceptionCount() {
//        String Time = TimeDateUtil.getDateBefore(2);
//        Map<String, Object> scenicTravelReceptionCount = iBaseTravelLineReportService.getScenicTravelReceptionCount(Time);
//        return R.ok(scenicTravelReceptionCount);
//    }

    @GetMapping("/getScenicTravelReceptionCount")
    @ApiOperation(value = "各景区旅行社接待人数")
    public R getScenicTravelReceptionCount() {
        String Time = TimeDateUtil.getDateBefore(0);
        List<Map<String, Object>> scenicTravelReceptionCount = iBaseTravelLineReportService.getScenicTravelReceptionCount(Time);
        return R.ok(scenicTravelReceptionCount);
    }

    @GetMapping("/getTravelForecastWeek")
    @ApiOperation(value = "预计一周接待人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTravelForecastWeek(String startTime, String endTime) {
        Integer travelForecastWeek = iBaseTravelLineReportService.getTravelForecastWeek(startTime, endTime);
        if (travelForecastWeek != null) {
            return R.ok(travelForecastWeek);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getNowWeekReport")
    @ApiOperation(value = "当前一周的接待人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2019-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2019-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getNowWeekReport(String startTime, String endTime) {
        Integer travelForecastWeek = iBaseTravelLineReportService.getTravelForecastWeek(startTime, endTime);
        if (travelForecastWeek != null) {
            return R.ok(travelForecastWeek);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getTravelLineRateAnalysis")
    @ApiOperation(value = "路线占比分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTravelLineRateAnalysis(String startTime, String endTime) {
        List<Map<String, Object>> travelLineRate = iBaseTravelLineReportService.getTravelLineRate(startTime, endTime);
        if (travelLineRate.size() != 0) {
            return R.ok(travelLineRate);
        }
        return R.ok("暂无数据");
    }

    @ApiOperation(value = "路线占比分析Excel")
    @GetMapping("/getTravelLineRateAnalysisExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public String getTouristDistributionExcel(String startTime, String endTime, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> travelLineRate = iBaseTravelLineReportService.getTravelLineRate(startTime, endTime);
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            String fileName = "路线占比分析.xls";
            response.setHeader("Content-disposition", "attachment; filename=" +new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.TravelLineReport.class);
            sheet.setSheetName("目录");
            List<ExcelService.TravelLineReport> catagoryList = new ArrayList<>();
            for (Map<String, Object> map : travelLineRate) {
                ExcelService.TravelLineReport touristDistribution = new ExcelService.TravelLineReport();
                touristDistribution.setLine(map.get("line").toString());
                touristDistribution.setPeopleNum(map.get("num").toString());
                touristDistribution.setRate(map.get("rate").toString()+"%");
                catagoryList.add(touristDistribution);
            }
            //输出
            writer.write(catagoryList, sheet);
            writer.finish();
            outputStream.flush();
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

    @GetMapping("/getTravelLineRate")
    @ApiOperation(value = "旅游产品分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTravelLineRate(String startTime, String endTime) {
        List<Map<String, Object>> travelLineRate = iBaseTravelLineReportService.getTravelProduct(startTime, endTime);
        if (travelLineRate.size() != 0) {
            return R.ok(travelLineRate);
        }
        return R.ok("暂无数据");
    }

}
