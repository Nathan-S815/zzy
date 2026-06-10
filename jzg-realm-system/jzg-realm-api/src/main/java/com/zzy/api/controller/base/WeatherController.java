package com.zzy.api.controller.base;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.zzy.api.service.base.IWeatherInfoService;
import com.zzy.core.dto.R;
import com.zzy.core.utils.JsonUtil;
import com.zzy.core.utils.WeatherUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
@Api(value = "极速天气接口", tags = "极速天气接口")
public class WeatherController {
    @Autowired
    private IWeatherInfoService iWeatherInfoService;

    @GetMapping("/getWeather")
    @ApiOperation(value = "获取天气数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nowcity", value = "城市名称", required = true, paramType = "query", dataType = "String")
    })
    public R getWeather(String nowcity) throws Exception {
        if(nowcity!=null) {
            Map<String, String> weather = WeatherUtil.getWeather(nowcity);
            if (weather != null) {
                return R.ok(weather);
            }
            return R.ok("暂无数据");
        }
        return R.ok("参数不能为空");
    }
    @GetMapping("/public/getJzgWeather")
    @ApiOperation(value = "获取九寨沟天气数据")
    public R getJzgWeather()  {
//        String jzgWeather = WeatherUtil.getJzgWeather();
//        List<Map<String, Object>> tempAndIndex = iWeatherInfoService.getTempAndIndex();
//        String temp =(String) tempAndIndex.get(0).get("temp");
//        JSONObject jsonObject = JSON.parseObject(jzgWeather);
//        JSONObject result = (JSONObject)jsonObject.get("result");
//        result.put("temphigh",temp);
//        JSONArray daily = result.getJSONArray("daily");
//        JSONObject o = (JSONObject)daily.get(0);
//        JSONObject day=(JSONObject)o.get("day");
//        day.put("temphigh",temp);
//        return R.ok(jsonObject);
        String jzgWeatherNew = WeatherUtil.getJzgWeatherNew();
        return R.ok(jzgWeatherNew);
    }
    @GetMapping("/public/getTempAndIndex")
    @ApiOperation(value = "获取温度和穿衣指数")
    public R getTempAndIndex()  {
        return R.ok(iWeatherInfoService.getTempAndIndex());
    }


//    public static void main(String[] args) {
//
//    }
}
