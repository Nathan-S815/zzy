package com.nuwa.infrastructure.attract.database.travel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.vo.TravelTeamVO;

/**
 *  服务类
 *
 * @author nanhuang @南皇
 * @since 2022-09-14
 */
public interface TravelTeamService extends SuperService<TravelTeam> {
    /**
     * 旅行社团队列表
     * @param teamId
     * @param leadName
     * @param refMch
     * @param teamStatus
     * @param pageSize
     * @param pageNum
     * @param beginDate
     * @param endDate
     * @param userAware
     * @return
     */
    IPage<TravelTeamVO> qryTravelTeamPage(Long teamId, String leadName, String refMch, String teamStatus, Long
            pageSize, Long pageNum, String beginDate, String endDate , UserAware userAware);
}
