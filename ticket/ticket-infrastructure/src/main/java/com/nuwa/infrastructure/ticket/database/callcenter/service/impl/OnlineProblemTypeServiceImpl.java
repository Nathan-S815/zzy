package com.nuwa.infrastructure.ticket.database.callcenter.service.impl;

import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.mapper.OnlineProblemTypeMapper;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
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
public class OnlineProblemTypeServiceImpl extends SuperServiceImpl<OnlineProblemTypeMapper, OnlineProblemType> implements OnlineProblemTypeService {

    @Autowired
    private OnlineProblemTypeMapper onlineProblemTypeMapper;

}
