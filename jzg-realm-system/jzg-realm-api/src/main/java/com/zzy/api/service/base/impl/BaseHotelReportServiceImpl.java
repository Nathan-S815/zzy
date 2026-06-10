package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseHotelReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.core.utils.SortUtil;
import com.zzy.db.dao.base.*;
import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.BaseTravelLineReport;
import com.zzy.db.entity.eventdepart.EventDepartMember;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 酒店客流收入表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class BaseHotelReportServiceImpl extends ServiceImpl<BaseHotelReportMapper, BaseHotelReport> implements IBaseHotelReportService {
    @Autowired
    private BaseHotelReportMapper baseHotelReportMapper;
    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;
    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;
    ;
    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;
    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;

    @Override
    public PageInfo<Map<String, Object>> selectPageListBaseHotelReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<Map<String, Object>>(baseHotelReportMapper.selectPageListBaseHotelReport(keyword));
    }

    @Override
    public Map<String, Object> getBaseHotelReportByHotelId(Integer pagNumber, Integer pagSize, Integer id, String startTime, String endTime) {
        Integer beginNumber = (pagNumber - 1) * pagSize;
        List<Map<String, Object>> baseHotelReportByHotelIds = baseHotelReportMapper.getBaseHotelReportByHotelId(beginNumber, pagSize + 7, id, startTime, endTime);
        try {
            for (Map<String, Object> baseHotelReportByHotelId : baseHotelReportByHotelIds) {
                baseHotelReportByHotelId.put("rateByDay", NumberMathUtil.getRateDown(baseHotelReportByHotelId.get("surplusRoomNum"), baseHotelReportByHotelId.get("roomNum"), 0));
            }
            int i = 0;
            for (; i < Math.min(pagSize, baseHotelReportByHotelIds.size()); i++) {
                int num = 0;
                int rate = 0;
                Map<String, Object> baseHotelReportByHotelId = baseHotelReportByHotelIds.get(i);
                String reportTime = TimeDateUtil.getFormatDate(baseHotelReportByHotelId.get("reportTime").toString());
                for (int j = 0; j < 7; j++) {
                    if ((i + j) > baseHotelReportByHotelIds.size() - 1) {
                        break;
                    }
                    Map<String, Object> baseHotelReportByHotel = baseHotelReportByHotelIds.get(i + j);
                    TimeDateUtil.getFormatDate(baseHotelReportByHotelId.get("reportTime").toString());
                    if (TimeDateUtil.compareDate(TimeDateUtil.getFormatDate(baseHotelReportByHotelId.get("reportTime").toString()), TimeDateUtil.getDateOfThisMonday(reportTime))) {
                        break;
                    }
                    int r = Integer.parseInt(baseHotelReportByHotel.get("rateByDay").toString());
                    rate += r;
                    num++;
                }
                baseHotelReportByHotelId.put("rateByWeek", rate / num);
            }
            if (i < baseHotelReportByHotelIds.size()) {
                while (i < baseHotelReportByHotelIds.size()) {
                    baseHotelReportByHotelIds.remove(i);
                }
            }
        } catch (NullPointerException e) {
            baseHotelReportByHotelIds = null;
        }
        Wrapper<BaseHotelReport> wrapper = new QueryWrapper<BaseHotelReport>().eq("user_id", id).between("report_time", startTime, endTime);
        Integer count = baseHotelReportMapper.selectCount(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", baseHotelReportByHotelIds);
        result.put("count", count);
        result.put("pages", count % pagSize == 0 ? count / pagSize : count / pagSize + 1);
        return result;
    }

    @Override
    public Map<String, Object> getInPeopleAndIncome(String userId, String nowDate) {
        return baseHotelReportMapper.getInPeopleAndIncome(userId, nowDate);
    }

    @Override
    public Integer getTotalNumber() {
        String startTime = TimeDateUtil.getYesterdayDate();
        String endTime = TimeDateUtil.getFormatDate(new Date());
        List<BaseHotelReport> baseHotelReports = baseHotelReportMapper.getReportByTime(startTime, endTime);
        Set<String> hotel = new HashSet<>();
        Integer totelNum = 0;
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            if (hotel.add(baseHotelReport.getSubHotelName())) {
                totelNum += (Integer.parseInt(baseHotelReport.getInPeople()));
            }
        }
        return totelNum;
    }

    @Override
    public List<Map<String, Object>> getHotelOccupancy() {
        String startTime = TimeDateUtil.getYesterdayDate();
        String endTime = TimeDateUtil.getFormatDate(new Date());
        List<BaseHotelReport> baseHotelReports = baseHotelReportMapper.getReportByTime(startTime, endTime);
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> hotel = new HashSet<>();
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            if (hotel.add(baseHotelReport.getSubHotelName())) {
                Map<String, Object> map = new HashMap<>();
                map.put("HotelName", baseHotelReport.getSubHotelName());
                map.put("EmptyRoom", baseHotelReport.getSurplusRoomNum());
                int rate = 100 - Integer.parseInt(NumberMathUtil.getRateUp(baseHotelReport.getSurplusRoomNum(), Integer.parseInt(baseHotelReport.getRoomNum()), 0));
                map.put("rate", rate);
                result.add(map);
            }
        }
        return SortUtil.comparator(result, "rate", SortUtil.DESC);
    }

    @Override
    public String getHotelOccupancyByWeek() {
        String startTime = TimeDateUtil.getDateOfThisMonday();
        String endTime = TimeDateUtil.getDateOfThisSunday();
        List<BaseHotelReport> baseHotelReports = baseHotelReportMapper.getReportByTime(startTime, endTime);
        Set<String> hotel = new HashSet<>();
        Integer totelRoom = 0;
        Integer emptyRoom = 0;
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            while (TimeDateUtil.compareDate(TimeDateUtil.getFormatDate(baseHotelReport.getReportTime()), TimeDateUtil.getYesterdayDate(endTime))) {
                endTime = TimeDateUtil.getYesterdayDate(endTime);
                hotel = new HashSet<>();
            }
            if (hotel.add(baseHotelReport.getSubHotelName())) {
                totelRoom += Integer.parseInt(baseHotelReport.getRoomNum());
                emptyRoom += Integer.parseInt(baseHotelReport.getSurplusRoomNum());
            }
        }
        return NumberMathUtil.getRateUp(emptyRoom, totelRoom, 2);
    }

    @Override
    public Map<String, Object> getForecastByWeek() {
        String today = TimeDateUtil.getFormatDate(new Date());
        String startTime = TimeDateUtil.getDateOfThisMonday(TimeDateUtil.getYear(today) - 1 + "-" + TimeDateUtil.getMonth(today) + "-" + TimeDateUtil.getDate(today));
        String endTime = TimeDateUtil.getDateOfThisSunday(TimeDateUtil.getYear(today) - 1 + "-" + TimeDateUtil.getMonth(today) + "-" + TimeDateUtil.getDate(today));
        List<BaseHotelReport> baseHotelReports = baseHotelReportMapper.getReportByTime(startTime, endTime);
        Set<String> hotel = new HashSet<>();
        Integer totelRoom = 0;
        Integer emptyRoom = 0;
        Integer totlePeople = 0;
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            while (TimeDateUtil.compareDate(TimeDateUtil.getFormatDate(baseHotelReport.getReportTime()), TimeDateUtil.getYesterdayDate(endTime))) {
                endTime = TimeDateUtil.getYesterdayDate(endTime);
                hotel = new HashSet<>();
            }
            if (hotel.add(baseHotelReport.getSubHotelName())) {
                totelRoom += Integer.parseInt(baseHotelReport.getRoomNum());
                emptyRoom += Integer.parseInt(baseHotelReport.getSurplusRoomNum());
                totlePeople += Integer.parseInt(baseHotelReport.getInPeople());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rate", NumberMathUtil.getRateUp(emptyRoom, totelRoom, 2));
        result.put("totlePeople", totlePeople);
        return result;
    }

    @Override
    public List<Map<String, Object>> getOccupancyByScenic() {
        List result = new ArrayList();
        List<BaseHotelReport> baseHotelReports = getBaseHotelReports();
        Map<String, List<BaseHotelReport>> classification = classification(baseHotelReports);
        Set<Map.Entry<String, List<BaseHotelReport>>> entries = classification.entrySet();
        for (Map.Entry<String, List<BaseHotelReport>> entry : entries) {
            Map map = new HashMap();
            map.put("scenic", entry.getKey());
            List<BaseHotelReport> value = entry.getValue();
            int allRoomNum = 0;
            int allEmptyNum = 0;
            List h = new ArrayList();
            Set set = new HashSet();
            for (BaseHotelReport baseHotelReport : value) {
                if (set.add(baseHotelReport.getSubHotelName())) {
                    Map hotel = new HashMap();
                    Integer roomNum = Integer.parseInt(baseHotelReport.getRoomNum());
                    allRoomNum += roomNum;
                    Integer emptyNum = Integer.parseInt(baseHotelReport.getSurplusRoomNum());
                    allEmptyNum += emptyNum;
                    int rate = 100 - Integer.parseInt(NumberMathUtil.getRateUp(emptyNum, roomNum, 0));
                    hotel.put("hotelName", baseHotelReport.getSubHotelName());
                    hotel.put("value", rate);
                    h.add(hotel);
                }
            }
            Collections.sort(h, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("value").toString());
                    Integer name2 = Integer.valueOf(o2.get("value").toString());
                    return name2.compareTo(name1);
                }
            });
            map.put("hotel", h);
            int rate = 100 - Integer.parseInt(NumberMathUtil.getRateUp(allEmptyNum, allRoomNum, 0));
            map.put("rate", rate);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getEmptyRoomByScenic() {
        List result = new ArrayList();
        List<BaseHotelReport> baseHotelReports = getBaseHotelReports();
        Map<String, List<BaseHotelReport>> classification = classification(baseHotelReports);
        Set<Map.Entry<String, List<BaseHotelReport>>> entries = classification.entrySet();
        for (Map.Entry<String, List<BaseHotelReport>> entry : entries) {
            Map map = new HashMap();
            map.put("scenic", entry.getKey());
            List<BaseHotelReport> value = entry.getValue();
            int allEmptyNum = 0;
            List h = new ArrayList();
            Set set = new HashSet();
            for (BaseHotelReport baseHotelReport : value) {
                if (set.add(baseHotelReport.getSubHotelName())) {
                    Map hotel = new HashMap();
                    Integer emptyNum = Integer.parseInt(baseHotelReport.getSurplusRoomNum());
                    allEmptyNum += emptyNum;
                    hotel.put("hotelName", baseHotelReport.getSubHotelName());
                    hotel.put("value", emptyNum);
                    hotel.put("roomNum", baseHotelReport.getRoomNum());
                    hotel.put("rate", NumberMathUtil.getRateDown(emptyNum, baseHotelReport.getRoomNum(), 0));
                    h.add(hotel);
                }
            }
            Collections.sort(h, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("value").toString());
                    Integer name2 = Integer.valueOf(o2.get("value").toString());
                    return name1.compareTo(name2);
                }
            });
            map.put("hotel", h);
            map.put("emptyNum", allEmptyNum);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getTotelPeopleByScenic() {
        List result = new ArrayList();
        List<BaseHotelReport> baseHotelReports = getBaseHotelReports();
        Map<String, List<BaseHotelReport>> classification = classification(baseHotelReports);
        Set<Map.Entry<String, List<BaseHotelReport>>> entries = classification.entrySet();
        for (Map.Entry<String, List<BaseHotelReport>> entry : entries) {
            Map map = new HashMap();
            map.put("scenic", entry.getKey());
            List<BaseHotelReport> value = entry.getValue();
            int allInPeople = 0;
            List h = new ArrayList();
            Set set = new HashSet();
            for (BaseHotelReport baseHotelReport : value) {
                if (set.add(baseHotelReport.getSubHotelName())) {
                    Map hotel = new HashMap();
                    Integer inPeople = Integer.parseInt(baseHotelReport.getInPeople());
                    allInPeople += inPeople;
                    hotel.put("hotelName", baseHotelReport.getSubHotelName());
                    hotel.put("value", inPeople);
                    h.add(hotel);
                }
            }
            Collections.sort(h, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer name1 = Integer.valueOf(o1.get("value").toString());
                    Integer name2 = Integer.valueOf(o2.get("value").toString());
                    return name2.compareTo(name1);
                }
            });
            map.put("hotel", h);
            map.put("allInPeople", allInPeople);
            result.add(map);
        }
        return result;
    }

    @Override
    public Map<String, Object> getTouristStay(String startTime, String endTime) {
        Map map = new HashMap();
        Map in = new HashMap();
        Map out = new HashMap();
        int in1 = 0;
        int in2 = 0;
        int in3 = 0;
        int in4 = 0;
        int in5 = 0;
        int in6 = 0;
        int out1 = 0;
        int out2 = 0;
        int out3 = 0;
        int out4 = 0;
        int out5 = 0;
        int out6 = 0;
        List<BaseHotelReport> baseHotelReports = baseHotelReportMapper.getReportByTimeWithScenic(startTime, endTime);
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            in1 += baseHotelReport.getStayOneIn();
            in2 += baseHotelReport.getStayTwoIn();
            in3 += baseHotelReport.getStayThreeIn();
            in4 += baseHotelReport.getStayFourIn();
            in5 += baseHotelReport.getStayFiveIn();
            in6 += baseHotelReport.getStaySixIn();
            out1 += baseHotelReport.getStayOneOut();
            out2 += baseHotelReport.getStayTwoOut();
            out3 += baseHotelReport.getStayThreeOut();
            out4 += baseHotelReport.getStayFourOut();
            out5 += baseHotelReport.getStayFiveOut();
            out6 += baseHotelReport.getStaySixOut();
        }
        in.put("1天", in1);
        in.put("2天", in2);
        in.put("3天", in3);
        in.put("4天", in4);
        in.put("5天", in5);
        in.put("6天", in6);
        in.put("省内平均天数", Math.round(1.0 * (in1 + 2 * in2 + 3 * in3 + 4 * in4 + 5 * in5 + 6 * in6) / (in1 + in2 + in3 + in4 + in5 + in6)));
        out.put("1天", out1);
        out.put("2天", out2);
        out.put("3天", out3);
        out.put("4天", out4);
        out.put("5天", out5);
        out.put("6天", out6);
        out.put("省外平均天数", Math.round(1.0 * (out1 + 2 * out2 + 3 * out3 + 4 * out4 + 5 * out5 + 6 * out6) / (out1 + out2 + out3 + out4 + out5 + out6)));
        map.put("省内", in);
        map.put("省外", out);
        return map;
    }

    @Override
    public List<Map<String, Object>> getTouristStayByScenic(String startTime, String endTime) {
        return baseHotelReportMapper.getTouristStayByScenic(startTime, endTime);
    }

    @Override
    public Map<String, List<Map<String, String>>> getTouristQuality(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("酒店", baseHotelReportMapper.getTouristQualityByScenic(startTime, endTime));
        map.put("娱乐", baseRecreationReportMapper.getTouristQualityByScenic(startTime, endTime));
        map.put("餐饮", baseRestaurantReportMapper.getTouristQualityByScenic(startTime, endTime));
        map.put("购物", baseShoppingReportMapper.getTouristQualityByScenic(startTime, endTime));
        map.put("交通", baseTrafficReportMapper.getTouristQualityByScenic(startTime, endTime));
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Map<String, List<Map<String, String>>> resule = new HashMap<>();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            List<Map<String, String>> value = (List<Map<String, String>>) entry.getValue();
            for (Map<String, String> map1 : value) {
                String scenicName = map1.get("scenicName");
                if (resule.get(scenicName) == null) {
                    List<Map<String, String>> list = new ArrayList<>();
                    map1.put("type",key);
                    map1.remove("scenicName");
                    list.add(map1);
                    resule.put(scenicName, list);
                } else {
                    List<Map<String, String>> list = resule.get(scenicName);
                    map1.put("type",key);
                    map1.remove("scenicName");
                    list.add(map1);
                }
            }
        }
        return resule;
    }

    private List<BaseHotelReport> getBaseHotelReports() {
        String startTime = TimeDateUtil.getYesterdayDate();
        String endTime = TimeDateUtil.getFormatDate(new Date());
        return baseHotelReportMapper.getReportByTimeWithScenic(startTime, endTime);
    }

    private Map<String, List<BaseHotelReport>> classification(List<BaseHotelReport> baseHotelReports) {
        List<BaseHotelReport> jiuzhaigou = new ArrayList<>();
//        List<BaseHotelReport> guzangzhai = new ArrayList<>();
        List<BaseHotelReport> shenxianchi = new ArrayList<>();
//        List<BaseHotelReport> jiawuchi = new ArrayList<>();
        List<BaseHotelReport> daxiongmao = new ArrayList<>();
        List<BaseHotelReport> ganhaizi = new ArrayList<>();
        for (BaseHotelReport baseHotelReport : baseHotelReports) {
            if (baseHotelReport.getSubScenicId().contains("九寨")) {
                jiuzhaigou.add(baseHotelReport);
//            }else if (baseHotelReport.getSubScenicId().contains("古藏")) {
//                guzangzhai.add(baseHotelReport);
            }else if (baseHotelReport.getSubScenicId().contains("嫩恩桑措")) {
                shenxianchi.add(baseHotelReport);
            } else if (baseHotelReport.getSubScenicId().contains("神仙池")) {
                shenxianchi.add(baseHotelReport);
//            } else if (baseHotelReport.getSubScenicId().contains("甲勿海大熊猫")) {
//                jiawuchi.add(baseHotelReport);
            } else if (baseHotelReport.getSubScenicId().contains("熊")) {
                daxiongmao.add(baseHotelReport);
            } else if (baseHotelReport.getSubScenicId().contains("爱情海")) {
                ganhaizi.add(baseHotelReport);
            } else if (baseHotelReport.getSubScenicId().contains("甘海子")) {
                ganhaizi.add(baseHotelReport);
            }
        }
        Map map = new HashMap();
        map.put("九寨沟", jiuzhaigou);
//        map.put("古藏寨", guzangzhai);
        map.put("嫩恩桑措(神仙池)", shenxianchi);
//        map.put("甲勿海", jiawuchi);
        map.put("甲勿海大熊猫保护研究园", daxiongmao);
        map.put("爱情海(甘海子)", ganhaizi);
        return map;
    }
}
