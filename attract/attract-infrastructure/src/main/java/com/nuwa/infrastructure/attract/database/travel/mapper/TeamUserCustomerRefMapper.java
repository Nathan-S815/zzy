package com.nuwa.infrastructure.attract.database.travel.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.travel.entity.TeamUserCustomerRef;

import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeamTripInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 *  Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Repository
public interface TeamUserCustomerRefMapper extends SuperMapper<TeamUserCustomerRef> {
    /**
     * 获取团队游玩详情
     *
     * @param teamId 团队id
     * @return TravelTeamTripInfo
     */
    List<TravelTeamTripInfo> getTravelTeamTripInfo(Long teamId);

   // void updateUserteamCustomerStatus(@Param("teamId")Long teamId, @Param("status")Integer status);

    List<Long> qryTeamUserCustomerRef(@Param("teamId") Long teamId,
                                                     @Param("travelDate") Date travelDate,
                                                     @Param("userId")Long userId);
}
