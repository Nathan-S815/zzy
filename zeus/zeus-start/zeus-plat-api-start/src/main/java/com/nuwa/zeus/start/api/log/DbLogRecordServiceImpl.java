package com.nuwa.zeus.start.api.log;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DbLogRecordServiceImpl
 *
 * @author hy
 * @date 2021/6/4 15:47
 * @since 1.0.0
 */
@Slf4j
@Service
public class DbLogRecordServiceImpl implements ILogRecordService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(LogRecord logRecord) {
        log.info("【logRecord】log={}", logRecord);
        com.nuwa.infrastructure.zeus.database.log.entity.LogRecord logRecordInsert = new com.nuwa.infrastructure.zeus.database.log.entity.LogRecord();
        BeanUtils.copyProperties(logRecord, logRecordInsert);
        logRecordInsert.insert();
    }

    @Override
    public List<LogRecord> queryLog(String bizKey) {
        return new ArrayList<>();
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo) {
        return new ArrayList<>();
    }
}
