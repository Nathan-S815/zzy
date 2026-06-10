package com.zzy.db.dao.tourist;

import com.zzy.db.entity.tourist.TouristSourceScenicDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区游客来源地市分析(日) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
public interface TouristSourceScenicDayMapper extends BaseMapper<TouristSourceScenicDay> {
    public List<Map<String,Object>> getTouristDistribution(@Param("startTime") String startTime,@Param("endTime") String endTime);

    public List<Map<String,Object>> getTouristFromByScenic(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("scenicName") String scenicName);

    public List<Map<String,Object>> getTouristFromByScenicCity(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("scenicName") String scenicName);

    public List<Map<String,Object>> getAllTouristFrom(@Param("startTime") String startTime,@Param("endTime") String endTime);

    public long getAllTouristNumberByScenic(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("scenicName") String scenicName);

    public long getAllTouristNumberByScenicCity(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("scenicName") String scenicName);

    public long getAllTouristNumber(@Param("startTime") String startTime,@Param("endTime") String endTime);

    public List<Map<String,Object>> getScenicTouristNumber(@Param("startTime") String startTime,@Param("endTime") String endTime);

    public List<Map<String,Object>> getTouristNumberContrast(@Param("startTime") String startTime,@Param("endTime") String endTime);



}
