package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetRemainingSpaceGanhaizi;
import com.zzy.db.entity.carpark.GetRemainingSpaceHGanhaizi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 停车场剩余车位(历史) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-03
 */
public interface GetRemainingSpaceHGanhaiziMapper extends BaseMapper<GetRemainingSpaceHGanhaizi> {

    int batchInsert(List<GetRemainingSpaceHGanhaizi> getParkInfos);
}
