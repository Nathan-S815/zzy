package com.zzy.api.controller.portrait;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzy.api.service.portrait.ITouristPortraitScenicDayService;
import com.zzy.api.service.yidong.IJzgydScenicPassengerAgeService;
import com.zzy.api.service.yidong.IJzgydScenicPassengerGenderService;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portrait")
@Api(value = "游客画像相关接口", tags = "游客画像相关接口")
public class TouristPortraitScenicDayController {
    @Autowired
    private ITouristPortraitScenicDayService iTouristPortraitScenicDayService;
    @Autowired
    private IJzgydScenicPassengerAgeService iJzgydScenicPassengerAgeService;
    @Autowired
    private IJzgydScenicPassengerGenderService iJzgydScenicPassengerGenderService;


    @GetMapping("/getTouristSex")
    @ApiOperation(value = "获取游客男女性别数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String")
    })
    public R getTouristSex(String accTime){
       /* QueryWrapper<TouristPortraitScenicDay> wrapper = new QueryWrapper<>();
        wrapper.select("men_num","woman_num").eq("acc_time",accTime);*/
       if(accTime!=null) {
           String replace = accTime.replace("-", "");
           String startTime = replace + "01";
           String endTime = replace + "31";
           return R.ok(iJzgydScenicPassengerGenderService.getTouristPortraitSexCount(startTime,endTime));
       }else {
           return R.ok(iJzgydScenicPassengerGenderService.getTouristPortraitSexCount("20000101","20991231"));
       }
    }

    @GetMapping("/getTouristSexNew")
    @ApiOperation(value = "获取游客男女性别数量")
    public R getTouristSexNew(){
//        0.57049;
//        0.42950;
        String post = HttpUtil.post("https://m.abatour.com/chartAbaTour/dataAnalysis/YKNLBL_4.html", new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(post);
        JSONArray series = jsonObject.getJSONArray("series");
        JSONArray datalist = series.getJSONObject(0).getJSONArray("datalist");
        JSONArray data = datalist.getJSONObject(0).getJSONArray("data");
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> map;
        for (Object datum : data) {
            map = (Map<String,Object>) datum;
            dataMap.put(map.get("name") + "", map.get("value"));
        }
        result.add(dataMap);
        return R.ok(result);
    }

    @GetMapping("/getAgeDistribution")
    @ApiOperation(value = "获取游客年龄分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accTime", value = "时间(eg:2020-03)", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sceneId", value = "景区编码", required = false, paramType = "query", dataType = "String")
    })
    public R getAgeDistribution(String accTime,String sceneId){
        if(accTime!=null) {
            String replace = accTime.replace("-", "");
            String startTime = replace + "01";
            String endTime = replace + "31";
            return R.ok(iJzgydScenicPassengerAgeService.getAgeDistribution(startTime,endTime));
        }else {
            return R.ok(iJzgydScenicPassengerAgeService.getAgeDistribution("20000101", "20991231"));
        }
    }


    @GetMapping("/getAgeDistributionNew")
    @ApiOperation(value = "获取游客年龄分布")
    public R getAgeDistributionNew(){
        String post = HttpUtil.post("https://m.abatour.com/chartAbaTour/dataAnalysis/YKNLDTJ_4.html", new HashMap<>());
        JSONObject jsonObject = JSONUtil.parseObj(post);
        JSONArray serise = jsonObject.getJSONArray("series");
        JSONArray data = serise.getJSONObject(0).getJSONArray("data");
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> map;
        for (Object datum : data) {
            map = (Map<String,Object>) datum;
            dataMap.put(map.get("name") + "", map.get("value"));
        }
        result.add(dataMap);
        return R.ok(result);
    }


    public static void main(String[] args) {
        String post = HttpUtil.post("https://m.abatour.com/chartAbaTour/dataAnalysis/YKNLBL_4.html", new HashMap<>());

        System.err.println(post);
//        JSONObject jsonObject = JSONUtil.parseObj(post);
//        JSONArray series = jsonObject.getJSONArray("series");
//        JSONArray datalist = series.getJSONObject(0).getJSONArray("datalist");
//        JSONArray data = datalist.getJSONObject(0).getJSONArray("data");

    }
}
