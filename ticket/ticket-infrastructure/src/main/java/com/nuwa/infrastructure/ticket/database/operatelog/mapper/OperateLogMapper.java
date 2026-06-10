package com.nuwa.infrastructure.ticket.database.operatelog.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;

import org.springframework.stereotype.Repository;


/**
 * 操作日志表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-12-27
 */
@Repository
public interface OperateLogMapper extends SuperMapper<OperateLog> {


}
