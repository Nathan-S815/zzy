package com.zzy.api.controller.carpark;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zzy.api.service.carpark.*;
import com.zzy.core.dto.R;
import com.zzy.core.utils.JsonUtil;
import com.zzy.db.dao.carpark.*;
import com.zzy.db.entity.carpark.*;
import com.zzy.db.entity.warning.NzFileModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/CarParkCollect")
@Api(value = "停车场数据采集接口", tags = "停车场数据采集接口")
public class CarParkCollectController {
    @Autowired
    private IGetParkInfoService iGetParkInfoService;
    @Autowired
    private IGetRemainingSpaceGanhaiziService iGetRemainingSpaceGanhaiziService;
    @Autowired
    private IGetRemainingSpaceHGanhaiziService iGetRemainingSpaceHGanhaiziService;
    @Autowired
    private GetParkInfoMapper getParkInfoMapper;
    @Autowired
    private GetRemainingSpaceMapper getRemainingSpaceMapper;
    @Autowired
    private GetRemainingSpaceHMapper getRemainingSpaceHMapper;

    @ApiOperation(value = "停车场数据采集")
    @PostMapping("/public/GanHaiZi")
    public R getGanHaiZi(@RequestBody String ParkInfoList) {
        ParkInfo parkInfo = JSON.parseObject(ParkInfoList, new TypeReference<ParkInfo>() {});
        List<GetParkInfo> getParkInfoList = new ArrayList<>();
        List<GetRemainingSpace> getRemainingSpaceList = new ArrayList<>();
        List<GetRemainingSpaceH> getRemainingSpaceHList = new ArrayList<>();
            parkInfo.setParkKey("ganhaizi");
            parkInfo.setParkName("爱情海停车场");
            parkInfo.setParkLatitude("33.26103060425887");
            parkInfo.setParkLongitude("103.76746853584291");
            GetParkInfo getParkInfo = new GetParkInfo();
            getParkInfo.setParkKey(parkInfo.getParkKey());
            getParkInfo.setParkName(parkInfo.getParkName());
            getParkInfo.setParkLatitude(parkInfo.getParkLatitude());
            getParkInfo.setParkLongitude(parkInfo.getParkLongitude());
            getParkInfo.setScenicName("爱情海(甘海子)");
            getParkInfo.setSpaceTotal(parkInfo.getSpaceTotal());
            getParkInfo.setCreateTime(new Date());
            getParkInfoList.add(getParkInfo);

            GetRemainingSpace getRemainingSpace = new GetRemainingSpace();
            getRemainingSpace.setRemaiSpaces(parkInfo.getSpaceRemai());
            getRemainingSpace.setTotalSpaces(parkInfo.getSpaceTotal());
            getRemainingSpace.setParkKey(parkInfo.getParkKey());
            getRemainingSpace.setCreateTime(new Date());
            getRemainingSpaceList.add(getRemainingSpace);

            GetRemainingSpaceH getRemainingSpaceH = new GetRemainingSpaceH();
            getRemainingSpaceH.setRemaiSpaces(parkInfo.getSpaceRemai());
            getRemainingSpaceH.setTotalSpaces(parkInfo.getSpaceTotal());
            getRemainingSpaceH.setParkKey(parkInfo.getParkKey());
            getRemainingSpaceH.setCreateTime(new Date());
            getRemainingSpaceH.setGetTime(LocalDateTime.parse(parkInfo.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            getRemainingSpaceHList.add(getRemainingSpaceH);
//        int i0 = getParkInfoMapper.batchInsert(getParkInfoList);
//        int i1 = getRemainingSpaceMapper.batchInsert(getRemainingSpaceList);
//        int i2 = getRemainingSpaceHMapper.batchInsert(getRemainingSpaceHList);
//        if (i0 != 0 && i1 != 0 && i2 != 0) {
//        if (i1 != 0 && i2 != 0) {
            return R.ok("保存成功");
//        } else {
//            return R.ok("保存失败");
//        }

    }
}


