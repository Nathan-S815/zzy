package com.nuwa.infrastructure.discovery.database.task.mapper;

import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskMessageSubscribe;

import org.springframework.stereotype.Repository;


/**
 * 任务消息订阅表 Mapper 接口
 *
 * @author huyonghack@163.com
 * @since 2021-12-01
 */
@Repository
public interface TaskMessageSubscribeMapper extends SuperMapper<TaskMessageSubscribe> {


}
