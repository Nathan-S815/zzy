package com.nuwa.infrastructure.zeus.database.log.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.zeus.database.log.entity.LogRecord;

import org.springframework.stereotype.Repository;


/**
 * 操作日志表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Repository
public interface LogRecordMapper extends SuperMapper<LogRecord> {


}
