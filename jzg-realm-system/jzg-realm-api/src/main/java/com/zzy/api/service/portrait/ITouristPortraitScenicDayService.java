package com.zzy.api.service.portrait;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.portrait.TouristPortraitScenicDay;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区旅客画像-游客性别、年龄统计(日) 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-26
 */
public interface ITouristPortraitScenicDayService extends IService<TouristPortraitScenicDay> {

    List<Map<String,Object>> getTouristPortraitSexCount(Long accTime);

    List<Map<String,Object>> getAgeDistribution( Long accTime,String sceneId);
}
