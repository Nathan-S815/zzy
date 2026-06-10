package com.nuwa.infrastructure.discovery.database.task.service.impl;

import com.nuwa.infrastructure.discovery.database.task.entity.TaskPlatform;
import com.nuwa.infrastructure.discovery.database.task.mapper.TaskPlatformMapper;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPlatformService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务平台表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Slf4j
@Service
public class TaskPlatformServiceImpl extends SuperServiceImpl<TaskPlatformMapper, TaskPlatform> implements TaskPlatformService {

    @Autowired
    private TaskPlatformMapper taskPlatformMapper;

}
