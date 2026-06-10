package com.zzy.db.dao.carpark;

import com.zzy.db.entity.carpark.GetRemainingSpaceH;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 停车场剩余车位(历史) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-24
 */
public interface GetRemainingSpaceHMapper extends BaseMapper<GetRemainingSpaceH> {
    public List<Map<String,Object>> getAllScenicPark();

    public List<Map<String,Object>> getAllScenicParkScreen();

    public List<Map<String,Object>> getParkByScenic(@Param("scenicName")String scenicName);

    int batchInsert(List<GetRemainingSpaceH> getRemainingSpaceHS);

    GetRemainingSpaceH selectOneByInfo(GetRemainingSpaceH gr);
}
