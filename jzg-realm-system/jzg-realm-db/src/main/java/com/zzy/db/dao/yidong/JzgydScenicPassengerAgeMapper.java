package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydScenicConsumption;
import com.zzy.db.entity.yidong.JzgydScenicPassengerAge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县及景区客流年龄数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydScenicPassengerAgeMapper extends BaseMapper<JzgydScenicPassengerAge> {

    List<JzgydScenicPassengerAge> getAgeDistribution(@Param("startTime")String startTime,@Param("endTime")String endTime);

    int batchInsert(List<JzgydScenicPassengerAge> list);

}
