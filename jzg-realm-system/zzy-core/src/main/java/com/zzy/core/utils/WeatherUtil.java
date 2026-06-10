package com.zzy.core.utils;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class WeatherUtil {
    public static final String APPKEY = "16b1d024da06618b";// 你的appkey
    public static final String URL = "https://api.jisuapi.com/weather/query";

    public static Map<String,String> getWeather(String nowcity) throws Exception {
        String result = null;
        String url = URL + "?appkey=" + APPKEY + "&city=" + URLEncoder.encode(nowcity, "utf-8");
        Map<String, String> map = new LinkedHashMap<>();
        try {
            result =sendGet(url, "utf-8");
            JSONObject json = JSONObject.fromObject(result);
            if (json.getInt("status") != 0) {
                System.out.println(json.getString("msg"));
            } else {
                JSONObject resultarr = json.optJSONObject("result");
                String city = resultarr.getString("city");
                String cityid = resultarr.getString("cityid");
                String citycode = resultarr.getString("citycode");
                String date = resultarr.getString("date");
                String week = resultarr.getString("week");
                String weather = resultarr.getString("weather");
                String temp = resultarr.getString("temp");
                String temphigh = resultarr.getString("temphigh");
                String templow = resultarr.getString("templow");
                String img = resultarr.getString("img");
                String humidity = resultarr.getString("humidity");
                String pressure = resultarr.getString("pressure");
                String windspeed = resultarr.getString("windspeed");
                String winddirect = resultarr.getString("winddirect");
                String aqi = resultarr.getString("aqi");
                String windpower = resultarr.getString("windpower");
                String index = resultarr.getString("index");
                map.put("城市", city);
                map.put("城市ID", cityid);
                map.put("日期", date);
                map.put("星期", week);
                map.put("天气", weather);
                map.put("气温", temp + "℃");
                map.put("最高气温", temphigh + "℃");
                map.put("最低气温", templow + "℃");
                map.put("图片数字", img);
                map.put("生活指数",index);
                map.put("湿度 %", humidity + "%");
                map.put("气压", pressure + "hpa");
                map.put("风速", windspeed + "m/s");
                map.put("风向", winddirect);
                map.put("风力",windpower);
                map.put("AQI", aqi);
                map.put("未来天气","");
                if (resultarr.opt("daily") != null) {
                    JSONArray daily = resultarr.optJSONArray("daily");
                    for (int i = 0; i < daily.size(); i++) {
                        JSONObject obj = (JSONObject) daily.opt(i);
                        String date1 = obj.getString("date");
                        String week1 = obj.getString("week");
                        String sunrise = obj.getString("sunrise");
                        String sunset = obj.getString("sunset");
                        //String humidity1 = resultarr.getString("humidity");

                        map.put("未来日期"+i+"",date1);
                        map.put("星期"+i+"",week1);
                        map.put("日出"+i+"",sunrise);
                        map.put("日落"+i+"",sunset);
                        if (obj.opt("night") != null) {
                            JSONObject night = (JSONObject) obj.opt("night");
                            String weather1 = night.getString("weather");
                            String temp1 = resultarr.getString("temp");
                            String temphigh1 = resultarr.getString("temphigh");
                            String templow1 = night.getString("templow");
                            String img1 = night.getString("img");
                            String winddirect1 = night.getString("winddirect");
                            String windpower1 = night.getString("windpower");
                            String humidity1 = resultarr.getString("humidity");
                            map.put("天气"+i+"",weather1);
                            map.put("当前温度"+i+"",temp1+"℃");
                            map.put("最高温度"+i+"",temphigh1);
                            map.put("最低温度"+i+"",templow1);
                            map.put("图片数字"+i+"",img1);
                            map.put("湿度"+i+"", humidity1 + "%");
                            map.put("风向"+i+"",winddirect1);
                            map.put("风力"+i+"",windpower1);

                        }
                        if (obj.opt("day") != null) {
                            JSONObject day = obj.optJSONObject("day");
                            String weather1 = day.getString("weather");
                            String templow1 = "";
                            String temphigh1="";
                            if (day.has("templow")) {
                                temphigh1 = resultarr.getString("temphigh");
                                templow1 = day.getString("templow");
                            }
                            String temp1 = resultarr.getString("temp");
                            String img1 = day.getString("img");
                            String winddirect1 = day.getString("winddirect");
                            String windpower1 = day.getString("windpower");
                            String humidity1 = resultarr.getString("humidity");
                            map.put("天气"+i+"",weather1);
                            map.put("当前温度",temp1);
                            map.put("最高温度"+i+"",temphigh1);
                            map.put("最低温度"+i+"",temphigh1);
                            map.put("图片数字"+i+"",img1);
                            map.put("湿度"+i+"", humidity1 + "%");
                            map.put("风向"+i+"",winddirect1);
                            map.put("风力"+i+"",windpower1);

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; .NET CLR 3.0.04506; customie8)";

    // HTTP GET request
    public static String sendGet(String url, String charset) throws Exception {
        java.net.URL realurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) realurl.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
        String inputLine;
        StringBuffer result = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
            result.append("\r\n");
        }
        in.close();
        con.disconnect();
        return result.toString();
    }

    public static Date utcToDate(String time) throws Exception {
        time = time.replace("Z", " UTC");//是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//格式化的表达式
        Date d = format.parse(time);
        return d;
    }

    public static String getJzgWeather(){
        String url ="https://jzgapi.1lianyou.com/api/ProductInfo/CityAirQualityByCityId";
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("citycode",101271906);
        objectObjectHashMap.put("appid","wx107fd71b83c2c7f9");
        String post = HttpUtil.get(url,objectObjectHashMap);
        return post;
    }

    public static String getJzgWeatherNew(){
        String host = "http://iweather.market.alicloudapi.com";
        String path = "/scenic";
        String method = "GET";
        String appcode = "eda21b36425549e783d26fa824947157";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("city", "杭州");
        querys.put("jq", "九寨沟");
        querys.put("needday", "7");

        String x = "";
        try {
            HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            x = EntityUtils.toString(httpResponse.getEntity());
            x = UnicodeUtil.toString(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public static void main(String[] args) throws Exception {
       /* *//*Date date = utcToDate("2020-05-13T16:00:00.000Z");
        System.out.println(date);*//*
        String url ="https://jzgapi.1lianyou.com/api/ProductInfo/CityAirQualityByCityId";
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("citycode",101271906);
        objectObjectHashMap.put("appid","wx107fd71b83c2c7f9");
        String post = HttpUtil.get(url,objectObjectHashMap);
        System.out.println(post);*/
        System.out.println(getWeather("九寨沟县"));
    }
}
