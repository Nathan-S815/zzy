package com.zzy.db.dao.hotmap;

import com.zzy.db.entity.hotmap.GetAbjzgMinuteLocalData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.yidong.JzgydCityPassengerSource;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县等区域当前人流总数、游客人数和常驻人数 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface GetAbjzgMinuteLocalDataMapper extends BaseMapper<GetAbjzgMinuteLocalData> {

    int batchInsert(List<GetAbjzgMinuteLocalData> list);
}
