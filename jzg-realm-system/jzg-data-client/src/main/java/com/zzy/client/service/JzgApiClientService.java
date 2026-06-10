package com.zzy.client.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.zzy.client.api.JzgApiClient;
import com.zzy.client.api.YidongClient;
import com.zzy.client.common.ANameEnum;
import com.zzy.client.common.JzgApiConstant;
import com.zzy.core.utils.ScenicNameUtil;
import com.zzy.db.entity.carpark.*;
import com.zzy.db.entity.hotmap.GetAbjzgHourLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinuteLocalData;
import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.zzy.db.entity.ticket.BookingTicketInformation;
import com.zzy.db.entity.ticket.FutureTicketInformation;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import com.zzy.db.entity.yidong.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
public class JzgApiClientService {


    public static final String json_total_page_key = "totalPage";
    static ParserConfig pc = ParserConfig.getGlobalInstance();

    static {
        pc.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
    }

    public static void main(String[] args) {
        Object o = null;
        List t = null;
        String str = null;
        Map<String, List<GetRemainingSpaceH>> p = null;

        int n = 1, s = 10;
//        p = getRemainingSpaceHData(n,s);

//        o = getRemainingSpaceData(n,s);
//        o = getEnterCarData(n,s);
//        o = getOutCarData(n,s);
        o = getParkInfoData(n, s);
//        o = getBookingTicketInformationData(n,s);
//        o = getFutureTicketInformationData(n,s);
//        o = getScenicEnterPeopleData(n,s);
//        o = getCarAlarmData(n,s);
//        o = getCarDriverData(n,s);
//        o = getCarInfoData(n,s);
//        o = getCarGpsData(n, s);
//        o = getAbJzgMinutePeopleHotData();
//        o = GetAbJzgMinuteLocalData();
//        o = GetAbJzgHourLocalData(new Date());
//        o = GetAbJzgDayLocalData(new Date(),1);
//        o = GetAbJzgDayLocalData(new Date(),2);
//        o = GetAbJzgDaySourceCountyData(new Date(),1);
//        o = GetAbJzgDaySourceCountyData(new Date(),2);
//        o = GetAbJzgDaySourceProvData(new Date(),1);
//        o = GetAbJzgDaySourceProvData(new Date(),2);
//        o = GetAbJzgDaySourceCityData(new Date(),1);
//        o = GetAbJzgDaySourceCityData(new Date(),2);
//        o = GetAbJzgDaySourceCountryData(new Date(),1);
//        o = GetAbJzgDaySourceCountryData(new Date(),2);
//        o = GetAbJzgDayStayTimeData(new Date(),1);
//        o = GetAbJzgDayStayTimeData(new Date(),2);
//        o = GetAbJzgDayGenderData(new Date(),1);
//        o = GetAbJzgDayGenderData(new Date(),2);
//        o = GetAbJzgDayAgeData(new Date(),1);
//        o = GetAbJzgDayAgeData(new Date(),2);
//        o = GetAbJzgDayConsumptionData(new Date(),1);
//        o = GetAbJzgDayConsumptionData(new Date(),2);
//        t = p.get(p.keySet().iterator().next());
//        System.out.println("1:"+JSON.toJSONString(t.get(0)));
//        System.out.println("last:"+JSON.toJSONString(t.get(t.size()-1)));
//        System.out.println(JSON.toJSONString(t));

        System.out.println(JSON.toJSONString(o));
    }


    /**
     * 驾驶员信息表
     *
     * @return
     */
    public static Map<String, List<CarDriver>> getCarDriverData(Integer pageNo, Integer pageSize) {
        Map<String, List<CarDriver>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.driver);
        try {
            if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
                log.error("jzg采集CarDriver-api数据为空:{}", res);
                return null;
            }

            JSONObject jo = JSON.parseObject(res);
            List<CarDriver> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToCarDriver(((JSONObject) o))));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<CarDriver>>();
        }

    }


    /**
     * 车辆gps信息
     *
     * @return
     */
    public static Map<String, List<CarGps>> getCarGpsData(Integer pageNo, Integer pageSize) {
        Map<String, List<CarGps>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.car_gps);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集CarGps-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<CarGps> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    JSONObject to = null;
                    CarGps cg = null;
                    for (Object o : ja) {
                        to = ((JSONObject) o);
                        cg = to.fluentRemove("create_time").toJavaObject(CarGps.class, pc, 1);
                        cg.setApiId(((JSONObject) o).getInteger("id")).setCreateTime(LocalDateTime.now());
                        cg.setLat(String.valueOf(Double.valueOf(cg.getLat()) * 0.000001));
                        cg.setLon(String.valueOf(Double.valueOf(cg.getLon()) * 0.000001));
                        list.add(cg);
                    }
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<CarGps>>();
        }
    }


    /**
     * 车辆报警信息
     *
     * @return
     */
    public static Map<String, List<CarAlarm>> getCarAlarmData(Integer pageNo, Integer pageSize) {
        Map<String, List<CarAlarm>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.car_alarm);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集CarAlarm-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<CarAlarm> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(((JSONObject) o).fluentRemove("create_time")
                            .fluentPut("warn_type_time", String.valueOf(((JSONObject) o).getLongValue("warn_type_time") * 1000))
                            .toJavaObject(CarAlarm.class, pc, 1).setApiId(((JSONObject) o).getInteger("id")).setCreateTime(LocalDateTime.now())));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<CarAlarm>>();
        }
    }


    /**
     * 车辆静态信息
     *
     * @return
     */
    public static Map<String, List<CarInfo>> getCarInfoData(Integer pageNo, Integer pageSize) {
        Map<String, List<CarInfo>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.car_info);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集CarInfo-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<CarInfo> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(((JSONObject) o).fluentRemove("create_time").toJavaObject(CarInfo.class, pc, 1).setApiId(((JSONObject) o).getInteger("id")).setCreateTime(LocalDateTime.now())));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<CarInfo>>();
        }
    }


    /**
     * 景区当日入园人数表
     *
     * @return
     */
    public static Map<String, List<ScenicEnterPeople>> getScenicEnterPeopleData(Integer pageNo, Integer pageSize) {
        Map<String, List<ScenicEnterPeople>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.scenic_enter_people);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集ScenicEnterPeople-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<ScenicEnterPeople> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToScenicEnterPeople(((JSONObject) o))));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<ScenicEnterPeople>>();
        }
    }


    /**
     * 景区未来n日余票信息表
     *
     * @return
     */
    public static Map<String, List<FutureTicketInformation>> getFutureTicketInformationData(Integer pageNo, Integer pageSize) {
        Map<String, List<FutureTicketInformation>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.future_ticket_information);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集FutureTicketInformation-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<FutureTicketInformation> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToFutureTicketInformation(((JSONObject) o))));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<FutureTicketInformation>>();
        }
    }


    /**
     * 景区当日团体/个人订票信息
     *
     * @return
     */
    public static Map<String, List<BookingTicketInformation>> getBookingTicketInformationData(Integer pageNo, Integer pageSize) {
        Map<String, List<BookingTicketInformation>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.booking_ticket_information);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集BookingTicketInformation-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<BookingTicketInformation> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToBookingTicketInformation(((JSONObject) o))));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<BookingTicketInformation>>();
        }
    }


    /**
     * 停⻋场剩余⻋位(历史)
     *
     * @return
     */
    public static Map<String, List<GetRemainingSpaceH>> getRemainingSpaceHData(Integer pageNo, Integer pageSize) {
        Map<String, List<GetRemainingSpaceH>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.get_remaining_space_h);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集GetRemainingSpaceH-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<GetRemainingSpaceH> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToRemainingSpaceH(((JSONObject) o))));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<GetRemainingSpaceH>>();
        }
    }


    public static Map<String, List<GetRemainingSpace>> getRemainingSpaceData(Integer pageNo, Integer pageSize) {
        Map<String, List<GetRemainingSpace>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.get_remaining_space);
//        System.out.println(res);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集GetRemainingSpace-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<GetRemainingSpace> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToRemainingSpace((JSONObject) o)));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<GetRemainingSpace>>();
        }
    }


    public static Map<String, List<GetEnterCar>> getEnterCarData(Integer pageNo, Integer pageSize) {
        Map<String, List<GetEnterCar>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.get_enter_car);
//        System.out.println(res);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集GetEnterCar-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<GetEnterCar> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToGetEnterCar((JSONObject) o)));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<GetEnterCar>>();
        }
    }


    public static Map<String, List<GetOutCar>> getOutCarData(Integer pageNo, Integer pageSize) {
        Map<String, List<GetOutCar>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.get_out_car);
//        System.out.println(res);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集GetOutCar-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<GetOutCar> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToGetOutCar((JSONObject) o)));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<GetOutCar>>();
        }
    }


    public static Map<String, List<GetParkInfo>> getParkInfoData(Integer pageNo, Integer pageSize) {
        Map<String, List<GetParkInfo>> m = new HashMap<>();
        String res = JzgApiClient.fetchData(pageNo, pageSize, JzgApiConstant.ServiceCodeEnum.get_park_info);
//        System.out.println(res);
        try {if (StrUtil.isBlankOrUndefined(JSON.parseObject(res).getString("data"))) {
            log.error("jzg采集GetParkInfo-api数据为空:{}", res);
            return null;
        }

            JSONObject jo = JSON.parseObject(res);
            List<GetParkInfo> list = new ArrayList<>();
            if (jo.getString("retMsg").equals("SUCCESS")) {
                jo = jo.getJSONObject("data").getJSONObject("result");
                if (jo != null) {
                    JSONArray ja = jo.getJSONArray("items").getJSONArray(0);
                    ja.forEach(o -> list.add(jsonToGetParkInfo((JSONObject) o)));
                }
            }
            if (list.size() < 1) {
                return null;
            }
            m.put(jo.getString(json_total_page_key), list);
            return m;
        } catch (Exception e) {
            return new HashMap<String, List<GetParkInfo>>();
        }
    }

    private static CarDriver jsonToCarDriver(JSONObject o) {
        CarDriver car = new CarDriver();
        car.setApiId(o.getInteger("id")).setCreateTime(LocalDateTime.now())
                .setDriverName(o.getString("driver_name"))
                .setTel(o.getString("tel")).setVehicleCode(o.getString("vehicle_code"));
        return car;
    }


    private static ScenicEnterPeople jsonToScenicEnterPeople(JSONObject o) {
        ScenicEnterPeople se = new ScenicEnterPeople();
        se.setApiId(o.getInteger("id"))
                .setCreateTime(LocalDateTime.now())
                .setEnterNumber(o.getLong("enter_number"))
                .setName(o.getString("name"))
                .setRecordDate(DateUtil.parseLocalDateTime(o.getString("record_date"), "yyyy-MM-dd"))
                .setRestNumber(o.getLong("rest_number"))
        ;
        return se;
    }

    private static FutureTicketInformation jsonToFutureTicketInformation(JSONObject o) {
        FutureTicketInformation fi = new FutureTicketInformation();
        fi.setApiId(o.getInteger("id"))
                .setCreateTime(LocalDateTime.now())
                .setName(o.getString("name"))
                .setRecordDate(DateUtil.parseLocalDateTime(o.getString("record_date"), "yyyy-MM-dd"))
                .setRestNumber(o.getLong("rest_number"))
        ;
        return fi;
    }

    private static BookingTicketInformation jsonToBookingTicketInformation(JSONObject o) {
        BookingTicketInformation ti = new BookingTicketInformation();
        ti.setApiId(o.getInteger("id"))
                .setCreateTime(LocalDateTime.now())
                .setName(o.getString("name"))
                .setPersonalNumber(o.getLong("personal_number"))
                .setTeamNumber(o.getLong("team_number"))
                .setRecordDate(DateUtil.parseLocalDateTime(o.getString("record_date"), "yyyy-MM-dd"))
        ;
        return ti;
    }


    private static GetEnterCar jsonToGetEnterCar(JSONObject o) {
        GetEnterCar ec = new GetEnterCar();
        ec.setCarNo(o.getString("carno")).setCarTypeNo(o.getString("cartypeno"))
                .setEnterGateName(o.getString("entergatename")).setEnterImgPath(o.getString("enterimgpath"))
                .setEnterOperatorName(o.getString("enteroperatorname")).setEnterTime(o.getString("entertime"))
                .setIndexId(o.getInteger("indexid")).setLockCar(o.getString("lockcar"))
                .setOrderNo(o.getString("orderno")).setParkKey(o.getString("parkkey")).setCreateTime(new Date());
        return ec;
    }

    private static GetParkInfo jsonToGetParkInfo(JSONObject o) {
        GetParkInfo gp = new GetParkInfo();
        gp.setRegTime(o.getString("regtime")).setReserveStatus(o.getString("reservestatus"))
                .setSpaceTotal(o.getString("spacetotal")).setParkTel(o.getString("parktel"))
                .setChargeSdesc(o.getString("chargesdesc")).setParkName(o.getString("parkname"))
                .setValidTime(o.getString("validtime")).setParkLatitude(o.getString("parklatitude"))
                .setCarTypeChargRules(o.getString("cartypechargrules")).setParkKey(o.getString("parkkey"))
                .setParkAdd(o.getString("parkadd")).setParkFreetime(o.getString("parkfreetime"))
                .setParkLinkman(o.getString("parklinkman")).setParkFreetimeOut(o.getString("parkfreetimeout"))
                .setCityShortName(o.getString("cityshortname")).setIndexId(o.getInteger("indexid"))
                .setParkLongitude(o.getString("parklongitude")).setParkqrcodeUrl(o.getString("parkqrcodeurl"));
        gp.setScenicName(ScenicNameUtil.setScenicName(gp.getParkName())).setCreateTime(new Date());
        return gp;
    }


    private static GetOutCar jsonToGetOutCar(JSONObject o) {
        GetOutCar go = new GetOutCar();
        go.setOutGateName(o.getString("outgatename")).setOrderNo(o.getString("orderno"))
                .setOutTime(o.getString("outtime")).setEnterTime(DateUtil.parse(o.getString("enter_time"), "dd/MM/yyyy HH:mm:ss").toDateStr())
                .setParkKey(o.getString("parkkey")).setOutImgPath(o.getString("outimgpath"))
                .setCarTypeNo(o.getString("cartypeno")).setOutOperatorName(o.getString("outoperatorname"))
                .setTotalAmount(o.getString("totalamount")).setCarNo(o.getString("carno"))
                .setIndexId(o.getInteger("indexid")).setLockCar(o.getString("lockcar"))
                .setFreeReason(o.getString("freereason")).setCreateTime(new Date());
        return go;
    }


    private static GetRemainingSpaceH jsonToRemainingSpaceH(JSONObject o) {
        GetRemainingSpaceH gh = new GetRemainingSpaceH();
        gh.setGetTime(DateUtil.toLocalDateTime(o.getDate("get_time")));
        gh.setParkKey(o.getString("parkkey")).setRemaiSpaces(o.getString("remaispaces"))
                .setTotalSpaces(o.getString("totalspaces")).setCreateTime(new Date())
        ;
        return gh;
    }


    private static GetRemainingSpace jsonToRemainingSpace(JSONObject o) {
        GetRemainingSpace gh = new GetRemainingSpace();
        gh.setParkKey(o.getString("parkkey")).setRemaiSpaces(o.getString("remaispaces"))
                .setTotalSpaces(o.getString("totalspaces")).setCreateTime(new Date())
        ;
        return gh;
    }


}
