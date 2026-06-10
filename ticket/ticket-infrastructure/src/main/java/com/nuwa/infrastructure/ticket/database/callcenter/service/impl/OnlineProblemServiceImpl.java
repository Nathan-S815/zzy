package com.nuwa.infrastructure.ticket.database.callcenter.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.mapper.OnlineProblemMapper;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-05-11
 */
@Slf4j
@Service
public class OnlineProblemServiceImpl extends SuperServiceImpl<OnlineProblemMapper, OnlineProblem> implements OnlineProblemService {

    @Autowired
    private OnlineProblemMapper onlineProblemMapper;

}
