package com.zzy.api.service.portrait;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.db.entity.portrait.TouristStayScenicMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区用户逗留时长分析(月) 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-27
 */
public interface ITouristStayScenicMonthService extends IService<TouristStayScenicMonth> {

    List<Map<String,Object>> getTouristStayOutProvince(Long accTime,String sceneId);

    List<Map<String,Object>> getTouristStayInProvince(Long accTime,String sceneId);

    List<Map<String,Object>> getTouristStayInProvinceByScenic(Long accTime);

    List<Map<String,Object>> getTouristStayOutProvinceByScenic(Long accTime);
}
