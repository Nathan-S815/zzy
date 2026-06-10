package com.nuwa.attract.start.api.log;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
