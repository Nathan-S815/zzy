package com.zzy.api.service.tourist.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.api.service.tourist.ITouristSourceScenicDayService;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.core.utils.SortUtil;
import com.zzy.db.dao.base.BaseTicketMapper;
import com.zzy.db.dao.tourist.TouristSourceScenicDayMapper;
import com.zzy.db.entity.tourist.TouristSourceScenicDay;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * <p>
 * 景区游客来源地市分析(日) 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
@Service
public class TouristSourceScenicDayServiceImpl extends ServiceImpl<TouristSourceScenicDayMapper, TouristSourceScenicDay> implements ITouristSourceScenicDayService {

    @Autowired
    private TouristSourceScenicDayMapper touristSourceScenicDayMapper;
    @Autowired
    private BaseTicketMapper baseTicketMapper;

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
            touristDistribution.put("rate", NumberMathUtil.getRate(touristDistribution.get("num"), touristNumber,0));
        }
        return SortUtil.comparator(touristDistributions,"num",SortUtil.DESC);
    }

    @Override
    public List<Map<String, Object>> getTouristFromByScenic(String startTime, String endTime, String scenicName) {
        Long allTouristNumberByScenic = 0L;
        List<Map<String, Object>> touristFroms = null;
        if ("整体".equals(scenicName)) {
            try {
                allTouristNumberByScenic = touristSourceScenicDayMapper.getAllTouristNumber(startTime, endTime);
                touristFroms = touristSourceScenicDayMapper.getAllTouristFrom(startTime, endTime);
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                allTouristNumberByScenic = touristSourceScenicDayMapper.getAllTouristNumberByScenic(startTime, endTime, scenicName);
                touristFroms = touristSourceScenicDayMapper.getTouristFromByScenic(startTime, endTime, scenicName);
            } catch (Exception e) {
                return null;
            }
        }
        for (Map<String, Object> touristFrom : touristFroms) {
            if ("四川".equals(touristFrom.get("province_name"))) {
                touristFrom.put("rate", NumberMathUtil.getRateDown(touristFrom.get("num"), allTouristNumberByScenic,0));
            } else {
                touristFrom.put("rate", NumberMathUtil.getRate(touristFrom.get("num"), allTouristNumberByScenic,0));
            }
        }
        return SortUtil.comparator(touristFroms,"rate",SortUtil.DESC);
    }

    @Override
    public List<Map<String, Object>> getTouristFromByScenicCity(String startTime, String endTime, String scenicName) {
        List<Map<String, Object>> touristFroms = touristSourceScenicDayMapper.getTouristFromByScenicCity(startTime, endTime, scenicName);

        return SortUtil.comparator(touristFroms,"num",SortUtil.DESC);
    }

    @Override
    public List<Map<String, Object>> getTouristNumberContrast(String startTime, String endTime) {
        List<Map<String, Object>> touristNumberByDates = null;
        try {
            touristNumberByDates = touristSourceScenicDayMapper.getTouristNumberContrast(startTime, endTime);
        } catch (Exception e) {
            return null;
        }
        Map<String, Object> first = new HashMap<>();
        first.put("week", "第1周");
        first.put("num", 0);
        Map<String, Object> second = new HashMap<>();
        second.put("week", "第2周");
        second.put("num", 0);
        Map<String, Object> third = new HashMap<>();
        third.put("week", "第3周");
        third.put("num", 0);
        Map<String, Object> forth = new HashMap<>();
        forth.put("week", "第4周");
        forth.put("num", 0);
        List list = new ArrayList();
        for (Map<String, Object> touristNumberByDate : touristNumberByDates) {
            Integer date = TimeDateUtil.getDate(touristNumberByDate.get("date").toString());
            if (date > 0 && date <= 7) {
                int num = Integer.parseInt(first.get("num").toString());
                num += Integer.parseInt(touristNumberByDate.get("num").toString());
                first.put("num", num);
            } else if (date > 7 && date <= 14) {
                int num = Integer.parseInt(second.get("num").toString());
                num += Integer.parseInt(touristNumberByDate.get("num").toString());
                second.put("num", num);
            } else if (date > 14 && date <= 22) {
                int num = Integer.parseInt(third.get("num").toString());
                num += Integer.parseInt(touristNumberByDate.get("num").toString());
                third.put("num", num);
            } else if (date > 22 && date <= 31) {
                int num = Integer.parseInt(forth.get("num").toString());
                num += Integer.parseInt(touristNumberByDate.get("num").toString());
                forth.put("num", num);
            }
        }
        if (Integer.parseInt(first.get("num").toString()) != 0) {
            list.add(first);
        }
        if (Integer.parseInt(second.get("num").toString()) != 0) {
            list.add(second);
        }
        if (Integer.parseInt(third.get("num").toString()) != 0) {
            list.add(third);
        }
        if (Integer.parseInt(forth.get("num").toString()) != 0) {
            list.add(forth);
        }
        return list;
    }

    @Override
    public Long getAllTouristNumber(String startTime, String endTime) {
        long allTouristNumber = 0;
        try {
            allTouristNumber = touristSourceScenicDayMapper.getAllTouristNumber(startTime, endTime);
        } catch (Exception e) {
            return null;
        }
        return allTouristNumber;
    }

    @Override
    public Map<String, Object> getTouristNumberContrastByHistory(String startTimeHistory, String endTimeHistory, String startTime, String endTime) {
        long history = 0;
        try{
            history = touristSourceScenicDayMapper.getAllTouristNumber(startTimeHistory, endTimeHistory);
        }catch (BindingException e){}
        long current = 0;
        try {
            current = touristSourceScenicDayMapper.getAllTouristNumber(startTime, endTime);
        }catch (BindingException e){}
        Map<String,Object> map = new HashMap<>();
        map.put("history",history);
        map.put("current",current);
        return map;
    }

    @Override
    public Map<String, Object> getTouristNumberContrastByHistoryAndScenic(String startTimeHistory, String endTimeHistory, String startTime, String endTime) {
        List<Map<String, Object>> history = null;
        try{
            history = touristSourceScenicDayMapper.getScenicTouristNumber(startTimeHistory, endTimeHistory);
        }catch (BindingException e){}
        List<Map<String, Object>> current = null;
        try {
            current = touristSourceScenicDayMapper.getScenicTouristNumber(startTime, endTime);
        }catch (BindingException e){}
        Map<String,Object> map = new HashMap<>();
        map.put("history",history);
        map.put("current",current);
        return map;
    }
}
