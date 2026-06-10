package com.zzy.api.service.ticket;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.ticket.ScenicEnterPeople;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区当日入园人数表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
public interface IScenicEnterPeopleService extends IService<ScenicEnterPeople> {

    public List<Map<String,Object>> getScenicInformation();

    public int getAllTouristNumber();

    public int getAllTouristNumberByTime(String startTime,String endTime);
}
