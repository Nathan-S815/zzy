package com.zzy.db.dao.portrait;

import com.zzy.db.entity.portrait.TouristPortraitScenicDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区旅客画像-游客性别、年龄统计(日) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
public interface TouristPortraitScenicDayMapper extends BaseMapper<TouristPortraitScenicDay> {

    List<Map<String,Object>> getTouristPortraitSexCount(@Param("accTime") Long accTime);

    List<Map<String,Object>> getAgeDistribution(@Param("accTime") Long accTime,@Param("sceneId")String sceneId);

}
