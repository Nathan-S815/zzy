package com.zzy.api.controller.hotmap;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.zzy.core.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/abawenlv/public")
@Api(value = "阿坝文旅数据接口", tags = "阿坝文旅数据接口")
public class ABaWnLvController {

    @GetMapping("/getJZGPiaoWu")
    @ApiOperation(value = "九寨沟票务数据")
    public R getJZGPiaoWu(){
        String s = HttpUtil.get("http://c.abatour.com/kclistData/futureData_4.html");
        String quStr=s.substring(s.indexOf("(")+1,s.indexOf(")")).trim();
        return R.ok(JSONObject.parseObject(quStr));
    }

    @GetMapping("/getHuangLongPiaoWu")
    @ApiOperation(value = "黄龙票务数据")
    public R getHuangLongPiaoWu(){
        String s = HttpUtil.get("http://c.abatour.com/kclistData/futureData_1.html");
        String quStr=s.substring(s.indexOf("(")+1,s.indexOf(")")).trim();
        return R.ok(JSONObject.parseObject(quStr));
    }

    @GetMapping("/getSiGirlPiaoWu")
    @ApiOperation(value = "四姑娘山票务数据")
    public R getSiGirlPiaoWu(){
        String s = HttpUtil.get("http://c.abatour.com/kclistData/futureData_6.html");
        String quStr=s.substring(s.indexOf("(")+1,s.indexOf(")")).trim();
        return R.ok(JSONObject.parseObject(quStr));
    }

    @GetMapping("/getDaGuBingChuangPiaoWu")
    @ApiOperation(value = "达古冰川票务数据")
    public R getDaGuBingChuangPiaoWu(){
        String s = HttpUtil.get("http://c.abatour.com/kclistData/futureData_3.html");
        String quStr=s.substring(s.indexOf("(")+1,s.indexOf(")")).trim();
        return R.ok(JSONObject.parseObject(quStr));
    }

    @GetMapping("/getTemporary")
    @ApiOperation(value = "临时票务数据")
    public R getTemporary(){
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> daxiongmao = new HashMap<>();
        daxiongmao.put("name","甲勿海大熊猫保护研究园");
        daxiongmao.put("number",543);
        Map<String,Object> shenxianchi = new HashMap<>();
        shenxianchi.put("name","嫩恩桑措(神仙池)");
        shenxianchi.put("number",237);
        Map<String,Object> ganhaizi = new HashMap<>();
        ganhaizi.put("name","爱情海(甘海子)");
        ganhaizi.put("number",652);
        result.add(daxiongmao);
        result.add(shenxianchi);
        result.add(ganhaizi);
        return R.ok(result);
    }


    @GetMapping("/getSalesVolume")
    @ApiOperation(value = "跑马灯数据")
    public R getSalesVolume(){
        List<Map<String,Object>> result = new ArrayList<>();
        result.add(getTodaySalesVolume("http://c.abatour.com/kclistData/futureData_4.html"));
        result.add(getTodaySalesVolume("http://c.abatour.com/kclistData/futureData_1.html"));
        result.add(getTodaySalesVolume("http://c.abatour.com/kclistData/futureData_6.html"));
        result.add(getTodaySalesVolume("http://c.abatour.com/kclistData/futureData_3.html"));
        return R.ok(result);
    }

    //获取今日票的销量
    public Map<String,Object> getTodaySalesVolume(String url){
        String s = HttpUtil.get(url);
        String quStr=s.substring(s.indexOf("(")+1,s.indexOf(")")).trim();
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(quStr);
        JSONArray dateList = jsonObject.getJSONArray("dateList");
        String szscenicname = jsonObject.getStr("szscenicname");
        String today = DateUtil.today();
        Map<String, Object> map;
        List<Map<String, Object>> list;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", szscenicname);
        for (Object o : dateList) {
            map = (Map<String,Object>) o;
            if (today.equals(Convert.toStr(map.get("date")))) {
                list = (List<Map<String, Object>>)map.get("numberList");
                //计算库存-剩余数量就是售出票的数量
                Integer stockNumb = Convert.toInt(list.get(0).get("stockNumb"));
                Integer surplusNumber = Convert.toInt(list.get(0).get("surplusNumber"));
                resultMap.put("num",stockNumb - surplusNumber);
                break;
            }
        }
        return resultMap;
    }

}
