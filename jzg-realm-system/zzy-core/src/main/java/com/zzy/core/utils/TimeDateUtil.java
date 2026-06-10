package com.zzy.core.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.mysql.cj.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class TimeDateUtil {



    /**
     * 时间截取转int
     *
     * @param dateTime
     * @param format:  yyyy-MM转yyyyMM,yyyy-MM-dd转yyyyMMdd
     * @return
     */
    public static int dateStrToInt(String dateTime, String format) {
        if (dateTime.length() == 7) {
            return Integer.parseInt(dateTime.replace("-", "").substring(0, 6));
        }
        if (dateTime.length() == 4) {
            return Integer.parseInt(dateTime.replace("-", ""));
        }
        return Integer.parseInt(DateUtil.format(TypeUtils.castToDate(dateTime), format));
    }

    /**
     * 字符串时间转date
     *
     * @param str
     * @return
     */
    public static Date strToDate(String str) {
        return TypeUtils.castToDate(str);
    }

    /**
     * 获取今年1月1日
     *
     * @param str yyyy-MM-dd或yyyyMMdd
     * @return yyyy-MM-dd或yyyyMMdd
     */
    public static String getFirstOfYear(String str) {
        if (str.length() == 8) {
            return str.substring(0, 4) + "0101";
        } else if (str.length() == 10) {
            return str.substring(0, 4) + "-01-01";
        } else return "";
    }

    /**
     * 获取本月1日
     *
     * @param str yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getFirstOfMonth(String str) {
        if (str.length() == 8) {
            return str.substring(0, 6) + "01";
        } else if (str.length() == 10) {
            return str.substring(0, 8) + "01";
        } else return "";
    }

    /**
     * 获取本周周一日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getDateOfThisMonday() {
        return DateUtil.format(DateUtil.beginOfWeek(new Date()),"yyyy-MM-dd");
    }

    /**
     * 获取本周周一日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getDateOfThisMonday(String date) {
        return DateUtil.format(DateUtil.beginOfWeek(DateUtil.parse(date, "yyyy-MM-dd")),"yyyy-MM-dd");
    }

    /**
     * 获取下周周一日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getDateOfThisSunday() {
        DateTime dateTime = DateUtil.endOfWeek(new Date());
        return DateUtil.format(dateTime,"yyyy-MM-dd");
    }

    /**
     * 获取下周周一日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getDateOfThisSunday(String date) {
        return DateUtil.format(DateUtil.endOfWeek(DateUtil.parse(date, "yyyy-MM-dd")),"yyyy-MM-dd");
    }

    /**
     * 获取yyyy-MM-dd
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(Date date) {
        return DateUtil.format(date, "yyyy-MM-dd");
    }

    /**
     * 获取yyyy-MM-dd
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String getFormatDate(String date) {
        return date.substring(0, 10);
    }

    /**
     * 获取昨天日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getYesterdayDate() {
        return DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
    }

    /**
     * 获取昨天日期
     *
     * @param str yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getYesterdayDate(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(str, "yyyy-MM-dd"));
        calendar.add(Calendar.DATE, -1);
        return DateUtil.format(calendar.getTime(),"yyyy-MM-dd");
    }

    /**
     * 获取日期
     *
     * @param
     * @return dd
     */
    public static Integer getDate(String str) {
        if (str.contains("-")) {
            return Integer.parseInt(str.substring(8, 10));
        } else {
            return Integer.parseInt(str.substring(6, 8));
        }
    }

    /**
     * 获取月份
     *
     * @param
     * @return MM
     */
    public static Integer getMonth(String str) {
        if (str.contains("-")) {
            return Integer.parseInt(str.substring(5, 7));
        } else {
            return Integer.parseInt(str.substring(4, 6));
        }
    }

    /**
     * 获取年份
     *
     * @param
     * @return MM
     */
    public static Integer getYear(String str) {
        return Integer.parseInt(str.substring(0, 4));
    }

    /**
     * 比较日期(day1早于day2?)
     *
     * @param
     * @return dd
     */
    public static boolean compareDate(String day1, String day2) {
       if (DateUtil.compare(DateUtil.parse(day1,"yyyy-MM-dd"), DateUtil.parse(day2,"yyyy-MM-dd")) == -1){
            return true;
        }else return false;
    }

    /**
     * beforDate
     *
     * @param
     * @return dd
     */
    public static String getBeforeDate(String day1, String day2) {
        return DateUtil.format(DateUtil.offsetDay(DateUtil.parse(day1, "yyyy-MM-dd"),-(int)DateUtil.betweenDay(DateUtil.parse(day1, "yyyy-MM-dd"), DateUtil.parse(day2, "yyyy-MM-dd"), false)),"yyyy-MM-dd");
    }

    public static String getDateBefore(int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(new Date());
        no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
        return TimeDateUtil.getFormatDate(no.getTime());
    }
}
