package com.nuwa.infrastructure.attract.database.travel.service;

import java.util.List;

import com.nuwa.infrastructure.attract.database.travel.entity.TeamUserCustomerRef;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeamTripInfo;

/**
 * 服务类
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
public interface TeamUserCustomerRefService extends SuperService<TeamUserCustomerRef> {

    /**
     * 获取团队游玩详情
     * @param teamId 团队id
     * @return TravelTeamTripInfo
     */
    List<TravelTeamTripInfo> getTravelTeamTripInfo(Long teamId);
}
