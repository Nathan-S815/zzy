package com.zzy.db.dao.hotmap;

import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.yidong.JzgydScenicPassengerGender;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县等区域当前人流热力图 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface GetAbjzgMinutePeopleHotDataMapper extends BaseMapper<GetAbjzgMinutePeopleHotData> {

    int batchInsert(List<GetAbjzgMinutePeopleHotData> list);

    List<Map<String,Object>> getLatest(@Param("time")String time);
}
