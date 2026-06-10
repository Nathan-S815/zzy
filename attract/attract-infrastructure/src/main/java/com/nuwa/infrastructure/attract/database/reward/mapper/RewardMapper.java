package com.nuwa.infrastructure.attract.database.reward.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 *  Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-21
 */
@Repository
public interface RewardMapper extends SuperMapper<Reward> {


    String selectReawdData(@Param("date") String date);
}
