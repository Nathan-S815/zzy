package com.zzy.db.dao.hotmap;

import com.zzy.db.entity.hotmap.GetAbjzgHourLocalData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.yidong.JzgydCityPassengerSource;

import java.util.List;

/**
 * <p>
 * 按小时指定查询时间，反馈阿坝九寨沟县等区域小时客流数据 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface GetAbjzgHourLocalDataMapper extends BaseMapper<GetAbjzgHourLocalData> {

    int batchInsert(List<GetAbjzgHourLocalData> list);
}
