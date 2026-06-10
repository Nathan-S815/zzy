package com.zzy.task.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@SuppressWarnings("ALL")
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }


    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        ThreadPoolTaskScheduler ex = new ThreadPoolTaskScheduler();
        ex.initialize();
        ex.setPoolSize(200);
        return ex; // 指定线程池大小
    }

}