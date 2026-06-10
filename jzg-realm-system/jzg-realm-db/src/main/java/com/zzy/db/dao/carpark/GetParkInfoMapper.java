package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetParkInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 停车场信息 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
public interface GetParkInfoMapper extends BaseMapper<GetParkInfo> {

    int batchInsert(List<GetParkInfo> getParkInfos);
}
