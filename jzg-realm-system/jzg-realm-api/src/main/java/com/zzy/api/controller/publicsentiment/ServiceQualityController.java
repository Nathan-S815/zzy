package com.zzy.api.controller.publicsentiment;

import cn.hutool.core.util.StrUtil;
import com.zzy.api.service.ota.IHotelCommentInfoService;
import com.zzy.api.service.ota.IRestaurantCommentInfoService;
import com.zzy.api.service.ota.IScenicCommentInfoService;
import com.zzy.api.service.publicsentiment.ScenicSpotService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.publicsentiment.ScenicNews;
import com.zzy.db.entity.publicsentiment.ScenicSpot;
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
@RequestMapping("/quality")
@Api(tags = "服务质量排名")
public class ServiceQualityController {

    @Autowired
    private IScenicCommentInfoService iScenicCommentInfoService;

    @Autowired
    private IHotelCommentInfoService iHotelCommentInfoService;

    @Autowired
    private IRestaurantCommentInfoService iRestaurantCommentInfoService;

    @Autowired
    private ScenicSpotService scenicSpotService;


    @ApiOperation(value = "服务排名")
    @GetMapping("/getQualityRank")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间（2020-05-09）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间（2020-05-16）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "serviceNumber", value = "1.景区2.酒店3.餐饮", required = false, paramType = "query", dataType = "int"),
    })
    public R getScenicSpot(String startTime, String endTime, Integer serviceNumber) {
        if (endTime == null) {
            endTime = TimeDateUtil.getFormatDate(new Date());
        }
        if (startTime == null) {
            startTime = TimeDateUtil.getFirstOfYear(endTime);
        }
        if (serviceNumber == null) {
            return R.ok("缺少参数");
        }
        if (serviceNumber == 1) {
//            return R.ok(iScenicCommentInfoService.getScenicSpot(startTime,endTime));
            List<Map> list = new ArrayList<>();
            list.add(getMap("5.0",1,"九寨·云顶秘境","up"));
            list.add(getMap("5.0",2,"聚宝山风成寺","up"));
            list.add(getMap("4.9",3,"九寨金丝猴森林公园","up"));
            list.add(getMap("4.8",4,"白马藏族风情园","down"));
            list.add(getMap("4.8",5,"九寨千古情","up"));
            return R.ok(list);

        } else if (serviceNumber == 2) {
//            return R.ok(iHotelCommentInfoService.getHotel(startTime, endTime));
            List<Map> list = new ArrayList<>();
            list.add(getMap("4.8",1,"鲁能希尔顿度假酒店","up"));
            list.add(getMap("4.8",2,"天源豪生度假酒店","up"));
            list.add(getMap("4.7",3,"金龙国际度假酒店","up"));
            list.add(getMap("4.5",4,"润都国际温泉酒店","up"));
            list.add(getMap("3.5",5,"世纪顺水·九寨观海酒店","down"));
            return R.ok(list);

        } else if (serviceNumber == 3) {
//            return R.ok(iRestaurantCommentInfoService.getRestaurant(startTime, endTime));
            List<Map> list = new ArrayList<>();
            list.add(getMap("5.0",1,"蜀大侠","up"));
            list.add(getMap("4.9",2,"重庆九五老火锅","down"));
            list.add(getMap("4.8",3,"苗家酸菜鸡","down"));
            list.add(getMap("4.8",4,"老四川饭店","up"));
            list.add(getMap("4.7",5,"九号厚厨（九寨沟店）","up"));
            return R.ok(list);

        } else {
            return R.ok("参数错误");
        }

    }

    @ApiOperation(value = "服务排名-新")
    @GetMapping("/getScenicSpot")
    public R getQualityRank(Integer serviceNumber){
        List<ScenicSpot> scenicSpotList = scenicSpotService.getScenicSpotList(serviceNumber);
        return R.ok(scenicSpotList);
    }

    @ApiOperation(value = "新闻排名-新")
    @GetMapping("/getScenicNews")
    public R getScenicNews(){
        List<ScenicNews> scenicNewsList = scenicSpotService.getScenicNewsList();
        return R.ok(scenicNewsList);
    }
    private Map<String,Object> getMap(String score,int rank,String commentPlaceName,String f){
        Map<String, Object> map = new HashMap<>();
        map.put("score", score);
        map.put("rank", rank);
        map.put("commentPlaceName", commentPlaceName);
        map.put("float", f);
        return map;
    }

}
