package com.nuwa.infrastructure.discovery.database.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.discovery.database.user.vo.MyTaskVO;
import com.nuwa.infrastructure.discovery.database.user.vo.NewTaskVO;

/**
 * 景区任务表 服务类
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
public interface ScenicTaskService extends SuperService<ScenicTask> {

    /**
     * 我发布的任务列表
     * @param pageSize
     * @param pageNum
     * @param userAware
     * @return
     */
    IPage<MyTaskVO> getMyPublishTaskList(Long pageSize, Long pageNum, UserAware userAware);

    IPage<NewTaskVO> getNewestTask(Long pageSize, Long pageNum, String mchId);
}
