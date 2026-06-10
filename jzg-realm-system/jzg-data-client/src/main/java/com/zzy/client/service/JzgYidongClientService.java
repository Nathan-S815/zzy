package com.zzy.client.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzy.client.api.YidongClient;
import com.zzy.client.common.ANameEnum;
import com.zzy.db.entity.hotmap.GetAbjzgHourLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinuteLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.zzy.db.entity.yidong.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class JzgYidongClientService {

    public static void main(String[] args) {
        Object o = null;
        Date dt = DateUtil.offsetMonth(new Date(),-2);
//        o = getGetAbjzgMinutePeopleHotData();
//        o = getGetAbjzgMinuteLocalData();
//        o = getGetAbjzgHourLocalData(new Date());
//        o = getJzgydCountyScenicPassenger(dt,1);
        o = getJzgydCountyScenicPassenger(dt,2);
//        o = getJzgydCountyPassengerSource(dt,1);
//        o = getJzgydCountyPassengerSource(dt,2);
//        o = getJzgydProvincePassengerSource(dt,1);
//        o = getJzgydProvincePassengerSource(dt,2);
//        o = getJzgydCityPassengerSource(dt,1);
//        o = getJzgydCityPassengerSource(dt,2);
//        o = getJzgydCountryPassengerSource(dt,1);
//        o = getJzgydCountryPassengerSource(dt,2);
//        o = getJzgydScenicPassengerStayTime(dt,1);
//        o = getJzgydScenicPassengerStayTime(dt,2);
//        o = getJzgydScenicPassengerGender(dt,1);
//        o = getJzgydScenicPassengerGender(dt,2);
//        o = getJzgydScenicPassengerAge(dt,1);
//        o = getJzgydScenicPassengerAge(dt,2);
//        o = getJzgydScenicConsumption(dt,1);
//        o = getJzgydScenicConsumption(dt,2);
        System.out.println(JSON.toJSONString(o));
    }

    /**
     * 日客流arpu分析数据
     *
     * @return
     */
    public static List<JzgydScenicConsumption> getJzgydScenicConsumption(Date date, Integer type) {
        Map<String, List<JzgydScenicConsumption>> m = new HashMap<>();
        List<JzgydScenicConsumption> list = new ArrayList<JzgydScenicConsumption>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCountyConsumption, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayConsumptionData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayConsumptionData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCountyConsumption, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayConsumptionData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayConsumptionData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 日客流年龄数据
     *
     * @return
     */
    public static List<JzgydScenicPassengerAge> getJzgydScenicPassengerAge(Date date, Integer type) {
        Map<String, List<JzgydScenicPassengerAge>> m = new HashMap<>();
        List<JzgydScenicPassengerAge> list = new ArrayList<JzgydScenicPassengerAge>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCountyScenicAges, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayAgeData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayAgeData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCountyScenicPassengerAge, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayAgeData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayAgeData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }


    /**
     * 日客流性别数据
     *
     * @return
     */
    public static List<JzgydScenicPassengerGender> getJzgydScenicPassengerGender(Date date, Integer type) {
        Map<String, List<JzgydScenicPassengerGender>> m = new HashMap<>();
        List<JzgydScenicPassengerGender> list = new ArrayList<JzgydScenicPassengerGender>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCountyScenicGender, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayGenderData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayGenderData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCountyScenicPassengerGender, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayGenderData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayGenderData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 日驻留时长客流数据
     *
     * @return
     */
    public static List<JzgydScenicPassengerStayTime> getJzgydScenicPassengerStayTime(Date date, Integer type) {
        Map<String, List<JzgydScenicPassengerStayTime>> m = new HashMap<>();
        List<JzgydScenicPassengerStayTime> list = new ArrayList<JzgydScenicPassengerStayTime>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayScenicPassengerStayTime, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayStayTimeData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayStayTimeData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthPassengerStayTime, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayStayTimeData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayStayTimeData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 国际来源地日人流数据
     *
     * @return
     */
    public static List<JzgydCountryPassengerSource> getJzgydCountryPassengerSource(Date date, Integer type) {
        Map<String, List<JzgydCountryPassengerSource>> m = new HashMap<>();
        List<JzgydCountryPassengerSource> list = new ArrayList<JzgydCountryPassengerSource>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayInternationalPassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCountryData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCountryData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthInternationalSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCountryData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCountryData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 各地市来源地日客流数据
     *
     * @return
     */
    public static List<JzgydCityPassengerSource> getJzgydCityPassengerSource(Date date, Integer type) {
        Map<String, List<JzgydCityPassengerSource>> m = new HashMap<>();
        List<JzgydCityPassengerSource> list = new ArrayList<JzgydCityPassengerSource>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCityPassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCityData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCityData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCityPassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCityData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCityData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 各省来源地日客流数据
     *
     * @return
     */
    public static List<JzgydProvincePassengerSource> getJzgydProvincePassengerSource(Date date, Integer type) {
        Map<String, List<JzgydProvincePassengerSource>> m = new HashMap<>();
        List<JzgydProvincePassengerSource> list = new ArrayList<JzgydProvincePassengerSource>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayProvincesPassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceProvData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceProvData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthProvincePassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceProvData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceProvData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 各区县来源地客流数据
     * 1日,2月
     * @return
     */
    public static List<JzgydCountyPassengerSource> getJzgydCountyPassengerSource(Date date, Integer type) {
        Map<String, List<JzgydCountyPassengerSource>> m = new HashMap<>();
        List<JzgydCountyPassengerSource> list = new ArrayList<JzgydCountyPassengerSource>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCountyScenicPassengerSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCountyData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCountyData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCountyPassengerSourc, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDaySourceCountyData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDaySourceCountyData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }

        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 日客流数据
     *
     * @return
     */
    public static List<JzgydCountyScenicPassenger> getJzgydCountyScenicPassenger(Date date, Integer type) {
        Map<String, List<JzgydCountyScenicPassenger>> m = new HashMap<>();
        List<JzgydCountyScenicPassenger> list = new ArrayList<JzgydCountyScenicPassenger>();
        if (type == 1) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.dayCountyScenicPassenger, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayLocalData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayLocalData(((JSONObject) o), 1, Integer.parseInt(DateUtil.format(date, "yyyyMMdd")))));
                }
            }
        } else if (type == 2) {
            String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.monthCountyScenicSource, date);
//            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//                log.error("jzg采集AbJzgDayLocalData数据为空:{}", res);
//                return null;
//            }
            JSONObject jo = JSON.parseObject(res);
            if (jo.getString("success").equals("true")) {
                if (jo.getString("result") != null) {
                    JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                    ja.forEach(o -> list.add(jsonToAbJzgDayLocalData(((JSONObject) o), 2, Integer.parseInt(DateUtil.format(date, "yyyyMM")))));
                }
            }
        }
        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 小时客流数据
     *
     * @return
     */
    public static List<GetAbjzgHourLocalData> getGetAbjzgHourLocalData(Date date) {
        Map<String, List<GetAbjzgHourLocalData>> m = new HashMap<>();
        String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.GetAbjzgHourLocalData, date);
//        if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//            log.error("jzg采集AbJzgHourLocalData数据为空:{}", res);
//            return null;
//        }
        JSONObject jo = JSON.parseObject(res);
        List<GetAbjzgHourLocalData> list = new ArrayList<GetAbjzgHourLocalData>();
        if (jo.getString("success").equals("true")) {
            if (jo.getString("result") != null) {
                JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                ja.forEach(o -> list.add(jsonToAbJzgHourLocalData(((JSONObject) o))));
            }
        }
        if (list.size() < 1) {
            return null;
        }
        return list;
    }

    /**
     * 当前人流总数、游客人数和常驻人数
     *
     * @return
     */
    public static List<GetAbjzgMinuteLocalData> getGetAbjzgMinuteLocalData() {
        Map<String, List<GetAbjzgMinuteLocalData>> m = new HashMap<>();
        String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.GetAbjzgMinuteLocalData);
//        if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//            log.error("jzg采集AbJzgMinuteLocalData数据为空:{}", res);
//            return null;
//        }
        JSONObject jo = JSON.parseObject(res);
        List<GetAbjzgMinuteLocalData> list = new ArrayList<GetAbjzgMinuteLocalData>();
        if (jo.getString("success").equals("true")) {
            if (jo.getString("result") != null) {
                JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                ja.forEach(o -> list.add(jsonToAbJzgMinuteLocalData(((JSONObject) o))));
            }
        }
        if (list.size() < 1) {
            return null;
        }
        return list;
    }


    /**
     * 当前人流热力图
     *
     * @return
     */
    public static List<GetAbjzgMinutePeopleHotData> getGetAbjzgMinutePeopleHotData() {
        Map<String, List<GetAbjzgMinutePeopleHotData>> m = new HashMap<>();
        String res = YidongClient.getDataFromExternalInterfacePlatform(ANameEnum.GetAbjzgMinutePeopleHotData);
//        if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
//            log.error("jzg采集AbJzgMinutePeopleHotData数据为空:{}", res);
//            return null;
//        }
        JSONObject jo = JSON.parseObject(res);
        List<GetAbjzgMinutePeopleHotData> list = new ArrayList<GetAbjzgMinutePeopleHotData>();
        if (jo.getString("success").equals("true")) {
            if (jo.getString("result") != null) {
                JSONArray ja = JSONArray.parseArray(jo.getString("result"));
                ja.forEach(o -> list.add(jsonToAbJzgMinutePeopleHotData(((JSONObject) o))));
            }
        }
        if (list.size() < 1) {
            return null;
        }
        return list;
    }


    private static JzgydScenicConsumption jsonToAbJzgDayConsumptionData(JSONObject o, Integer type, Integer date) {
        JzgydScenicConsumption consumption = new JzgydScenicConsumption();
        consumption.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setTypeId(o.getString("TYPE_ID")).setTypeName(o.getString("TYPE_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return consumption;
    }

    private static JzgydScenicPassengerAge jsonToAbJzgDayAgeData(JSONObject o, Integer type, Integer date) {
        JzgydScenicPassengerAge age = new JzgydScenicPassengerAge();
        age.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setTypeId(o.getString("TYPE_ID")).setTypeName(o.getString("TYPE_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return age;
    }

    private static JzgydScenicPassengerGender jsonToAbJzgDayGenderData(JSONObject o, Integer type, Integer date) {
        JzgydScenicPassengerGender gender = new JzgydScenicPassengerGender();
        gender.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setTypeId(o.getString("TYPE_ID")).setTypeName(o.getString("TYPE_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return gender;
    }

    private static JzgydScenicPassengerStayTime jsonToAbJzgDayStayTimeData(JSONObject o, Integer type, Integer date) {
        JzgydScenicPassengerStayTime time = new JzgydScenicPassengerStayTime();
        time.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setStayTime(o.getString("STAY_TIME")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return time;
    }

    private static JzgydCountryPassengerSource jsonToAbJzgDaySourceCountryData(JSONObject o, Integer type, Integer date) {
        JzgydCountryPassengerSource country = new JzgydCountryPassengerSource();
        country.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setCountryId(o.getString("COUNTRY_ID")).setCountryName(o.getString("COUNTRY_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return country;
    }

    private static JzgydCityPassengerSource jsonToAbJzgDaySourceCityData(JSONObject o, Integer type, Integer date) {
        JzgydCityPassengerSource city = new JzgydCityPassengerSource();
        city.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setCityId(o.getString("CITY_ID")).setCityName(o.getString("CITY_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return city;
    }

    private static JzgydProvincePassengerSource jsonToAbJzgDaySourceProvData(JSONObject o, Integer type, Integer date) {
        JzgydProvincePassengerSource prov = new JzgydProvincePassengerSource();
        prov.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setProvId(o.getString("PROV_ID")).setProvName(o.getString("PROV_NAME"))
                .setPeopleNum(o.getInteger("PROPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return prov;
    }

    private static JzgydCountyPassengerSource jsonToAbJzgDaySourceCountyData(JSONObject o, Integer type, Integer date) {
        JzgydCountyPassengerSource county = new JzgydCountyPassengerSource();
        county.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setCountyId(o.getString("COUNTY_ID")).setCountyName(o.getString("COUNTY_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return county;
    }

    private static JzgydCountyScenicPassenger jsonToAbJzgDayLocalData(JSONObject o, Integer type, Integer date) {
        JzgydCountyScenicPassenger local = new JzgydCountyScenicPassenger();
        local.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setPeopleNum(o.getInteger("PEOPLE_NUM")).setTime(o.getString("TIME"))
                .setDateType(type).setCreateTime(LocalDateTime.now()).setDateLog(date);
        return local;
    }

    private static GetAbjzgHourLocalData jsonToAbJzgHourLocalData(JSONObject o) {
        GetAbjzgHourLocalData local = new GetAbjzgHourLocalData();
        local.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setMemberType(o.getInteger("MEMBER_TYPE")).setPeopleNum(o.getInteger("PEOPLE_NUM"))
                .setTime(o.getString("TIME")).setCreateTime(LocalDateTime.now());
        return local;
    }

    private static GetAbjzgMinuteLocalData jsonToAbJzgMinuteLocalData(JSONObject o) {
        GetAbjzgMinuteLocalData local = new GetAbjzgMinuteLocalData();
        local.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setMemberType(o.getInteger("MEMBER_TYPE")).setPeopleNum(o.getInteger("PEOPLE_NUM"))
                .setCreateTime(LocalDateTime.now());
        return local;
    }

    private static GetAbjzgMinutePeopleHotData jsonToAbJzgMinutePeopleHotData(JSONObject o) {
        GetAbjzgMinutePeopleHotData hot = new GetAbjzgMinutePeopleHotData();
        hot.setScenicId(o.getString("SCENIC_ID")).setScenicName(o.getString("SCENIC_NAME"))
                .setLng(o.getString("LNG")).setLat(o.getString("LAT")).setPeopleNum(o.getInteger("PEOPLE_NUM"))
                .setCreateTime(LocalDateTime.now());
        return hot;
    }


}
