package com.nuwa.infrastructure.discovery.database.task.service.impl;

import com.nuwa.infrastructure.discovery.database.task.entity.TaskMessageSubscribe;
import com.nuwa.infrastructure.discovery.database.task.mapper.TaskMessageSubscribeMapper;
import com.nuwa.infrastructure.discovery.database.task.service.TaskMessageSubscribeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务消息订阅表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-12-01
 */
@Slf4j
@Service
public class TaskMessageSubscribeServiceImpl extends SuperServiceImpl<TaskMessageSubscribeMapper, TaskMessageSubscribe> implements TaskMessageSubscribeService {

    @Autowired
    private TaskMessageSubscribeMapper taskMessageSubscribeMapper;

}
