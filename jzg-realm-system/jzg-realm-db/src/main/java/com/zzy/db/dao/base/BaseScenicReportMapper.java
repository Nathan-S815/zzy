package com.zzy.db.dao.base;

;
import com.zzy.db.entity.base.BaseScenicReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 景区人数收入上报表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseScenicReportMapper extends BaseMapper<BaseScenicReport> {
    List<Map<String,Object>> selectPageListBaseScenicReport(@Param("keyword") String keyword);

    Map<String,Object> getInPeopleAndIncome(@Param("userId") String userId,@Param("nowDate") String nowDate);

    Long getIncomeByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String,Object>> getByScenicId(@Param("scenicId") Integer scenicId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    int updateIncome(@Param("income") Integer income);
}
