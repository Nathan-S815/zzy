package com.nuwa.infrastructure.attract.database.teamuserref.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.client.attract.dto.clientobject.travel.qry.MchTravelTeamPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.infrastructure.attract.database.teamuserref.mapper.TeamUserRefMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.service.TeamUserRefService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.vo.MchTravelTeamPageVO;
import com.nuwa.infrastructure.vo.TravelTeamVO;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-11-08
 */
@Slf4j
@Service
public class TeamUserRefServiceImpl extends SuperServiceImpl<TeamUserRefMapper, TeamUserRef> implements TeamUserRefService {

    @Autowired
    private TeamUserRefMapper teamUserRefMapper;

}
