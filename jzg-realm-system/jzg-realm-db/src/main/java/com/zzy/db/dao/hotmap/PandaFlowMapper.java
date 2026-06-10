package com.zzy.db.dao.hotmap;

import com.zzy.db.entity.hotmap.PandaFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 熊猫馆客流统计表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-02
 */
public interface PandaFlowMapper extends BaseMapper<PandaFlow> {

      List<Map<String,Object>> getInAndOutPeople(@Param("dayTime") String dayTime);

      int insertPandan(PandaFlow pandaFlow);

      Integer getInPeople(@Param("dayTime") String dayTime);

}
