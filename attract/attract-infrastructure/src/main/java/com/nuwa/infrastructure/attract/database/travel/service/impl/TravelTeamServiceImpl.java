package com.nuwa.infrastructure.attract.database.travel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeam;
import com.nuwa.infrastructure.attract.database.travel.mapper.TravelTeamMapper;
import com.nuwa.infrastructure.attract.database.travel.service.TravelTeamService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.vo.TravelTeamVO;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-14
 */
@Slf4j
@Service
public class TravelTeamServiceImpl extends SuperServiceImpl<TravelTeamMapper, TravelTeam> implements TravelTeamService {

    @Autowired
    private TravelTeamMapper travelTeamMapper;

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
    @Override
    public IPage<TravelTeamVO> qryTravelTeamPage(Long teamId, String leadName, String refMch, String teamStatus, Long
            pageSize, Long pageNum, String beginDate, String endDate, UserAware userAware) {
        Long userId = userAware.getUserId();

        if (StringUtils.isNotBlank(beginDate)) {
            beginDate = beginDate + " 00:00:00";
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate = endDate + " 23:59:59";
        }

        Page<TravelTeamVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }
        IPage<TravelTeamVO> record = travelTeamMapper.qryTravelTeamPage(page, teamId, leadName, refMch, teamStatus, beginDate, endDate, userId);
        return record;
    }
}
