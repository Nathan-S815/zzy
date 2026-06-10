package com.zzy.api.controller.warning;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.service.carpark.IGetParkInfoService;
import com.zzy.api.service.reportbase.IReportBaseScenicService;
import com.zzy.api.service.warning.IWarningInfoService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.JsonUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.carpark.GetParkInfo;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import com.zzy.db.entity.warning.NzFileModel;
import com.zzy.db.entity.warning.WarningInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/public/warning")
@Api(tags = "预警模块")
public class WarningController {

    @Autowired
    private IWarningInfoService iWarningInfoService;
//    {"addTime":"2020-05-28 00:00:00","address":"1","describe":"1","fileList1":{"uid":"31ab9c544a804f92aa0d313083ed97c5","url":"https://smsycs.hxxpl.com.cn//2020/202005281608230151910.jpg","name":"1.jpg","type":"image/jpeg","status":"success"},"fileList2":null,"fileList3":null}

    @Autowired
    private IReportBaseScenicService iReportBaseScenicService;
    @Autowired
    private IGetParkInfoService iGetParkInfoService;


    @ApiOperation(value = "一键报警")
    @GetMapping("/OneButtonAlarm")
    public R oneButtonAlarm(String param) {
        JSONObject json = JsonUtil.strToJSONObject(param);
        String address = null;
        String describe = null;
        String longitude = null;
        String latitude = null;
        NzFileModel fileList1 = null;
        try {
            address = json.getString("address");
            describe = json.getString("describe");
            longitude = json.getString("longitude");
            latitude = json.getString("latitude");

//            fileList1 = (NzFileModel) JSONObject.toBean(JSONObject.fromObject(json.getString("fileList1")), NzFileModel.class);
            fileList1 = json.getJSONObject("fileList1").toJavaObject(NzFileModel.class);
        } catch (Exception e) {
            return R.ok("参数错误");
        }
        String addTime = null;
        NzFileModel fileList2 = null;
        NzFileModel fileList3 = null;
        try {
            addTime = json.getString("addTime");
        } catch (Exception e) {
        }
        try {
//            fileList2 = (NzFileModel) JSONObject.toBean(JSONObject.fromObject(json.getString("fileList2")), NzFileModel.class);
            fileList2 = json.getJSONObject("fileList2").toJavaObject(NzFileModel.class);
//            fileList3 = (NzFileModel) JSONObject.toBean(JSONObject.fromObject(json.getString("fileList3")), NzFileModel.class);
            fileList3 = json.getJSONObject("fileList3").toJavaObject(NzFileModel.class);
        } catch (Exception e) {
        }
        if (address == null || "".equals(address) || describe == null || "".equals(describe) || fileList1 == null) {
            return R.ok("参数不足");
        }
        if (addTime == null || "null".equals(addTime)) {
            addTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        List<NzFileModel> fileList = new ArrayList<>();
        fileList.add(fileList1);
        if (fileList2 != null) {
            fileList.add(fileList2);
        }
        if (fileList3 != null) {
            fileList.add(fileList3);
        }
        if (iWarningInfoService.saveMessage(address, addTime, describe, longitude, latitude, fileList)) {
            if (iWarningInfoService.sendMessage(address, addTime, describe, longitude, latitude, fileList)) {
                return R.ok("添加成功");
            } else {
                return R.ok("添加失败");
            }
        } else {
            return R.ok("添加失败");
        }
    }

    @ApiOperation(value = "景区客流量报警")
    @GetMapping("/scenicAlarm")
    public R scenicAlarm(String scenicName, int peopleNum) {
        try {
            ReportBaseScenic reportBaseScenic = iReportBaseScenicService.getOne(new QueryWrapper<ReportBaseScenic>().eq("scenic_name", scenicName));
            String latitude = reportBaseScenic.getLat();
            String longitude = reportBaseScenic.getLng();
            String address = reportBaseScenic.getAddress();
            String addTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            String describe = scenicName + "游客人数已超过预警值，目前在园人数为" + peopleNum + ",请及时处理！";
            if (iWarningInfoService.saveMessage(address, addTime, describe, longitude, latitude, null)) {
                if (iWarningInfoService.sendMessage(address, addTime, describe, longitude, latitude, null)) {
                    reportBaseScenic.setWarningTime(DateUtil.format(DateUtil.offsetHour(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
                    iReportBaseScenicService.updateById(reportBaseScenic);
                    return R.ok("添加成功");
                } else {
                    return R.ok("添加失败");
                }
            } else {
                return R.ok("添加失败");
            }
        } catch (Exception e) {
            return R.ok("添加失败");
        }

    }

    @ApiOperation(value = "停车位报警")
    @GetMapping("/carParkAlarm")
    public R carParkAlarm(String parkName, int remainNum) {
        try {
            GetParkInfo parkInfo = iGetParkInfoService.getOne(new QueryWrapper<GetParkInfo>().eq("park_name", parkName));
            String latitude = parkInfo.getParkLatitude();
            String longitude = parkInfo.getParkLongitude();
            String address = parkInfo.getParkAdd();
            String addTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            String describe = parkName + "剩余车位数已超过预警值，目前剩余车位数为" + remainNum + ",请及时处理！";
            if (iWarningInfoService.saveMessage(address, addTime, describe, longitude, latitude, null)) {
                if (iWarningInfoService.sendMessage(address, addTime, describe, longitude, latitude, null)) {
                    parkInfo.setWarningTime(DateUtil.format(DateUtil.offsetHour(new Date(),1),"yyyy-MM-dd HH:mm:ss"));
                    iGetParkInfoService.updateById(parkInfo);
                    return R.ok("添加成功");
                } else {
                    return R.ok("添加失败");
                }
            } else {
                return R.ok("添加失败");
            }
        } catch (Exception e) {
            return R.ok("添加失败");
        }

    }

}
