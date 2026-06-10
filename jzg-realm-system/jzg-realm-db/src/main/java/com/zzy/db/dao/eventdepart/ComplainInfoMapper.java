package com.zzy.db.dao.eventdepart;

import com.zzy.db.entity.eventdepart.ComplainInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 小程序投诉表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-15
 */
public interface ComplainInfoMapper extends BaseMapper<ComplainInfo> {

    List<Map<String,Object>> listComplainInfo(@Param("state") Integer state,@Param("createTime") String createTime,@Param("startTime") String startTime,@Param("endTime") String endTime);

    int updateComplainInfoState(@Param("id") Integer id,@Param("state") Integer state);

    int updateReplenish(@Param("id") Integer id,@Param("replenish") String replenish);

}
