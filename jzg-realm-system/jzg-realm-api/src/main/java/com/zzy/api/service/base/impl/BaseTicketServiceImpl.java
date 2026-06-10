package com.zzy.api.service.base.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.SortUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.dao.base.BaseScenicMapper;
import com.zzy.db.dao.reportbase.ReportBaseScenicMapper;
import com.zzy.db.entity.base.BaseScenic;
import com.zzy.db.entity.base.BaseTicket;
import com.zzy.db.dao.base.BaseTicketMapper;
import com.zzy.api.service.base.IBaseTicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.entity.reportbase.ReportBaseScenic;
import com.zzy.db.entity.ticket.ScenicEnterPeople;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-22
 */
@Service
public class BaseTicketServiceImpl extends ServiceImpl<BaseTicketMapper, BaseTicket> implements IBaseTicketService {

    @Autowired
    private BaseTicketMapper baseTicketMapper;
    @Autowired
    private ReportBaseScenicMapper reportBaseScenicMapper;

    @Override
    public List<Map<String, Object>> getTouristDistribution(String startTime, String endTime) {
        List<Map<String, Object>> touristDistributions = null;
        try {
            touristDistributions = baseTicketMapper.getTouristDistribution(startTime, endTime);
        } catch (Exception e) {
            return null;
        }
        Long touristNumber = 0L;
        for (Map<String, Object> touristDistribution : touristDistributions) {
            Long num = Long.parseLong(touristDistribution.get("num").toString());
            touristNumber += num;
        }
        for (Map<String, Object> touristDistribution : touristDistributions) {
            touristDistribution.put("rate", NumberMathUtil.getRate(touristDistribution.get("num"), touristNumber, 0));
        }
        return SortUtil.comparator(touristDistributions, "num", SortUtil.DESC);
    }

    @Override
    public int getAllTouristNumber() {
        List<BaseTicket> baseTickets = baseTicketMapper.getTodayEnterPeople(TimeDateUtil.getFormatDate(new Date()));
        int num = 0;
        for (BaseTicket baseTicket : baseTickets) {
            num += baseTicket.getInPeople();
        }
        return num;
    }

    @Override
    public int getAllTouristNumberByTime(String startTime, String endTime) {
        try {
            return baseTicketMapper.getEnterPeopleByTime(startTime, endTime);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> getScenicInformation() {
        List<BaseTicket> baseTickets = baseTicketMapper.getTodayEnterPeople(TimeDateUtil.getFormatDate(new Date()));
        List result = new ArrayList();
        for (BaseTicket baseTicket : baseTickets) {
            Map<String, Object> map = new HashMap<>();
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.selectById(baseTicket.getScenicId());
            Integer maxBearing = Integer.parseInt(reportBaseScenic.getMaxBearing());
            Integer warningValue = Integer.parseInt(reportBaseScenic.getWarningValue());
            map.put("scenic", reportBaseScenic.getScenicName());
            map.put("rate", maxBearing * warningValue / 100);
            map.put("income", baseTicket.getIncome());
            map.put("inPeople", baseTicket.getInPeople());
            map.put("warningTime",reportBaseScenic.getWarningTime());
            if ((maxBearing * warningValue / 100) < baseTicket.getInPeople()) {
                map.put("warning", true);
            } else {
                map.put("warning", false);
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public Map<String, Object> getAllTouristNumberForecast() {
        List<BaseTicket> baseTickets = baseTicketMapper.getTodayEnterPeople(DateUtil.format(DateUtil.offsetDay(new Date(),-1),"yyyy-MM-dd"));
        Map<String, Object> map = new HashMap<>();
        for (BaseTicket baseTicket : baseTickets) {
            ReportBaseScenic reportBaseScenic = reportBaseScenicMapper.selectById(baseTicket.getScenicId());
            map.put(reportBaseScenic.getScenicName(),baseTicket.getInPeople());
        }
        return map;
    }

    @Override
    public Map<String, Object> getTouristNumberContrastByHistory(String startTimeHistory, String endTimeHistory, String startTime, String endTime) {
        long history = 0;
        try{
            history = baseTicketMapper.getEnterPeopleByTime(startTimeHistory, endTimeHistory);
        }catch (Exception e){}
        long current = 0;
        try {
            current = baseTicketMapper.getEnterPeopleByTime(startTime, endTime);
        }catch (Exception e){}
        Map<String,Object> map = new HashMap<>();
        map.put("history",history);
        map.put("current",current);
        return map;
    }

    @Override
    public Map<String, Object> getTouristNumberContrastByHistoryAndScenic(String startTimeHistory, String endTimeHistory, String startTime, String endTime) {
        List<Map<String, Object>> history = null;
        try{
            history = baseTicketMapper.getEachScenicEnterPeopleByTime(startTimeHistory, endTimeHistory);
        }catch (BindingException e){}
        List<Map<String, Object>> current = null;
        try {
            current = baseTicketMapper.getEachScenicEnterPeopleByTime(startTime, endTime);
        }catch (BindingException e){}
        Map<String,Object> map = new HashMap<>();
        map.put("history",history);
        map.put("current",current);
        return map;
    }

}
