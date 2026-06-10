package com.nuwa.ticket.start.api.log;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OperateLogServiceImpl 操作日志
 *
 * @author hy
 * @date 2021/6/4 15:47
 * @since 1.0.0
 */
@Slf4j
@Service
public class DbOperateLogServiceImpl implements ILogRecordService {

    @Override
    public void record(LogRecord logRecord) {
        log.info("【logRecord】log={}", logRecord);
        OperateLog operateLog = new OperateLog();
        operateLog.setAction(logRecord.getAction());
        operateLog.setBizId(Long.parseLong(logRecord.getBizNo()));
        operateLog.setOperatorUserName(logRecord.getOperator() + "");
        operateLog.setOperatorUserId(0L);
        operateLog.setCreateTime(new Date());
        operateLog.setCategory(logRecord.getCategory());
        operateLog.setDetail(logRecord.getDetail());
        operateLog.setBizKey(logRecord.getBizKey());
        operateLog.insert();
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
