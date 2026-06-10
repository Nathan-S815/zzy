package com.zzy.client.common;

import java.util.concurrent.TimeUnit;

public enum ANameEnum {

    /**1.1反馈阿坝九寨沟县等区域当前人流热力图*/
    GetAbjzgMinutePeopleHotData("GetAbJzgMinutePeopleHotData"),

    /**1.2反馈阿坝九寨沟县等区域当前人流总数、游客人数和常驻人数*/
    GetAbjzgMinuteLocalData("GetAbJzgMinuteLocalData"),

    /**1.3按小时指定查询时间，反馈阿坝九寨沟县等区域小时客流数据*/
    GetAbjzgHourLocalData("GetAbJzgHourLocalData"),

    /**1.4按日指定查询时间，反馈阿坝九寨沟县、景区日客流数据*/
    dayCountyScenicPassenger("GetAbJzgDayLocalData"),

    /** 1.5按天指定查询时间，反馈阿坝九寨沟县市内各区县来源地日客流数据*/
    dayCountyScenicPassengerSource("GetAbJzgDaySourceCountyData"),

    /**1.6按日指定查询时间，反馈阿坝九寨沟县国内各省来源地日客流数据*/
    dayProvincesPassengerSource("GetAbJzgDaySourceProvData"),

    /** 1.7按日指定查询时间，反馈阿坝九寨沟县国内各地市来源地日客流数据*/
    dayCityPassengerSource("GetAbJzgDaySourceCityData"),

    /** 1.8按日指定查询时间，反馈阿坝九寨沟县国际来源地日人流数据*/
    dayInternationalPassengerSource("GetAbJzgDaySourceCountryData"),

    /** 1.9按日指定查询时间，反馈阿坝九寨沟县景区日驻留时长客流数据*/
    dayScenicPassengerStayTime("GetAbJzgDayStayTimeData"),

    /** 1.10按日指定查询时间，反馈阿坝九寨沟县及景区日客流性别数据*/
    dayCountyScenicGender("GetAbJzgDayGenderData"),

    /** 1.11按日指定查询时间，反馈阿坝九寨沟县及景区日客流年龄数据 */
    dayCountyScenicAges("GetAbJzgDayAgeData"),

    /** 1.12按日指定查询时间，反馈阿坝九寨沟县日客流arpu分析数据 */
    dayCountyConsumption("GetAbJzgDayConsumptionData"),

    /** 1.16按月指定查询时间，反馈阿坝九寨沟县、景区月客流数据*/
    monthCountyScenicSource("GetAbJzgMonthLocalData"),

    /**1.17按月指定查询时间，反馈阿坝九寨沟县市内各区县来源地月客流数据*/
    monthCountyPassengerSourc("GetAbJzgMonthSourceCountyData"),

    /** 1.18按月指定查询时间，反馈阿坝九寨沟县国内各省来源地月客流数据 */
    monthProvincePassengerSource("GetAbJzgMonthSourceProvData"),

    /** 1.19按月指定查询时间，反馈阿坝九寨沟县国内各地市来源地月客流数据 */
    monthCityPassengerSource("GetAbJzgMonthSourceCityData"),

    /** 1.20按月指定查询时间，反馈阿坝九寨沟县国际来源地月客流数据 */
    monthInternationalSource("GetAbJzgMonthSourceCountryData"),

    /** 1.21按月指定查询时间，反馈阿坝九寨沟县景区月平均驻留时长客流数据*/
    monthPassengerStayTime("GetAbJzgMonthStayTimeData"),

    /** 1.22按月指定查询时间，反馈阿坝九寨沟县及景区月客流性别数据*/
    monthCountyScenicPassengerGender("GetAbJzgMonthGenderData"),

    /** 1.23按月指定查询时间，反馈阿坝九寨沟县及景区月客流年龄数据*/
    monthCountyScenicPassengerAge("GetAbJzgMonthAgeData"),

    /** 1.24 按月指定查询时间，反馈阿坝九寨沟县月客流arpu分析数据 */
    monthCountyConsumption("GetAbJzgMonthConsumptionData"),



    ;
    private String code;


    ANameEnum(String code){
        this.code = code;
    }


    public String getCode() {
        return code;
    }

}
