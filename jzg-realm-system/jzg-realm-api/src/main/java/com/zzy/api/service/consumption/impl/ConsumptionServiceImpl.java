package com.zzy.api.service.consumption.impl;

import com.zzy.api.service.consumption.ConsumptionService;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.*;
import com.zzy.db.entity.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    @Autowired
    private BaseHotelReportMapper baseHotelReportMapper;

    @Autowired
    private BaseRestaurantReportMapper baseRestaurantReportMapper;

    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;

    @Autowired
    private BaseRecreationReportMapper baseRecreationReportMapper;

    @Autowired
    private BaseTrafficReportMapper baseTrafficReportMapper;


    @Override
    public List<Map<String, Object>> getConsumption(String startTime, String endTime) {
        int hotelMoney = 0;
        List<BaseHotelReport> baseHotelReport = baseHotelReportMapper.getReportByTime(startTime, endTime);
        for (BaseHotelReport hotelReport : baseHotelReport) {
            hotelMoney += Integer.parseInt(hotelReport.getIncome().toString());
        }
        int restaurantMoney = 0;
        List<BaseRestaurantReport> baseRestaurantReport = baseRestaurantReportMapper.getReportByTime(startTime, endTime);
        for (BaseRestaurantReport restaurantReport : baseRestaurantReport) {
            restaurantMoney += Integer.parseInt(restaurantReport.getIncome().toString());
        }
        int shoppingMoney = 0;
        List<BaseShoppingReport> baseShoppingReport = baseShoppingReportMapper.getReportByTime(startTime, endTime);
        for (BaseShoppingReport shoppingReport : baseShoppingReport) {
            shoppingMoney += Integer.parseInt(shoppingReport.getIncome().toString());
        }
        int recreationMoney = 0;
        List<BaseRecreationReport> baseRecreationReport = baseRecreationReportMapper.getReportByTime(startTime, endTime);
        for (BaseRecreationReport recreationReport : baseRecreationReport) {
            recreationMoney += Integer.parseInt(recreationReport.getIncome().toString());
        }
        int trafficMoney = 0;
        List<BaseTrafficReport> baseTrafficReport = baseTrafficReportMapper.getReportByTime(startTime, endTime);
        for (BaseTrafficReport trafficReport : baseTrafficReport) {
            trafficMoney += Integer.parseInt(trafficReport.getIncome().toString());
        }
        int money = hotelMoney + restaurantMoney + shoppingMoney + recreationMoney + trafficMoney;
        Map<String, Object> hotel = new HashMap<>();
        hotel.put("type", "住宿消费");
        hotel.put("money", hotelMoney);
        hotel.put("rate", NumberMathUtil.getRate(hotelMoney, money, 0));
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put("type", "餐饮消费");
        restaurant.put("money", restaurantMoney);
        restaurant.put("rate", NumberMathUtil.getRate(restaurantMoney, money, 0));
        Map<String, Object> shopping = new HashMap<>();
        shopping.put("type", "购物消费");
        shopping.put("money", shoppingMoney);
        shopping.put("rate", NumberMathUtil.getRate(shoppingMoney, money, 0));
        Map<String, Object> recreation = new HashMap<>();
        recreation.put("type", "娱乐消费");
        recreation.put("money", recreationMoney);
        recreation.put("rate", NumberMathUtil.getRate(recreationMoney, money, 0));
        Map<String, Object> traffic = new HashMap<>();
        traffic.put("type", "交通消费");
        traffic.put("money", trafficMoney);
        traffic.put("rate", 100 - Integer.parseInt(hotel.get("rate").toString()) - Integer.parseInt(restaurant.get("rate").toString()) - Integer.parseInt(shopping.get("rate").toString()) - Integer.parseInt(recreation.get("rate").toString()));
        List list = new ArrayList();
        list.add(hotel);
        list.add(restaurant);
        list.add(shopping);
        list.add(recreation);
        list.add(traffic);
        return list;
    }

    @Override
    public List<Map<String, Object>> getConsumptionPerPerson(String startTime, String endTime) {
        int hotelMoney = 0;
        int hotelPeople = 0;
        List<BaseHotelReport> baseHotelReport = baseHotelReportMapper.getReportByTime(startTime, endTime);
        for (BaseHotelReport hotelReport : baseHotelReport) {
            hotelMoney += Integer.parseInt(hotelReport.getIncome());
            hotelPeople += Integer.parseInt(hotelReport.getInPeople());
        }
        int hotelPer = hotelPeople == 0 ? 0 : hotelMoney / hotelPeople;
        int restaurantMoney = 0;
        int restaurantPeople = 0;
        List<BaseRestaurantReport> baseRestaurantReport = baseRestaurantReportMapper.getReportByTime(startTime, endTime);
        for (BaseRestaurantReport restaurantReport : baseRestaurantReport) {
            restaurantMoney += Integer.parseInt(restaurantReport.getIncome());
            restaurantPeople += Integer.parseInt(restaurantReport.getInPeople());
        }
        int restaurantPer = restaurantPeople == 0 ? 0 : restaurantMoney / restaurantPeople;
        int shoppingMoney = 0;
        int shoppingPeople = 0;
        List<BaseShoppingReport> baseShoppingReport = baseShoppingReportMapper.getReportByTime(startTime, endTime);
        for (BaseShoppingReport shoppingReport : baseShoppingReport) {
            shoppingMoney += Integer.parseInt(shoppingReport.getIncome());
            shoppingPeople += Integer.parseInt(shoppingReport.getInPeople());
        }
        int shoppingPer = shoppingPeople == 0 ? 0 : shoppingMoney / shoppingPeople;
        int recreationMoney = 0;
        int recreationPeople = 0;
        List<BaseRecreationReport> baseRecreationReport = baseRecreationReportMapper.getReportByTime(startTime, endTime);
        for (BaseRecreationReport recreationReport : baseRecreationReport) {
            recreationMoney += Integer.parseInt(recreationReport.getIncome());
            recreationPeople += Integer.parseInt(recreationReport.getInPeople());
        }
        int recreationPer = recreationPeople == 0 ? 0 : recreationMoney / recreationPeople;
        int trafficMoney = 0;
        int trafficPeople = 0;
        List<BaseTrafficReport> baseTrafficReport = baseTrafficReportMapper.getReportByTime(startTime, endTime);
        for (BaseTrafficReport trafficReport : baseTrafficReport) {
            trafficMoney += Integer.parseInt(trafficReport.getIncome());
            trafficPeople += Integer.parseInt(trafficReport.getInPeople());
        }
        int trafficPer = trafficPeople == 0 ? 0 : trafficMoney / trafficPeople;
        int money = hotelPer + restaurantPer + shoppingPer + recreationPer + trafficPer;
        Map<String, Object> hotel = new HashMap<>();
        hotel.put("type", "住宿消费");
        hotel.put("money", hotelPer);
        hotel.put("rate", NumberMathUtil.getRate(hotelPer, money, 0));
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put("type", "餐饮消费");
        restaurant.put("money", restaurantPer);
        restaurant.put("rate", NumberMathUtil.getRate(restaurantPer, money, 0));
        Map<String, Object> shopping = new HashMap<>();
        shopping.put("type", "购物消费");
        shopping.put("money", shoppingPer);
        shopping.put("rate", NumberMathUtil.getRate(shoppingPer, money, 0));
        Map<String, Object> recreation = new HashMap<>();
        recreation.put("type", "娱乐消费");
        recreation.put("money", recreationPer);
        recreation.put("rate", NumberMathUtil.getRate(recreationPer, money, 0));
        Map<String, Object> traffic = new HashMap<>();
        traffic.put("type", "交通消费");
        traffic.put("money", trafficPer);
        traffic.put("rate", 100 - Integer.parseInt(hotel.get("rate").toString()) - Integer.parseInt(restaurant.get("rate").toString()) - Integer.parseInt(shopping.get("rate").toString()) - Integer.parseInt(recreation.get("rate").toString()));
        List list = new ArrayList();
        list.add(hotel);
        list.add(restaurant);
        list.add(shopping);
        list.add(recreation);
        list.add(traffic);
        return list;
    }

    @Override
    public List<Map<String, Object>> getConsumptionForecast() {
        String startTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date())) - 1) + "-01-01";
        String endTime = (TimeDateUtil.getYear(TimeDateUtil.getFormatDate(new Date())) - 1) + "-12-31";
        int hotelMoney = 0;
        List<BaseHotelReport> baseHotelReport = baseHotelReportMapper.getReportByTime(startTime, endTime);
        for (BaseHotelReport hotelReport : baseHotelReport) {
            hotelMoney += Integer.parseInt(hotelReport.getIncome().toString());
        }
        int restaurantMoney = 0;
        List<BaseRestaurantReport> baseRestaurantReport = baseRestaurantReportMapper.getReportByTime(startTime, endTime);
        for (BaseRestaurantReport restaurantReport : baseRestaurantReport) {
            restaurantMoney += Integer.parseInt(restaurantReport.getIncome().toString());
        }
        int shoppingMoney = 0;
        List<BaseShoppingReport> baseShoppingReport = baseShoppingReportMapper.getReportByTime(startTime, endTime);
        for (BaseShoppingReport shoppingReport : baseShoppingReport) {
            shoppingMoney += Integer.parseInt(shoppingReport.getIncome().toString());
        }
        int recreationMoney = 0;
        List<BaseRecreationReport> baseRecreationReport = baseRecreationReportMapper.getReportByTime(startTime, endTime);
        for (BaseRecreationReport recreationReport : baseRecreationReport) {
            recreationMoney += Integer.parseInt(recreationReport.getIncome().toString());
        }
        int trafficMoney = 0;
        List<BaseTrafficReport> baseTrafficReport = baseTrafficReportMapper.getReportByTime(startTime, endTime);
        for (BaseTrafficReport trafficReport : baseTrafficReport) {
            trafficMoney += Integer.parseInt(trafficReport.getIncome().toString());
        }
        Map<String, Object> hotel = new HashMap<>();
        hotel.put("type", "住宿消费");
        hotel.put("money", hotelMoney);
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put("type", "餐饮消费");
        restaurant.put("money", restaurantMoney);
        Map<String, Object> shopping = new HashMap<>();
        shopping.put("type", "购物消费");
        shopping.put("money", shoppingMoney);
        Map<String, Object> recreation = new HashMap<>();
        recreation.put("type", "娱乐消费");
        recreation.put("money", recreationMoney);
        Map<String, Object> traffic = new HashMap<>();
        traffic.put("type", "交通消费");
        traffic.put("money", trafficMoney);
        List list = new ArrayList();
        list.add(hotel);
        list.add(restaurant);
        list.add(shopping);
        list.add(recreation);
        list.add(traffic);
        return list;
    }
}
