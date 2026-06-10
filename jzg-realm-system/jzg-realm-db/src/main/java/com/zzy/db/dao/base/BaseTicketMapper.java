package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.carpark.CarGps;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-22
 */
public interface BaseTicketMapper extends BaseMapper<BaseTicket> {

    int batchInsert(List<BaseTicket> getBaseTicket);

    public List<Map<String,Object>> getTouristDistribution(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<BaseTicket> getTodayEnterPeople(@Param("time") String time);

    Integer getEnterPeopleByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String, Object>> getEachScenicEnterPeopleByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
