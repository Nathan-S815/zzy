package com.zzy.api.controller.base;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zzy.api.service.base.*;
import com.zzy.api.service.excel.ExcelListener;
import com.zzy.api.service.excel.ExcelService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Api(value = "ecxel导入导出接口", tags = "ecxel导入导出接口")
public class ExcleController {

    @Autowired
    private IBaseScenicService iBaseScenicService;
    @Autowired
    private IBaseGuideService iBaseGuideService;

    @Autowired
    private IBaseHotelService iBaseHotelService;

    @Autowired
    private IBaseRecreationService iBaseRecreationService;

    @Autowired
    private IBaseRestaurantService iBaseRestaurantService;

    @Autowired
    private IBaseShoppingService iBaseShoppingService;

    @Autowired
    private IBaseTravelService iBaseTravelService;

    @GetMapping("/exporBaseHotelExcel")
    @ApiOperation(value = "导出旅行饭店基础数据")
    public String exporBaseHotelExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "hotel.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseHotelExcel.class);
            sheet.setSheetName("目录");
            List<BaseHotel> list = iBaseHotelService.list();
            List<ExcelService.BaseHotelExcel> catagoryList = new ArrayList<>();
            for (BaseHotel base : list) {
                ExcelService.BaseHotelExcel model = new ExcelService.BaseHotelExcel();
                model.setHotelName(base.getHotelName());
                model.setHotelLevel(base.getHotelLevel());
                model.setCreditCode(base.getCreditCode());
                model.setAreaCode(base.getAreaCode());
                model.setLat(base.getLat());
                model.setLng(base.getLng());
                model.setSiteType(base.getSiteType());
                model.setZipCode(base.getZipCode());
                model.setZxPhone(base.getZxPhone());
                model.setTsPhone(base.getTsPhone());
                model.setRoomCount(base.getRoomCount());
                model.setCarParkNum(base.getCarParkNum());
                model.setBedCount(base.getBedCount());
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
    @GetMapping("/ScenicExpor")
    @ApiOperation(value = "导出景区基础数据")
    public String exporExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "scenic.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.ScenicExcelModel.class);
            sheet.setSheetName("目录");
            List<BaseScenic> list = iBaseScenicService.list();
            List<ExcelService.ScenicExcelModel> catagoryList = new ArrayList<>();
            for (BaseScenic baseScenic : list) {
                ExcelService.ScenicExcelModel excelPropertyIndexModel = new ExcelService.ScenicExcelModel();
                excelPropertyIndexModel.setScenicName(baseScenic.getScenicName());
                excelPropertyIndexModel.setAreaCode(baseScenic.getAreaCode());
                excelPropertyIndexModel.setCreditCode(baseScenic.getCreditCode());
                excelPropertyIndexModel.setScenicLevel(baseScenic.getScenicLevel());
                excelPropertyIndexModel.setLat(baseScenic.getLat());
                excelPropertyIndexModel.setLng(baseScenic.getLng());
                excelPropertyIndexModel.setSiteType(baseScenic.getSiteType());
                excelPropertyIndexModel.setAppropriateMonth(baseScenic.getAppropriateMonth());
                catagoryList.add(excelPropertyIndexModel);
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
    @GetMapping("/exporBaseGuideExcel")
    @ApiOperation(value = "导出导游基础数据")
    public String exporBaseGuideExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "guidef.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseGuideExcelModel.class);
            sheet.setSheetName("目录");
            List<BaseGuide> list = iBaseGuideService.list();
            List<ExcelService.BaseGuideExcelModel> catagoryList = new ArrayList<>();
            for (BaseGuide base : list) {
                ExcelService.BaseGuideExcelModel model = new ExcelService.BaseGuideExcelModel();
                model.setGuideName(base.getGuideName());
                model.setSex(base.getSex());
                model.setIdNumber(base.getIdNumber());
                model.setBirthday(base.getBirthday());
                model.setTel(base.getTel());
                model.setNation(base.getNation());
                model.setEducational(base.getEducational());
                model.setMajor(base.getMajor());
                model.setGraduationUniversity(base.getGraduationUniversity());
                model.setSubTravel(base.getSubTravel());
                model.setGuideCertificate(base.getGuideCertificate());
                model.setGradeNum(base.getGradeNum());
                model.setGuideGrade(base.getGuideGrade());
                model.setGuideGrade(base.getGuideGrade());
                model.setLanguageGrade(base.getLanguageGrade());
                model.setSeniorityCertificate(base.getSeniorityCertificate());
                model.setNatureWork(base.getNatureWork());
                model.setRegisteOrganName(base.getRegisteOrganName());
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

    @GetMapping("/exporBaseRecreationExcel")
    @ApiOperation(value = "导出休闲娱乐基础数据")
    public String exporBaseRecreationExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "recreation.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseRecreationExcel.class);
            sheet.setSheetName("目录");
            List<BaseRecreation> list = iBaseRecreationService.list();
            List<ExcelService.BaseRecreationExcel> catagoryList = new ArrayList<>();
            for (BaseRecreation base : list) {
                ExcelService.BaseRecreationExcel model = new ExcelService.BaseRecreationExcel();
                model.setRecreationName(base.getRecreationName());
                model.setRecreationLevel(base.getRecreationLevel());
                model.setCreditCode(base.getCreditCode());
                model.setAreaCode(base.getAreaCode());
                model.setLat(base.getLat());
                model.setLng(base.getLng());
                model.setSiteType(base.getSiteType());
                model.setZipCode(base.getZipCode());
                model.setZxPhone(base.getZxPhone());
                model.setTsPhone(base.getTsPhone());
                model.setLicenseNo(base.getLicenseNo());
                model.setLegalPerson(base.getLegalPerson());
                model.setContributor(base.getContributor());
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
    @GetMapping("/exporBaseRestaurantExcel")
    @ApiOperation(value = "导出餐厅基础数据")
    public String exporBaseRestaurantExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "restaurant.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseRestaurantExcel.class);
            sheet.setSheetName("目录");
            List<BaseRestaurant> list = iBaseRestaurantService.list();
            List<ExcelService.BaseRestaurantExcel> catagoryList = new ArrayList<>();
            for (BaseRestaurant base : list) {
                ExcelService.BaseRestaurantExcel model = new ExcelService.BaseRestaurantExcel();
                model.setRestaurantName(base.getRestaurantName());
                model.setRestaurantLevel(base.getRestaurantLevel());
                model.setCreditCode(base.getCreditCode());
                model.setAreaCode(base.getAreaCode());
                model.setLat(base.getLat());
                model.setLng(base.getLng());
                model.setSiteType(base.getSiteType());
                model.setZipCode(base.getZipCode());
                model.setZxPhone(base.getZxPhone());
                model.setTsPhone(base.getTsPhone());
                model.setRoomNum(base.getRoomNum());
                model.setCarParkNum(base.getCarParkNum());
                model.setBusinessTime(base.getBusinessTime());
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

    @GetMapping("/exporBaseShoppingExcel")
    @ApiOperation(value = "导出购物场所基础数据")
    public String exporBaseShoppingExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "shopping.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseShoppingExcel.class);
            sheet.setSheetName("目录");
            List<BaseShopping> list = iBaseShoppingService.list();
            List<ExcelService.BaseShoppingExcel> catagoryList = new ArrayList<>();
            for (BaseShopping base : list) {
                ExcelService.BaseShoppingExcel model = new ExcelService.BaseShoppingExcel();
                model.setShoppingName(base.getShoppingName());
                model.setShoppingLevel(base.getShoppingLevel());
                model.setCreditCode(base.getCreditCode());
                model.setAreaCode(base.getAreaCode());
                model.setLat(base.getLat());
                model.setLng(base.getLng());
                model.setSiteType(base.getSiteType());
                model.setZipCode(base.getZipCode());
                model.setZxPhone(base.getZxPhone());
                model.setTsPhone(base.getTsPhone());
                model.setLicenseNo(base.getLicenseNo());
                model.setLegalPerson(base.getLegalPerson());
                model.setContributor(base.getContributor());
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

    @GetMapping("/exporBaseTravelExcel")
    @ApiOperation(value = "导出旅行社基础数据")
    public String exporBaseTravelExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + "travel.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, ExcelService.BaseTravelExcel.class);
            sheet.setSheetName("目录");
            List<BaseTravel> list = iBaseTravelService.list();
            List<ExcelService.BaseTravelExcel> catagoryList = new ArrayList<>();
            for (BaseTravel base : list) {
                ExcelService.BaseTravelExcel model = new ExcelService.BaseTravelExcel();
               /* model.setTravelName(base.getTravelName());
                model.setTravelLevel(base.getTravelLevel());
                model.setCreditCode(base.getCreditCode());
                model.setAreaCode(base.getAreaCode());
                model.setLat(base.getLat());
                model.setLng(base.getLng());
                model.setSiteType(base.getSiteType());
                model.setZipCode(base.getZipCode());
                model.setZxPhone(base.getZxPhone());
                model.setTsPhone(base.getTsPhone());
                model.setLicenseNo(base.getLicenseNo());
                model.setLegalPerson(base.getLegalPerson());
                model.setContributor(base.getContributor());*/
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

    @PostMapping("/Scenicimport")
    @ApiOperation(value = "导入景区基础数据")
    public R importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.ScenicExcelModel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        List<ExcelService.ScenicExcelModel> catagoryList = new ArrayList<>();
        ExcelService.ScenicExcelModel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.ScenicExcelModel) list.get(i);
            BaseScenic baseScenic = new BaseScenic();
            System.out.println(catagory);
           baseScenic.setScenicName(catagory.getScenicName());
           baseScenic.setAreaCode(catagory.getAreaCode());
           baseScenic.setCreditCode(catagory.getCreditCode());
           baseScenic.setScenicLevel(catagory.getScenicLevel());
           baseScenic.setLat(catagory.getLat());
           baseScenic.setLng(catagory.getLng());
           baseScenic.setSiteType(catagory.getSiteType());
           baseScenic.setAppropriateMonth(catagory.getAppropriateMonth());
            iBaseScenicService.save(baseScenic);
        }
        return R.ok("导入成功");
    }

    @PostMapping("/importBaseGuideExcel")
    @ApiOperation(value = "导入导游基础数据")
    public R importBaseGuideExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseGuideExcelModel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        List<BaseGuide> catagoryList = new ArrayList<>();
        ExcelService.BaseGuideExcelModel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseGuideExcelModel) list.get(i);
            BaseGuide baseGuide = new BaseGuide();
            System.out.println(catagory);
            baseGuide.setGuideName(catagory.getGuideName()).setSex(catagory.getSex()).setIdNumber(catagory.getIdNumber())
                    .setBirthday(catagory.getBirthday()).setTel(catagory.getTel()).setNation(catagory.getNation()).setEducational(catagory.getEducational())
                    .setMajor(catagory.getMajor()).setGraduationUniversity(catagory.getGraduationUniversity()).setSubTravel(catagory.getSubTravel())
                    .setGuideCertificate(catagory.getGuideCertificate()).setGradeNum(catagory.getGradeNum()).setGuideGrade(catagory.getGuideGrade())
                    .setLanguageGrade(catagory.getLanguageGrade()).setSeniorityCertificate(catagory.getSeniorityCertificate()).setNatureWork(catagory.getNatureWork())
                    .setRegisteOrganName(catagory.getRegisteOrganName());
            catagoryList.add(baseGuide);
            iBaseGuideService.save(baseGuide);
        }
        return R.ok("导入成功");
    }

    @PostMapping("/imporrBaseHotelExcel")
    @ApiOperation(value = "导入旅行饭店基础数据")
    public R imporrBaseHotelExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseHotelExcel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        ExcelService.BaseHotelExcel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseHotelExcel) list.get(i);
            BaseHotel baseHotel = new BaseHotel();
            System.out.println(catagory);
            baseHotel.setHotelName(catagory.getHotelName()).setHotelLevel(catagory.getHotelLevel()).setCreditCode(catagory.getCreditCode())
                    .setAreaCode(catagory.getAreaCode()).setZipCode(catagory.getZipCode()).setLat(catagory.getLat()).setLng(catagory.getLng()).setSiteType(catagory.getSiteType())
                    .setZxPhone(catagory.getZxPhone()).setTsPhone(catagory.getTsPhone()).setRoomCount(catagory.getRoomCount())
                    .setBedCount(catagory.getBedCount()).setCarParkNum(catagory.getCarParkNum());
            iBaseHotelService.save(baseHotel);
        }
        return R.ok("导入成功");
    }

    @PostMapping("/imporrBaseRecreationExcel")
    @ApiOperation(value = "导入休闲娱乐基础数据")
    public R imporrBaseRecreationExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseRecreationExcel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        ExcelService.BaseRecreationExcel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseRecreationExcel) list.get(i);
            BaseRecreation baseRecreation = new BaseRecreation();
            System.out.println(catagory);
            baseRecreation.setRecreationName(catagory.getRecreationName()).setRecreationLevel(catagory.getRecreationLevel()).setCreditCode(catagory.getCreditCode())
                    .setAreaCode(catagory.getAreaCode()).setZipCode(catagory.getZipCode()).setLat(catagory.getLat()).setLng(catagory.getLng()).setSiteType(catagory.getSiteType())
                    .setZxPhone(catagory.getZxPhone()).setTsPhone(catagory.getTsPhone()).setLicenseNo(catagory.getLicenseNo())
                    .setLegalPerson(catagory.getLegalPerson()).setContributor(catagory.getContributor());
            iBaseRecreationService.save(baseRecreation);
        }
        return R.ok("导入成功");
    }
    @PostMapping("/imporBaseRestaurantExcel")
    @ApiOperation(value = "导入餐厅基础数据")
    public R imporBaseRestaurantExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseRestaurantExcel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        ExcelService.BaseRestaurantExcel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseRestaurantExcel) list.get(i);
            BaseRestaurant baseRestaurant = new BaseRestaurant();
            System.out.println(catagory);
            baseRestaurant.setRestaurantName(catagory.getRestaurantName()).setRestaurantLevel(catagory.getRestaurantLevel()).setCreditCode(catagory.getCreditCode())
                    .setAreaCode(catagory.getAreaCode()).setZipCode(catagory.getZipCode()).setLat(catagory.getLat()).setLng(catagory.getLng()).setSiteType(catagory.getSiteType())
                    .setZxPhone(catagory.getZxPhone()).setTsPhone(catagory.getTsPhone()).setRoomNum(catagory.getRoomNum())
                    .setCarParkNum(catagory.getCarParkNum()).setBusinessTime(catagory.getBusinessTime());
            iBaseRestaurantService.save(baseRestaurant);
        }
        return R.ok("导入成功");
    }

    @PostMapping("/imporrBaseShoppingExcel")
    @ApiOperation(value = "导入购物场所基础数据")
    public R imporrBaseShoppingExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseShoppingExcel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        ExcelService.BaseShoppingExcel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseShoppingExcel) list.get(i);
            BaseShopping baseRecreation = new BaseShopping();
            System.out.println(catagory);
            baseRecreation.setShoppingName(catagory.getShoppingName()).setShoppingLevel(catagory.getShoppingLevel()).setCreditCode(catagory.getCreditCode())
                    .setAreaCode(catagory.getAreaCode()).setZipCode(catagory.getZipCode()).setLat(catagory.getLat()).setLng(catagory.getLng()).setSiteType(catagory.getSiteType())
                    .setZxPhone(catagory.getZxPhone()).setTsPhone(catagory.getTsPhone()).setLicenseNo(catagory.getLicenseNo())
                    .setLegalPerson(catagory.getLegalPerson()).setContributor(catagory.getContributor());
            iBaseShoppingService.save(baseRecreation);
        }
        return R.ok("导入成功");
    }
    @PostMapping("/imporrBaseTravelExcel")
    @ApiOperation(value = "导入旅行社基础数据")
    public R imporrBaseTravelExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1,ExcelService.BaseTravelExcel.class));

        //获取数据
        List<Object> list = listener.getDatas();

        ExcelService.BaseTravelExcel catagory;

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (ExcelService.BaseTravelExcel) list.get(i);
            BaseTravel baseTravel = new BaseTravel();
            System.out.println(catagory);
          /*  baseTravel.setTravelName(catagory.getTravelName()).setTravelLevel(catagory.getTravelLevel()).setCreditCode(catagory.getCreditCode())
                    .setAreaCode(catagory.getAreaCode()).setZipCode(catagory.getZipCode()).setLat(catagory.getLat()).setLng(catagory.getLng()).setSiteType(catagory.getSiteType())
                    .setZxPhone(catagory.getZxPhone()).setTsPhone(catagory.getTsPhone()).setLicenseNo(catagory.getLicenseNo())
                    .setLegalPerson(catagory.getLegalPerson()).setContributor(catagory.getContributor()).setCreateTime(new Date());*/
            BeanUtils.copyProperties(catagory, baseTravel);
            iBaseTravelService.save(baseTravel);
        }
        return R.ok("导入成功");
    }
}
