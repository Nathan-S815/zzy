package com.nuwa.infrastructure.discovery.database.task.service.impl;

import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrize;
import com.nuwa.infrastructure.discovery.database.task.mapper.TaskPrizeMapper;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务权益表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Slf4j
@Service
public class TaskPrizeServiceImpl extends SuperServiceImpl<TaskPrizeMapper, TaskPrize> implements TaskPrizeService {

    @Autowired
    private TaskPrizeMapper taskPrizeMapper;

}
