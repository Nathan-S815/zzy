package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetRemainingSpace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 停车场剩余车位 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-05-11
 */
public interface GetRemainingSpaceMapper extends BaseMapper<GetRemainingSpace> {

    int batchInsert(List<GetRemainingSpace> getRemainingSpaces);

    int updateCarPark(@Param("id")Integer id , @Param("num") Integer num);
}
