package com.nuwa.attract.start.api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {


    @Bean
    public ExecutorService executorService(
            @Value("${spring.task.execution.pool.core-size}") Integer corePoolSize,
            @Value("${spring.task.execution.pool.max-size}") Integer maximumPoolSize,
            @Value("${spring.task.execution.pool.keep-alive}") Integer keepAliveTime,
            @Value("${spring.task.execution.pool.queue-capacity}") Integer blockQueueSize
    ){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(blockQueueSize));
    }
}
