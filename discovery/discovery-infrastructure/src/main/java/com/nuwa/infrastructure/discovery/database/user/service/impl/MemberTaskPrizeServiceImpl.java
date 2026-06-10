package com.nuwa.infrastructure.discovery.database.user.service.impl;

import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.infrastructure.discovery.database.user.mapper.MemberTaskPrizeMapper;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 达人任务权益表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
@Slf4j
@Service
public class MemberTaskPrizeServiceImpl extends SuperServiceImpl<MemberTaskPrizeMapper, MemberTaskPrize> implements MemberTaskPrizeService {

    @Autowired
    private MemberTaskPrizeMapper memberTaskPrizeMapper;

}
