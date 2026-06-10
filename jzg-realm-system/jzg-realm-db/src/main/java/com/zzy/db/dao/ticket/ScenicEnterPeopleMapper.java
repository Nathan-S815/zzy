package com.zzy.db.dao.ticket;

import com.zzy.db.entity.ticket.ScenicEnterPeople;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 景区当日入园人数表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
public interface ScenicEnterPeopleMapper extends BaseMapper<ScenicEnterPeople> {

    Integer batchInsert(List<ScenicEnterPeople> scenicEnterPeople);

    ScenicEnterPeople selectOneByInfo(ScenicEnterPeople gr);

    public List<ScenicEnterPeople> getLatestScenicEnterPeople();

    public int insertScenicEnterPeople(ScenicEnterPeople scenicEnterPeople);

    public int getAllTouristNumberByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

}
