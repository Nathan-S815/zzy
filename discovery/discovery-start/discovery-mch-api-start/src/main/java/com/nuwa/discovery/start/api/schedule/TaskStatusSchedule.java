package com.nuwa.discovery.start.api.schedule;

import cn.hutool.core.date.StopWatch;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.task.service.ScenicTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Component
@Slf4j
public class TaskStatusSchedule {

    @Autowired
    private ScenicTaskService scenicTaskService;

    @Scheduled(fixedDelay = 1000 * 10)
    public void updateTaskStatus() {
        StopWatch stopWatch = new StopWatch("updateTaskStatus");
        try {
            log.info(">>>> updateTaskStatus");
            stopWatch.start();
            List<ScenicTask> taskList = scenicTaskService.lambdaQuery()
                    .eq(ScenicTask::getValidityMode, 1)
                    .eq(ScenicTask::getStatus, 2)
                    .lt(ScenicTask::getEndDate, new Date()).last("limit 1")
                    .list();
            taskList.forEach(x -> {
                boolean update = scenicTaskService.lambdaUpdate()
                        .set(ScenicTask::getStatus, 3)
                        .set(ScenicTask::getLastUpdateTime, new Date())
                        .eq(ScenicTask::getId, x.getId()).update();
                if (update) {
                    log.info("update ScenicTask[{}] status to 4", x.getId());
                }
            });
            stopWatch.stop();
            log.info("<<<< updateTaskStatus time:[{} ms]", stopWatch.getTotalTimeSeconds());
        } catch (Exception ex) {
            log.error("updateTaskStatus 执行失败", ex);
        }
    }
}
