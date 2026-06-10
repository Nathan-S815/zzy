package com.zzy.client.api;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asiainfo.crypt.MD5Util;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.ticket.ABaWenLvTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Slf4j
public class TicketClient {
    public static void main(String[] args) {
        System.out.println(getSgnsTicket());
    }


    public static Integer getJzgTicket() {
        String url = "http://c.abatour.com/kclistData/futureData_4.html";
        String result = HttpUtil.get(url);
        String substring = result.substring(16, result.length() - 2);
        JSONObject jsonObj = JSONObject.parseObject(substring);
        String dateList = jsonObj.getString("dateList");
        List<ABaWenLvTicket> aBaWenLvTickets = JSONObject.parseArray(dateList, ABaWenLvTicket.class);
        Integer total = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(0).getSurplusNumber();
        Integer surplus = 0;
        for (ABaWenLvTicket aBaWenLvTicket : aBaWenLvTickets) {
            if (aBaWenLvTicket.getType() == 0) {
                surplus = aBaWenLvTicket.getNumberList().get(0).getSurplusNumber();
                break;
            }
        }
        return total - surplus;

//        String quStr = result.substring(result.indexOf("(") + 1, result.indexOf(")")).trim();
//        String quStr1 = quStr.substring(quStr.indexOf(TimeDateUtil.getFormatDate(new Date())), quStr.indexOf("\"type\":0}"));
//        String quStr2 = quStr1.substring(quStr1.indexOf("{"), quStr1.lastIndexOf("}") + 1);
//        return quStr2;
    }

    public static Integer getHlTicket() {
        String url = "http://c.abatour.com/kclistData/futureData_1.html";
        String result = HttpUtil.get(url);
        String substring = result.substring(16, result.length() - 2);
        JSONObject jsonObj = JSONObject.parseObject(substring);
        String dateList = jsonObj.getString("dateList");
        List<ABaWenLvTicket> aBaWenLvTickets = JSONObject.parseArray(dateList, ABaWenLvTicket.class);
        Integer total = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(0).getSurplusNumber();
        Integer surplus = 0;
        for (ABaWenLvTicket aBaWenLvTicket : aBaWenLvTickets) {
            if (aBaWenLvTicket.getType() == 0) {
                surplus = aBaWenLvTicket.getNumberList().get(0).getSurplusNumber();
                break;
            }
        }
        return total - surplus;
//        String quStr = result.substring(result.indexOf("(") + 1, result.indexOf(")")).trim();
//        String quStr1 = quStr.substring(quStr.indexOf(TimeDateUtil.getFormatDate(new Date())), quStr.indexOf("\"type\":0}"));
//        String quStr2 = quStr1.substring(quStr1.indexOf("{"), quStr1.lastIndexOf("}") + 1);
//        return quStr2;

    }

    public static Integer getSgnsTicket() {
        String url = "http://c.abatour.com/kclistData/futureData_6.html";
        String result = HttpUtil.get(url);
        String substring = result.substring(16, result.length() - 2);
        JSONObject jsonObj = JSONObject.parseObject(substring);
        String dateList = jsonObj.getString("dateList");
        List<ABaWenLvTicket> aBaWenLvTickets = JSONObject.parseArray(dateList, ABaWenLvTicket.class);
        Integer total1 = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(0).getSurplusNumber();
        Integer total2 = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(1).getSurplusNumber();
        Integer total3 = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(2).getSurplusNumber();
        Integer surplus1 = 0;
        Integer surplus2 = 0;
        Integer surplus3 = 0;
        for (ABaWenLvTicket aBaWenLvTicket : aBaWenLvTickets) {
            if (aBaWenLvTicket.getType() == 0) {
                surplus1 = aBaWenLvTicket.getNumberList().get(0).getSurplusNumber();
                surplus2 = aBaWenLvTicket.getNumberList().get(1).getSurplusNumber();
                surplus3 = aBaWenLvTicket.getNumberList().get(2).getSurplusNumber();
                break;
            }
        }
        return total1 + total2 + total3 - surplus1 - surplus2 - surplus3;
//        String quStr = result.substring(result.indexOf("(") + 1, result.indexOf(")")).trim();
//        String quStr1 = quStr.substring(quStr.indexOf(TimeDateUtil.getFormatDate(new Date())), quStr.indexOf("\"type\":0}"));
//        String quStr2 = quStr1.substring(quStr1.indexOf("{"), quStr1.lastIndexOf("}") + 1);
//        return quStr2;
    }

    public static Integer getDgbcTicket() {
        String url = "http://c.abatour.com/kclistData/futureData_3.html";
        String result = HttpUtil.get(url);
        String substring = result.substring(16, result.length() - 2);
        JSONObject jsonObj = JSONObject.parseObject(substring);
        String dateList = jsonObj.getString("dateList");
        List<ABaWenLvTicket> aBaWenLvTickets = JSONObject.parseArray(dateList, ABaWenLvTicket.class);
        Integer total = aBaWenLvTickets.get(aBaWenLvTickets.size() - 1).getNumberList().get(0).getSurplusNumber();
        Integer surplus = 0;
        for (ABaWenLvTicket aBaWenLvTicket : aBaWenLvTickets) {
            if (aBaWenLvTicket.getType() == 0) {
                surplus = aBaWenLvTicket.getNumberList().get(0).getSurplusNumber();
                break;
            }
        }
        return total - surplus;
//        String quStr = result.substring(result.indexOf("(") + 1, result.indexOf(")")).trim();
//        String quStr1 = quStr.substring(quStr.indexOf(TimeDateUtil.getFormatDate(new Date())), quStr.indexOf("\"type\":0}"));
//        String quStr2 = quStr1.substring(quStr1.indexOf("{"), quStr1.lastIndexOf("}") + 1);
//        return quStr2;
    }

    public static String getSXCTicket() {
        String url = "http://182.130.51.2:8880/ota/data/board/index?app_key=09014880053&sign=4cfd1d06e48cdc7449a011aa7ec27aaf";
//        String apiKey = "09014880053";
//        String apiSecret = "9851471f50c6e379aa228a0f591267c8";
//        Long time = new Date().getTime()/1000;
//        String sign = DigestUtils.md5DigestAsHex(("api_key=" + apiKey + "&time=" + time+apiSecret).getBytes());
        String str = HttpUtil.get(url);
        String result = str.substring(16, str.length() - 1);
        return result;
    }

}
