package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetParkInfo;
import com.zzy.db.entity.carpark.GetRemainingSpaceGanhaizi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 甘海子停车场剩余车位 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface GetRemainingSpaceGanhaiziMapper extends BaseMapper<GetRemainingSpaceGanhaizi> {

    int batchInsert(List<GetRemainingSpaceGanhaizi> getParkInfos);
}
