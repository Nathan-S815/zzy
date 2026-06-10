package com.nuwa.infrastructure.attract.database.travel.service.impl;

import com.nuwa.infrastructure.attract.database.travel.entity.TeamCustomerRef;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.service.TeamCustomerRefService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 团队-客户关联表 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Slf4j
@Service
public class TeamCustomerRefServiceImpl extends SuperServiceImpl<TeamCustomerRefMapper, TeamCustomerRef> implements TeamCustomerRefService {

    @Autowired
    private TeamCustomerRefMapper teamCustomerRefMapper;

}
