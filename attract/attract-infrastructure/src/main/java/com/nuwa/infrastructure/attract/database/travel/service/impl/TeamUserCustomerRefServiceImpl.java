package com.nuwa.infrastructure.attract.database.travel.service.impl;

import java.util.List;

import com.nuwa.infrastructure.attract.database.travel.entity.TeamUserCustomerRef;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeamTripInfo;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamUserCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.service.TeamUserCustomerRefService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Slf4j
@Service
public class TeamUserCustomerRefServiceImpl extends SuperServiceImpl<TeamUserCustomerRefMapper, TeamUserCustomerRef> implements TeamUserCustomerRefService {

    @Autowired
    private TeamUserCustomerRefMapper teamUserCustomerRefMapper;

    /**
     * 获取团队游玩详情
     *
     * @param teamId 团队id
     * @return TravelTeamTripInfo
     */
    @Override
    public List<TravelTeamTripInfo> getTravelTeamTripInfo(Long teamId) {
        return teamUserCustomerRefMapper.getTravelTeamTripInfo(teamId);
    }
}
