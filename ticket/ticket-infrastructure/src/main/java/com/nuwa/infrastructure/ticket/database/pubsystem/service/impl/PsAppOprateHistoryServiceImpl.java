package com.nuwa.infrastructure.ticket.database.pubsystem.service.impl;

import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppOprateHistory;
import com.nuwa.infrastructure.ticket.database.pubsystem.mapper.PsAppOprateHistoryMapper;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppOprateHistoryService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 小程序操作历史 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2022-05-06
 */
@Slf4j
@Service
public class PsAppOprateHistoryServiceImpl extends SuperServiceImpl<PsAppOprateHistoryMapper, PsAppOprateHistory> implements PsAppOprateHistoryService {

    @Autowired
    private PsAppOprateHistoryMapper psAppOprateHistoryMapper;

}
