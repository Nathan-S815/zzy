package com.nuwa.infrastructure.discovery.database.task.service.impl;

import com.nuwa.infrastructure.discovery.database.task.entity.TaskPrizeType;
import com.nuwa.infrastructure.discovery.database.task.mapper.TaskPrizeTypeMapper;
import com.nuwa.infrastructure.discovery.database.task.service.TaskPrizeTypeService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务权益类型表 服务实现类
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Slf4j
@Service
public class TaskPrizeTypeServiceImpl extends SuperServiceImpl<TaskPrizeTypeMapper, TaskPrizeType> implements TaskPrizeTypeService {

    @Autowired
    private TaskPrizeTypeMapper taskPrizeTypeMapper;

}
