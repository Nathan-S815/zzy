package com.zzy.client.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzy.client.api.TicketClient;
import com.zzy.client.api.YidongClient;
import com.zzy.client.common.ANameEnum;
import com.zzy.core.utils.JsonUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.BaseTicket;

import com.zzy.db.entity.yidong.JzgydScenicConsumption;
import com.zzy.db.entity.yidong.JzgydScenicPassengerAge;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ABaWenLvClientService {
    public static void main(String[] args) {
        Object o = null;
        o = getSgnsTicket();
        System.out.println(JSON.toJSONString(o));
    }


    public static List<BaseTicket> getJzgTicket() {
        List<BaseTicket> list = new ArrayList<>();
        Integer jzgTicket = TicketClient.getJzgTicket();
        BaseTicket baseTicket = new BaseTicket();
        baseTicket.setScenicId(1);
        baseTicket.setScenicName("九寨沟");
        baseTicket.setInPeople(jzgTicket);
        baseTicket.setIncome(jzgTicket * 256);
        baseTicket.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        baseTicket.setReportTime(TimeDateUtil.getFormatDate(new Date()));
        list.add(baseTicket);
        return list;
    }

    public static List<BaseTicket> getHlTicket() {
        List<BaseTicket> list = new ArrayList<>();
        Integer hlTicket = TicketClient.getHlTicket();
        BaseTicket baseTicket = new BaseTicket();
        baseTicket.setScenicId(7);
        baseTicket.setScenicName("黄龙");
        baseTicket.setInPeople(hlTicket);
        baseTicket.setIncome(hlTicket * 170);
        baseTicket.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        baseTicket.setReportTime(TimeDateUtil.getFormatDate(new Date()));
        list.add(baseTicket);
        return list;
    }

    public static List<BaseTicket> getSgnsTicket() {
        List<BaseTicket> list = new ArrayList<>();
        Integer sgnsTicket = TicketClient.getSgnsTicket();
//        JSONArray json = JsonUtil.strToJSONArray("["+sgnsTicket+"]");
//        Integer surplusNumber = 0;
//        for (Object o : json) {
//            surplusNumber += Integer.parseInt(JsonUtil.strToJSONObject(JSON.toJSONString(o)).getString("surplusNumber"));
//        }
        BaseTicket baseTicket = new BaseTicket();
        baseTicket.setScenicId(8);
        baseTicket.setScenicName("四姑娘山");
        baseTicket.setInPeople(sgnsTicket);
        baseTicket.setIncome(sgnsTicket*100);
        baseTicket.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        baseTicket.setReportTime(TimeDateUtil.getFormatDate(new Date()));
        list.add(baseTicket);
        return list;
    }

    public static List<BaseTicket> getDgbcTicket() {
        List<BaseTicket> list = new ArrayList<>();
        Integer dgbcTicket = TicketClient.getDgbcTicket();
//        JSONObject json = JsonUtil.strToJSONObject(dgbcTicket);
//        Integer surplusNumber = Integer.parseInt(json.getString("surplusNumber"));
        BaseTicket baseTicket = new BaseTicket();
        baseTicket.setScenicId(9);
        baseTicket.setScenicName("达古冰川");
        baseTicket.setInPeople(dgbcTicket);
        baseTicket.setIncome(dgbcTicket * 370);
        baseTicket.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        baseTicket.setReportTime(TimeDateUtil.getFormatDate(new Date()));
        list.add(baseTicket);
        return list;
    }

    public static List<BaseTicket> getSXCTicket() {
        List<BaseTicket> list = new ArrayList<>();
        String sxcTicket = TicketClient.getSXCTicket();
        JSONObject json = JsonUtil.strToJSONObject(sxcTicket);
        Integer count = Integer.parseInt(json.getString("count"));
        Integer sales = Integer.parseInt(json.getString("sales"));
        BaseTicket baseTicket = new BaseTicket();
        baseTicket.setScenicId(2);
        baseTicket.setScenicName("嫩恩桑措(神仙池)");
        baseTicket.setInPeople(count);
        baseTicket.setIncome(sales);
        baseTicket.setCreateTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        baseTicket.setReportTime(TimeDateUtil.getFormatDate(new Date()));
        list.add(baseTicket);
        return list;
    }
}
