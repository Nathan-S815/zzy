package com.zzy.api.controller.tourist;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.math.MathUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.service.base.IBaseHotelReportService;
import com.zzy.api.service.base.IBaseTicketService;
import com.zzy.api.service.excel.ExcelService;
import com.zzy.api.service.ticket.IScenicEnterPeopleService;
import com.zzy.api.service.tourist.ITouristSourceScenicDayService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.BaseScenic;
import com.zzy.db.entity.base.BaseTicket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.TableStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tourist")
@Api(tags = "游客分析")
public class TouristController {

    @Autowired
    private ITouristSourceScenicDayService iTouristSourceScenicDayService;
    @Autowired
    private IScenicEnterPeopleService iScenicEnterPeopleService;
    @Autowired
    private IBaseHotelReportService iBaseHotelReportService;
    @Autowired
    private IBaseTicketService iBaseTicketService;

    @ApiOperation(value = "游客分布分析")
    @GetMapping("/getTouristDistribution")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristDistribution(String startTime, String endTime) {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
//        endTime = endTime.replaceAll("-", "");
//        startTime = startTime.replaceAll("-", "");
        return R.ok(iBaseTicketService.getTouristDistribution(startTime, endTime));
    }

    @ApiOperation(value = "游客分布分析导出Excel")
    @GetMapping("/getTouristDistributionExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public String getTouristDistributionExcel(String startTime, String endTime, HttpServletResponse response) throws IOException {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        endTime = endTime.replaceAll("-", "");
        startTime = startTime.replaceAll("-", "");
        List<Map<String, Object>> touristDistributions = iTouristSourceScenicDayService.getTouristDistribution(startTime, endTime);
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            String fileName = startTime+"至"+endTime+"游客分布分析.xls";
            response.setHeader("Content-disposition", "attachment; filename=" +new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.TouristDistribution.class);
            sheet.setSheetName("目录");
            List<ExcelService.TouristDistribution> catagoryList = new ArrayList<>();
            for (Map<String, Object> map : touristDistributions) {
                ExcelService.TouristDistribution touristDistribution = new ExcelService.TouristDistribution();
                touristDistribution.setScenicName(map.get("scenic_name").toString());
                touristDistribution.setPeopleNum(map.get("num").toString());
                touristDistribution.setPeopleRate(map.get("rate").toString()+"%");
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

    @ApiOperation(value = "景区游客来源地分析", notes = "九寨沟、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)")
    @GetMapping("/getTouristFromByScenic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "scenicName", value = "九寨沟、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)、古藏寨", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristFromByScenic(String startTime, String endTime, String scenicName) {
        if (scenicName == null) {
            scenicName = "整体";
        } else {
            if (!("九寨沟".equals(scenicName) ||
                    "嫩恩桑措(神仙池)".equals(scenicName) ||
                    "甲勿海".equals(scenicName) ||
                    "大熊猫".equals(scenicName) ||
                    "爱情海(甘海子)".equals(scenicName) ||
                    "古藏寨".equals(scenicName) ||
                    "整体".equals(scenicName))) {
                return R.error("参数错误");
            }
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        endTime = endTime.replaceAll("-", "");
        startTime = startTime.replaceAll("-", "");
        return R.ok(iTouristSourceScenicDayService.getTouristFromByScenic(startTime, endTime, scenicName));
    }



    @ApiOperation(value = "景区游客来源地分析-新", notes = "九寨沟、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)")
    @GetMapping("/getTouristFromByScenicNew")
    public R getTouristFromByScenicNew() {
        String result = HttpUtil.post("https://m.abatour.com/chartAbaTour/dataAnalysis/YKLYTJ_4.html", new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray series = jsonObject.getJSONArray("series");
        JSONObject jsonObject1 = series.getJSONObject(0);
        JSONArray numData = jsonObject1.getJSONArray("data");
        JSONArray categories = jsonObject.getJSONArray("categories");

        List<Map<String, Object>> dataList = new ArrayList<>();

        Integer totalNum = 0;
        for (Object numDatum : numData) {
            totalNum += Convert.toInt(numDatum);
        }

        Map<String, Object> dataMap;
        for (int i = 0; i < numData.size(); i++) {
            String str = numData.getStr(i);
            double rate = 0;
            if(totalNum != 0){
                rate = Math.round(Convert.toInt(str) * 100 / totalNum) / 100.0 * 100;
            }
            dataMap = new HashMap<>();
            dataMap.put("rate",rate + "");
            dataMap.put("num",str);
            dataMap.put("province_name",categories.getStr(i));
            dataList.add(dataMap);
        }
        Collections.reverse(dataList);
        List<Map<String, Object>> collect = dataList.stream().sorted(Comparator.comparing(TouristController::sortMap).reversed()).collect(Collectors.toList());
        return R.ok(collect);
    }



    @ApiOperation(value = "景区游客来源地分析(省内)", notes = "九寨沟、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)")
    @GetMapping("/getTouristFromByScenicCity")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "scenicName", value = "九寨沟、嫩恩桑措(神仙池)、甲勿海、大熊猫、爱情海(甘海子)、古藏寨", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristFromByScenicCity(String startTime, String endTime, String scenicName) {
        if (scenicName == null) {
            scenicName = "整体";
        } else {
            if (!("九寨沟".equals(scenicName) ||
                    "嫩恩桑措(神仙池)".equals(scenicName) ||
                    "甲勿海".equals(scenicName) ||
                    "大熊猫".equals(scenicName) ||
                    "爱情海(甘海子)".equals(scenicName) ||
                    "古藏寨".equals(scenicName) ||
                    "整体".equals(scenicName))) {
                return R.error("参数错误");
            }
        }
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        endTime = endTime.replaceAll("-", "");
        startTime = startTime.replaceAll("-", "");
        return R.ok(iTouristSourceScenicDayService.getTouristFromByScenicCity(startTime, endTime, scenicName));
    }

    @ApiOperation(value = "客流对比分析")
    @GetMapping("/getTouristNumberContrast")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristNumberContrast(String startTime, String endTime) {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfMonth(endTime);
        }
        endTime = endTime.replaceAll("-", "");
        startTime = startTime.replaceAll("-", "");
        return R.ok(iTouristSourceScenicDayService.getTouristNumberContrast(startTime, endTime));
    }

//    @ApiOperation(value = "旅游接待人次")
//    @GetMapping("/getAllTouristNumber")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
//    })
//    public R getAllTouristNumber(String startTime, String endTime) {
//        int num = 23000-new Random().nextInt(500);
//        return R.ok(num);
////        if (endTime == null) {
////            endTime = TimeDateUtil.getFormatDate(new Date());
////        }
////        if (startTime == null) {
////            startTime = TimeDateUtil.getFirstOfYear(endTime);
////        }
////        endTime = endTime.replaceAll("-", "");
////        startTime = startTime.replaceAll("-", "");
////        return R.ok(iTouristSourceScenicDayService.getAllTouristNumber(startTime, endTime));
//    }

    @ApiOperation(value = "历史客流数据对比分析")
    @GetMapping("/getTouristNumberContrastByHistory")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTimeHistory", value = "历史开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTimeHistory", value = "历史结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "当前开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "当前结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristNumberContrastByHistory(String startTimeHistory, String endTimeHistory,String startTime, String endTime) {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null||startTimeHistory == null||endTimeHistory == null) {
            return R.ok("缺少参数");
        }
//        endTime = endTime.replaceAll("-", "");
//        endTimeHistory = endTimeHistory.replaceAll("-", "");
//        startTime = startTime.replaceAll("-", "");
//        startTimeHistory = startTimeHistory.replaceAll("-", "");
        return R.ok(iBaseTicketService.getTouristNumberContrastByHistory(startTimeHistory, endTimeHistory,startTime,endTime));
    }

    @ApiOperation(value = "历史客流数据对比分析(景区)")
    @GetMapping("/public/getTouristNumberContrastByHistoryAndScenic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTimeHistory", value = "历史开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTimeHistory", value = "历史结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "当前开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "当前结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristNumberContrastByHistoryAndScenic(String startTimeHistory, String endTimeHistory,String startTime, String endTime) {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null||startTimeHistory == null||endTimeHistory == null) {
            return R.ok("缺少参数");
        }
//        endTime = endTime.replaceAll("-", "");
//        endTimeHistory = endTimeHistory.replaceAll("-", "");
//        startTime = startTime.replaceAll("-", "");
//        startTimeHistory = startTimeHistory.replaceAll("-", "");
        return R.ok(iBaseTicketService.getTouristNumberContrastByHistoryAndScenic(startTimeHistory, endTimeHistory,startTime,endTime));
    }

    @ApiOperation(value = "获取峰值日期")
    @GetMapping("/public/getMaxDate")
    public R getMaxDate(){
        Integer year = TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()));
        Map<String,Object> map = new HashMap<>();
        BaseTicket dateTicket = iBaseTicketService.getOne(new QueryWrapper<BaseTicket>().select("sum(in_people) as in_people,left(report_time,10) as report_time").between("report_time", year + "-01-01", year + "-12-31").groupBy("report_time").orderByDesc("sum(in_people)").last("limit 1"));
        map.put("day",dateTicket.getReportTime());
        BaseTicket monthTicket = iBaseTicketService.getOne(new QueryWrapper<BaseTicket>().select("sum(in_people) as in_people,left(report_time,7) as report_time").between("report_time", year + "-01-01", year + "-12-31").groupBy("left(report_time,7)").orderByDesc("sum(in_people)").last("limit 1"));
        map.put("month",monthTicket.getReportTime());
        return R.ok(map);
    }


    @ApiOperation(value = "年客流量预测")
    @GetMapping("/public/getTouristNumberForecast")
    public R getTouristNumberForecast() {
        String startTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date())) - 1) + "-01-01";
        String endTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date())) - 1) + "-12-31";
        return R.ok(iBaseTicketService.getAllTouristNumberByTime(startTime, endTime));
    }

    @ApiOperation(value = "年客流量")
    @GetMapping("/public/getTouristNumberCurrent")
    public R getTouristNumberCurrent() {
        String startTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))) + "-01-01";
        String endTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))) + "-12-31";
        return R.ok(iBaseTicketService.getOne(new QueryWrapper<BaseTicket>().select("sum(in_people) as in_people").between("report_time",startTime,endTime)));
    }

    @ApiOperation(value = "各景区游客接待属性分析")
    @GetMapping("/public/getTouristQuality")
    public R getTouristQuality() {
        String startTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))) + "-01-01";
        String endTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date()))) + "-12-31";
        return R.ok(iBaseHotelReportService.getTouristQuality(startTime, endTime));
    }

    @ApiOperation(value = "游客交通出行方式")
    @GetMapping("/public/getTouristTraffic")
    public R getTouristTraffic() {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        Map<String,Object> map3 = new HashMap<>();
        Map<String,Object> map4 = new HashMap<>();
        map1.put("type","飞机");
        map1.put("rate",15.12);
        map1.put("peopleNum",452191);
        map1.put("forecast",768421);

        map2.put("type","火车");
        map2.put("rate",31.34);
        map2.put("peopleNum",936894);
        map2.put("forecast",3762418);

        map3.put("type","客车");
        map3.put("rate",31.85);
        map3.put("peopleNum",952216);
        map3.put("forecast",1657214);

        map4.put("type","自驾");
        map4.put("rate",21.69);
        map4.put("peopleNum",648389);
        map4.put("forecast",1048433);

        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        return R.ok(result);
    }

    @ApiOperation(value = "景区热门购票软件分析")
    @GetMapping("/public/getTouristTicket")
    public R getTouristTicket() {
        Map<String,Map<String,Object>> result = new HashMap();
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        Map<String,Object> map3 = new HashMap<>();
        Map<String,Object> map4 = new HashMap<>();
        Map<String,Object> map5 = new HashMap<>();
        Map<String,Object> map6 = new HashMap<>();

        Map<String,Object> ticket1 = new HashMap<>();
        Map<String,Object> ticket2 = new HashMap<>();
        Map<String,Object> ticket3 = new HashMap<>();
        Map<String,Object> ticket4 = new HashMap<>();
        Map<String,Object> ticket5 = new HashMap<>();
        Map<String,Object> ticket6 = new HashMap<>();
        Map<String,Object> ticket7 = new HashMap<>();

        ticket1.put("酒店",19);
        ticket1.put("机票",8);
        ticket1.put("门票",9);

        ticket2.put("酒店",19);
        ticket2.put("机票",13);
        ticket2.put("门票",8);

        ticket3.put("酒店",15);
        ticket3.put("机票",12);
        ticket3.put("门票",10);

        ticket4.put("酒店",7);
        ticket4.put("机票",20);
        ticket4.put("门票",3);

        ticket5.put("酒店",7);
        ticket5.put("机票",19);
        ticket5.put("门票",16);

        ticket6.put("酒店",12);
        ticket6.put("机票",20);
        ticket6.put("门票",5);

        ticket7.put("酒店",11);
        ticket7.put("机票",7);
        ticket7.put("门票",2);

        map1.put("飞猪",ticket1);
        map1.put("马蜂窝",ticket2);
        map1.put("去哪儿",ticket3);
        map1.put("携程",ticket4);
        map1.put("驴妈妈",ticket5);
        map1.put("艺龙",ticket6);
        map1.put("乐享九寨",ticket7);

        map2.put("飞猪",ticket1);
        map2.put("马蜂窝",ticket3);
        map2.put("去哪儿",ticket5);
        map2.put("携程",ticket7);
        map2.put("驴妈妈",ticket2);
        map2.put("艺龙",ticket4);
        map2.put("乐享九寨",ticket6);

        map3.put("飞猪",ticket7);
        map3.put("马蜂窝",ticket3);
        map3.put("去哪儿",ticket1);
        map3.put("携程",ticket2);
        map3.put("驴妈妈",ticket5);
        map3.put("艺龙",ticket6);
        map3.put("乐享九寨",ticket4);

        map4.put("飞猪",ticket5);
        map4.put("马蜂窝",ticket3);
        map4.put("去哪儿",ticket1);
        map4.put("携程",ticket4);
        map4.put("驴妈妈",ticket2);
        map4.put("艺龙",ticket6);
        map4.put("乐享九寨",ticket7);

        map5.put("飞猪",ticket6);
        map5.put("马蜂窝",ticket7);
        map5.put("去哪儿",ticket2);
        map5.put("携程",ticket3);
        map5.put("驴妈妈",ticket1);
        map5.put("艺龙",ticket5);
        map5.put("乐享九寨",ticket4);

        map6.put("飞猪",ticket4);
        map6.put("马蜂窝",ticket1);
        map6.put("去哪儿",ticket6);
        map6.put("携程",ticket5);
        map6.put("驴妈妈",ticket2);
        map6.put("艺龙",ticket3);
        map6.put("乐享九寨",ticket7);

        result.put("九寨沟景区",map1);
        result.put("神仙池景区",map2);
        result.put("甲勿海景区",map3);
        result.put("古藏寨景区",map4);
        result.put("大熊猫保护研究院",map5);
        result.put("甘海子景区",map6);

        return R.ok(result);
    }


    private static Integer sortMap(Map<String,Object> map){
        return Integer.valueOf(map.get("num").toString());
    }
}
