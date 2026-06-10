package com.nuwa.infrastructure.zeus.database.log.service.impl;

import com.nuwa.infrastructure.zeus.database.log.entity.LogRecord;
import com.nuwa.infrastructure.zeus.database.log.mapper.LogRecordMapper;
import com.nuwa.infrastructure.zeus.database.log.service.LogRecordService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作日志表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-06-04
 */
@Slf4j
@Service
public class LogRecordServiceImpl extends SuperServiceImpl<LogRecordMapper, LogRecord> implements LogRecordService {

    @Autowired
    private LogRecordMapper logRecordMapper;

}
