package com.zzy.api.controller.report;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.common.constant.ShangHuTypeEnum;
import com.zzy.api.service.base.IBaseAdminService;
import com.zzy.api.service.base.IBaseGuideService;
import com.zzy.api.service.base.IMerchantTypeService;
import com.zzy.api.service.reportbase.*;
import com.zzy.core.dto.R;
import com.zzy.core.utils.AuthUtil;
import com.zzy.core.utils.MinioUtil;
import com.zzy.db.entity.base.BaseAdmin;
import com.zzy.db.entity.base.MerchantType;
import com.zzy.db.entity.reportbase.*;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/report")
@Api(value = "基础信息及查询接口", tags = "基础信息及查询接口")
public class ReportBaseController {

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
    @Autowired
    private IBaseAdminService iBaseAdminService;


    @Autowired
    private IMerchantTypeService merchantTypeService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/public/getScenicList")
    @ApiOperation(value = "获取所有景区及其id")
    public R getScenicList() {
        Wrapper<ReportBaseScenic> wrapper = new QueryWrapper<ReportBaseScenic>().eq("is_delete", 0);
        return R.ok(iReportBaseScenicService.list(wrapper));
    }

    @GetMapping("/getMerchantPermissionListByLoginName")
    @ApiOperation(value = "根据登录名获取对应商户功能权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "商户登录名", required = true, paramType = "query", dataType = "string"),
    })
    public R getMerchantPermissionListById(String userName, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
//        if(!cu.getAuthorities().contains("ADMIN")){
//            return R.error("权限不足");
//        }
        if (StrUtil.isBlankOrUndefined(userName)) {
            return R.nullValueError();
        }
        List<RoleInfo> ui = usersService.findRolesByUserName(userName);
        return R.ok(merchantTypeService.list(new QueryWrapper<MerchantType>().in("type_name", ui.stream().map(o -> o.getRoleName()).collect(Collectors.toList()))));
    }


    @PostMapping("/removeMerchantAccount")
    @ApiOperation(value = "删除商户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baseId", value = "商户baseId", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "typeId", value = "商户类型Id(entertainment,hotel,restaurant,scenic,shopping,traffic,travel)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "userId", value = "该商户的登录账户Id", required = true, paramType = "query", dataType = "string"),
    })
    @RequiredPermission(hasAnyRole = {"ADMIN", "ROOT"})
    public R removeMerchantAndLoginAccount(HttpServletRequest request) {
        String typeId = request.getParameter("typeId");
        String baseId = request.getParameter("baseId");
        String userId = request.getParameter("userId");
        if (StrUtil.isBlankOrUndefined(typeId) || StrUtil.isBlankOrUndefined(baseId) || StrUtil.isBlankOrUndefined(userId)) {
            return R.nullValueError();
        }
        MerchantType mt = merchantTypeService.getById(Integer.parseInt(typeId));
        UserInfo ui = new UserInfo();
        ui.setIsDelete(1);
        ui.setId(Integer.parseInt(userId));
        try {
            usersService.editUserById(ui);
        } catch (Exception e) {
            log.error("删除商户及账户异常", e);
            return R.fail();
        }
        Map<String, Object> m = new HashMap<>();
        m.put("tableName", mt.getTypeCode());
        m.put("baseId", Integer.parseInt(baseId));
        return merchantTypeService.delBaseMerchantInfo(m) ? R.success() : R.fail();
    }


    @PostMapping("/merchantFunctionPermissionSetting")
    @ApiOperation(value = "商户功能权限设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "商户登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "typeIds", value = "商户权限Id(多个用逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    public R merchantRoleSetByMerchantType(String userName, String typeIds, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
//        if(!cu.getAuthorities().contains("ADMIN")){
//            return R.error("权限不足");
//        }
        if (StrUtil.isBlankOrUndefined(userName) || typeIds == null || StrUtil.isBlankOrUndefined(typeIds = typeIds.replace("[", "").replace("]", ""))) {
            return R.nullValueError();
        }
        String[] ids = typeIds.split(",");
        List<MerchantType> mis = merchantTypeService.list(new QueryWrapper<MerchantType>().in("id", ids));
        List<RoleInfo> roles = usersService.findRoleIdsByRoleNames(mis.stream().map(o -> o.getTypeName()).collect(Collectors.toList()));
        UserInfo ui = usersService.findUserByName(userName);
        boolean flag = usersService.changeUserRoleList(ui, roles.stream().map(o -> o.getId()).collect(Collectors.toList()));
        return flag ? R.success() : R.fail();
    }


    @PostMapping("/public/merchantRegister")
    @ApiOperation(value = "商户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pwd", value = "登录密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "merchantId", value = "商户类型(商户类型列表提供的id:景区管理/酒店管理/...)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "repeatPwd", value = "确认密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "shangHuType", value = "商户类型(entertainment,hotel,restaurant,scenic,shopping,traffic,travel)", required = true, paramType = "query", dataType = "string"),
    })
    public R merchantRegister(String userName, String pwd, String repeatPwd, Integer merchantId, String shangHuType, HttpServletRequest request) {
        if (StrUtil.isBlankOrUndefined(userName) || StrUtil.isBlankOrUndefined(pwd)
                || merchantId == null || StrUtil.isBlankOrUndefined(repeatPwd)) {
            return R.nullValueError();
        }
        if (!pwd.equals(repeatPwd)) {
            return R.error("两次密码不一致");
        }
        if (usersService.findUserByName(userName) != null) {
            return R.error("用户名已存在,请修改");
        }
        RoleInfo ri = iReportBaseScenicService.getRoleInfoBymerchantId(merchantId);
        UserInfo ui = new UserInfo();
        ui.setCreateTime(new Date());
        ui.setIsDelete(0);
        ui.setIsEnable(1);
        ui.setPassWord(AuthUtil.getSaltedPwd(pwd));
        ui.setUserName(userName);
        boolean flag = usersService.saveUserWithRole(ui, ri.getId());
        if (flag) {
            Map<String, Object> para = new HashMap<>();
            if (EnumUtil.contains(ShangHuTypeEnum.class, shangHuType)) {
                if (ShangHuTypeEnum.entertainment.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.entertainment.getFields());
                } else if (ShangHuTypeEnum.hotel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.hotel.getFields());
                } else if (ShangHuTypeEnum.travel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.travel.getFields());
                } else if (ShangHuTypeEnum.traffic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.traffic.getFields());
                } else if (ShangHuTypeEnum.shopping.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.shopping.getFields());
                } else if (ShangHuTypeEnum.scenic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.scenic.getFields());
                } else if (ShangHuTypeEnum.restaurant.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.restaurant.getFields());
                }
            } else {
                return R.ok(new JSONArray());
            }
            para.put("userId", ui.getId());
            flag = merchantTypeService.addBaseMerchantInfo(para) > 0;
        }
        return flag ? R.success() : R.fail();
    }

    @PostMapping("/createAdminLoginAccount")
    @ApiOperation(value = "创建管理员登录账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "管理员账号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pwd", value = "登录密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "adminName", value = "管理员姓名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", required = true, paramType = "query", dataType = "string")
    })
    public R createAdminLoginAccount(String userName, String pwd, String adminName, String phoneNumber) {
        if (StrUtil.isBlankOrUndefined(userName) || StrUtil.isBlankOrUndefined(pwd) || StrUtil.isBlankOrUndefined(adminName) || StrUtil.isBlankOrUndefined(phoneNumber)) {
            return R.nullValueError();
        }
        if (usersService.findUserByName(userName) != null) {
            return R.error("用户名已存在,请修改");
        }
        UserInfo ui = new UserInfo();
        ui.setCreateTime(new Date());
        ui.setIsDelete(0);
        ui.setIsEnable(1);
        ui.setPassWord(AuthUtil.getSaltedPwd(pwd));
        ui.setUserName(userName);
        int i = usersService.addUserReturnId(ui);
        ui = usersService.findUserByName(userName);
        BaseAdmin baseAdmin = new BaseAdmin();
        baseAdmin.setAdminName(adminName);
        baseAdmin.setAdminPhone(phoneNumber);
        baseAdmin.setUserId(ui.getId());
        baseAdmin.setCreateTime(LocalDateTime.now());
        baseAdmin.setIsDelete(0);
        boolean save = iBaseAdminService.save(baseAdmin);
        return save ? R.success() : R.fail();
    }

    @PostMapping("/updateReportBaseAdmin")
    @ApiOperation(value = "修改管理员基础信息")
    public R updateReportBaseAdmin(BaseAdmin baseAdmin) {
        boolean b = iBaseAdminService.updateById(baseAdmin);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteReportBaseAdmin")
    @ApiOperation(value = "删除管理员数据")
    public R deleteReportBaseAdmin(Integer id) {
        BaseAdmin byId = iBaseAdminService.getById(id);
        if (byId != null) {
            byId.setIsDelete(1);
            boolean b = iBaseAdminService.updateById(byId);
            if (b) {
                return R.ok("删除成功");
            }
        }
        return R.ok("删除失败");
    }

    @PostMapping("/createShangHuLoginAccount")
    @ApiOperation(value = "创捷商户登录账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "登录名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pwd", value = "登录密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "merchantId", value = "商户类型(商户类型列表提供的id:景区管理/酒店管理/...)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "shangHuName", value = "商户名称(eg:景区/酒店名称)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "shangHuType", value = "商户类型(entertainment,hotel,restaurant,scenic,shopping,traffic,travel)", required = true, paramType = "query", dataType = "string"),
    })
    public R createShangHu(String userName, String pwd, String shangHuName, Integer merchantId, String shangHuType, HttpServletRequest request) {
//        CustomUser cu = JwtUtil.getFromJwt(request);
//        if(!cu.getAuthorities().contains("ADMIN")){
//            return R.error("权限不足");
//        }
        if (StrUtil.isBlankOrUndefined(userName) || StrUtil.isBlankOrUndefined(pwd) || merchantId == null || StrUtil.isBlankOrUndefined(shangHuName)) {
            return R.nullValueError();
        }
        if (usersService.findUserByName(userName) != null) {
            return R.error("用户名已存在,请修改");
        }
        RoleInfo ri = iReportBaseScenicService.getRoleInfoBymerchantId(merchantId);
        UserInfo ui = new UserInfo();
        ui.setCreateTime(new Date());
        ui.setIsDelete(0);
        ui.setIsEnable(1);
        ui.setPassWord(AuthUtil.getSaltedPwd(pwd));
        ui.setUserName(userName);
        boolean flag = usersService.saveUserWithRole(ui, ri.getId());
        if (flag) {
            Map<String, Object> para = new HashMap<>();
            if (EnumUtil.contains(ShangHuTypeEnum.class, shangHuType)) {
                if (ShangHuTypeEnum.entertainment.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.entertainment.getFields());
                } else if (ShangHuTypeEnum.hotel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.hotel.getFields());
                } else if (ShangHuTypeEnum.travel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.travel.getFields());
                } else if (ShangHuTypeEnum.traffic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.traffic.getFields());
                } else if (ShangHuTypeEnum.shopping.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.shopping.getFields());
                } else if (ShangHuTypeEnum.scenic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.scenic.getFields());
                } else if (ShangHuTypeEnum.restaurant.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.restaurant.getFields());
                }
            } else {
                return R.ok(new JSONArray());
            }
            para.put("spotName", shangHuName);
            para.put("userId", ui.getId());
            Map<String, Object> m = merchantTypeService.getBaseSpotInfoByName(para);
            if (m == null) {
                merchantTypeService.addBaseMerchantInfo(para);
            } else {
                flag = merchantTypeService.editBaseMerchantInfo(para);
            }
        }
        return flag ? R.success() : R.fail();
    }


    @PostMapping("/saveReportBaseScenic")
    @ApiOperation(value = "新增景区上报基础信息")
    public R saveReportBaseScenic(ReportBaseScenic reportBaseScenic, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseScenic.setUserId(cu.getUserId());
        reportBaseScenic.setCreateTime(new Date());
        boolean save = iReportBaseScenicService.save(reportBaseScenic);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseScenic")
    @ApiOperation(value = "修改景区上报基础信息")
    public R updateReportBaseScenic(ReportBaseScenic reportBaseScenic) {
        boolean b = iReportBaseScenicService.updateById(reportBaseScenic);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteReportBaseScenic")
    @ApiOperation(value = "删除景区上报数据")
    public R deleteReportBaseScenic(Integer id) {
        ReportBaseScenic byId = iReportBaseScenicService.getById(id);
        if (byId != null) {
            byId.setIsDelete(1);
            boolean b = iReportBaseScenicService.updateById(byId);
            if (b) {
                return R.ok("删除成功");
            }
        }
        return R.ok("删除失败");
    }

    @GetMapping("/getReportBaseScenic")
    @ApiOperation(value = "根据id获取景区上报数据")
    public R getReportBaseScenic(Integer id) {
        ReportBaseScenic byId = iReportBaseScenicService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

    @GetMapping("/getReportBaseAdminInfoList")
    @ApiOperation(value = "获取管理员信息列表")
    public R getReportBaseAdminInfoList(Integer pageNo, Integer pageSize, String keyWord) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 10;
        }
        PageInfo<Map<String, Object>> page = iBaseAdminService.getBaseAdminList(pageNo, pageSize, keyWord);
        return R.ok(page);
    }

    @GetMapping("/getReportBaseInfoList")
    @ApiOperation(value = "获取商户基础信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页条数", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "shangHuType", value = "商户类型(entertainment,hotel,restaurant,scenic,shopping,traffic,travel)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "keyWord", value = "商户名称", required = false, paramType = "query", dataType = "string"),
    })
    public R getReportBaseScenicList(Integer pageNo, Integer pageSize, String keyWord, String shangHuType) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 10;
        }
        Map<String, Object> para = new HashMap<>();
        if (StrUtil.isBlankOrUndefined(shangHuType)) {
            return R.nullValueError();
        } else {
            if (EnumUtil.contains(ShangHuTypeEnum.class, shangHuType)) {
                if (ShangHuTypeEnum.entertainment.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.entertainment.getFields());
                } else if (ShangHuTypeEnum.hotel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.hotel.getFields());
                } else if (ShangHuTypeEnum.travel.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.travel.getFields());
                } else if (ShangHuTypeEnum.traffic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.traffic.getFields());
                } else if (ShangHuTypeEnum.shopping.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.shopping.getFields());
                } else if (ShangHuTypeEnum.scenic.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.scenic.getFields());
                } else if (ShangHuTypeEnum.restaurant.equals(ShangHuTypeEnum.valueOf(shangHuType))) {
                    para.put("tableName", ShangHuTypeEnum.restaurant.getFields());
                }
            } else {
                return R.ok(new JSONArray());
            }
        }
        if (!StrUtil.isBlankOrUndefined(keyWord)) {
            para.put("keyWord", keyWord);
        }
        PageInfo<Map<String, Object>> page = iReportBaseScenicService.getBaseSpotListByPara(pageNo, pageSize, para);
        return R.ok(page);
    }


    @PostMapping("/saveReportBaseHotel")
    @ApiOperation(value = "新增酒店上报基础信息")
    public R saveReportBaseHotel(ReportBaseHotel reportBaseHotel, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseHotel.setUserId(cu.getUserId());
        reportBaseHotel.setCreateTime(new Date());
        boolean save = iReportBaseHotelService.save(reportBaseHotel);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseHotel")
    @ApiOperation(value = "修改酒店上报基础信息")
    public R updateReportBaseHotel(ReportBaseHotel reportBaseHotel) {
        boolean b = iReportBaseHotelService.updateById(reportBaseHotel);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseHotel")
    @ApiOperation(value = "删除酒店上报基础信息")
    public R deleteReportBaseHotel(Integer id) {
        ReportBaseHotel reportBaseHotel = iReportBaseHotelService.getById(id);
        if (reportBaseHotel != null) {
            reportBaseHotel.setIsDelete(1);
            boolean b = iReportBaseHotelService.updateById(reportBaseHotel);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }


    @GetMapping("/getReportBaseHotel")
    @ApiOperation(value = "根据id获取酒店上报信息")
    public R getReportBaseHotel(Integer id) {
        ReportBaseHotel byId = iReportBaseHotelService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }


//    @GetMapping("/getReportBaseHotelList")
//    @ApiOperation(value = "获取酒店上报基础信息")
//    public R getReportBaseHotelList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseHotel> reportBaseHotelList = iReportBaseHotelService.getReportBaseHotelList(pagNumber, pagSize, keyword);
//        if(reportBaseHotelList!=null){
//            return R.ok(reportBaseHotelList);
//        }
//        return R.ok("暂无数据");
//    }


    @PostMapping("/saveReportBaseTraffic")
    @ApiOperation(value = "新增交通上报基础信息")
    public R saveReportBaseTraffic(ReportBaseTraffic reportBaseTraffic, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseTraffic.setUserId(cu.getUserId());
        reportBaseTraffic.setCreateTime(new Date());
        boolean save = iReportBaseTrafficService.save(reportBaseTraffic);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseTraffic")
    @ApiOperation(value = "修改交通上报基础信息")
    public R updateReportBaseTraffic(ReportBaseTraffic reportBaseTraffic) {
        boolean b = iReportBaseTrafficService.updateById(reportBaseTraffic);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseTraffic")
    @ApiOperation(value = "删除交通上报基础信息")
    public R deleteReportBaseTraffic(Integer id) {
        ReportBaseTraffic reportBaseTraffic = iReportBaseTrafficService.getById(id);
        if (reportBaseTraffic != null) {
            reportBaseTraffic.setIsDelete(1);
            boolean b = iReportBaseTrafficService.updateById(reportBaseTraffic);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getReportBaseTraffic")
    @ApiOperation(value = "根据id获取交通上报信息")
    public R getReportBaseTraffic(Integer id) {
        ReportBaseTraffic byId = iReportBaseTrafficService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getReportBaseTrafficList")
//    @ApiOperation(value = "获取交通上报基础信息")
//    public R getReportBaseTrafficList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseTraffic> reportBaseTrafficList = iReportBaseTrafficService.getReportBaseTrafficList(pagNumber, pagSize, keyword);
//        if(reportBaseTrafficList!=null){
//            return R.ok(reportBaseTrafficList);
//        }
//        return R.ok("暂无数据");
//    }


    @PostMapping("/saveReportBaseEntertainment")
    @ApiOperation(value = "新增娱乐上报基础信息")
    public R saveReportBaseEntertainment(ReportBaseEntertainment reportBaseEntertainment, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseEntertainment.setUserId(cu.getUserId());
        reportBaseEntertainment.setCreateTime(new Date());
        boolean save = iReportBaseEntertainmentService.save(reportBaseEntertainment);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseEntertainment")
    @ApiOperation(value = "修改娱乐上报基础信息")
    public R updateReportBaseEntertainment(ReportBaseEntertainment reportBaseEntertainment) {
        boolean b = iReportBaseEntertainmentService.updateById(reportBaseEntertainment);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseEntertainment")
    @ApiOperation(value = "删除娱乐上报基础信息")
    public R deleteReportBaseEntertainment(Integer id) {
        ReportBaseEntertainment reportBaseEntertainment = iReportBaseEntertainmentService.getById(id);
        if (reportBaseEntertainment != null) {
            reportBaseEntertainment.setIsDelete(1);
            boolean b = iReportBaseEntertainmentService.updateById(reportBaseEntertainment);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getReportBaseEntertainment")
    @ApiOperation(value = "根据id获取娱乐上报信息")
    public R getReportBaseEntertainment(Integer id) {
        ReportBaseEntertainment byId = iReportBaseEntertainmentService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getReportBaseEntertainmentList")
//    @ApiOperation(value = "获取娱乐上报基础信息")
//    public R getReportBaseEntertainmentList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseEntertainment> reportBaseEntertainmentList = iReportBaseEntertainmentService.getReportBaseEntertainmentList(pagNumber, pagSize, keyword);
//        if(reportBaseEntertainmentList!=null){
//            return R.ok(reportBaseEntertainmentList);
//        }
//        return R.ok("暂无数据");
//    }


    @PostMapping("/saveReportBaseShopping")
    @ApiOperation(value = "新增购物上报基础信息")
    public R saveReportBaseShopping(ReportBaseShopping reportBaseShopping, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseShopping.setUserId(cu.getUserId());
        reportBaseShopping.setCreateTime(new Date());
        boolean save = iReportBaseShoppingService.save(reportBaseShopping);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseShopping")
    @ApiOperation(value = "修改购物上报基础信息")
    public R updateReportBaseShopping(ReportBaseShopping reportBaseShopping) {
        boolean b = iReportBaseShoppingService.updateById(reportBaseShopping);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseShopping")
    @ApiOperation(value = "删除购物上报基础信息")
    public R deleteReportBaseShopping(Integer id) {
        ReportBaseShopping reportBaseShopping = iReportBaseShoppingService.getById(id);
        if (reportBaseShopping != null) {
            reportBaseShopping.setIsDelete(1);
            boolean b = iReportBaseShoppingService.updateById(reportBaseShopping);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getReportBaseShopping")
    @ApiOperation(value = "根据id获取购物上报信息")
    public R getReportBaseShopping(Integer id) {
        ReportBaseShopping byId = iReportBaseShoppingService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getReportBaseShoppingList")
//    @ApiOperation(value = "获取购物上报基础信息")
//    public R getReportBaseShoppingList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseShopping> reportBaseShopping = iReportBaseShoppingService.getReportBaseShoppingList(pagNumber, pagSize, keyword);
//        if(reportBaseShopping!=null){
//            return R.ok(reportBaseShopping);
//        }
//        return R.ok("暂无数据");
//    }


    @PostMapping("/saveReportBaseRestaurant")
    @ApiOperation(value = "新增餐饮上报基础信息")
    public R saveReportBaseRestaurant(ReportBaseRestaurant reportBaseRestaurant, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseRestaurant.setUserId(cu.getUserId());
        reportBaseRestaurant.setCreateTime(new Date());
        boolean save = iReportBaseRestaurantService.save(reportBaseRestaurant);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseRestaurant")
    @ApiOperation(value = "修改餐饮上报基础信息")
    public R updateReportBaseRestaurant(ReportBaseRestaurant reportBaseRestaurant) {
        boolean b = iReportBaseRestaurantService.updateById(reportBaseRestaurant);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseRestaurant")
    @ApiOperation(value = "删除餐饮上报基础信息")
    public R deleteReportBaseRestaurant(Integer id) {
        ReportBaseRestaurant reportBaseRestaurant = iReportBaseRestaurantService.getById(id);
        if (reportBaseRestaurant != null) {
            reportBaseRestaurant.setIsDelete(1);
            boolean b = iReportBaseRestaurantService.updateById(reportBaseRestaurant);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getReportBaseRestaurant")
    @ApiOperation(value = "根据id获取餐饮上报信息")
    public R getReportBaseRestaurant(Integer id) {
        ReportBaseRestaurant byId = iReportBaseRestaurantService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getReportBaseRestaurantList")
//    @ApiOperation(value = "获取餐饮上报基础信息")
//    public R getReportBaseRestaurantList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseRestaurant> reportBaseRestaurant = iReportBaseRestaurantService.getReportBaseRestaurantList(pagNumber, pagSize, keyword);
//        if(reportBaseRestaurant!=null){
//            return R.ok(reportBaseRestaurant);
//        }
//        return R.ok("暂无数据");
//    }


    @PostMapping("/saveReportBaseTravel")
    @ApiOperation(value = "新增旅行社上报基础信息")
    public R saveReportBaseTravel(ReportBaseTravel reportBaseTravel, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseTravel.setUserId(cu.getUserId());
        boolean save = iReportBaseTravelService.save(reportBaseTravel);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseTravel")
    @ApiOperation(value = "修改旅行社上报基础信息")
    public R updateReportBaseTravel(ReportBaseTravel reportBaseTravel) {
        boolean b = iReportBaseTravelService.updateById(reportBaseTravel);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @PostMapping("/deleteReportBaseTravel")
    @ApiOperation(value = "删除旅行社上报基础信息")
    public R deleteReportBaseTravel(Integer id) {
        ReportBaseTravel reportBaseTravel = iReportBaseTravelService.getById(id);
        if (reportBaseTravel != null) {
            reportBaseTravel.setIsDelete(1);
            boolean b = iReportBaseTravelService.updateById(reportBaseTravel);
            if (b) {
                return R.ok("修改成功");
            }
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getReportBaseTravel")
    @ApiOperation(value = "根据id获取旅行社上报信息")
    public R getReportBaseTravel(Integer id) {
        ReportBaseTravel byId = iReportBaseTravelService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }

//    @GetMapping("/getReportBaseTravelList")
//    @ApiOperation(value = "获取餐饮上报基础信息")
//    public R getReportBaseTravelList(Integer pagNumber, Integer pagSize, String keyword){
//        if (pagNumber == null || pagSize == null) {
//            pagNumber = 1;
//            pagSize = 10;
//        }
//        if (keyword == null) {
//            keyword = "";
//        }
//        PageInfo<ReportBaseTravel> reportBaseTravel = iReportBaseTravelService.getReportBaseTravelList(pagNumber, pagSize, keyword);
//        if(reportBaseTravel!=null){
//            return R.ok(reportBaseTravel);
//        }
//        return R.ok("暂无数据");
//    }

    @PostMapping("/saveReportBaseGuide")
    @ApiOperation(value = "新增导游上报基础信息")
    public R saveReportBaseGuide(ReportBaseGuide reportBaseGuide, HttpServletRequest request) {
        CustomUser cu = JwtUtil.getFromJwt(request);
        reportBaseGuide.setCreateTime(LocalDateTime.now());
        reportBaseGuide.setUpdateTime(LocalDateTime.now());
        reportBaseGuide.setIsDelete(0);
        boolean save = iReportBaseGuideService.save(reportBaseGuide);
        if (save) {
            return R.ok("新增成功");
        }
        return R.ok("新增失败");
    }

    @PostMapping("/updateReportBaseGuide")
    @ApiOperation(value = "修改导游上报基础信息")
    public R updateReportBaseGuide(ReportBaseGuide reportBaseGuide) {
        boolean b = iReportBaseGuideService.updateById(reportBaseGuide);
        if (b) {
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @DeleteMapping("/deleteReportBaseGuide")
    @ApiOperation(value = "删除导游上报数据")
    public R deleteReportBaseGuide(Integer id) {
        ReportBaseGuide byId = iReportBaseGuideService.getById(id);
        if (byId != null) {
            byId.setIsDelete(1);
            boolean b = iReportBaseGuideService.updateById(byId);
            if (b) {
                return R.ok("删除成功");
            }
        }
        return R.ok("删除失败");
    }

    @GetMapping("/getReportBaseGuide")
    @ApiOperation(value = "根据id获取导游上报数据")
    public R getReportBaseGuide(Integer id) {
        ReportBaseGuide byId = iReportBaseGuideService.getById(id);
        if (byId != null) {
            return R.ok(byId);
        }
        return R.ok("暂无数据");
    }
}
