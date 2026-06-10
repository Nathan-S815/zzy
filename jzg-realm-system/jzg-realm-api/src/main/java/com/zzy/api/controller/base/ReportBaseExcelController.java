package com.zzy.api.controller.base;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.service.excel.ExcelReportService;
import com.zzy.api.service.reportbase.*;
import com.zzy.db.entity.reportbase.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reportExcel")
@Api(value = "基础数据上报ecxel导出接口", tags = "基础数据上报ecxel导出接口")
public class ReportBaseExcelController {

    @Autowired
    private IReportBaseHotelService iReportBaseHotelService;
    @Autowired
    private IReportBaseTrafficService iReportBaseTrafficService;
    @Autowired
    private IReportBaseEntertainmentService iReportBaseEntertainmentService;
    @Autowired
    private IReportBaseShoppingService iReportBaseShoppingService;
    @Autowired
    private IReportBaseRestaurantService iReportBaseRestaurantService;
    @Autowired
    private IReportBaseTravelService iReportBaseTravelService;
    @Autowired
    private IReportBaseScenicService iReportBaseScenicService;
    @Autowired
    private IReportBaseGuideService iReportBaseGuideService;


    @GetMapping("/exporReportBaseHotelExcel")
    @ApiOperation(value = "导出酒店上报基础数据")
    public String exporReportBaseHotelExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "repotHotel.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseHotelExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseHotel> list = iReportBaseHotelService.list(new QueryWrapper<ReportBaseHotel>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseHotelExcel> catagoryList = new ArrayList<>();
            for (ReportBaseHotel base : list) {
                ExcelReportService.ReportBaseHotelExcel model = new ExcelReportService.ReportBaseHotelExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseTrafficExcel")
    @ApiOperation(value = "导出交通上报基础数据")
    public String exporReportBaseTrafficExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "trafficReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseTrafficExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseTraffic> list = iReportBaseTrafficService.list(new QueryWrapper<ReportBaseTraffic>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseTrafficExcel> catagoryList = new ArrayList<>();
            for (ReportBaseTraffic base : list) {
                ExcelReportService.ReportBaseTrafficExcel model = new ExcelReportService.ReportBaseTrafficExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseEntertainmentExcel")
    @ApiOperation(value = "导出娱乐上报基础数据")
    public String exporReportBaseEntertainmentExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "entertainmentReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseEntertainmentExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseEntertainment> list = iReportBaseEntertainmentService.list(new QueryWrapper<ReportBaseEntertainment>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseEntertainmentExcel> catagoryList = new ArrayList<>();
            for (ReportBaseEntertainment base : list) {
                ExcelReportService.ReportBaseEntertainmentExcel model = new ExcelReportService.ReportBaseEntertainmentExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseShoppingExcel")
    @ApiOperation(value = "导出购物场所上报基础数据")
    public String exporReportBaseShoppingExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "shoppingReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseShoppingExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseShopping> list = iReportBaseShoppingService.list(new QueryWrapper<ReportBaseShopping>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseShoppingExcel> catagoryList = new ArrayList<>();
            for (ReportBaseShopping base : list) {
                ExcelReportService.ReportBaseShoppingExcel model = new ExcelReportService.ReportBaseShoppingExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseRestaurantExcel")
    @ApiOperation(value = "导出餐厅上报基础数据")
    public String exporReportBaseRestaurantExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "restaurantReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseRestaurantExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseRestaurant> list = iReportBaseRestaurantService.list(new QueryWrapper<ReportBaseRestaurant>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseRestaurantExcel> catagoryList = new ArrayList<>();
            for (ReportBaseRestaurant base : list) {
                ExcelReportService.ReportBaseRestaurantExcel model = new ExcelReportService.ReportBaseRestaurantExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseTravelExcel")
    @ApiOperation(value = "导出旅行社上报基础数据")
    public String exporReportBaseTravelExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "travelReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseTravelExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseTravel> list = iReportBaseTravelService.list(new QueryWrapper<ReportBaseTravel>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseTravelExcel> catagoryList = new ArrayList<>();
            for (ReportBaseTravel base : list) {
                ExcelReportService.ReportBaseTravelExcel model = new ExcelReportService.ReportBaseTravelExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseScenicExcel")
    @ApiOperation(value = "导出景区上报基础数据")
    public String exporReportBaseScenicExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "ScenicReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseScenicExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseScenic> list = iReportBaseScenicService.list(new QueryWrapper<ReportBaseScenic>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseScenicExcel> catagoryList = new ArrayList<>();
            for (ReportBaseScenic base : list) {
                ExcelReportService.ReportBaseScenicExcel model = new ExcelReportService.ReportBaseScenicExcel();
                BeanUtils.copyProperties(base, model);
                catagoryList.add(model);
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

    @GetMapping("/exporReportBaseGuideExcel")
    @ApiOperation(value = "导出导游上报基础数据")
    public String exporReportBaseGuideExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "GuideReport.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelReportService.ReportBaseGuideExcel.class);
            sheet.setSheetName("目录");
            List<ReportBaseGuide> list = iReportBaseGuideService.list(new QueryWrapper<ReportBaseGuide>().eq("is_delete",0));
            List<ExcelReportService.ReportBaseGuideExcel> catagoryList = new ArrayList<>();
            for (ReportBaseGuide base : list) {
                ExcelReportService.ReportBaseGuideExcel model = new ExcelReportService.ReportBaseGuideExcel();
                BeanUtils.copyProperties(base, model);
                model.setCredit(base.getCredit().toString());
                model.setUpdateTime(DateUtil.format(base.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                model.setCreateTime(DateUtil.format(base.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                catagoryList.add(model);
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
}
