package com.zzy.db.dao.portrait;

import com.zzy.db.entity.portrait.TouristStayScenicMonth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区用户逗留时长分析(月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-27
 */
public interface TouristStayScenicMonthMapper extends BaseMapper<TouristStayScenicMonth> {
    /**
     * 获取省外逗留时长
     */
    List<Map<String,Object>> getTouristStayOutProvince(@Param("accTime") Long accTime,@Param("sceneId")String sceneId);
    /**
     * 获取省内逗留时长
     */
    List<Map<String,Object>> getTouristStayInProvince(@Param("accTime") Long accTime,@Param("sceneId")String sceneId);

    /**
     * 获取各景区省内人数
     * */
    List<Map<String,Object>> getTouristStayInProvinceByScenic(@Param("accTime") Long accTime);

    /**
     * 获取各景区省外人数
     * */
    List<Map<String,Object>> getTouristStayOutProvinceByScenic(@Param("accTime") Long accTime);
}
