package com.nuwa.infrastructure.ticket.database.operatelog.service.impl;

import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.mapper.OperateLogMapper;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作日志表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-27
 */
@Slf4j
@Service
public class OperateLogServiceImpl extends SuperServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

}
